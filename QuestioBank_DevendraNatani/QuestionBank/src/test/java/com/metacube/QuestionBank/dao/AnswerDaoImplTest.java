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

import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.AnswerDetail;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionDetail;
import com.metacube.QuestionBank.model.User;


@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})

public class AnswerDaoImplTest  {


	@Autowired
	private AnswerDao answerDaoImpl;
	private Answer answer ;
	private boolean expected = true;
	
	@Autowired
	QuestionDao questionDao;

	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		answer = new Answer();
		answer.setAnswerBody("test");
		
		Popularity popularityAnswer = new Popularity();
		AnswerDetail answerDetail = new AnswerDetail();
		
        User user = new User();
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
		
		questionDao.addQuestion(question);
		
	    answer.setAnswerDetail(answerDetail);
		answer.setUser(user);
	    answer.setPopularity(popularityAnswer);
		answer.setQuestion(question);

	}
	
	/**
	 * to test the method that adds user object in table
	 */
	@Transactional
	@Test
	public void testAddAnswer() {
		
		boolean output = false;
		int answerId = answerDaoImpl.addAnswer(answer);
		System.out.println(answerId);
		if (answerId != -1) {
			output = true;
		}
		assertEquals(expected, output);
	
	}
	
	@Transactional
	@Test
	public void testGetAnswerById() {
		answerDaoImpl.addAnswer(answer);
		int answerId = answer.getAnswerId();
		
		assertEquals(answer , answerDaoImpl.getAnswer(answerId));
	}
	
	@After
	public void tearDown() {
		answer = null;
	}
}

