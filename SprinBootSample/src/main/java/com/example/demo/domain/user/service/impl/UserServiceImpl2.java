package com.example.demo.domain.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.user.model.MUser;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.repository.UserRepository;

@Service
@Primary
public class UserServiceImpl2 implements UserService { //-> JPA
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder; //bean already instantiated
	
	/** User signup*/
	@Transactional
	@Override
	public void signup(MUser user) {
		
		//existence check
		boolean exists = repository.existsById(user.getUserId());
		if(exists) {
			throw new DataAccessException("User already exist") {
				private static final long serialVersionUID = 1L;};
		}
				
				
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		
		//Password encryption
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		
		//insert
		repository.save(user);
	}
	
	/** Get user*/
	@Override
	public List<MUser> getUsers(MUser user) {
		
		//Serach conditions
		ExampleMatcher matcher = ExampleMatcher
				.matching() // and conditions
				.withStringMatcher(StringMatcher.CONTAINING) //Like clause
				.withIgnoreCase(); //both uppercase and lowercase
		
		return repository.findAll(Example.of(user, matcher));
	}

	/** Get user(1 record)*/
	@Override
	public MUser getUserOne(String userId) {
		Optional<MUser> option = repository.findById(userId);
		MUser user = option.orElse(null);
		return user;
	}
	
	
	/** Update user*/
	@Transactional
	@Override
	public void updateUserOne(String userId,
			String password,
			String userName) {
		
		//Password encryption
		String encryptPassword = encoder.encode(password);
		
		//user update
		repository.updateUser(userId, encryptPassword, userName);
		
		//Raise an exception
		//int i = 1 / 0;
		
	}
	
	/**Delete user*/
	@Override
	public void deleteUserOne(String userId) {
		repository.deleteById(userId);
	}
	
	/** Get login user information */
	public MUser getLoginUser(String userId) {
		return repository.findLoginUser(userId);
	}


}
