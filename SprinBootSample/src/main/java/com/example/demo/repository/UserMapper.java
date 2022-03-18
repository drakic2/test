package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.user.model.MUser;

@Mapper
public interface UserMapper {
	
	/** user signup*/
	public int insertOne(MUser user);
	
	/** Get user*/
	public List<MUser> findMany(MUser user);
	
	/** Get user (1 record)*/
	public MUser findOne(String userId);
	
	/** Update user*/
	public void updateOne(@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);
	
	/**Delete user*/
	public void deleteOne(@Param("userId") String userId);
	
	/** Get Login User */
	public MUser findLoginUser(String userId);

}
