package com.metacube.QuestionBank.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.model.Popularity;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class PopularityDaoImplTest {

	@Autowired
	PopularityDao popularityDao;
	
	Popularity popularity = new Popularity();
	boolean expected = true; 
	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		
	}
	
	
	@Transactional
	@Test
	public void testAddPopularity() {
		assertEquals(expected, popularityDao.addPopularity(popularity));
	}
	
	
	@Transactional
	@Test
	public void testGetPoplarity(){
		assertEquals(expected, popularityDao.addPopularity(popularity));
		assertEquals(popularity, popularityDao.getPopularity(popularity.getPopularityId()));
	}
	
	

}
