package com.muke.security.socail.qq;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQService> {

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
		// TODO Auto-generated constructor stub
	}

}
