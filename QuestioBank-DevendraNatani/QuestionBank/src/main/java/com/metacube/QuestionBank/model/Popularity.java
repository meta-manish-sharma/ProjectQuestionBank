/**
 * model class for handling the popularity attributes of the question based on upvotes and downvotes
 * 
 *  @author Team Devendra
 */
package com.metacube.QuestionBank.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
//table annotation for creating table in the database
@Table(name = "popularity")
public class Popularity implements Serializable{

	private static final long serialVersionUID = 3786769624835285037L;

	// annotations for setting properties for the columns of the table
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "popularity_id")
	private Integer popularityId;

	@Column(name = "up_votes")
	private Integer upVotes;

	@Column(name = "down_votes")
	private Integer downVotes;

	//private int difference;
	/*	@Column(name = "popularity_identity", unique = true, nullable = false)
	private String popularityIdentity;
	 */
	public Popularity() {
		this.upVotes = 0;
		this.downVotes = 0;
		//this.difference = 0;
	}
	/**
	 * over riding the equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Popularity other = (Popularity) obj;
		if (popularityId == null) {
			if (other.popularityId != null)
				return false;
		} else if (!popularityId.equals(other.popularityId))
			return false;
		return true;
	}
	
	/**
	 * over riding the hash code method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((popularityId == null) ? 0 : popularityId.hashCode());
		return result;
	}

	/**
	 * over riding the hash code method
	 */
	@Override
	public String toString() {
		return "Popularity [popularityId=" + popularityId + ", upVotes=" + upVotes + ", downVotes=" + downVotes + "]";
	}
}