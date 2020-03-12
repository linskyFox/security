package com.muke.security.socail.qq;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class QQConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
        System.err.println("-------------");
		return null;
	}

}
