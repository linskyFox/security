package com.muke.security.endpoint;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.muke.security.entity.User;
import com.muke.security.exception.PhoneSMSAuthenticationException;
import com.muke.security.service.CustomUserDetailsService;
import com.muke.security.service.PhoneCodeService;

public class PhoneSMSAuthenticationProvider implements AuthenticationProvider {

	private CustomUserDetailsService userDetailsService;

	private PhoneCodeService phoneCodeService;

	public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public void setPhoneCodeService(PhoneCodeService phoneCodeService) {
		this.phoneCodeService = phoneCodeService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			final String phone = (String) authentication.getPrincipal();
			final String code = (String) authentication.getCredentials();
			User user = userDetailsService.loadUserByPhone(phone);
			if (!phoneCodeService.ververify(phone, code)) {
				throw new PhoneSMSAuthenticationException("code error");
			}
			PhoneSMSAuthentication phoneSmsAuthentication = new PhoneSMSAuthentication(user, user.getPassword());
			phoneSmsAuthentication.setDetails(authentication.getDetails());
			return phoneSmsAuthentication;
		} catch (Exception e) {
			throw new PhoneSMSAuthenticationException(e.getMessage());
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (PhoneSMSAuthentication.class.isAssignableFrom(authentication));
	}

}
