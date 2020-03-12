package com.muke.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muke.security.entity.PhoneCode;
import com.muke.security.mapper.PhoneCodeMapper;
import com.muke.security.service.PhoneCodeService;

@Service
public class PhoneCodeServiceImple implements PhoneCodeService {

	@Autowired
	private PhoneCodeMapper mapper;

	@Override
	public PhoneCode findPhoneCodeByPhoneAndCode(String phone, String code) {
		PhoneCode phoneCode = mapper.findPhoneCode(phone, code);
		return phoneCode;
	}

	@Override
	public boolean ververify(String phone, String code) {
		return this.findPhoneCodeByPhoneAndCode(phone, code) != null;
	}

}
