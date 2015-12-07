/**model class for questions
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Data;


@Data
@Entity
@Table(name = "question")
public class Question implements Serializable {

	private static final long serialVersionUID = -8593531140331165047L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "question_id")
	private Integer questionId;

	@Column(name = "question_title")
	private String questionTitle;

	@Column (name = "question_body", columnDefinition = "Text")
	private String questionBody;

	@Column(name="post_time",columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Timestamp postTime;

	@ManyToOne(fetch=FetchType.EAGER,cascade = {CascadeType.ALL,CascadeType.REMOVE})
	@JoinColumn(name="user_id", nullable = false)
	private User user;	

	@JsonIgnore
	@OneToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER)
	@JoinColumn(name = "popularity_id" ,nullable = false)
	private Popularity popularity;

	@ManyToMany(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER)
	@JoinTable(name="question_tag", joinColumns={@JoinColumn(name="question_id")}, 
	inverseJoinColumns={@JoinColumn(name="tag_id")})
	private Set<Tag> tagList ;

	@JsonIgnore
	@OneToMany(mappedBy="question",fetch=FetchType.EAGER)
	private Set<Answer> answerList;

	@OneToOne(cascade = {CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER)
	@JoinColumn(name = "question_detail_id" ,nullable = false)
	private QuestionDetail questionDetail;
	
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
		Question other = (Question) obj;
		if (questionBody == null) {
			if (other.questionBody != null)
				return false;
		} else if (!questionBody.equals(other.questionBody))
			return false;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (questionTitle == null) {
			if (other.questionTitle != null)
				return false;
		} else if (!questionTitle.equals(other.questionTitle))
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
		result = prime * result + ((questionBody == null) ? 0 : questionBody.hashCode());
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((questionTitle == null) ? 0 : questionTitle.hashCode());
		return result;
	}

	/**
	 * over riding the toString method
	 */
	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionTitle=" + questionTitle + ", questionBody="
				+ questionBody 
				+ ", postTime=" + postTime 
				+ ", popularity=" + popularity 
				+ "]";
	}
}