/**DAO (Data Access Object) interface for answers
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.Set;

import com.metacube.QuestionBank.model.AnswerDetail;

public interface AnswerDetailDao {
	
	public boolean addAnswerDetail(AnswerDetail answerDetail);

	public AnswerDetail getAnswerDetailById(int answerId); 
}