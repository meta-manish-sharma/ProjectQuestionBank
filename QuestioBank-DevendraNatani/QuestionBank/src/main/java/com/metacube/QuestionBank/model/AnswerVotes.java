/**model class for handling Answer Votes 
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
// table annotation for creating table in the database
@Table(name = "answerVotes")
public class AnswerVotes {

	// annotations for setting properties for the columns of the table
	@Id
	@Column(name = "a_vote_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int AVoteId;

	@Column
	private int status; // 1 for like 0 for dislike

	@Column(name = "answer_id", nullable = false)
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "answer"))
	private int answerId;

	@Column(name = "user_id", nullable = false)
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	private int userId;

	//Constructor for setting the default status value
	public AnswerVotes() {
		this.status = 0;
	}

}
