package com.muke.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import com.muke.security.entity.User;
import com.muke.security.mapper.PhoneCodeMapper;
import com.muke.security.mapper.UserMapper;
import com.muke.security.service.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	PhoneCodeMapper phoneCodeMapper;


	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userMapper.findUserByName(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("not found user");

	}

	public User loadUserByPhone(String phone) throws Exception {
		User user = userMapper.findUserByPhone(phone);
		if (user == null) {
			throw new UsernameNotFoundException("not found user");
		}
		return user;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String userId) {
		// 根据用户名查找用户信息
		// 根据查找到的用户信息判断用户是否被冻结
		String password = new  BCryptPasswordEncoder().encode("123456");
		return new SocialUser(userId, password, true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}
}
