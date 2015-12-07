/**model class for tags
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "tag")
public class Tag implements Serializable {

	private static final long serialVersionUID = -7618932046855540869L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "tag_id")
	private Integer tagId;

	@Column(name = "tag_name" , unique = true)
	private String tagName;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER,mappedBy="tagList")
	private Set<Question> questionList = new HashSet<Question>();

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
		Tag other = (Tag) obj;
		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId))
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
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
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
		return result;
	}

	/**
	 * over riding the toString method
	 */
	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", tagName=" + tagName + ", questionList=" + questionList + "]";
	}
}