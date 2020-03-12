package com.muke.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.muke.security.entity.PhoneCode;

@Mapper
public interface PhoneCodeMapper {

	@Select("select id, phone, code,create_time from phone_code where phone = #{phone} and code=#{code}")
	PhoneCode findPhoneCode(@Param("phone") String phone,@Param("code") String code);
}
