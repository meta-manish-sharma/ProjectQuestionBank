package com.metacube.QuestionBank.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionDetail;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class QuestionServiceTest {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	 private TagService tagService;
	
	private Question question;
	private User user ;
	private boolean expected = true;
	
	List<String> tagList;
	
	@Transactional
	@Before
	public void setUp() {
		user =new User();
		user.setUserName("test");
		user.setEmail("test@metacube.com");
		
		userService.addUser(user);
		question = new Question();
		Popularity popularity = new Popularity();
		question.setPopularity(popularity);
		
		QuestionDetail questionDetail = new QuestionDetail();
		question.setQuestionDetail(questionDetail);
		
		question.setUser(user);
		
		question.setQuestionBody("test");
		question.setQuestionTitle("test");
		
		tagList = new ArrayList<String>();
		tagList.add("HTML");
		
		
	}
	
	@Transactional
	@Test
	public void testAddQuestion() {
		boolean output = false;
		questionService.addQuestion(question, tagList);
		int questionId = question.getQuestionId();
		if (questionId != -1) {
			output = true;
		}
		assertEquals(expected , output);
	}
	
	@Transactional
	@Test
	public void testListQuestions() {

		int size = questionService.listQuestions().size() + 1;
	
		questionService.addQuestion(question,tagList);
	
		assertEquals(size, questionService.listQuestions().size());
	}
	
	@Transactional
	@Test
	public void testListQuestionsByTagId() {		
	
		questionService.addQuestion(question,tagList);
		System.out.println(question);
		Tag tag = tagService.getTagByName("HTML");
		Question question1 = new Question();
		
		List<String> tagList2 = new ArrayList<String>();
		tagList2.add("HTML");
		tagList2.add("JAVA");
		Popularity popularity = new Popularity();
		question1.setPopularity(popularity);
		
		QuestionDetail questionDetail = new QuestionDetail();
		question1.setQuestionDetail(questionDetail);
		
		question1.setQuestionBody("test1");
		question1.setQuestionTitle("test1");
		
		Question question2 = new Question();
		
		List<String> tagList3 = new ArrayList<String>();
		tagList3.add("JAVA");
		Popularity popularity2 = new Popularity();
		question2.setPopularity(popularity2);
		
		QuestionDetail questionDetail2 = new QuestionDetail();
		question2.setQuestionDetail(questionDetail2);
		
		question2.setQuestionBody("test2");
		question2.setQuestionTitle("test2");
		
		question2.setUser(user);
		int size1 = questionService.listQuestions(tag.getTagId()).size();
		questionService.addQuestion(question2,tagList3);
		
	
		assertEquals(size1, questionService.listQuestions(tag.getTagId()).size());
	}
	
	@Transactional
	@Test
	public void testCloseQuestion(){
		questionService.addQuestion(question, tagList);
		boolean output = questionService.closeQuestion(question.getQuestionId(), "Accepted");
		
		System.out.println(question.getQuestionDetail().getStatus());
		assertEquals(expected, output);
	}
	
	@Transactional
	@Test
	public void testDisLikeQuestion() {
		questionService.addQuestion(question, tagList);
		Popularity popularity = questionService.disLikeQuestion(question, user);
		int actual = popularity.getDownVotes();
		assertEquals(1, actual);
	}
	
	@Transactional
	@Test
	public void testLikeQuestion() {
		questionService.addQuestion(question, tagList);
		Popularity popularity = questionService.likeQuestion(question, user);
		int actual = popularity.getUpVotes();
		assertEquals(1, actual);
	}
	
	@Transactional
	@Test
	public void testSearch() {
		questionService.addQuestion(question, tagList);
		int size = questionService.searchByTitle("test").size();
		assertEquals(1, size);
	}
}
