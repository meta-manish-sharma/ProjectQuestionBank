/**DAO (Data Access Object) class implementation for question votes
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

import com.metacube.QuestionBank.dao.QuestionVotesDao;
import com.metacube.QuestionBank.model.QuestionVotes;

@Repository
public class QuestionVotesDaoImplement implements QuestionVotesDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	//error message
	private static final String MSG = "Exception in QuestionVotesDaoImpl"; 

	/**
	 * method to add question votes in the database
	 */
	public boolean addQuestionVotes(QuestionVotes questionVotes) {
		boolean flag = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(questionVotes);
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
	 * method to get votes for a particular question by ID
	 */
	public Set<QuestionVotes> getQuestionVotesByQuestionId(int questionId) {
		Set<QuestionVotes> results = null;
		try {
			Criteria newTags = sessionFactory.getCurrentSession().createCriteria(QuestionVotes.class);
			newTags.add(Restrictions.eq("questionId",questionId ));
			@SuppressWarnings("unchecked")
			List<QuestionVotes> votes = newTags.list();
			results = new HashSet<QuestionVotes>(votes);

		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return results;
	}
}