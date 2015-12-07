/**class for converting the DTO to normal object
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dto.dtoConvertor;

import java.util.ArrayList;
import java.util.List;

import com.metacube.QuestionBank.dto.QuestionDTO;
import com.metacube.QuestionBank.model.Question;

public class DTOConvertor {
	
	/**method for converting DTO to normal object
	 * 
	 * @param questionDTO
	 * @param question
	 * @return
	 */
	public List<String> questionConvertor(QuestionDTO questionDTO,Question question) {
		
		//setting the question title using the DTO
		question.setQuestionTitle(questionDTO.getQuestionTitle());
		
		//setting the question body using the DTO
		question.setQuestionBody(questionDTO.getQuestionBody());
		
		//tag list for tags related to the question
		String tagList = questionDTO.getTagList();
		
		//converting tag string to string array
		String[] tags = tagList.split(",");
		List<String> tagSet = new ArrayList<String>();
		
		//adding tags to tag set after removing leading and trailing white spaces
		for(int count = 0 ; count < tags.length; count++) {
			String tag = tags[count];
			tag = tag.trim();
			tagSet.add(tag);
		}
		
		//returning the tag set
		return tagSet;
	}
}