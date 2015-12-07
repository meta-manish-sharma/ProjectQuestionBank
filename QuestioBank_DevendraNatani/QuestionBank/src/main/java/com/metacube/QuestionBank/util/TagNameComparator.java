/**utility class for comparing tag names
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.Comparator;

import com.metacube.QuestionBank.model.Tag;

public class TagNameComparator implements Comparator<Tag> {

	/**
	 * method for comparing 2 tag names
	 */
	public int compare(Tag tag1, Tag tag2) {

		//returning the result after comparing tag names
		return tag1.getTagName().compareToIgnoreCase(tag2.getTagName());
	}
}