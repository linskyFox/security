package com.muke.security.service;

import com.muke.security.entity.PhoneCode;

public interface PhoneCodeService {
	
	PhoneCode findPhoneCodeByPhoneAndCode(String phone,String code);
	boolean ververify(String phone,String code);
}
