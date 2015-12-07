/**DAO (Data Access Object) interface for question votes
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.Set;

import com.metacube.QuestionBank.model.QuestionVotes;

public interface QuestionVotesDao {
	
	public boolean addQuestionVotes(QuestionVotes questionVotes);
	
	public Set<QuestionVotes> getQuestionVotesByQuestionId(int questionId);
}
