package com.metacube.QuestionBank.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.model.QuestionDetail;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class QuestionDetailDoaImpTest {
	
	@Autowired	
	QuestionDetailDao questionDetailDao;
	private QuestionDetail questionDetail = new QuestionDetail();
	private boolean expected = true;
	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		questionDetail.setClosingReason("none");
		questionDetail.setStatus("open");
	}
	
	@Transactional
	@Test
	public void testAddQuestionDetailDao() {
		assertEquals(expected, questionDetailDao.addQuestionDetail(questionDetail));
	}
	
	@Transactional
	@Test
	public void testEditQuestionDetailDao() {
		assertEquals(expected, questionDetailDao.addQuestionDetail(questionDetail));
		questionDetail.setStatus("closed");
		questionDetail.setClosingReason("accepted");
		 questionDetailDao.addQuestionDetail(questionDetail);

		 QuestionDetail questionDetail1 = questionDetailDao.getQuestionDetailById(questionDetail.getId());
		assertEquals(expected, questionDetail1.getStatus().equalsIgnoreCase("closed"));
	}
	
	
	@Transactional
	@Test
	public void testGetQuestiondetailById() {
		questionDetailDao.addQuestionDetail(questionDetail);
		questionDetail.getId();
		assertEquals(questionDetail, questionDetailDao.getQuestionDetailById(questionDetail.getId()));
	}

}
