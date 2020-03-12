package com.muke.security.endpoint;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public abstract class AbstractCustomTokenGranter extends AbstractTokenGranter {

	private final AuthenticationManager authenticationManager;

	protected AbstractCustomTokenGranter(AuthenticationManager authenticationManager,
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory, String grantType) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
		Authentication authentication = null;
		try {
			authentication = this.getAuthentication(parameters);
			authentication = authenticationManager.authenticate(authentication);
		} catch (AuthenticationException ase) {
			throw new InvalidGrantException(ase.getMessage());
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, authentication);
	}

	protected abstract Authentication getAuthentication(Map<String, String> parameters);
}