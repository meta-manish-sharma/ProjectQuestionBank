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

import com.metacube.QuestionBank.dao.impl.TagDaoImpl;
import com.metacube.QuestionBank.dao.impl.UserDaoImpl;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;


@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})

public class TagDaoImplTest  {


	@Autowired
	private TagDao tagDaoImpl;
	private Tag tag = new Tag();
	private boolean expected = true;

	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		tag.setTagName("test");
		tag.setTagId(1);
	}
	
	/**
	 * to test the method that adds user object in table
	 */
	@Transactional
	@Test
	public void testAddTag() {
		
		assertEquals(expected, tagDaoImpl.addTag(tag));
	}
	
	
	/**
	 * to test the method that get user object from table by email
	 */
	@Transactional
	@Test
	public void testGetTagById() {
		
		assertEquals(expected, tagDaoImpl.addTag(tag));
		assertEquals(tag, tagDaoImpl.getTag(tag.getTagId()));
	}

	
	/**
	 * to test the method that gets list of all users
	 */
	@Transactional
	@Test
	public void testListUsers() {
		
		assertEquals(expected, tagDaoImpl.addTag(tag));
		int size = tagDaoImpl.listTags().size() + 1;
		Tag newtag = new Tag();
		newtag.setTagName("test");
		newtag.setTagId(1);
		assertEquals(-1, tagDaoImpl.addTag(newtag));
		assertEquals(size, tagDaoImpl.listTags().size());
	}
	
	
	@After
	public void tearDown() {
		tag = null;
	}
}

