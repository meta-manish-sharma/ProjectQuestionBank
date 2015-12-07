package com.metacube.QuestionBank.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionDetail;
import com.metacube.QuestionBank.model.QuestionVotes;
import com.metacube.QuestionBank.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/questionbank-servlet.xml" })
public class QuestionVotesDaoImplTest {

	@Autowired
	QuestionVotesDao questionVotesDao;
	
	@Autowired
	QuestionDao questionDao;

	private User user;
	private QuestionVotes questionVotes;
	private boolean expected = true;
	int questionId = 0;
	QuestionVotes questionVotesTemp;
	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		user = new User();
		questionVotes = new QuestionVotes();
		user.setEmail("test@metacube.com");
		user.setUserName("test");
     
	    Question question = new Question();
	    Popularity popularityQuestion = new Popularity();
	    QuestionDetail questionDetail = new QuestionDetail();
	    question.setPopularity(popularityQuestion);
		question.setQuestionDetail(questionDetail);
		question.setUser(user);
		question.setQuestionTitle("test");
		question.setQuestionBody("test");
		
		questionId = questionDao.addQuestion(question);
		questionVotes.setUserId(user.getUserId());
		questionVotes.setQuestionId(questionId);	
	}

	
	/*
	 * Add Question Vote in question Vote table
	 * */
	@Transactional
	@Test
	public void testAddQuestionVotes() {
		System.out.println(questionVotes);
		assertEquals(expected, questionVotesDao.addQuestionVotes(questionVotes));
	}
	
	
	/*
	 * Get Question Votes By QuestionId in question Vote table
	 * */
	@Transactional
	@Test
	public void testgetQuestionVotesByQuestionId() {

		assertEquals(expected, questionVotesDao.addQuestionVotes(questionVotes));
		int size = questionVotesDao.getQuestionVotesByQuestionId(questionId).size() ;
		System.out.println("first size is "+questionId);
		assertEquals(size, questionVotesDao.getQuestionVotesByQuestionId(questionId).size());
	}
	

}
