/**model class for answers details
 * 
 * @author Team Devendra
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

@Data
@Entity
//table annotation for creating table in the database
@Table(name = "answer_detail")
public class AnswerDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4634442814064968334L;

	//annotations for setting properties for the columns of the table
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	//column annotation for column name in the database table
	@Column
	private String status;

	//constructor of class for setting some default value
	public AnswerDetail() {
		this.status = "not accepted";
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

		return true;
	}
	/**
	 * over riding the hash code method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * over riding the toString method
	 */
	@Override
	public String toString() {
		return "AnswerDetail [Id=" + id  + ", status=" + status + "]";
	}


}
