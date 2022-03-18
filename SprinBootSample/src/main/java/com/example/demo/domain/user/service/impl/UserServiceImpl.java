package com.example.demo.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.repository.UserMapper;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper mapper; //instantiated with @Mapper inside its own class and connected with UserMapper.xml file
	
	@Autowired
	private PasswordEncoder encoder; //bean already instantiated
	
	/** User signup*/
	@Override
	public void signup(MUser user) {
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		
		//Password encryption
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		
		mapper.insertOne(user); //execution of SQL code with UserMapper.xml file
		
	}
	
	/** Get user*/
	@Override
	public List<MUser> getUsers(MUser user) {
		return mapper.findMany(user);
	}

	/** Get user(1 record)*/
	@Override
	public MUser getUserOne(String userId) {
		return mapper.findOne(userId);
	}
	
	
	/** Update user*/
	@Transactional
	@Override
	public void updateUserOne(String userId,
			String password,
			String userName) {
		
		//Password encryption
		String encryptedPassword = encoder.encode(password);
		mapper.updateOne(userId, encryptedPassword, userName);
		
		//Raise an exception
		//int i = 1 / 0;
	}
	
	/**Delete user*/
	@Override
	public void deleteUserOne(String userId) {
		mapper.deleteOne(userId);
	}
	
	/** Get login user information */
	public MUser getLoginUser(String userId) {
		return mapper.findLoginUser(userId);
	}


}
