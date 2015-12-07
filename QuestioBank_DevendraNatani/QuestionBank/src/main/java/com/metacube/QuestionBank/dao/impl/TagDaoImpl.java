/**DAO (Data Access Object) class implementation for tags
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.TagDao;
import com.metacube.QuestionBank.model.Tag;

@Repository
public class TagDaoImpl implements TagDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	//error message
	private static final String MSG = "Exception in TagDaoImpl";

	/**
	 * method to add tag in the database
	 */
	public int addTag(Tag tag) {
		int tagId = -1;

		try {
			sessionFactory.getCurrentSession().save(tag);
			tagId = tag.getTagId();
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return tagId;
	}

	/**
	 * method to list all the tags from the database
	 */
	@SuppressWarnings("unchecked")
	public List<Tag> listTags() {
		List<Tag> listTags = null;

		try {
			listTags = sessionFactory.getCurrentSession()
					.createCriteria(Tag.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return listTags;
	}

	/**
	 * method to get tag from database by tag ID
	 */
	public Tag getTag(Integer tagId) {
		Tag tag = null;

		try {
			Criteria newTags = sessionFactory.getCurrentSession()
					.createCriteria(Tag.class);
			newTags.add(Restrictions.eq("tagId", tagId));
			@SuppressWarnings("unchecked")
			List<Tag> results = newTags.list();
			if (results.isEmpty()) {
				return tag;
			}
			tag = results.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return tag;
	}

	/**
	 * method to get tag from database by name
	 */
	public Tag getTagByName(String tagName) {
		Tag tag = null;

		try {
			Criteria newTags = sessionFactory.getCurrentSession()
					.createCriteria(Tag.class);
			newTags.add(Restrictions.eq("tagName", tagName));
			@SuppressWarnings("unchecked")
			List<Tag> results = newTags.list();
			if (results.isEmpty()) {
				return tag;
			}
			tag = results.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * method to get tags list from the database
	 */
	public List<String> getTagList() {
		List<String> tagNames = null;
		Session sessionObject = sessionFactory.getCurrentSession();
		Criteria criteriaObject = sessionObject.createCriteria(Tag.class);
		criteriaObject.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteriaObject.setProjection(Projections.property("tagName"));
		tagNames = criteriaObject.list();
		return tagNames;
	}

	/**
	 * method to display tags in paginated layout
	 */
	public List<Tag> listTagsByPage(int offset, int noOfRecords) {
		List<Tag> listTags = null;
		int lastIndex = offset + noOfRecords;
		int maxSize = 0;
		List<Tag> finalResult = null;
		try {
			listTags = sessionFactory.getCurrentSession()
					.createCriteria(Tag.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			maxSize = listTags.size();
			if (lastIndex > maxSize) {
				lastIndex = maxSize;
			}
			finalResult = listTags.subList(offset, lastIndex);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return finalResult;
	}
}