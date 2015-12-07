package com.metacube.QuestionBank.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.QuestionBank.dao.UserDao;
import com.metacube.QuestionBank.exception.UserDoesNotExistExeption;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired(required = true)
	private UserDao userDao;
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	private static final String MSG="Exception in UserServiceImpl";
	
	@Transactional
	public boolean addUser(User user) {
		
		boolean flag = false;
		
		try {
			userDao.addUser(user);
			flag = true;
			
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		return flag;
	}
	
	@Transactional
	public Set<User> listUsers() {
		Set<User> users = null;
		
		try {
			users = userDao.listUsers();
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return users;
	}

	@Transactional
	public User getUserByEmail(String email) {
		
		User user = null;
		
		try {
			user = userDao.getUser(email);
			
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return user;
	}
	
	

	@Transactional
	public boolean blockUser(int userId) throws UserDoesNotExistExeption {
		boolean flag = false;
		User user = null;
		try {
			user = userDao.getUserById(userId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		//System.out.println(user);
		
		if(user == null) {
			throw new UserDoesNotExistExeption("User Does Not Exists With Id : " + userId);
		} 
		try {
			if(user.getUserStatus().equals("blocked")){
				user.setUserStatus("Unblocked");
			} else {
				user.setUserStatus("blocked");
			} 
					
			userDao.addUser(user);
			flag = true;
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		return flag;
	}
	
	@Transactional
	public User getUserByUserId(int userId) {
		User user = null;
		
		try {
			user = userDao.getUserById(userId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} 
		return user;
	}

	@Transactional
	public boolean addAdmin(int userId) throws UserDoesNotExistExeption {
		boolean flag = false;
		
		User user = null;
		try {
			user = userDao.getUserById(userId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		//System.out.println(user);
		
		if(user == null) {
			throw new UserDoesNotExistExeption("User Does Not Exists With Id : " + userId);
		} 
		
		try {
			user.setIsAdmin(!(user.getIsAdmin()));
			flag = true;
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		return flag;
	}

	@Transactional 
	public boolean logout(int userId) throws UserDoesNotExistExeption {
		
		boolean flag = false;
		User user = null;
		
		try {
			user = userDao.getUserById(userId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		//System.out.println(user);
		
		if(user == null) {
			throw new UserDoesNotExistExeption("User Does Not Exists With Id : " + userId);
		} 
		java.util.Date date= new java.util.Date();
		user.setLastLoggedInTime(new Timestamp(date.getTime()));
				
		try {
			userDao.addUser(user);
			flag = true;
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}	
		
		return flag;
	}
	
	@Transactional
	public List<String> getUserList() {
		return userDao.getUserList();
	}

	@Transactional
	public List<User> getUsers(int offset, int noOfRecords) {
        List<User> users = null;
		
		try {
			users = userDao.getUsers(offset,noOfRecords);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return users;
	}

}
