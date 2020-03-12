package com.muke.security.exception;

import org.springframework.security.core.AuthenticationException;

public class PhoneSMSAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneSMSAuthenticationException(String msg) {
		super(msg);
		
	}

}
