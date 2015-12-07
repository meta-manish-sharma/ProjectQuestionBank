/**utility class for searching a question by title
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.Comparator;

import lombok.Data;

@Data
public class SearchQuestionByTitle implements Comparable<SearchQuestionByTitle> {

	//question title data member
	private String questionTitle;
	
	//question ID data member
	private int questionId;
	
	//priority data member
	private int priority;

	/**
	 * comparing the search query with database entries
	 */
	public int compareTo(SearchQuestionByTitle other) {
		if(this.priority < other.getPriority()) {
			return 1;
		} else if (this.priority == other.getPriority()) {
			if(this.questionId < other.getQuestionId()) {
				return 1;
			}else {
				return -1;
			}
		} else {
			return -1;
		}
	}
}