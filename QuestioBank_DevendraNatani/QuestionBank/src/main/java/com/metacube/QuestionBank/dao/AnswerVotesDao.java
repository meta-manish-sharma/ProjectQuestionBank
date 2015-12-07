/**DAO (Data Access Object) interface for answers
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.Set;

import com.metacube.QuestionBank.model.AnswerVotes;
import com.metacube.QuestionBank.model.QuestionVotes;

public interface AnswerVotesDao {
	
	/**method to add answer votes in database
	 * 
	 * @param answerVotes
	 */
	public boolean addAnswerVotes(AnswerVotes answerVotes);
	
	/**method to get answer votes by answer id
	 * 
	 * @param answerId
	 * @return
	 */
	public Set<AnswerVotes> getAnswerVotesByAnswerId(int answerId);
}