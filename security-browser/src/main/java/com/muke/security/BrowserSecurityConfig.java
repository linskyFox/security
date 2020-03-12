/**
 * 
 */
package com.muke.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http

				.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().csrf().disable()
				.apply(socialSecurityConfig);

	}

}
