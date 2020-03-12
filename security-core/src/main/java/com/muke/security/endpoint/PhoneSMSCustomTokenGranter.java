package com.muke.security.endpoint;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.muke.security.service.CustomUserDetailsService;

public class PhoneSMSCustomTokenGranter extends AbstractCustomTokenGranter {

	public static final String GRANT_TYPE = "sms_code";

	public PhoneSMSCustomTokenGranter(AuthenticationManager authenticationManager,
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory, CustomUserDetailsService userDetailsService) {

		super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);

	}

	@Override
	protected Authentication getAuthentication(Map<String, String> parameters) {
		final String phone = parameters.get("phone");
		final String code = parameters.get("code");
		return new PhoneSMSAuthentication(phone, code);
	}

}
