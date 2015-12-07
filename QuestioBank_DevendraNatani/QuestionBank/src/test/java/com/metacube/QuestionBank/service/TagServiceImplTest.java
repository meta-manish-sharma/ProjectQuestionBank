package com.metacube.QuestionBank.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.service.impl.TagServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class TagServiceImplTest {
	
	@Autowired
	public TagService tagService;
	
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
	 * to test the method that adds tag object in table
	 */
	@Test
	public void testAddTag() {
		
		assertEquals(expected, tagService.addTag(tag));
	}

	@After
	public void tearDown() {
		tag = null;
	}
}
