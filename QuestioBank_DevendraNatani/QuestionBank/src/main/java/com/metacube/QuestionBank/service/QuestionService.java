/**service interface for question service
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.service;

import java.util.List;

import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.User;

public interface QuestionService {

	public int addQuestion(Question question, List<String> tagList);

	public List<Question> listQuestions();

	public List<Question> listQuestions(int tagId);

	public Question getQuestionById(int questionId);

	public List<Question> getRecentQuestions(int tagId, int offset,
			int noOfRecords);

	public List<Question> getUnAnsweredQuestions(int tagId, int offset,
			int noOfRecords);

	public List<Question> getClosedQuestions(int tagId, int offset,
			int noOfRecords);

	public List<Question> getActiveQuestions(int tagId, int offset,
			int noOfRecords);

	public Popularity disLikeQuestion(Question question, User user);

	public Popularity likeQuestion(Question question, User user);

	public List<Question> searchByTitle(String input);
	
	public boolean closeQuestion(int questionId ,String reason);

}