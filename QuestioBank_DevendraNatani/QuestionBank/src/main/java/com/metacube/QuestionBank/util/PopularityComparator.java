/**utility class for managing the popularity of a question
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.Comparator;

import com.metacube.QuestionBank.model.Question;

public class PopularityComparator implements Comparator<Question> {

	/**
	 * method for comparing the popularity of 2 questions
	 */
	public int compare(Question question1, Question question2) {
		
		//popularity of question 1
		int popularity1 = question1.getPopularity().getUpVotes() - question1.getPopularity().getDownVotes();

		//popularity of question 2
		int popularity2 = question2.getPopularity().getUpVotes() - question2.getPopularity().getDownVotes();
		
		//comparing the 2 popularity values
		if(popularity1 == popularity2) {	//if same value then comparing questions by ID
			if(question1.getQuestionId() < question2.getQuestionId()) {
				return 1;
			} else {
				return -1;
			}
		} else if(popularity1 > popularity2) {	//question 1 is more popular than question 2
			return 1;
		} else {	//question 2 is more popular than question 1
			return -1;
		}
	}
}