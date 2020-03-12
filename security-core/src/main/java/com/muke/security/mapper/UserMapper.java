package com.muke.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.muke.security.entity.User;

@Mapper
public interface UserMapper {
	
	@Select("select id, username, password,phone from users where username = #{username}")
	User findUserByName(@Param("username") String username);
	
	@Select("select id, username, password ,phone from users where phone = #{phone}")
	User findUserByPhone(@Param("phone") String phone);

}
