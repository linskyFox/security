package com.muke.security.contoller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muke.security.entity.User;

@RestController
public class ProductController {

	@GetMapping("/")
	public User index(@AuthenticationPrincipal User user) {
		return user;
	}

}
