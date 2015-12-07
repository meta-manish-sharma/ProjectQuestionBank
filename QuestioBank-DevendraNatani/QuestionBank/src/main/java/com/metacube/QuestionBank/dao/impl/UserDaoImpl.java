/**DAO (Data Access Object) class implementation for users
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.metacube.QuestionBank.dao.UserDao;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	//session factory object
	@Autowired
	private SessionFactory sessionFactory;

	//logger object
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	//error message
	private static final String MSG = "Exception in UserDaoImpl";

	/**
	 * method to add user in the database
	 */
	public boolean addUser(User user) {

		boolean flag = false;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			flag = true;
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * method to get user by email address
	 */
	public User getUser(String email) {

		User user = null;

		try {
			Criteria newUsers = sessionFactory.getCurrentSession()
					.createCriteria(User.class);
			newUsers.add(Restrictions.eq("email", email));
			@SuppressWarnings("unchecked")
			List<User> results = newUsers.list();
			if (results.isEmpty()) {
				return user;
			}
			user = results.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * method to get user by ID
	 */
	public User getUserById(int userId) {

		User user = null;

		try {
			Criteria newUsers = sessionFactory.getCurrentSession()
					.createCriteria(User.class);
			newUsers.add(Restrictions.eq("userId", userId));
			@SuppressWarnings("unchecked")
			List<User> results = newUsers.list();
			if (results.isEmpty()) {
				return user;
			}
			user = results.get(0);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}
		return user;

	}

	/**
	 * method to get list of all the users from the database
	 */
	@SuppressWarnings("unchecked")
	public Set<User> listUsers() {
		Set<User> usersList = null;

		try {
			List<User> users = (List<User>) sessionFactory.getCurrentSession()
					.createCriteria(User.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			usersList = new HashSet<User>(users);
		} catch (HibernateException e) {
			logger.error(Thread.currentThread().getStackTrace()[2]
					.getLineNumber());
			logger.error(MSG, e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(MSG, e);
			e.printStackTrace();
		}

		return usersList;
	}

	/**
	 * method to get list of users by email
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUserList() {
		List<String> userNames = null;
		Session sessionObject = sessionFactory.getCurrentSession();
		Criteria criteriaObject = sessionObject.createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteriaObject.setProjection(Projections.property("email"));
		userNames = criteriaObject.list();
		return userNames;
	}

	/**
	 * method to get users in paginated form
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsers(int offset, int noOfRecords) {
		List<User> usersList = null;
		int lastIndex = offset + noOfRecords;
		List<User> finalResult = new ArrayList<User>();
		int maxSize = 0;

		try {
			usersList = (List<User>) sessionFactory.getCurrentSession()
					.createCriteria(User.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			maxSize = usersList.size();
			if (lastIndex > maxSize) {
				lastIndex = maxSize;
			}
			finalResult = usersList.subList(offset, lastIndex);

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