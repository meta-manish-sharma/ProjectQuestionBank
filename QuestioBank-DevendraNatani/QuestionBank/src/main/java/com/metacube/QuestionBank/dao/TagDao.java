/**DAO (Data Access Object) interface for tags
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.List;

import com.metacube.QuestionBank.model.Tag;

public interface TagDao {

	public int addTag(Tag tag);

	public List<Tag> listTags();

	public List<Tag> listTagsByPage(int offset, int noOfRecords);

	public Tag getTag(Integer tagId);

	public List<String> getTagList();

	public Tag getTagByName(String tagName);
}