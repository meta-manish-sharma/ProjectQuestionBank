package com.metacube.QuestionBank.service;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metacube.QuestionBank.exception.UserDoesNotExistExeption;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.impl.UserServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class UserServiceImplTest {
	
	@Autowired
	public UserService userService;
	
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
		
		assertEquals(expected, userService.addUser(user));
	}
	
	@Transactional
	@Test
	public void testListUser() {
		
		int size = userService.listUsers().size() + 1;
		User newUser = new User();
		newUser.setUserName("test1");
		newUser.setEmail("test1@metacube.com");
		assertEquals(expected, userService.addUser(newUser));
		assertEquals(size, userService.listUsers().size());
	}
	
	
	
	/**
	 * to test the method that get user object from table by email
	 */
	@Transactional
	@Test
	public void testGetUserByEmail() {
		
		assertEquals(expected, userService.addUser(user));
		assertEquals(user, userService.getUserByEmail(user.getEmail()));
	}
	
	 /**
	 * to test the method that get user object from table by user id
	 */
	@Transactional
	@Test
	public void testGetUserById() {

		assertEquals(expected, userService.addUser(user));		
		assertEquals(user, userService.getUserByUserId(user.getUserId()));
	}
	
	@Transactional
	@Test
	public void testBlockUser() throws UserDoesNotExistExeption {

		assertEquals(expected, userService.addUser(user));		
		assertEquals(expected, userService.blockUser(user.getUserId()));
	}
	
	@Transactional
	@Test
	public void testUserNameList() throws UserDoesNotExistExeption {
		int size = userService.getUserList().size()+1;
		assertEquals(expected, userService.addUser(user));		
		assertEquals(size, userService.getUserList().size());
	}
	
	@Transactional
	@Test
	public void testAddAdmin() throws UserDoesNotExistExeption {

		assertEquals(expected, userService.addUser(user));		
		assertEquals(expected, userService.addAdmin(user.getUserId()));
	}
	
	
	
	@After
	public void tearDown() {
		user = null;
	}
}
