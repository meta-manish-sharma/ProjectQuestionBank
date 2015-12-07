package com.metacube.QuestionBank.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
	import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.AnswerDetail;
import com.metacube.QuestionBank.model.AnswerVotes;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionDetail;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/questionbank-servlet.xml" })
public class AnswerVotesDaoImplTest {

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private AnswerVotesDao answerVotesDao;
	
	private Question question ;
	private Answer answer = new Answer();
	private boolean expected = true;


	private AnswerVotes answerVotes;
	
	int answerId = 0;
	AnswerVotes answerVotesTemp;
	
	/**
	 * 	Set User attributes before test case start executing
	 */
	@Before
	public void setUp() {
		User user =new User();
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
		questionDao.addQuestion(question);
		Popularity popularityAnswer =  new Popularity();
		answer.setPopularity(popularityAnswer);;
		AnswerDetail answerDetail = new AnswerDetail();
		answer.setAnswerDetail(answerDetail);
		answer.setQuestion(question);
		answer.setAnswerBody("Answer for Test");
		answer.setUser(user);
		answerDao.addAnswer(answer);
		int answerId = answer.getAnswerId();
	
		answerVotes = new AnswerVotes();
		answerVotes.setUserId(user.getUserId());
		answerVotes.setAnswerId(answerId);	
	}
	
	/*
	 * Add Answer Vote in answer Vote table
	 * */
	@Transactional
	@Test
	public void testAddQuestionVotes() {
		System.out.println(answerVotes);
		assertEquals(expected, answerVotesDao.addAnswerVotes(answerVotes));
	}
	
	/**
	 * Get Answer Voted By answer id
	 */
	@Transactional
	@Test
	public void testgetAnswerVotesByAnswerId() {

		assertEquals(expected, answerVotesDao.addAnswerVotes(answerVotes));
		int size = answerVotesDao.getAnswerVotesByAnswerId(answerId).size() ;
		System.out.println("first size is "+answerId);
		assertEquals(size, answerVotesDao.getAnswerVotesByAnswerId(answerId).size());
	}
}
