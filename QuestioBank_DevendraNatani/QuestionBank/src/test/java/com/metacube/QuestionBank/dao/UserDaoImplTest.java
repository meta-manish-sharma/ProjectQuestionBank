package com.metacube.QuestionBank.dao;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.dao.impl.UserDaoImpl;
import com.metacube.QuestionBank.model.User;


@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})

public class UserDaoImplTest  {


	@Autowired
	private UserDaoImpl userDaoImpl;
	private User user = new User();
	private boolean expected = true;

	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		user.setUserName("test");
		user.setEmail("test@metacube.com");
	}
	
	/**
	 * to test the method that adds user object in table
	 */
	@Transactional
	@Test
	public void testAddUser() {
		
		assertEquals(expected, userDaoImpl.addUser(user));
	}
	
	
	/**
	 * to test the method that get user object from table by email
	 */
	@Transactional
	@Test
	public void testGetUser() {
		
		assertEquals(expected, userDaoImpl.addUser(user));
		assertEquals(user, userDaoImpl.getUser(user.getEmail()));
	}
	
	 /**
	 * to test the method that get user object from table by user id
	 */
	@Transactional
	@Test
	public void testGetUserById() {

		assertEquals(expected, userDaoImpl.addUser(user));		
		assertEquals(user, userDaoImpl.getUserById(user.getUserId()));
	}
	
	/**
	 * to test the method that gets list of all users
	 */
	@Transactional
	@Test
	public void testListUsers() {
		
		assertEquals(expected, userDaoImpl.addUser(user));
		int size = userDaoImpl.listUsers().size() + 1;
		User newUser = new User();
		newUser.setUserName("test1");
		newUser.setEmail("test1@metacube.com");
		assertEquals(expected, userDaoImpl.addUser(newUser));
		assertEquals(size, userDaoImpl.listUsers().size());
	}
	
	
	@Transactional
	@Test
	public void testListUsersUsingPagination() {
		int size = userDaoImpl.getUsers(0,3).size();
		System.out.println("before    =    "+size);
		User newUser = new User();
		newUser.setUserName("test1");
		newUser.setEmail("test1@metacube.com");
		assertEquals(expected, userDaoImpl.addUser(newUser));
		size = userDaoImpl.getUsers(1,3).size();
		System.out.println("After first insertion   = =  "+size);
		newUser = new User();
		newUser.setUserName("test2");
		newUser.setEmail("test2@metacube.com");
		assertEquals(expected, userDaoImpl.addUser(newUser));
		newUser = new User();
		newUser.setUserName("test3");
		newUser.setEmail("test3@metacube.com");
		assertEquals(expected, userDaoImpl.addUser(newUser));
		newUser = new User();
		newUser.setUserName("test4");
		newUser.setEmail("test4@metacube.com");
		assertEquals(expected, userDaoImpl.addUser(newUser));
		size = userDaoImpl.getUsers(1,3).size();
		System.out.println("After   = =  "+size);
	}
	@After
	public void tearDown() {
		user = null;
	}
}

