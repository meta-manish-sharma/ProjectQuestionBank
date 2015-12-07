/**DAO (Data Access Object) interface for popularity
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.List;

import com.metacube.QuestionBank.model.Popularity;

public interface PopularityDao {

	/**
	 * 
	 * @param popularity
	 * @return
	 */
	public boolean addPopularity(Popularity popularity);

	/**
	 * 
	 * @param popularityId
	 * @return
	 */
	public Popularity getPopularity(Integer popularityId);
}