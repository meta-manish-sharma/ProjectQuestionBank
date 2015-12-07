/**controller class for managing users
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;




import com.metacube.QuestionBank.auth.GoogleAuthService;
import com.metacube.QuestionBank.exception.UserDoesNotExistExeption;
import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.AnswerService;
import com.metacube.QuestionBank.service.QuestionService;
import com.metacube.QuestionBank.service.TagService;
import com.metacube.QuestionBank.service.UserService;

@Controller
public class UserController {

	//user service layer object
	@Autowired
	private UserService userService;

	//question service layer object
	@Autowired
	private QuestionService questionService;

	//answer service layer object
	@Autowired
	private AnswerService answerService;

	//tag service layer object
	@Autowired
	private TagService tagService;

	/**method for displaying user details on the jsp page
	 * 
	 * @param userId
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "profile.do", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam("userId") int userId, HttpServletRequest request,
			@RequestParam("page") int pageNo) {

		//number of records to be displayed per page
		int recordsPerPage = 3;

		//initial page displayed when the page is loaded
		int page = 1;
		page = pageNo;

		//getting session object
		HttpSession session = request.getSession(false);

		//new model and view type object
		ModelAndView mav = new ModelAndView();

		//new google oAuth service type object
		final GoogleAuthService authService = new GoogleAuthService();

		//login URL for google oAuth service
		String loginURL = authService.buildLoginUrl();

		//adding login URL to model and view object
		mav.addObject("loginURL", loginURL);

		//getting the list of tag names from the database using tag service layer's getTagList method
		List<String> tagNames = tagService.getTagList();

		//adding tag list to the mav object
		mav.addObject("tagNames", tagNames);

		//getting the list of user names from the database using user service layer's geUserList method
		List<String> userNames = userService.getUserList();

		//adding user name list to the mav object
		mav.addObject("userNames", userNames);

		//getting the entire details for a user using the user ID
		User userProfile = userService.getUserByUserId(userId);

		//getting the list of questions asked by the user
		Set<Question> list= userProfile.getQuestionList();
		List<Question> questionList = new ArrayList<Question>(list);

		//pagination data regarding number of records to be
		//displayed per page and the number of pages
		int noOfRecords = recordsPerPage;
		int offset = (page - 1) * recordsPerPage;
		int lastIndex = offset+ noOfRecords;
		int noOfPages = 0 ;

		//complete list of questions asked by the user
		List<Question> finalResult = null;

		noOfRecords = questionList.size();
		int maxSize = questionList.size();

		if(lastIndex > maxSize) {
			lastIndex = maxSize;
		}

		//displaying paginated results
		finalResult = questionList.subList(offset, lastIndex);

		//total number of pages for questions
		noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

		//total number of pages for answers
		int AnswerNoOfPages = (int) Math.ceil(userProfile.getAnswerList().size() * 1.0 / recordsPerPage);

		//checking session value
		if(session != null && session.getAttribute("userObject") != null) {	//if session is not null

			//getting the user details from the database
			User user = (User)session.getAttribute("userObject");

			//getting user privileges from session object
			String privilege = (String)session.getAttribute("privilege");

			//adding the user object to the mav object
			mav.addObject("userObject", user);

			//adding the user privileges to the mav object
			mav.addObject("privilege",privilege);
		}

		//adding the number of pages for questions to the mav object
		mav.addObject("noOfPages", noOfPages);

		//adding the number of pages for answers to the mav object
		mav.addObject("AnswerNoOfPages", AnswerNoOfPages);

		//adding the current page number to the mav object
		mav.addObject("currentPage", page);

		//adding the complete user profile to the mav object
		mav.addObject("userProfile", userProfile);

		//adding the final result for pagination to the mav object
		mav.addObject("questionList", finalResult);

		//setting the view name as UserProfile
		mav.setViewName("UserProfile");

		//returning the model and view object
		return mav;
	}

	/**method for blocking the user
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/blockOrUnblockUser.do", method = RequestMethod.GET)
	public ModelAndView blockUser(@RequestParam("userId") int userId, HttpServletRequest request) {

		//setting a string message
		String message = "Your Operation complete";

		//handling the user not exists exception
		try {
			userService.blockUser(userId);
		} catch (UserDoesNotExistExeption e) {
			message = e.getMessage();
		}

		//getting http session object
		HttpSession session = request.getSession(false);

		//getting user details from the database
		User user = (User)session.getAttribute("userObject");

		//getting user privileges from session object
		String privilege = (String)session.getAttribute("privilege");

		//getting the user profile using user ID
		User userProfile = userService.getUserByUserId(userId);

		//getting list of questions asked by the user
		Set<Question> questionList= userProfile.getQuestionList();

		//getting list of answers posted by the user
		Set<Answer> answerList= userProfile.getAnswerList();

		//new model and view type object
		ModelAndView mav = new ModelAndView();

		//google oAuth service object
		final GoogleAuthService authService = new GoogleAuthService();

		//login URL for googleoAuth service
		String loginURL = authService.buildLoginUrl();

		//adding the login URL to the mav object
		mav.addObject("loginURL", loginURL);

		//list of tag names from the database
		List<String> tagNames = tagService.getTagList();

		//adding the tags list to the mav object
		mav.addObject("tagNames", tagNames);

		//list of user names from the database
		List<String> userNames = userService.getUserList();

		//adding the users list to the mav object
		mav.addObject("userNames", userNames);

		//adding the user object to the mav object
		mav.addObject("userObject", user);

		//adding the user privileges to the mav object
		mav.addObject("privilege", privilege);

		//adding the user profile to the mav object
		mav.addObject("userProfile", userProfile);

		//adding the question list to the mav object
		mav.addObject("questionList", questionList);

		//adding the answers list to the mav object
		mav.addObject("answerList", answerList);

		//setting the view name to user profile
		mav.setViewName("UserProfile");

		//returning the model and view type object
		return mav;
	}

	/**method for viewing my answers in user profile
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "myAnswers.do", method = RequestMethod.GET)
	public ModelAndView myAnswer(HttpServletRequest request, @RequestParam("page") int pageNo) {

		//getting the session object
		HttpSession session = request.getSession(false);

		//new user object
		User user = null;
		String privilege = null;

		//hash map to store all the details and send to jsp page
		Map<String, Object> model = new HashMap<String, Object>();

		//checking session value and value inside the session object
		if(session == null || session.getAttribute("userObject") == null || 
				session.getAttribute("privilege") == null){
			//if session is null return new model andview type object
			return new ModelAndView("index", model);
		}

		//getting user object from the session object
		user = (User)session.getAttribute("userObject");

		//getting user privileges
		privilege = (String)session.getAttribute("privilege");

		//pagination information begin
		int page = pageNo;
		int recordsPerPage = 3;

		int noOfRecords = recordsPerPage;
		int offset = (page - 1) * recordsPerPage;
		int lastIndex = offset+ noOfRecords;
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		//pagination information end

		//list of answers by the user from the database
		Set<Answer> answers = user.getAnswerList();

		//list of questions by the user
		List<Question> questionList = null;

		//is answers list is not null
		if(!(answers == null)) {
			//creating new questions list
			questionList = new ArrayList<Question>();

			//iterator object for looping through the list
			Iterator<Answer> listIterator = answers.iterator();

			//looping through the list and adding question to the list
			while (listIterator.hasNext()) {
				Answer answer = (Answer) listIterator.next();
				questionList.add(answer.getQuestion());
			}
		}

		//pagination data begin
		noOfRecords = questionList.size();
		int maxSize = questionList.size();

		if(lastIndex > maxSize) {
			lastIndex = maxSize;
		}

		List<Question> finalResult = questionList.subList(offset, lastIndex);
		noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		//pagination data end

		//adding all the required details to the hash map
		model.put("userQuestionList", finalResult);
		model.put("session", session);
		model.put("userObject", user);
		model.put("privilege", privilege);
		model.put("noOfPages", noOfPages);
		model.put("currentPage", page);

		//returning the new model and view type object
		return new ModelAndView("UserAnswerList", model);
	}

	/**method to display my question on the user profile page
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "myQuestions.do", method = RequestMethod.GET)
	public ModelAndView myQuestion(HttpServletRequest request, @RequestParam("page") int pageNo) {

		//getting the session object
		HttpSession session = request.getSession(false);

		//new user object
		User user = null;
		String privilege = null;

		//hash map to store all the details and send to jsp page
		Map<String, Object> model = new HashMap<String, Object>();

		//checking session value
		if(session == null || session.getAttribute("userObject") == null || 
				session.getAttribute("privilege") == null){

			//if session is null or value inside session is null then return new mav object
			return new ModelAndView("index", model);
		}

		//getting user details from the database
		user = (User)session.getAttribute("userObject");

		//getting user privileges from session object
		privilege = (String)session.getAttribute("privilege");

		//pagination data begin
		int page = pageNo;
		int recordsPerPage = 3;

		int noOfRecords = recordsPerPage;
		int offset = (page - 1) * recordsPerPage;
		int lastIndex = offset + noOfRecords;
		int noOfPages = 0;
		//pagination data end

		//list of question asked by the user
		List<Question> questionList = null;

		//final redult
		List<Question> finalResult = null;

		//checking the value inside the user object
		if(user != null) {

			//getting list of questions from the database
			questionList = new ArrayList<Question>(user.getQuestionList());

			//pagination data begin
			noOfRecords = questionList.size();
			int maxSize = questionList.size();

			if(lastIndex > maxSize) {
				lastIndex = maxSize;
			}

			finalResult = questionList.subList(offset, lastIndex);
			noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			//pagination data end
		}

		//System.out.println("list is"+finalResult);

		//adding all the required data to the hash map
		model.put("userQuestionList", finalResult);
		model.put("session", session);
		model.put("userObject", user);
		model.put("privilege", privilege);
		model.put("noOfPages", noOfPages);
		model.put("currentPage", page);

		//returning new model and view type object
		return new ModelAndView("UserQuestionsList", model);
	}

	/**method to add new user
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addAdmin.do", method = RequestMethod.GET)
	public ModelAndView addUser(@RequestParam("userId") int userId,HttpServletRequest request) {
		String message = "Your Operation complete";

		//handling the user does not exists exception
		try {
			userService.addAdmin(userId);
		} catch (UserDoesNotExistExeption e) {
			message = e.getMessage();
		}
		

		//getting the session object
		HttpSession session = request.getSession(false);

		//getting user object from the database
		User user = (User)session.getAttribute("userObject");

		//getting user details using user ID
		User userProfile = userService.getUserByUserId(userId);

		//getting list of question asked by the user
		Set<Question> questionList= userProfile.getQuestionList();

		//getting list of answers posted by the user
		Set<Answer> answerList= userProfile.getAnswerList();

		//new model and view type object
		ModelAndView mav = new ModelAndView();

		//google oAuth service type object
		final GoogleAuthService authService = new GoogleAuthService();

		//login URL for google oAuth service
		String loginURL = authService.buildLoginUrl();

		//adding all the required data to the mav object
		mav.addObject("loginURL", loginURL);

		//getting list of tag names from the database
		List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);

		//getting list of user names from the database
		List<String> userNames = userService.getUserList();

		mav.addObject("userNames", userNames);
		mav.addObject("userObject",user);
		mav.addObject("privilege","admin");
		mav.addObject("userProfile",userProfile);
		mav.addObject("session",session);
		mav.addObject("questionList",questionList);
		mav.addObject("answerList",answerList);

		//setting the view name
		mav.setViewName("UserProfile");

		//returning the mav object
		return mav;
	}

	/**method to display all the user from the database on the jsp page
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value="/getUsers.do",method=RequestMethod.GET)
	public ModelAndView getUsers(HttpServletRequest request, @RequestParam("page") int pageNo) {

		//getting the http session object
		HttpSession session = request.getSession(false);
		
		//new model and view type object
		ModelAndView mav = new ModelAndView();
		
		// temporary list of uses from the database
		Set<User> usersTempList = userService.listUsers();

		//googleoAuth service type object
		final GoogleAuthService authService = new GoogleAuthService();
		
		//login URL for google oAuth service
		String loginURL = authService.buildLoginUrl();
		
		//adding all the required details to the mav object
		mav.addObject("loginURL", loginURL);
		
		//getting list of tag names from the database
		List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);

		//getting list of user names from the database
		List<String> userNames = userService.getUserList();
		mav.addObject("userNames", userNames);

		//pagination data begin
		int page = 1;
		int recordsPerPage = 9;
		page = pageNo;
		int noOfRecords = 0;
		noOfRecords = usersTempList.size();
		
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		List<User> users = userService.getUsers(((page - 1) * recordsPerPage), recordsPerPage);
		System.out.println("Final User List is" +users);
		//pagination data end
		
		//checking value in the session object
		if(session != null && session.getAttribute("userObject") != null) {	//if session is not null
			mav.addObject("noOfPages", noOfPages);
			mav.addObject("currentPage", page);
			
			//getting user object from the database
			User user = (User) session.getAttribute("userObject");
			
			//getting user privileges from the database
			String privilege = (String)session.getAttribute("privilege");
			
			mav.addObject("userObject", user);
			mav.addObject("privilege",privilege);
			mav.addObject("usersList", users);
			mav.setViewName("SearchUser");
			
			//returning the mav object
			return mav;
		} else {
			mav.addObject("noOfPages", noOfPages);
			mav.addObject("currentPage", page);
			mav.addObject("usersList",users);
			mav.setViewName("SearchUser");
			return mav;
		}

	}

	/**method to list all the answers posted by a particular user
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/AnswerListInProfile.do",method=RequestMethod.GET)
	public @ResponseBody List<Answer> allAnswers(@RequestParam("userId") int userId, 
			@RequestParam("page") int pageNo) {

		int recordsPerPage = 3;
		int page = pageNo;

		//getting user profile from the database using user ID
		User userProfile = userService.getUserByUserId(userId);
		
		//getting all the answers for posted by by the user
		Set<Answer> answerList= userProfile.getAnswerList();
		List<Answer> list = new ArrayList<Answer>(answerList);
		//System.out.println("list is " +list);

		//pagination data begin
		int noOfRecords = recordsPerPage;
		int offset = (page - 1) * recordsPerPage;
		int lastIndex = offset+ noOfRecords;
		List<Answer>finalResult = null;


		noOfRecords = list.size();
		int maxSize = list.size();
		if(lastIndex>maxSize){
			lastIndex = maxSize;
		}
		finalResult = list.subList(offset, lastIndex);
		//pagination data end
		
		//returning the final result
		return finalResult;

	}
}