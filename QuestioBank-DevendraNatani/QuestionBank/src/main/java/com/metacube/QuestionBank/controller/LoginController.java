/**controller class for login using google oAuth
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.metacube.QuestionBank.auth.GoogleAuthService;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.QuestionService;
import com.metacube.QuestionBank.service.TagService;
import com.metacube.QuestionBank.service.UserService;
import com.metacube.QuestionBank.util.SendEmailUtil;

@SessionAttributes("user")
@Controller
public class LoginController {

	//question service type object
	@Autowired
	private QuestionService questionService;

	//user service type object
	@Autowired
	UserService userService;

	//tag service type object
	@Autowired
	TagService tagService;

	//user http session object
	HttpSession userSession;

	//new user object
	User newUser;

	/**
	 * method for login with google oAuth
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = "/loginWithOauth", method = RequestMethod.GET)
	public ModelAndView oauthLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
					throws IOException, JSONException {

		//getting user session object
		userSession = request.getSession(true);
		ModelAndView mav = new ModelAndView("index");

		//user details name, email and image URL
		String name;
		String email;
		String image;

		//getting list of tag names from the database
		List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);

		//getting list of user names from the database
		List<String> userNames = userService.getUserList();
		mav.addObject("userNames", userNames);

		//getting list of questions from the database
		List<Question> questionList = questionService.getRecentQuestions(0,0, 3);
		mav.addObject("questionList", questionList);

		//google oAuth service object
		final GoogleAuthService helper = new GoogleAuthService();

		//checking code and state values
		if (request.getParameter("code") != null
				&& request.getParameter("state") != null) {
			request.removeAttribute("state");

			//getting user info in JSON object
			JSONObject jsonObj = helper.getUserInfoJson(request
					.getParameter("code"));
			
			String hd = (String) jsonObj.get("hd");
			if (hd == null || hd.equals("") || !hd.equals("metacube.com")) {
				return new ModelAndView("redirect:/getURL.com");
			} else {
				
				//getting user details from the JSON object
				//getting name
				name = (String) jsonObj.get("name");
				//getting email
				email = (String) jsonObj.get("email");
				//getting image URL
				image = (String) jsonObj.get("picture");
				
				//getting user details from the database
				User user = userService.getUserByEmail(email);
				
				//checking user session value
				if (user == null || user.getUserName() == null
						|| user.getUserName().equals("")) {	//if user session is null
					
					//new user object
					newUser = new User();
					
					//setting user details in the new user object
					//setting name
					newUser.setEmail(email);
					//setting email
					newUser.setUserName(name);
					//setting image URL
					newUser.setImageURL(image);
					
					//saving user details in the database
					userService.addUser(newUser);
	
					
					//send email utility object for sending email to newly registered users
					SendEmailUtil emailUtil = new SendEmailUtil();
					
					//email content
					String emailStatus = emailUtil.sendEmail(email, "Registred to Meta StackOverflow", "congratulation !!! "
							+name+ ", registerd with us successfully.\n\n\n\nThanks,\nMeta StackOverFlow\nmetacube Software Pvt. Ltd.");
					
			

					//setting the attributes in the session object
					userSession.setAttribute("userObject", newUser);
					userSession.setAttribute("privilege", "user");
					
					//adding the session object to the mav object and sending to the jsp page
					mav.addObject("userObject", newUser);
				} else {	//if user session is not null
					
					//checking if user is admin or not
					if (user.getIsAdmin()) {	//if user is admin
						
						//setting privileges for user
						userSession.setAttribute("privilege", "admin");
						userSession.setAttribute("userObject", user);
						mav.addObject("userObject", user);
					} else {	//if user is not admin
						
						//checking blocking status of user
						if(!(user.getUserStatus().equals("blocked"))) {
							
							//if user is unblocked
							userSession.setAttribute("privilege", "user");
							userSession.setAttribute("userObject", user);
							mav.addObject("userObject", user); 
						} else {	//if user is blocked the showing blocking message
							mav.addObject("message", "You Are Blocked"); 
						}
					}
				}
			}
		}
		
		//adding the session object to the mav object
		mav.addObject("session", userSession);

		//returning the mav object
		return mav;
	}

	/**method for redirecting to the index page
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getURL.do")
	public ModelAndView getIndex(HttpServletRequest request) {

		//getting session object
		HttpSession session = request.getSession(false);
		
		//new model and view type onbject
		ModelAndView mav = new ModelAndView();

		//getting list of tag names from the database and adding to the mav object
		List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);

		//getting list of user names from the database and adding to the mav object
		List<String> userNames = userService.getUserList();
		mav.addObject("userNames", userNames);

		//setting the view name to index page
		mav.setViewName("index");
		
		//getting list of popular questions to be displayed on the index page and adding to the mav object
		List<Question> questionList = questionService.getRecentQuestions(0,0, 3);
		mav.addObject("questionList", questionList);

		//checking he user session object and value inside session
		if (session != null && session.getAttribute("userObject")!=null ) {	//if session is not null
			
			//getting the user object from the session object
			User user = (User) session.getAttribute("userObject");
			
			//getting user privileges from the session object
			String privilege = (String) session.getAttribute("privilege");
			
			//adding the user details to the mav object
			mav.addObject("userObject", user);
			mav.addObject("privilege", privilege);
			
			//returning the mav object
			return mav;
		} else {	//if session is null
			
			//new google oAuth service object
			GoogleAuthService helper = new GoogleAuthService();
			
			//creating new login URL and sending the user to login page
			String loginURL = helper.buildLoginUrl();
			mav.addObject("loginURL", loginURL);
			return mav;
		}
	}

	/**method for log out
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public ModelAndView askQuestion(HttpServletRequest request) {
		
		//getting the session
		HttpSession session = request.getSession(false);
		ModelAndView mav;
		
		//invalidating the session to logout the user
		session.invalidate();
		mav = new ModelAndView("redirect:/getURL.do");
		
		//returning the mav object
		return mav;
	}
	
	@RequestMapping("/getAboutUs.do")
	public ModelAndView getAboutUs(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		ModelAndView mav = new ModelAndView();
		String privilege = null;
		User user = null;
		if(session!=null && session.getAttribute("userObject")!=null) {
			 user = (User) session.getAttribute("userObject");
			 privilege = (String) session.getAttribute("privilege");
			
		}
		GoogleAuthService helper = new GoogleAuthService();
		String loginURL = helper.buildLoginUrl();
		mav.addObject("loginURL", loginURL);
		mav.addObject("userObject", user);
		mav.addObject("privilege", privilege);
		mav.setViewName("about_us");
		return mav;
	}
}