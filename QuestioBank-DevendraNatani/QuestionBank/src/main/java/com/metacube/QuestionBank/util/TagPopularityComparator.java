/**utility class for comparing tag popularity
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.Comparator;

import com.metacube.QuestionBank.model.Tag;

public class TagPopularityComparator implements Comparator<Tag> {

	/**
	 * method for comparing popularity of 2 tags
	 * based on the number of questions containing that tag
	 */
	public int compare(Tag tag1, Tag tag2) {
		int count1 = tag1.getQuestionList().size();
		int count2 = tag2.getQuestionList().size();
		
		if(count1 < count2) {	//comparing number of questions having that tag and deciding the popularity
			return 1;
		} else if(count1 == count2){
			 return tag1.getTagName().compareToIgnoreCase(tag2.getTagName());
		} else {
			return -1;
		}
	}
}