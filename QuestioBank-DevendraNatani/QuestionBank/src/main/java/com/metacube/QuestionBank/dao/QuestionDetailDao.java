/**DAO (Data Access Object) interface for questions details
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.Set;

import com.metacube.QuestionBank.model.QuestionDetail;

public interface QuestionDetailDao {

	/**
	 * 
	 * @param questionDetail
	 * @return
	 */
	public boolean addQuestionDetail(QuestionDetail questionDetail);

	/**
	 * 
	 * @return
	 */
	public Set<QuestionDetail> listQuestionDetails();

	/**
	 * 
	 * @param questionId
	 * @return
	 */
	public QuestionDetail getQuestionDetailById(int questionId); 

	/**
	 * 
	 * @return
	 */
	public Set<Integer> getClosedQuestions();
}