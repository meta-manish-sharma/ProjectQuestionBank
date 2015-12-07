/**DAO (Data Access Object) class implementation for answers
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.AnswerVotesDao;
import com.metacube.QuestionBank.dao.QuestionVotesDao;
import com.metacube.QuestionBank.model.AnswerVotes;
import com.metacube.QuestionBank.model.QuestionVotes;

@Repository
public class AnswerVotesDaoImplement implements AnswerVotesDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	//error message
	private static final String MSG = "Exception in QuestionVotesDaoImpl"; 

	/**
	 * method to add answer votes in the database
	 */
	public boolean addAnswerVotes(AnswerVotes answerVotes) {
		boolean flag = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(answerVotes);
			flag = true;

		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * method to get answer votes by answer ID
	 */
	public Set<AnswerVotes> getAnswerVotesByAnswerId(int answerId) {
		Set<AnswerVotes> results = null;
		try {
			Criteria newTags = sessionFactory.getCurrentSession().createCriteria(AnswerVotes.class);
			newTags.add(Restrictions.eq("answerId",answerId ));
			@SuppressWarnings("unchecked")
			List<AnswerVotes> votes = newTags.list();
			results = new HashSet<AnswerVotes>(votes);

		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return results;
	}
}