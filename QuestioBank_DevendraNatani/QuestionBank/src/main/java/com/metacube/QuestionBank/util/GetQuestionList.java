/**utility class for getting questions related to a particular tag
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.Tag;

public class GetQuestionList {

	/**method for getting list of question for a particular tag
	 * 
	 * @param questionlistTemp
	 * @param tagId
	 * @return
	 */
	public List<Question> getQuestionListByTagId(List<Question> questionlistTemp, int tagId) {
		List<Question> questionlist=new ArrayList<Question>();
		
		for(Question question : questionlistTemp) {
			
			Set<Tag> tagList = question.getTagList();
			System.out.println("tagList is" +tagList);
			
			for(Tag tag : tagList) {
				if(tag.getTagId() == tagId) {
					System.out.println("question is" + question);
					questionlist.add(question);
					break;
				}
			}
		}
		//returning the questions list for a particular tag
		return questionlist;
	}
}