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

import com.metacube.QuestionBank.dao.AnswerDetailDao;
import com.metacube.QuestionBank.model.AnswerDetail;

@Repository
public class AnswerDetailDaoImpl implements AnswerDetailDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(AnswerDetailDaoImpl.class);

	//error message
	private static final String MSG = "Exception in AnswerDetailDaoImpl";

	/**
	 * method for adding answer details to the database
	 */
	public boolean addAnswerDetail(AnswerDetail answerDetail) {
		boolean flag = false;

		try {
			//creating session object
			sessionFactory.getCurrentSession().saveOrUpdate(answerDetail);
			flag = true;
		} catch (HibernateException e) {
			//handling hibernate exception
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		//returning the flag for success/failure
		return flag;
	}

	/**
	 * method to get answer details from the database
	 */
	public AnswerDetail getAnswerDetailById(int answerId) {
		
		//new answer details object
		AnswerDetail answerDetail = null;

		try {
			Criteria newAnswers = sessionFactory.getCurrentSession().createCriteria(AnswerDetail.class);
			newAnswers.add(Restrictions.eq("answerId", answerId ));
			@SuppressWarnings("unchecked")
			List<AnswerDetail> results = newAnswers.list();
			if(results.isEmpty()) {	//if list is empty then no answer details present
				return answerDetail;
			}
			answerDetail = results.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		//returning the answer details for the answer
		return answerDetail;
	}
}