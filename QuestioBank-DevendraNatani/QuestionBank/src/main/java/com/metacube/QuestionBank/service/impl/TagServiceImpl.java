package com.metacube.QuestionBank.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metacube.QuestionBank.dao.TagDao;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.service.TagService;


@Service

public class TagServiceImpl implements TagService {

	@Autowired
	private TagDao tagDao;
	
	private static Logger logger = Logger.getLogger(TagServiceImpl.class);

	private static final String MSG="Exception in TagServiceImpl";

	
	@Transactional
	public boolean addTag(Tag tag) {
		boolean flag = false;
		
		try {
			tagDao.addTag(tag);
			flag = true;
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return flag;
	}
	
	@Transactional
	public List<Tag> listTags() {
		List<Tag> listTags = null;
		
		try {
			listTags = tagDao.listTags();
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		
		return listTags;
	}

	@Transactional
	public Tag getTag(Integer tagId) {
		Tag tag = null;
		
		try {
			tag = tagDao.getTag(tagId);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return tag;
	}
	

	public Tag getTagByName(String tagName) {
		Tag tag = null;
		
		try {
			tag = tagDao.getTagByName(tagName);
		} catch (Exception e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return tag;
	}
	@Transactional
	public List<String> getTagList() {
		return tagDao.getTagList();
	}

	@Transactional 
	public List<Tag> listTagsByPage(int offset, int noOfRecords) {
		return tagDao.listTagsByPage(offset, noOfRecords);
	}
	
}