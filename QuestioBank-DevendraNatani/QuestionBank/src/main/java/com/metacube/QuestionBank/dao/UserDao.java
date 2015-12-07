/**DAO (Data Access Object) interface for users
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dao;

import java.util.List;
import java.util.Set;

import com.metacube.QuestionBank.model.User;

public interface UserDao {

	public boolean addUser(User user);

	public Set<User> listUsers();

	public List<User> getUsers(int offset, int noOfRecords);

	public User getUser(String email);

	public List<String> getUserList();

	public User getUserById(int userId);
}