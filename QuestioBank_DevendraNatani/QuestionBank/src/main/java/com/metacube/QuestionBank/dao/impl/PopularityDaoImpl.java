/**DAO (Data Access Object) class implementation for popularity
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.swing.text.rtf.RTFEditorKit;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.PopularityDao;
import com.metacube.QuestionBank.dao.TagDao;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Tag;

@Repository
public class PopularityDaoImpl implements PopularityDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(PopularityDaoImpl.class);

	//error message
	private static final String MSG = "Exception in PopularityDaoImpl";

	/**
	 * method to add popularity in the database
	 */
	public boolean addPopularity(Popularity popularity) {
		boolean flag = false;

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(popularity);
			flag = true;
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * method to get the popularity by ID
	 */
	public Popularity getPopularity(Integer popularityId) {
		Popularity popularity = null;

		try {
			Criteria newPopularities = sessionFactory.getCurrentSession().createCriteria(Popularity.class);
			newPopularities.add(Restrictions.eq("popularityId",popularityId ));
			@SuppressWarnings("unchecked")
			List<Popularity> results = newPopularities.list();		
			if(results.isEmpty()) {
				return popularity;
			}
			popularity =  results.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2].getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return popularity;
	}
}