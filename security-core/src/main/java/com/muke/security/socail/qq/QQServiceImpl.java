package com.muke.security.socail.qq;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQServiceImpl extends AbstractOAuth2ApiBinding implements QQService {

	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

	private final String appId;
	private String openId;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public QQServiceImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
		String url = String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
	}

	@Override
	public QQUserInfo getUserInfo() {

		String url = String.format(URL_GET_USERINFO, appId, openId);
		String result = getRestTemplate().getForObject(url, String.class);
		QQUserInfo userInfo;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

}
