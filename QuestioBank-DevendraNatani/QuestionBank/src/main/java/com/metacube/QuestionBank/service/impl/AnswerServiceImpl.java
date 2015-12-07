/**service class implementation for answer service
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.QuestionBank.dao.AnswerDao;
import com.metacube.QuestionBank.dao.AnswerDetailDao;
import com.metacube.QuestionBank.dao.AnswerVotesDao;
import com.metacube.QuestionBank.dao.PopularityDao;
import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.AnswerDetail;
import com.metacube.QuestionBank.model.AnswerVotes;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.QuestionVotes;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	//answer DAO object
	@Autowired
	private AnswerDao answerDao;

	//answer detail DAO object
	@Autowired
	private AnswerDetailDao answerDetailDao;

	//answer votes DAO object
	@Autowired
	private AnswerVotesDao answerVotesDao;
	
	@Autowired
	private PopularityDao popularityDao;
	
	//logger object
	private static Logger logger = Logger.getLogger(AnswerServiceImpl.class);

	//error message
	private static final String MSG = "Exception in AnswerServiceImpl";

	/**
	 * method to add answer to the database
	 * 
	 * transactional annotation for maintaining atomicity of transaction
	 */
	@Transactional
	public boolean addAnswer(Answer answer) {
		boolean flag = false;

		try {
			Popularity popularity = new Popularity();
			answer.setPopularity(popularity);
			AnswerDetail answerDetail = new AnswerDetail();
			answerDetailDao.addAnswerDetail(answerDetail);
			answer.setAnswerDetail(answerDetail);
			int answerId = answerDao.addAnswer(answer);
			if (answerId != -1) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return flag;
	}



	/**
	 * method to get answer by ID
	 */
	@Transactional
	public Answer getAnswerById(Integer answerId) {
		Answer answer = null;
		try {
			answer = answerDao.getAnswer(answerId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return answer;
	}

	/**
	 * method to like the answer
	 */
	@Transactional
	public Popularity likeAnswer(Answer answer, User user) {
		Set<AnswerVotes> votesForCurrentAnswer = answerVotesDao
				.getAnswerVotesByAnswerId(answer.getAnswerId());
		boolean isVoteGivenByUser = false;
		Iterator<AnswerVotes> iterator = votesForCurrentAnswer.iterator();
		AnswerVotes answerVotes = null;
		while (iterator.hasNext()) {
			answerVotes = (AnswerVotes) iterator.next();
			if (answerVotes.getUserId() == user.getUserId()) {
				isVoteGivenByUser = true;
				break;
			}
		}

		Popularity popularity = answer.getPopularity();
		if (isVoteGivenByUser) {
			if (answerVotes.getStatus() == 1) {
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
				answerVotes.setStatus(1);
			}
		} else {
			answerVotes = new AnswerVotes();
			answerVotes.setStatus(1);
			answerVotes.setAnswerId(answer.getAnswerId());
			answerVotes.setUserId(user.getUserId());
			answerVotesDao.addAnswerVotes(answerVotes);
			int upVotes = popularity.getUpVotes();
			upVotes++;
			popularity.setUpVotes(upVotes);
		}
		popularityDao.addPopularity(popularity);

		return popularity;
	}

	/**
	 * method to dislike the answer
	 */
	@Transactional
	public Popularity disLikeAnswer(Answer answer, User user) {
		Set<AnswerVotes> votesForCurrentAnswer = answerVotesDao
				.getAnswerVotesByAnswerId(answer.getAnswerId());
		boolean isVoteGivenByUser = false;
		Iterator<AnswerVotes> iterator = votesForCurrentAnswer.iterator();
		AnswerVotes answerVotes = null;
		while (iterator.hasNext()) {
			answerVotes = (AnswerVotes) iterator.next();
			if (answerVotes.getUserId() == user.getUserId()) {
				isVoteGivenByUser = true;
				break;
			}
		}

		Popularity popularity = answer.getPopularity();
		if (isVoteGivenByUser) {
			if (answerVotes.getStatus() == 0) {
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
				answerVotes.setStatus(0);
			}
		} else {
			answerVotes = new AnswerVotes();
			answerVotes.setStatus(0);
			answerVotes.setAnswerId(answer.getAnswerId());
			answerVotes.setUserId(user.getUserId());
			answerVotesDao.addAnswerVotes(answerVotes);
			int downVotes = popularity.getDownVotes();
			downVotes++;
			popularity.setDownVotes(downVotes);
		}
		popularityDao.addPopularity(popularity);

		return popularity;
	}
	
	@Transactional
	public boolean acceptAnswer(int answerId) {
		boolean isAccepted = false;
		Answer answer = answerDao.getAnswer(answerId);
		AnswerDetail answerDetail = answer.getAnswerDetail();
		answerDetail.setStatus("accepted");
		try{
			answerDetailDao.addAnswerDetail(answerDetail);
			isAccepted = true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
		return isAccepted;
	}

}
