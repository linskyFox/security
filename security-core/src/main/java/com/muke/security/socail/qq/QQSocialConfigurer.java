package com.muke.security.socail.qq;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class QQSocialConfigurer extends SpringSocialConfigurer {

	private String filterProcessesUrl;

	QQSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);

		return (T) filter;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	
	
}