package com.metacube.QuestionBank.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.model.AnswerDetail;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class AnswerDetailDoaImpTest {
	
	@Autowired	
	AnswerDetailDao answerDetailDao;
	private AnswerDetail answerDetail;
	private boolean expected = true;
	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		answerDetail = new AnswerDetail();
		answerDetail.setStatus("not accepted");
	}
	
	@Transactional
	@Test
	public void testAddAnswerDetailDao() {
		assertEquals(expected, answerDetailDao.addAnswerDetail(answerDetail));
	}
	
	@Transactional
	@Test
	public void testEditAnswerDetailDao() {
		assertEquals(expected, answerDetailDao.addAnswerDetail(answerDetail));
		answerDetail.setStatus("accepted");
		 answerDetailDao.addAnswerDetail(answerDetail);

		 AnswerDetail AnswerDetail1 = answerDetailDao.getAnswerDetailById(answerDetail.getId());
		assertEquals(expected, AnswerDetail1.getStatus().equalsIgnoreCase("accepted"));
	}
	
	
	@Transactional
	@Test
	public void testGetAnswerdetailById() {
		answerDetailDao.addAnswerDetail(answerDetail);
		answerDetail.getId();
		assertEquals(answerDetail, answerDetailDao.getAnswerDetailById(answerDetail.getId()));
	}

}
