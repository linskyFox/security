package com.muke.security.endpoint;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class PhoneSMSAuthentication extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Object principal;
	private String credentials;

	public PhoneSMSAuthentication(Object principal, String credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	public PhoneSMSAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = null;
		super.setAuthenticated(true); // must use super, as we override
	}

	public String getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}
}
