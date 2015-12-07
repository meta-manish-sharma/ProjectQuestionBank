/**DAO (Data Access Object) interface for answers
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.List;

import com.metacube.QuestionBank.model.Answer;

public interface AnswerDao {
	
	/**
	 * 
	 * @param answer
	 * @return
	 */
	public int addAnswer(Answer answer);

	/**
	 * 
	 * @param answerId
	 * @return
	 */
	public Answer getAnswer(Integer answerId);
}