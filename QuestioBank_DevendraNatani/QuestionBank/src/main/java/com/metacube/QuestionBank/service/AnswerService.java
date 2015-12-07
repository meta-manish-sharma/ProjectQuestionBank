/**service interface for answer service class
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.service;

import java.util.List;

import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.User;

public interface AnswerService {

	public boolean addAnswer(Answer answer);

	public Answer getAnswerById(Integer answerId);

	public Popularity disLikeAnswer(Answer answer, User user);

	public Popularity likeAnswer(Answer answer, User user);
	
	public boolean acceptAnswer(int answerId);

}
