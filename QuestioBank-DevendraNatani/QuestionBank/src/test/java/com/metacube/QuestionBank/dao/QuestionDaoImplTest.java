package com.metacube.QuestionBank.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionDetail;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/questionbank-servlet.xml"})
public class QuestionDaoImplTest {

	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TagDao tagDao;
	private Question question ;
	private User user ;
	Question question2;

	private boolean expected = true;
	
	@Transactional
	@Before
	public void setUp() {
		user =new User();
		user.setUserName("test");
		user.setEmail("test@metacube.com");
		
		userDao.addUser(user);
		System.out.println(user);
		question = new Question();
		Popularity popularity = new Popularity();
		question.setPopularity(popularity);
		
		QuestionDetail questionDetail = new QuestionDetail();
		question.setQuestionDetail(questionDetail);
		
		question.setQuestionBody("test");
		question.setQuestionTitle("test");
		Tag tag = tagDao.getTagByName("HTML");
		question.setUser(user);
		if(tag == null) {
			tag = new Tag();
			int tagId = tagDao.addTag(tag);
			tag = tagDao.getTag(tagId);
		}
		
		Set<Tag> tagList = new HashSet<Tag>();
		tagList.add(tag);
		question.setTagList(tagList);
		
		
		question2 = new Question();
		Popularity popularity2 = new Popularity();
		question2.setPopularity(popularity2);
		
		QuestionDetail questionDetail2 = new QuestionDetail();
		question2.setQuestionDetail(questionDetail2);
		
		question2.setQuestionBody("test");
		question2.setQuestionTitle("test");
		Tag tag2 = tagDao.getTagByName("JAVA");
		question2.setUser(user);
		if(tag2 == null) {
			tag2 = new Tag();
			int tagId = tagDao.addTag(tag2);
			tag2 = tagDao.getTag(tagId);
		}
		
		Set<Tag> tagList2 = new HashSet<Tag>();
		tagList2.add(tag2);
		question2.setTagList(tagList2);
		
	}
	
	@Transactional
	@Test
	public void testAddQuestion() {
		boolean output = false;
		questionDao.addQuestion(question);
		int questionId = question.getQuestionId();
		if (questionId != -1) {
			output = true;
		}
		assertEquals(expected , output);
		
		
	}

	@Transactional
	@Test
	public void testListQuestions() {

		int size = questionDao.listQuestions().size() + 1;
	
		questionDao.addQuestion(question);
	
		assertEquals(size, questionDao.listQuestions().size());
	}
	
	@Transactional
	@Test
	public void testListQuestionsByTagId() {		
	
		questionDao.addQuestion(question);
		System.out.println(question);
		Tag tag;
		Question question1 = new Question();
		
		
		tag = tagDao.getTagByName("HTML");
		System.out.println(tag);
		Popularity popularity = new Popularity();
		question1.setPopularity(popularity);
		
		QuestionDetail questionDetail = new QuestionDetail();
		question1.setQuestionDetail(questionDetail);
		
		question1.setQuestionBody("test1");
		question1.setQuestionTitle("test1");
		
		Set<Tag> tagList2 = new HashSet<Tag>();
		tagList2.add(tag);
		question1.setTagList(tagList2);
		question1.setUser(user);
		int size1 = questionDao.listQuestions(tag.getTagId()).size() + 1;
		questionDao.addQuestion(question1);
		questionDao.addQuestion(question2);
	
		assertEquals(size1, questionDao.listQuestions(tag.getTagId()).size());
	}
	
	@Transactional
	@Test
	public void testGetQuestionsQuestionId() {
		questionDao.addQuestion(question);		
		Question questionTest = questionDao.getQuestionById(question.getQuestionId());
		assertEquals(question, questionTest);
	}

}
