/**DAO (Data Access Object) interface for questions
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.List;

import com.metacube.QuestionBank.model.Question;

public interface QuestionDao {

	/**
	 * 
	 * @param question
	 * @return
	 */
	public int addQuestion(Question question);

	/**
	 * 
	 * @return
	 */
	public List<Question> listQuestions();

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public List<Question> listQuestions(int tagId);

	/**
	 * 
	 * @param questionId
	 * @return
	 */
	public Question getQuestionById(int questionId);

	/**
	 * 
	 * @param tagId
	 * @param offset
	 * @param noOfRecords
	 * @return
	 */
	public List<Question> getRecentQuestions(int tagId,int offset, int noOfRecords);

	/**
	 * 
	 * @param tagId
	 * @param offset
	 * @param noOfRecords
	 * @return
	 */
	public List<Question> getUnAnsweredQuestions(int tagId, int offset, int noOfRecords);

	/**
	 * 
	 * @param integer
	 * @return
	 */
	public Question getQuestionByDetailId(Integer integer);

	/**
	 * 
	 * @param tagId
	 * @param offset
	 * @param noOfRecords
	 * @return
	 */
	public List<Question> getActiveQuestions(int tagId, int offset, int noOfRecords);

	/**
	 * 
	 * @param tagId
	 * @param offset
	 * @param noOfRecords
	 * @return
	 */
	public List<Question> getClosedQuestions(int tagId, int offset, int noOfRecords);

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public int getClosedQuestionsCount(int tagId);

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public int getActiveQuestionsCount(int tagId);

	/**
	 * 
	 * @param tagId
	 * @return
	 */
	public int getUnAnsweredQuestionsCount(int tagId);

	/**
	 * 
	 * @param tagId
	 * @param offset
	 * @param noOfRecords
	 * @return
	 */
	public List<Question> listQuestions(int tagId, int offset, int noOfRecords);
}