package com.muke.security.socail.qq;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "muke.security")
public class SecurityProperties {

	private final QQSocialProperties social = new QQSocialProperties();

	public QQSocialProperties getSocial() {
		return social;
	}

}
