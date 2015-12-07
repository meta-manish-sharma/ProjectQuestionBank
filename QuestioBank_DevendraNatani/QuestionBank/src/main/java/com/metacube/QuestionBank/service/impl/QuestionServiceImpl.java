/**service class implementation for question service
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.QuestionBank.dao.PopularityDao;
import com.metacube.QuestionBank.dao.QuestionDao;
import com.metacube.QuestionBank.dao.QuestionDetailDao;
import com.metacube.QuestionBank.dao.QuestionVotesDao;
import com.metacube.QuestionBank.dao.TagDao;
import com.metacube.QuestionBank.dao.UserDao;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionDetail;
import com.metacube.QuestionBank.model.QuestionVotes;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.QuestionService;
import com.metacube.QuestionBank.util.SearchQuestionByTitle;

@Service
public class QuestionServiceImpl implements QuestionService {

	//question DAO
	@Autowired
	private QuestionDao questionDao;

	//popularity DAO
	@Autowired
	private PopularityDao popularityDao;
	
	//question details DAO
	@Autowired
	private QuestionDetailDao questionDetailDao;

	//tag DAO
	@Autowired
	private TagDao tagDao;
	
	//user DAO
	@Autowired
	private UserDao userDao;
	

	//question votes DAO
	@Autowired
	private QuestionVotesDao questionVotesDao;

	//logger object
	private static Logger logger = Logger.getLogger(QuestionServiceImpl.class);

	//error message
	private static final String MSG = "Exception in QuestionServiceImpl";

	/**
	 * method to add the question to the database
	 */
	@Transactional
	public int addQuestion(Question question, List<String> tagNameList) {

		Popularity popularity = new Popularity();
		question.setPopularity(popularity);
		int userId = question.getUser().getUserId();
		User user = userDao.getUserById(userId);
		question.setUser(user);
		Set<Tag> tagList = new HashSet<Tag>();
		System.out.println("ravika");
		for (int index = 0; index < tagNameList.size(); index++) {
			int tagId;
			Tag newTag = null;
			try {
				newTag = tagDao.getTagByName(tagNameList.get(index));
			} catch (QueryException e) {
				System.out.println("there is no tahg with this name");
			}
			System.err.println("usdsa");
			if (newTag == null) {
				Tag tag = new Tag();
				tag.setTagName(tagNameList.get(index));
				tagId = tagDao.addTag(tag);
			} else {
				tagId = newTag.getTagId();
			}
			tagList.add(tagDao.getTag(tagId));
		}
		question.setTagList(tagList);

		QuestionDetail questionDetail = new QuestionDetail();
		questionDetailDao.addQuestionDetail(questionDetail);
		question.setQuestionDetail(questionDetail);
		int questionId = questionDao.addQuestion(question);
		return questionId;
	}

	/**
	 * method to list all the questions in the database
	 */
	@Transactional
	public List<Question> listQuestions() {
		List<Question> listQuestions = null;

		try {
			listQuestions = questionDao.listQuestions();
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return listQuestions;
	}

	/**
	 * method to list all the questions containing a particular tag
	 */
	@Transactional
	public List<Question> listQuestions(int tagId) {
		List<Question> listQuestions = null;

		try {
			listQuestions = questionDao.listQuestions(tagId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return listQuestions;
	}

	/**
	 * method to get question by ID
	 */
	@Transactional
	public Question getQuestionById(int QuestionId) {
		Question question = null;

		try {
			question = questionDao.getQuestionById(QuestionId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return question;
	}

	/**
	 * method to get the most recent questions
	 */
	@Transactional
	public List<Question> getRecentQuestions(int tagId, int offset,
			int noOfRecords) {
		List<Question> recentQuestions = null;

		try {
			recentQuestions = questionDao.getRecentQuestions(tagId, offset,
					noOfRecords);
			System.out.println("List in service  " + recentQuestions);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return recentQuestions;
	}

	/**
	 * method to get the unanswered questions
	 */
	@Transactional
	public List<Question> getUnAnsweredQuestions(int tagId, int offset,
			int noOfRecords) {
		List<Question> unAnsweredQuestions = null;
		try {
			unAnsweredQuestions = questionDao.getUnAnsweredQuestions(tagId,
					offset, noOfRecords);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return unAnsweredQuestions;
	}

	/**
	 * method to like a question
	 */
	@Transactional
	public Popularity likeQuestion(Question question, User user) {
		Set<QuestionVotes> votesForCurrentQuestion = questionVotesDao
				.getQuestionVotesByQuestionId(question.getQuestionId());
		boolean isVoteGivenByUser = false;
		Iterator<QuestionVotes> iterator = votesForCurrentQuestion.iterator();
		QuestionVotes questionVotes = null;
		while (iterator.hasNext()) {
			questionVotes = (QuestionVotes) iterator.next();
			if (questionVotes.getUserId() == user.getUserId()) {
				isVoteGivenByUser = true;
				break;
			}
		}

		Popularity popularity = question.getPopularity();
		if (isVoteGivenByUser) {
			if (questionVotes.getStatus() == 1) {
				return popularity;
			} else {
				int downVotes = popularity.getDownVotes();
				int upVotes = popularity.getUpVotes();
				if (downVotes > 0) {
					downVotes--;
				}
				upVotes++;
				popularity.setDownVotes(downVotes);
				popularity.setUpVotes(upVotes);
				questionVotes.setStatus(1);
				
			}
		} else {
			questionVotes = new QuestionVotes();
			questionVotes.setStatus(1);
			questionVotes.setQuestionId(question.getQuestionId());
			questionVotes.setUserId(user.getUserId());
			questionVotesDao.addQuestionVotes(questionVotes);
			int upVotes = popularity.getUpVotes();
			upVotes++;
			popularity.setUpVotes(upVotes);
		}
		return popularity;
	}

	/**
	 * method to dislike a question
	 */
	@Transactional
	public Popularity disLikeQuestion(Question question, User user) {
		Set<QuestionVotes> votesForCurrentQuestion = questionVotesDao
				.getQuestionVotesByQuestionId(question.getQuestionId());
		boolean isVoteGivenByUser = false;
		Iterator<QuestionVotes> iterator = votesForCurrentQuestion.iterator();
		QuestionVotes questionVotes = null;
		while (iterator.hasNext()) {
			questionVotes = (QuestionVotes) iterator.next();
			if (questionVotes.getUserId() == user.getUserId()) {
				isVoteGivenByUser = true;
				break;
			}
		}

		Popularity popularity = question.getPopularity();
		if (isVoteGivenByUser) {
			if (questionVotes.getStatus() == 0) {
				return popularity;
			} else {
				int downVotes = popularity.getDownVotes();
				int upVotes = popularity.getUpVotes();
				downVotes++;
				if (upVotes > 0) {
					upVotes--;
				}
				popularity.setDownVotes(downVotes);
				popularity.setUpVotes(upVotes);
				questionVotes.setStatus(0);
				
			}
		} else {
			questionVotes = new QuestionVotes();
			questionVotes.setStatus(0);
			questionVotes.setQuestionId(question.getQuestionId());
			questionVotes.setUserId(user.getUserId());
			questionVotesDao.addQuestionVotes(questionVotes);
			int downVotes = popularity.getDownVotes();
			downVotes++;
			popularity.setDownVotes(downVotes);
		}
		
		return popularity;
	}

	/**
	 * method to get the closed questions
	 */
	@Transactional
	public List<Question> getClosedQuestions(int tagId, int offset,
			int noOfRecords) {
		List<Question> closedQuestions = null;
		try {
			closedQuestions = questionDao.getClosedQuestions(tagId, offset,
					noOfRecords);

		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return closedQuestions;
	}

	/**
	 * method to get active questions
	 */
	@Transactional
	public List<Question> getActiveQuestions(int tagId, int offset,
			int noOfRecords) {
		List<Question> unActiveQuestions = null;
		try {
			unActiveQuestions = questionDao.getActiveQuestions(tagId, offset,
					noOfRecords);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		System.out.println("list in service" + unActiveQuestions);
		return unActiveQuestions;
	}

	@Transactional
	public List<Question> searchByTitle(String input) {
		List<Question> listQuestions = null;

		try {
			listQuestions = questionDao.listQuestions();
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		if (listQuestions == null) {
			return listQuestions;
		} else if (listQuestions.isEmpty()) {
			return listQuestions;
		}
		List<SearchQuestionByTitle> questions = new ArrayList<SearchQuestionByTitle>();

		Question currentQuestion;
		for (int i = 0; i < listQuestions.size(); i++) {
			currentQuestion = listQuestions.get(i);
			SearchQuestionByTitle question = new SearchQuestionByTitle();
			question.setQuestionId(currentQuestion.getQuestionId());
			question.setQuestionTitle(currentQuestion.getQuestionTitle());
			questions.add(question);
		}
		ArrayList<String> alist = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(input, " ");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			alist.add(key);
			System.out.println(key);
		}
		int count = questions.size();
		for (int i = 0; i < count; i++) {
			StringTokenizer stTokenizer = new StringTokenizer(questions.get(i)
					.getQuestionTitle(), " ");
			int current = 0;
			int lastMatched = -2;
			int priority = 0;
			while (stTokenizer.hasMoreTokens()) {
				String key1 = stTokenizer.nextToken();

				for (int j = 0; j < alist.size(); j++) {
					if (key1.equalsIgnoreCase(alist.get(j))) {
						if (lastMatched == current - 1) {
							priority++;
						}
						priority++;
						lastMatched = current;

					}
				}

				current++;
			}
			questions.get(i).setPriority(priority);
		}
		Collections.sort(questions);
		System.out.println("qusdsad" + questions);
		List<Question> questionSorted = new ArrayList<Question>();
		for (int i = 0; i < questions.size(); i++) {
			int questionId = questions.get(i).getQuestionId();
			if (!(questions.get(i).getPriority() == 0)) {
				questionSorted.add(questionDao.getQuestionById(questionId));
			}

		}
		return questionSorted;
	}
	
	@Transactional
	public boolean closeQuestion(int questionId ,String reason) {
		boolean isClosed = false;
		Question question = questionDao.getQuestionById(questionId);
		QuestionDetail questionDetail = question.getQuestionDetail();
		questionDetail.setClosingReason(reason);
		questionDetail.setStatus("closed");
		try {
			questionDetailDao.addQuestionDetail(questionDetail);
			isClosed = true;
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return isClosed;
	}
}
