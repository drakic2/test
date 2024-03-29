package com.example.demo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.user.model.MUser;

public interface UserRepository extends JpaRepository<MUser, String> {
	
	
	/**Login user search **/
	@Query("select user"
			+ " from MUser user"
			+ " where userId = :userId")
	public MUser findLoginUser(@Param("userId") String userId);
	
	/** User update */
	@Modifying
	@Query("update MUser"
			+ " set"
			+ " password = :password"
			+ " , userName = :userName"
			+ " where"
			+ " userId = :userId")
	public Integer updateUser(
			@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);
	

}
