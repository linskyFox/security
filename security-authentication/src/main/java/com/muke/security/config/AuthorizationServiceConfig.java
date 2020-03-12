package com.muke.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.muke.security.endpoint.PhoneSMSAuthenticationProvider;
import com.muke.security.endpoint.PhoneSMSCustomTokenGranter;
import com.muke.security.service.CustomUserDetailsService;
import com.muke.security.service.PhoneCodeService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServiceConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorizationCodeServices authorizationCodeService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PhoneCodeService phoneCodeService;

	@Bean
	public AuthorizationCodeServices authorizationCodeService(DataSource dataSource) {
		return new JdbcAuthorizationCodeServices(dataSource);
	}

	private ClientDetailsService clientDetailsService(DataSource dataSource) {
		ClientDetailsService service = new JdbcClientDetailsService(dataSource);
		((JdbcClientDetailsService) service).setPasswordEncoder(passwordEncoder);
		return service;
	}

	@Bean
	public AuthorizationServerTokenServices tokenServices() {
		DefaultTokenServices services = new DefaultTokenServices();
		services.setClientDetailsService(clientDetailsService(dataSource));
		services.setSupportRefreshToken(true);
		services.setTokenStore(tokenStore);

		services.setAccessTokenValiditySeconds(7200);
		services.setRefreshTokenValiditySeconds(259200);
		return services;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService(dataSource));
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).authorizationCodeServices(authorizationCodeService)
				.tokenServices(tokenServices()).allowedTokenEndpointRequestMethods(HttpMethod.POST);
		List<TokenGranter> tokenGranters = getTokenGranters(endpoints.getTokenServices(),
				endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());
		tokenGranters.add(endpoints.getTokenGranter());
		endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));

	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll").allowFormAuthenticationForClients();
	}

	private List<TokenGranter> getTokenGranters(AuthorizationServerTokenServices tokenServices,
			ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		return new ArrayList<>(Arrays.asList(new PhoneSMSCustomTokenGranter(new ProviderManager(getProvider(), null),
				tokenServices, clientDetailsService, requestFactory, userDetailsService)));
	}

	private List<AuthenticationProvider> getProvider() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		PhoneSMSAuthenticationProvider provider = new PhoneSMSAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPhoneCodeService(phoneCodeService);
		providers.add(provider);
		return providers;
	}
}
