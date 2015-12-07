/**DAO (Data Access Object) class implementation for answers
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.AnswerDao;
import com.metacube.QuestionBank.model.Answer;

@Repository
public class AnswerDaoImpl implements AnswerDao {

	//session factory object for creating session objects
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(AnswerDaoImpl.class);

	//error message
	private static final String MSG = "Exception in AnswerDaoImpl";

	/**
	 * method for adding the answer in the database
	 */
	public int addAnswer(Answer answer) {

		int answerId = -1;

		try {
			//getting the session object
			sessionFactory.getCurrentSession().save(answer);

			//getting the answer using answer ID
			answerId = answer.getAnswerId();
		} catch (HibernateException e) {
			//handling hibernate exceptions
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		//returning the answer ID
		return answerId;
	}

	/**
	 * method for getting the answer from the database
	 */
	public Answer getAnswer(Integer answerId) {
		
		//criteria object for answer model class
		Criteria newAnswers = sessionFactory.getCurrentSession().createCriteria(Answer.class);
		newAnswers.add(Restrictions.eq("answerId", answerId ));
		@SuppressWarnings("unchecked")
		List<Answer> results = newAnswers.list();
		if(results.isEmpty()) {
			return null;
		}
		return results.get(0);
	}
}