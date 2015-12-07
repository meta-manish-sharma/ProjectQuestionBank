/**model class for questions details
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
@Table (name = "question_detail")
public class QuestionDetail implements Serializable {

	private static final long serialVersionUID = -4555111823870540486L;

	@Id
	@Column(name ="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name = "closing_reason")
	private String closingReason;

	@Column(name = "status")
	private String status;

	public QuestionDetail() {
		this.status = "open";
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
		if (getClass() != obj.getClass()){
			return false;
		} 
		return true;
	}

	/**
	 * over riding the hashCode method
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
		return "QuestionDetail [Id=" + id + ", closingReason=" + closingReason + ", status="
				+ status + "]";
	}
}