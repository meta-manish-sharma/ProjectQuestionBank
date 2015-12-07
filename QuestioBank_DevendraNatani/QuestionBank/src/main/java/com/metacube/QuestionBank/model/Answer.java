/**model class for answers
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
//table annotation for creating table in the database
@Table(name = "answer")
public class Answer implements Serializable  {

	private static final long serialVersionUID = 7325342387250970307L;

	//annotations for setting properties for the columns of the table
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	//column annotation for column name in the database table
	@Column(name = "answer_id")
	private Integer answerId;

	@Column(name = "answer_body", columnDefinition = "text")
	private String answerBody;

	@Column(name="post_time", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Timestamp postTime;

	//one to one mapping for database tables
	@OneToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)	
	private User user;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="question_id" , nullable = false)
	private Question question;

	@OneToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER)
	@JoinColumn(name = "popularity_id" , nullable=false)
	private Popularity popularity;

	@OneToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER)
	@JoinColumn(name = "answer_Detail_id" , nullable=false)
	private AnswerDetail answerDetail;

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
		Answer other = (Answer) obj;
		if (answerId == null) {
			if (other.answerId != null)
				return false;
		} else if (!answerId.equals(other.answerId))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question)) {
			return false;
		}
		return true;
	}

	/**
	 * over riding the hash code method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerId == null) ? 0 : answerId.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		//result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	/**
	 * over riding the toString method
	 */
	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", answerBody=" + answerBody + ", postTime=" + postTime + ", user="
				+ user + ", question=" + question + ", popularity=" + popularity + ", answerDetail=" + answerDetail
				+ "]";
	}
}