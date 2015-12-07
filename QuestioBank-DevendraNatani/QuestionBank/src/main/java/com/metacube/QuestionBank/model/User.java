/**model class for users
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;





import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 3770375519104056642L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Integer userId;

	@Column(name="user_name")
	private String userName;

	@Column(name = "email" ,unique = true)
	private String email;

	@Column(name = "user_status",columnDefinition = "VARCHAR(40)  DEFAULT 'Unblocked' ")
	private String userStatus;

	@Column(name="is_admin",columnDefinition = "Boolean  DEFAULT 0")
	private Boolean isAdmin;

	@Column(name="blocking_reason",columnDefinition = "VARCHAR(100)  DEFAULT null")
	private String blockingReason;

	@Column(name="lastloggedintime",columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Timestamp lastLoggedInTime;

	@Column(name="image_url",columnDefinition = "VARCHAR(100)")
	private String imageURL;

	//set to store the questions asked by the user
	@JsonIgnore
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	private Set<Question> questionList=new HashSet<Question>();

	//set to store the answers posted by the user
	@JsonIgnore
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	private Set<Answer> answerList=new HashSet<Answer>();

	/**
	 * default constructor to create new user
	 * new user is not an admin and he is unblocked
	 */
	public User() {
		isAdmin = false;
		userStatus = "Unblocked";
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/**
	 * over riding the toString method
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", userStatus=" + userStatus
				+ ", isAdmin=" + isAdmin + ", blockingReason=" + blockingReason + "]";
	}
}