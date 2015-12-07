package com.metacube.QuestionBank.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class AnswerServiceImplTest {
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	private Answer answer ;
	private User user ;
	private boolean expected = true;
	
	@Transactional
	@Before
	public void setUp() {
		user =new User();
		user.setUserName("test");
		user.setEmail("test@metacube.com");
		
		userService.addUser(user);
		System.out.println(user);
		Question question = new Question();
		question.setQuestionBody("test");
		question.setQuestionTitle("test");
		List<String> tags = new ArrayList<String>();
		
		question.setUser(user);
		tags.add("HTML");
		questionService.addQuestion(question, tags);
		answer = new Answer();
		answer.setUser(user);
		answer.setQuestion(question);
		answer.setAnswerBody("test");
	}
	
	

	@Transactional
	@Test
	public void testAddAnswer() {
		assertEquals(true, answerService.addAnswer(answer));
	}
	

	@Transactional
	@Test
	public void testDisLikeAnswer() {
		assertEquals(true, answerService.addAnswer(answer));
		Popularity popularity = answerService.disLikeAnswer(answer, user);
		int actual = popularity.getDownVotes();
		assertEquals(1, actual);
	}
	
	@Transactional
	@Test
	public void testLikeAnswer() {
		assertEquals(true, answerService.addAnswer(answer));
		Popularity popularity = answerService.likeAnswer(answer, user);
		int actual = popularity.getUpVotes();
		assertEquals(1, actual);
	}
	@After
	public void tearDown() {
		answer = null;
	}

}
