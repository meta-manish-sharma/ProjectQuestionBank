/**utility class for advanced search
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.ArrayList;
import java.util.List;

import com.metacube.QuestionBank.dto.QuestionDTO;
import com.metacube.QuestionBank.model.Question;

import lombok.Data;

@Data
public class AdvanceSearch {

	//question title data member
	private String questionTitle;

	//tag name data member
	private String tagName;

	//user name data member
	private String userName;	

	/**method for converting tags string to tags list
	 * 
	 * @param tagList
	 * @return
	 */
	public static List<String> tagListConvertor(String tagList) {
		String[] tags = tagList.split(",");
		List<String> tagSet = new ArrayList<String>();

		for(int count = 0 ; count < tags.length; count++) {
			String tag = tags[count];
			tag = tag.trim();
			tagSet.add(tag);
		}
		return tagSet;
	}
}