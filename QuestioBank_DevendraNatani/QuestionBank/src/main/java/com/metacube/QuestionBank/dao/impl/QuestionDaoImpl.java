/**DAO (Data Access Object) class implementation for question
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.QuestionDao;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.util.GetQuestionList;

@Repository
public class QuestionDaoImpl implements QuestionDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(QuestionDaoImpl.class);

	//error message
	private static final String MSG = "Exception in QuestionDaoImpl";

	//get question list object
	private GetQuestionList getQuestionList = new GetQuestionList();

	/**
	 * method to add question in the database
	 */
	public int addQuestion(Question question) {
		int quetionId = -1;

		try {
			sessionFactory.getCurrentSession().save(question);
			quetionId = question.getQuestionId();
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return quetionId;
	}

	/**
	 * method to list all the questions from the database
	 */
	@SuppressWarnings("unchecked")
	public List<Question> listQuestions() {
		return (List<Question>) sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	/**
	 * method to get question by ID
	 */
	public Question getQuestionById(int questionId) {
		Question question = null;

		try {
			Criteria newQuestions = sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			newQuestions.add(Restrictions.eq("id", questionId));
			@SuppressWarnings("unchecked")
			List<Question> result =(List<Question>) newQuestions.list();

			if(result.isEmpty()) {
				return question;
			}
			question = result.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return question;	
	}

	/**
	 * method to get question by details ID
	 */
	public Question getQuestionByDetailId(Integer questionId) {
		Question question = null;

		try {
			Criteria newQuestions = sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			newQuestions.add(Restrictions.eq("id", questionId));
			@SuppressWarnings("unchecked")
			List<Question> result =(List<Question>) newQuestions.list();

			if(result.isEmpty()) {
				return question;
			}
			question =  result.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return question;
	}

	/**
	 * method to list all the questions from the database
	 */
	@SuppressWarnings("unchecked")
	public List<Question> listQuestions(int tagId) {
		List<Question> questionlist = (List<Question>) sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		if(tagId!=0)
		{
			questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
		}
		return questionlist;
	}

	/**
	 * method to get the most recent question from the database
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Question> getRecentQuestions(int tagId, int offset, int noOfRecords) {
		List<Question> questionlist=new ArrayList<Question>();
		List<Question> finalResult = new ArrayList<Question>();
		int lastIndex = offset+ noOfRecords;
		int maxSize = 0;
		try {
			Criteria newQuestions = sessionFactory.getCurrentSession().createCriteria(Question.class);
			newQuestions.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			newQuestions.addOrder(Order.desc("postTime"));
			questionlist = (List<Question>)newQuestions.list();
			if(tagId != 0) {
				questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset, lastIndex);
			} else {
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset,(lastIndex));
			}
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return finalResult;
	}

	/**
	 * method to get unanswered question from the database
	 */
	@SuppressWarnings("unchecked")
	public List<Question> getUnAnsweredQuestions(int tagId, int offset, int noOfRecords) {
		List<Question> questionlist=null;
		List<Question> finalResult = new ArrayList<Question>();
		int lastIndex = offset+ noOfRecords;
		int maxSize = 0;
		try {
			List<Question> questionlistTemp = (List<Question>)sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			questionlist = new ArrayList<Question>();

			for (Question question : questionlistTemp) {
				if(question.getAnswerList().size() < 1){
					questionlist.add(question);
				}
			}

			if(tagId != 0) {
				questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset, lastIndex);
			} else {
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset,(lastIndex));
			}
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return finalResult;
	}

	/**
	 * method to get active questions from the database
	 */
	public List<Question> getActiveQuestions(int tagId, int offset, int noOfRecords) {
		List<Question> questionlist=null;
		List<Question> finalResult = new ArrayList<Question>();
		int lastIndex = offset + noOfRecords;
		int maxSize = 0;
		try {
			@SuppressWarnings("unchecked")
			List<Question> questionlistTemp = (List<Question>)sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			questionlist = new ArrayList<Question>();
			for (Question question : questionlistTemp) {
				if(question.getQuestionDetail().getStatus().equalsIgnoreCase("open")) {
					questionlist.add(question);				
				}
			}
			
			if(tagId != 0) {
				questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset, lastIndex);
			} else {
				maxSize = questionlist.size();
				if(lastIndex > maxSize){
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset,(lastIndex));
			}
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return finalResult;
	}

	/**
	 * method to get closed questions from the database
	 */
	public List<Question> getClosedQuestions(int tagId, int offset, int noOfRecords) {
		List<Question> questionlist=null;
		List<Question> finalResult = new ArrayList<Question>();
		int lastIndex = offset+ noOfRecords;
		int maxSize = 0;
		
		try {
			@SuppressWarnings("unchecked")
			List<Question> questionlistTemp = (List<Question>)sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			questionlist = new ArrayList<Question>();
			
			for (Question question : questionlistTemp) {
				System.out.println("q1 is " + question);
				if(question.getQuestionDetail().getStatus().equalsIgnoreCase("closed")) {
					questionlist.add(question);				
				}
			}

			if(tagId != 0) {
				questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset, lastIndex);
			} else {
				maxSize = questionlist.size();
				if(lastIndex > maxSize) {
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset,(lastIndex));
			}
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return finalResult;
	}

	/**
	 * method to get number of closed questions from the database
	 */
	public int getClosedQuestionsCount(int tagId) {
		int totalQuestion = 0;

		List<Question> questionlistTemp = (List<Question>)sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		List<Question> questionlist = new ArrayList<Question>();
		for (Question question : questionlistTemp) {
			System.out.println("q1 is "+question);
			if(question.getQuestionDetail().getStatus().equalsIgnoreCase("closed")){
				questionlist.add(question);				
			}
		}
		totalQuestion = questionlist.size();
		if(tagId!=0) {
			questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
			totalQuestion = questionlist.size();
		}
		return totalQuestion;
	}

	/**
	 * method to get number of active questions from the database
	 */
	public int getActiveQuestionsCount(int tagId) {
		int totalQuestion = 0;

		List<Question> questionlistTemp = (List<Question>)sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		List<Question> questionlist = new ArrayList<Question>();
		for (Question question : questionlistTemp) {
			if(question.getQuestionDetail().getStatus().equalsIgnoreCase("open")){
				questionlist.add(question);				
			}
		}
		totalQuestion = questionlist.size();
		if(tagId!=0) {
			questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
			totalQuestion = questionlist.size();
		}
		return totalQuestion;
	}

	/**
	 * method to get number of unanswered questions from the database
	 */
	public int getUnAnsweredQuestionsCount(int tagId) {
		int totalQuestion = 0;

		List<Question> questionlistTemp = (List<Question>)sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		List<Question> questionlist = new ArrayList<Question>();
		for (Question question : questionlistTemp) {
			if(question.getAnswerList().size()<1){
				questionlist.add(question);
			}
		}
		totalQuestion = questionlist.size();
		if(tagId!=0) {
			questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
			totalQuestion = questionlist.size();
		}
		return totalQuestion;
	}

	/**
	 * method to list all the questions
	 */
	@SuppressWarnings("unchecked")
	public List<Question> listQuestions(int tagId, int offset, int noOfRecords) {
		List<Question> questionlist=new ArrayList<Question>();
		List<Question> finalResult = new ArrayList<Question>();
		int lastIndex = offset+ noOfRecords;
		int maxSize = 0;
		try {
			questionlist = (List<Question>) sessionFactory.getCurrentSession().createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			if(tagId!=0)
			{
				questionlist = getQuestionList.getQuestionListByTagId(questionlist, tagId);
				maxSize = questionlist.size();
				if(lastIndex>maxSize){
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset, lastIndex);
			}
			else {
				maxSize = questionlist.size();
				if(lastIndex>maxSize){
					lastIndex = maxSize;
				}
				finalResult = questionlist.subList(offset,(lastIndex));
			}
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch(Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return finalResult;
	}
}