/**	controller class for answer management
 * 
 * @author Team Dev
 */

package com.metacube.QuestionBank.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metacube.QuestionBank.auth.GoogleAuthService;
import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.AnswerService;
import com.metacube.QuestionBank.service.QuestionService;
import com.metacube.QuestionBank.service.TagService;
import com.metacube.QuestionBank.service.UserService;

@Controller
public class AnswerController {

	//create object for answer service layer class
	@Autowired
	private AnswerService answerService;

	//create object for question service layer class
	@Autowired
	private QuestionService questionService;

	

	//create object for tags service layer class
	@Autowired
	private TagService tagService;

	//create object for users service layer class
	@Autowired
	private UserService userService;

	/**method to save the answer of a question in the database
	 * 
	 * @param answer
	 * @param result
	 * @param questionId
	 * @param request
	 * @return
	 */
	//request mapping for the redirection URL and the post method
	@RequestMapping(value = "/postAnswer.do", method = RequestMethod.POST)
	public ModelAndView saveAnswer(@ModelAttribute("command") Answer answer,
			BindingResult result, @RequestParam("questionId") int questionId, HttpServletRequest request) {
		
		//to store the error message in case an error occurs
		String errorMessage = null;
		
		//http session object for a user after he has logged in using google account
		HttpSession userSession = request.getSession(false);
		
		//checking the value of the user session
		//if value is null then redirecting to login page
		if(userSession == null || userSession.getAttribute("userObject") == null) {
			return new ModelAndView("redirect:/getURL.do");
		}
		
		//creating new user object using the session created
		User userObject = (User)userSession.getAttribute("userObject");
		
		//user privileges denote whether the user is admin or not
		String privilege = (String)userSession.getAttribute("privilege");
		
		//getting the question using question ID from the database for which the user wants to provide an answer
		Question question = questionService.getQuestionById(questionId);
		
		//checking the answer provided by the user
		//if answer is null then prompting the user to re-enter the answer
		if(answer.getAnswerBody() == "") {
			//setting the error message to appropriate value
			errorMessage = "Please fill the answer";
		} else {			//if all values are fine then saving the answer in the database
			
			//saving question details
			answer.setQuestion(question);
			
			//saving user details
			answer.setUser(userObject);
			
			//calling service layer method for adding the answer to the database
			answerService.addAnswer(answer);
		}
		
		//hash map for setting the attributes and sending them to the jsp page
		Map<String, Object> model = new HashMap<String, Object>();
		
		//new google oAuth service object
		final GoogleAuthService authService = new GoogleAuthService();
		
		//login URL String for google login
		String loginURL = authService.buildLoginUrl();
		
		//setting the login URL in the hash map
		model.put("loginURL", loginURL);
		
		//array list to store all the tag names retrieved from the database
		//tag names are fetched using tagService layer object by calling the getTagList method
		List<String> tagNames = tagService.getTagList();

		//setting the list of tag names in the hash map
		model.put("tagNames", tagNames);

		//array list to store all the user names retrieved from the database
		//user names are fetched using userService layer object by calling the getUserList method
		List<String> userNames = userService.getUserList();
		
		//setting the list of user names in the hash map
		model.put("userNames", userNames);
		
		//new answer type object to store the answer for the question
		Answer answer1 = new Answer();
		
		//using the question ID to get the particular question from the database
		Question question2 = questionService.getQuestionById(questionId);
		
		//setting the error message in the hash map
		model.put("errorMessage", errorMessage);
		
		//setting the answer object in the hash map
		model.put("command", answer1);
		
		//setting the question object in the hash map
		model.put("question", question2);
		
		//setting the user object in the hash map
		model.put("userObject", userObject);
		
		//setting the user privileges in the hash map
		model.put("privilege", privilege);

		//returning the model and view object containing the hash map 
		//i.e. all the details for the answer, user and the question
		return new ModelAndView("questionDetails", model);
	}

	/**method for liking the question
	 * 
	 * @param answerId
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	//request mapping for the redirection URL and the post method
	@RequestMapping(value = "/answerUpVote.do",method = RequestMethod.GET)
	public void likeQuestion(@RequestParam("answerId") int answerId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		//getting the answer object from database using the answer ID
		Answer answer = answerService.getAnswerById(answerId);
		
		//getting the http session object
		HttpSession session = request.getSession(false);
		
		//creating new null user object
		User userObject = null;
		
		//checking the value of the session
		if(session != null && session.getAttribute("userObject") != null) {	//if session object is not null
			
			//getting user details from the existing session using the getAttribute method
			userObject = (User)session.getAttribute("userObject");
			
			//incrementing the like count of the answer
			//using the anserService layer's likeAnswer method
			Popularity popularity = answerService.likeAnswer(answer, userObject);
			
			
			//getting the number of up votes for the answer
			int upVotes = popularity.getUpVotes();
			
			//adding the value of up votes to the screen
			response.getWriter().append(upVotes + "");
		} else {	//if session object is null printing error message
			System.out.println("session empty");
		}
	}
	
	
	/**method for disliking the question
	 * 
	 * @param answerId
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	//request mapping for the redirection URL and the post method
	@RequestMapping(value = "/answerDownVote.do",method = RequestMethod.GET)
	public void disLikeQuestion(@RequestParam("answerId") int answerId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		//getting the answer object from database using the answer ID
		Answer answer = answerService.getAnswerById(answerId);
		
		//getting the http session object
		HttpSession session = request.getSession(false);
		
		//creating new null user object
		User userObject = null;
		
		//checking the value of the session
		if(session!=null && session.getAttribute("userObject") != null) {	//if session is not null
			
			//getting user details from the existing session using the getAttribute method
			userObject = (User)session.getAttribute("userObject");
			
			//incrementing the dislike count of the answer
			//using the anserService layer's disLikeAnswer method
			Popularity popularity = answerService.disLikeAnswer(answer, userObject);
			
			//getting the number of up votes for the answer
			int downVotes = popularity.getDownVotes();
						
			//addign the number of down votes to the screen
			response.getWriter().append(downVotes + "");
		} else {	//if session id null printing error message
			System.out.println("session empty");
		}
	}
	
	/**method to accept a particular answer for a question
	 * 
	 * @param answerId
	 * @return
	 */
	//request mapping for the redirection URL and the post method
	@RequestMapping(value = "/acceptAnswer.do", method = RequestMethod.GET)
	public ModelAndView acceptAnswer(@RequestParam("answerId") int answerId) {
		
		boolean isAccepted = answerService.acceptAnswer(answerId);
		Answer answer  = answerService.getAnswerById(answerId);
		int questionId = answer.getQuestion().getQuestionId();
		if(isAccepted) {
			questionService.closeQuestion(questionId, "Answer Accepted");
		}
		//redirecting the user to the question details page
		return new ModelAndView("redirect:getQuestion.do?questionId="+questionId);
	}
}