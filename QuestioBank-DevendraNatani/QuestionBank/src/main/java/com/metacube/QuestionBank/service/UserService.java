package com.metacube.QuestionBank.service;

import java.util.List;
import java.util.Set;

import com.metacube.QuestionBank.exception.UserDoesNotExistExeption;
import com.metacube.QuestionBank.model.User;


public interface UserService {
	
	public boolean addUser(User user);

	public Set<User> listUsers();
	
	public List<User> getUsers(int offset, int noOfRecords);
	
	public User getUserByEmail(String email);
	
	public User getUserByUserId(int userId);
	
	public boolean blockUser(int userId) throws UserDoesNotExistExeption;

	public boolean addAdmin(int userId) throws UserDoesNotExistExeption;

	public boolean logout(int userId) throws UserDoesNotExistExeption;
	
	public List<String> getUserList();
}
