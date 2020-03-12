package com.muke.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUserDetailsService;

import com.muke.security.entity.User;

public interface CustomUserDetailsService extends UserDetailsService,SocialUserDetailsService {
	User loadUserByPhone(String phone) throws Exception;
}
