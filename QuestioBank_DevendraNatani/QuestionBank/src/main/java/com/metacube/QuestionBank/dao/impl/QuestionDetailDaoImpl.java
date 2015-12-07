/**DAO (Data Access Object) class implementation for question details
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.QuestionDetailDao;
import com.metacube.QuestionBank.model.QuestionDetail;

@Repository
public class QuestionDetailDaoImpl implements QuestionDetailDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(QuestionDetailDaoImpl.class);

	//error message
	private static final String MSG = "Exception in QuestionDetailDaoImpl";

	/**
	 * method to add question details in the database
	 */
	public boolean addQuestionDetail(QuestionDetail questionDetail) {
		boolean flag = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(questionDetail);
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
	 * method to list all question details
	 */
	@SuppressWarnings("unchecked")
	public Set< QuestionDetail> listQuestionDetails() {
		Set<QuestionDetail> listQuestionDetails = null;
		try {
			listQuestionDetails  =  new HashSet< QuestionDetail>((List< QuestionDetail>) sessionFactory.getCurrentSession().createCriteria(QuestionDetail.class).list());
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
		return listQuestionDetails;
	}

	/**
	 * method to get question details by ID
	 */
	public QuestionDetail getQuestionDetailById(int questionId) {
		QuestionDetail detail = null;
		try {
			Criteria newAnswers = sessionFactory.getCurrentSession().createCriteria( QuestionDetail.class);
			newAnswers.add(Restrictions.eq("question_Id", questionId ));
			@SuppressWarnings("unchecked")
			List< QuestionDetail> results = newAnswers.list();
			if(results.isEmpty()) {
				return detail;
			}
			detail = results.get(0);
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
		return detail;
	}

	/**
	 * method to get closed questions from the database
	 */
	public Set<Integer> getClosedQuestions() {
		Set<Integer> list = new HashSet<Integer>();
		try {
			Criteria newAnswers = sessionFactory.getCurrentSession().createCriteria( QuestionDetail.class);
			newAnswers.add(Restrictions.eq("status", "closed" ));
			@SuppressWarnings("unchecked")
			Set< QuestionDetail> results = (Set<QuestionDetail>) newAnswers.list();
			if(!(results == null)) {
				Iterator<QuestionDetail> iterator = results.iterator();
				while (iterator.hasNext()) {
					QuestionDetail questionDetail = (QuestionDetail) iterator.next();
					list.add(questionDetail.getId());
				}
			}
		} catch (HibernateException e) {
			list = null;
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			list = null;
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return list;
	}
}