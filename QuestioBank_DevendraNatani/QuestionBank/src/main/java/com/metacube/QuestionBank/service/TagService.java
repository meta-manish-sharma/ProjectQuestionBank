package com.metacube.QuestionBank.service;

import java.util.List;

import com.metacube.QuestionBank.model.Tag;

public interface TagService {
	
	public boolean addTag(Tag tag);

	public List<Tag> listTags();
	
	public List<Tag> listTagsByPage(int offset, int noOfRecords);
	
	public Tag getTag(Integer tagId);
	
	public Tag getTagByName(String tagName);
	
	public List<String> getTagList();
}
