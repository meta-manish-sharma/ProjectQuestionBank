/**model class for questions votes
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.Parameter;


import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name="questionVotes")

public class QuestionVotes {

	@Id
	@Column(name = "q_vote_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int QVoteId;

	@Column
	private int status;	//1 for like 0 for dislike

	@Column(name = "question_id" , nullable = false)
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "question"))
	private int questionId;

	@Column(name = "user_id" , nullable = false)
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	private int userId;

	/**
	 * constructor for default number of votes = 0
	 */
	public QuestionVotes() {
		this.status = 0;
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
		QuestionVotes other = (QuestionVotes) obj;
		if (QVoteId != other.QVoteId)
			return false;
		if (questionId != other.questionId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/**
	 * over riding the hashCode method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + QVoteId;
		result = prime * result + questionId;
		result = prime * result + userId;
		return result;
	}

	/**
	 * over riding the toString method
	 */
	@Override
	public String toString() {
		return "QuestionVotes [QVoteId=" + QVoteId + ", status=" + status
				+ ", questionId=" + questionId + ", userId=" + userId + "]";
	}
}