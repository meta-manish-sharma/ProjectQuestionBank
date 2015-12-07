package com.metacube.QuestionBank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

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
import com.metacube.QuestionBank.dao.QuestionDao;
import com.metacube.QuestionBank.dto.QuestionDTO;
import com.metacube.QuestionBank.dto.dtoConvertor.DTOConvertor;
import com.metacube.QuestionBank.model.Answer;
import com.metacube.QuestionBank.model.Popularity;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.QuestionService;
import com.metacube.QuestionBank.service.TagService;
import com.metacube.QuestionBank.service.UserService;
import com.metacube.QuestionBank.util.AdvanceSearch;
import com.metacube.QuestionBank.util.GetQuestionList;
import com.metacube.QuestionBank.util.PopularityComparator;
import com.metacube.QuestionBank.util.SendEmailUtil;
import com.metacube.QuestionBank.util.Validation;

/**This class performs all the control operations realted to the question ranging from postin to retirving details
**
** @author Team Devendra
*/
@Controller
public class QuestionController {

        //create object for tag service layer class
	@Autowired
	private TagService tagService;
        
        //create object for question service layer class
	@Autowired
	private QuestionService questionService;
         
        //create object for user service layer class
	@Autowired
	private UserService userService;
	
	//create object for question database object class
	@Autowired
	private QuestionDao questionDao;

	//creating Object for getQuestionList class
        private GetQuestionList getQuestionList = new GetQuestionList();
	
        //request mapping for the redirection URL and the post method
        /**This performs the saving of the question details in the database**/
	@RequestMapping(value = "/postQuestion.do", method = RequestMethod.POST)
	public ModelAndView saveQuestion( @ModelAttribute("command") QuestionDTO questionDTO,
			BindingResult result, HttpServletRequest request) {
		  //setting redirect url in the string object
                  String redirect ="redirect:askQuestion.do";
		  String errorMessage = "";
		  
		
		  //checking if session exists or not 
                  HttpSession userSession = request.getSession(false);
		  int questionid = -1;
                  //if user is first time loggger then redirect to oAuth login
		  if(userSession==null || userSession.getAttribute("userObject")== null) {
			  return new ModelAndView("redirect:/getURL.do"); 
		  }
		  //else prepare userObject and get the user info and privilege from session 
		  User userObject = (User)userSession.getAttribute("userObject");
		  String privilege = (String)userSession.getAttribute("privilege");
		  
                  //setting error message for question post validations  
		  errorMessage = Validation.questionPostValidation(questionDTO);
		  //checking if no erroer messgae comes 
                  if(errorMessage.equals("")) {
			 // System.out.println("hi");
			  DTOConvertor dtoConvertor = new DTOConvertor();
			  //create question object
                          Question question = new Question();

			  //setting user for the question 
                          question.setUser(userService.getUserByUserId(userObject.getUserId()));
			  //getting tags list related to question 
                          List<String> tagList = dtoConvertor.questionConvertor(questionDTO,question);
			  //adding new question to the database and returning new questions question id 
                          questionid = questionService.addQuestion(question, tagList);
			  //redirecting to get Quezsstion page with question posted
                          redirect ="redirect:getQuestion.do?questionId="+questionid;
		  }
		  //setting attributes in session object
		  userSession.setAttribute("errorMessage", errorMessage);
                  //creating map object
		  Map<String, Object> model = new HashMap<String, Object>(); 
		  
         
		  model.put("userObject",userObject);
		  model.put("privilege", privilege);
                  //sending oAuth url to the page for setting in login link
		  final GoogleAuthService authService = new GoogleAuthService();
			//getting OAuth url
                        String loginURL = authService.buildLoginUrl();
			model.put("loginURL", loginURL);
			//getting the tags list in list object
		  List<String> tagNames = tagService.getTagList();
			model.put("tagNames", tagNames);
			//getting the number of user in the list
			List<String> userNames = userService.getUserList();
			model.put("userNames", userNames);
		return new ModelAndView(redirect);
	}

	
        //request mapping for the redirection URL and the get method 	
        /**This method deals with th getting the question dfrom the database**/
	@RequestMapping(value = "/getQuestion.do", method = RequestMethod.GET)
	public ModelAndView getQuestion(@RequestParam("questionId") int questionId,
			HttpServletRequest request) {
		//getting session
                HttpSession userSession = request.getSession(false);
		//intializing map object
                Map<String, Object> model = new HashMap<String, Object>();
		final GoogleAuthService authService = new GoogleAuthService();
		//getting OAuth login URL
                String loginURL = authService.buildLoginUrl();
		//getting the list of all the tags
                List<String> tagNames = tagService.getTagList();
		model.put("tagNames", tagNames);
		//getting listr of all the userNames
		List<String> userNames = userService.getUserList();
		model.put("userNames", userNames);
                //getting Questions Based on Question ID	
                Question question = questionService.getQuestionById(questionId);
		//creating answer object
                //putting all the things in the amp model object
		Answer answer = new Answer();
		model.put("question", question);
		model.put("command", answer);
		model.put("loginURL", loginURL);
		//checking if user session is null 
		if (userSession == null
				|| userSession.getAttribute("userObject") == null) {
			
			//redirect to questionDetails
			return new ModelAndView("questionDetails", model);
		}
                //getting user Object from session Object
		User userObject = (User) userSession.getAttribute("userObject");
		//getting user Privileges from session attribute
                String privilege = (String) userSession.getAttribute("privilege");
		model.put("userObject", userObject);
		model.put("privilege", privilege);		

		return new ModelAndView("questionDetails", model);
	}
        //request mapping for the redirection URL and the get method 	
        /**This method handles the ask question page and details related to it**/
	@RequestMapping(value = "/askQuestion.do", method = RequestMethod.GET)
	public ModelAndView askQuestion(HttpServletRequest request) {
		HttpSession userSession = request.getSession(false);//getting session
		Map<String, Object> model = new HashMap<String, Object>();//creating map object
		final GoogleAuthService authService = new GoogleAuthService();
		String loginURL = authService.buildLoginUrl();//getting OAuth login url for setting in login link of header
		model.put("loginURL", loginURL);//putting login url in map object
		List<String> tagNames = tagService.getTagList();//getting all the tags list
		model.put("tagNames", tagNames);//putting the taglist in map object
		//get userNames in the list
		List<String> userNames = userService.getUserList();
		model.put("userNames", userNames);
                //if usersession is null then user is not logged in hence redirect to the login page
		if (userSession == null	|| userSession.getAttribute("userObject") == null) {
			
			return new ModelAndView("redirect:/loginWithOauth.do");
		}
                //get user object from the session object
		User userObject = (User) userSession.getAttribute("userObject");
                //getting user privileges
		String privilege = (String) userSession.getAttribute("privilege");
		String errorMessage = (String) userSession.getAttribute("errorMessage");
		//removing error message from session
                userSession.removeAttribute("errorMessage");
		System.out.println(errorMessage);
		QuestionDTO questionDTO = new QuestionDTO();
		//putting all the object in map and sending it to the view 
		model.put("command", questionDTO);
		model.put("userObject", userObject);
		model.put("privilege", privilege);
		model.put("errorMessage", errorMessage);
		
		return new ModelAndView("askAQuestion", model);
	}

	
	
	
        //request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/questionUpVote.do",method = RequestMethod.GET)
        /**This method handles the like over the question**/
	public void likeQuestion(@RequestParam("questionId") int questionId, HttpServletResponse response,HttpServletRequest request) throws IOException {
		//getting questions based on question id 
                Question question = questionService.getQuestionById(questionId);
		//getting the session
                HttpSession session = request.getSession(false);
		//getting populartiy based on upvotes and downvotes
                Popularity popularity = question.getPopularity();
		User userObject = null;
		//checking if session is not null then user can downvote the question
                if(session!=null && session.getAttribute("userObject") != null) {
                       // get user object from the session object 
			userObject=(User)session.getAttribute("userObject");
			//System.out.println("session object user === "+userObject);
                        //user can like or upvote the question
			popularity = questionService.likeQuestion(question, userObject);
			
			
		}
	        //getting upvotes	
		int upVotes = popularity.getUpVotes();
                //appending it to the existing value
		response.getWriter().append(upVotes +"");
	}
	
        //request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/questiondDownVote.do",method = RequestMethod.GET)
	/**This function handles the dislike over the question**/
        public void disLikeQuestion(@RequestParam("questionId") int questionId, HttpServletResponse response ,HttpServletRequest request) throws IOException {
		//getting Question by id
                Question question = questionService.getQuestionById(questionId);
		//getting user session if exists
                HttpSession session = request.getSession(false);
		User userObject = null;
                //getting the question populartiy 
		Popularity popularity = question.getPopularity();
                //checking if session is not null then user can downvote the question
		if(session!=null && session.getAttribute("userObject") != null) {
			userObject=(User)session.getAttribute("userObject");
			//System.out.println("session object user === "+userObject);
			//get the question dislikes 
                        popularity = questionService.disLikeQuestion(question, userObject);
			
			
		}
		//getting the downvotes
		int downVotes = popularity.getDownVotes();
                //getting question by id
		question = questionService.getQuestionById(questionId);
		//appending downvotes to existing downvotes
                response.getWriter().append(downVotes+"");
	}
	//request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/closeQuestion.do")
        /**This function handles the close question request**/
	public ModelAndView closeQuestion(@RequestParam("questionId") int questionId, HttpServletRequest request) {
		//getting question by Id
		Question question = questionService.getQuestionById(questionId);
		//checking if the question is closed or not 
                boolean isClosed = questionService.closeQuestion(questionId, "Irrelevant");
		if(isClosed) {
                        //if true then sending email to the user informing that his question is closed and marked as irrevlevant
			User user = question.getUser();
			String email = user.getEmail();
			SendEmailUtil emailUtil = new SendEmailUtil();
			emailUtil.sendEmail(email, "Your Question is closed.", "Sorry "
					+user.getUserName()+ ", Your Question  http://localhost:8080/QuestionBank/getQuestion.do?questionId="+questionId +" is closed due to following reason:"+" Irrelevant Question.");
		}
                //else return to question page
		return new ModelAndView("redirect:getQuestion.do?questionId="+questionId);
		
	}
	
	@Transactional
        //request mapping for the redirection URL and the get method 
	@RequestMapping(value="/listQuestion.do",method=RequestMethod.GET)
        /**This function is getting the user for the Questions**/
	public ModelAndView getUsers(@RequestParam("tagId") int tagId, @RequestParam("page") int pageNo, HttpServletRequest request){
		//getting user session if exists
                HttpSession session = request.getSession(false);
		ModelAndView mav=new ModelAndView();
		int page = 1;
		int recordsPerPage = 3;
		page = pageNo;
		int noOfRecords = 0;
		//System.out.println("tagId ===  "+ tagId);
                //getting recent questions list 
		List<Question> questionList = questionService.getRecentQuestions(tagId,((page - 1) * recordsPerPage), recordsPerPage);
		//Set<Question> questionList = new HashSet<Question>(questions);
		//getting the list of questions
                List<Question> questionlist = questionService.listQuestions(0);
		//if tag I si zero 
		if(tagId==0){
                         //get the numberof records size
			 noOfRecords = questionlist.size();
		}
		else {   
                         //get number of records  based on tags and tags ID 
			 noOfRecords =  getQuestionList.getQuestionListByTagId(questionlist, tagId).size();
		}
                //settinf number of records to show on a page using pagination
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		/*Calulate noOfPages for other types of Questsions*/
		//getting number of active questions
		int activeQuestionNoOfPages = (int) Math.ceil(questionDao.getActiveQuestionsCount(tagId) * 1.0 / recordsPerPage);
		//getting number od clodes questions
		int closedQuestionNoOfPages = (int) Math.ceil(questionDao.getClosedQuestionsCount(tagId) * 1.0 / recordsPerPage);
		//getting the unanswered questions
		int unAnsweredQuestionNoOfPages = (int) Math.ceil(questionDao.getUnAnsweredQuestionsCount(tagId) * 1.0 / recordsPerPage);
		//getting the popular questions
		int popularQuestionNoOfPages = (int) Math.ceil(questionDao.listQuestions(tagId).size() * 1.0 / recordsPerPage);
		
		//getting the google auth service object and setting it in the login link of header 
		 final GoogleAuthService authService = new GoogleAuthService();
 		String loginURL = authService.buildLoginUrl();
 		mav.addObject("loginURL", loginURL);
 		//getting tags names list
 		List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);
		//getting thr users in the list
		List<String> userNames = userService.getUserList();
		mav.addObject("userNames", userNames);
		//adding all the objects to the session
		mav.addObject("noOfPages", noOfPages);
		mav.addObject("currentPage", page);
		mav.addObject("questionList", questionList);
		mav.addObject("activeQuestionNoOfPages", activeQuestionNoOfPages);
		mav.addObject("closedQuestionNoOfPages", closedQuestionNoOfPages);
		mav.addObject("unAnsweredQuestionNoOfPages", unAnsweredQuestionNoOfPages);
		mav.addObject("popularQuestionNoOfPages", popularQuestionNoOfPages);
		mav.addObject("tagId", tagId);
                //setting view to the search question page
                mav.setViewName("SearchQuestion");
                //if session is not null then get user object and privileges and send with the model view object
            if(session!=null && session.getAttribute("userObject")!=null){
    			User user = (User) session.getAttribute("userObject");
    			String privilege = (String)session.getAttribute("privilege");
    			mav.addObject("userObject", user);
    			mav.addObject("privilege",privilege);
    		}
    	return mav;
		
	}
	
	@Transactional
        //request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/allQuestion.do",method=RequestMethod.GET)
        /**This function deals with getting all the questions with all the details from database**/
	public @ResponseBody List<Question> allQuestion(@RequestParam("listType") String listType,@RequestParam("tagId") int tagId
			 ,@RequestParam("page") int pageNo) {
		         //number pages 
			int page = 1;
                        //records allowed to show in one page 
			int recordsPerPage = 3;
			page = pageNo;
		         //if list type is active then list all the question which are active
			if(listType.equalsIgnoreCase("Active"))	
			{
				System.out.println("list in controller" +questionService.getActiveQuestions(tagId,((page - 1) * recordsPerPage), recordsPerPage));
				return questionService.getActiveQuestions(tagId,((page - 1) * recordsPerPage), recordsPerPage);
				
				
			}
                        //if list type is closed then list all the question in which closed tag is set
			else if(listType.equalsIgnoreCase("Closed"))
			{
				return (List<Question>) questionService.getClosedQuestions(tagId,((page - 1) * recordsPerPage), recordsPerPage);
				
			}
                        //if question type is popular then return the list of questions which are popular
			else if(listType.equalsIgnoreCase("Popular"))
			{
				List<Question> list = questionDao.listQuestions(tagId,((page - 1) * recordsPerPage), recordsPerPage);
				Collections.sort(list, new PopularityComparator());
				return list;
				
			}
                        //if question type is unanswered then return the list of questions which are unanswered
			else if(listType.equalsIgnoreCase("Unanswered"))
			{
				return (List<Question>) questionService.getUnAnsweredQuestions(tagId,((page - 1) * recordsPerPage), recordsPerPage);
				
			}
	                //list the question based on tagId
		 	List<Question> list = questionService.listQuestions(tagId);
		return list;
	}
	//request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/sortByPopularity.do",method=RequestMethod.GET)
        /**This function sorts the question based on populartiy
         * 
         * @return
         */
	public @ResponseBody List<Question> sortByQuestion() {
                 
                //getting the list of questions
		List<Question> list = questionService.listQuestions();
		Collections.sort(list, new PopularityComparator());
		return list;
	}
	
	//request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/searchByTitle.do",method = RequestMethod.GET)
        /**This function performs the searching based on title**/
	public ModelAndView searchByTitle(HttpServletRequest request) {
		//getting the data from search input field 
		String input =request.getParameter("search");
		ModelAndView mav = new ModelAndView();
		final GoogleAuthService authService = new GoogleAuthService();
		//getting the Oauth Url
                String loginURL = authService.buildLoginUrl();
		mav.addObject("loginURL", loginURL);
		//getting the tagNames list 
                List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);
		//getting the user's list
		List<String> userNames = userService.getUserList();
		mav.addObject("userNames", userNames);
                //if their is no data in input object then user have not performed the searching 
		if(input == null || input ==""){
			
		}
                //return the question list based on search 
		List<Question> questionList = questionService.searchByTitle(input);
		if(questionList == null){
			mav.addObject("message","No Search Result Found");
		} else if (questionList.isEmpty()) {
			mav.addObject("message","No Search Result Found");
		}
		
		mav.addObject("questionList", questionList);
		mav.setViewName("SearchPage");
		System.out.println("new list" + questionList);
		return mav;
	}
	
	//request mapping for the redirection URL and the get method 
	@RequestMapping(value = "/advanceSearch.do",method = RequestMethod.POST)
        /**This function performs the advance search based on some additional parameters**/
	public ModelAndView advanceSearch(HttpServletRequest request) {
		//get input from question title
                String input =request.getParameter("questionTitle");
		//get parameter from the tags 
                String tagName = request.getParameter("tag");
		//get paraweter value from the user field
                String user = request.getParameter("user");
		/*ModelAndView mav = new ModelAndView();*/
                //model object 
		Map<String, Object> model = new HashMap<String, Object>();
		final GoogleAuthService authService = new GoogleAuthService();
		//get loginURl from the auth Service 
                String loginURL = authService.buildLoginUrl();
		model.put("loginURL", loginURL);
		//get Tag list 
                List<String> tagNames = tagService.getTagList();
		model.put("tagNames", tagNames);
		//get username from the user list 
		List<String> userNames = userService.getUserList();
		model.put("userNames", userNames);
		//get the question list 
                List<Question> questionList = new ArrayList<Question>();
		String message = "" ;
              
		List<String> tagsList = null;
                //if the tagname list is not empty then get the taglist baed on tagname
		if(tagName != null && tagName != "") {
			tagsList = AdvanceSearch.tagListConvertor(tagName);

		}
		System.out.println(input);
		System.out.println(tagName);
		System.out.println(user);
                //if input value for question title is null 
		if(input == null || input == "") {
			System.out.println(input);
			System.out.println(tagName);
			System.out.println(user);
                        //show error message Question title is mandatory
			message = "Question Title is mandatory.";
			if(tagName == null || tagName == "") {
				message += "You have to fill atleast 2 fields";
			}
                        //if user value is equal to null
			if(user == null || user == "") {
				message += "You have to fill atleast 2 fields ";
			}
		}
		//if tagname is empty
		if(tagName == "") {
                        //if input is null
			if(input == null) {
				message = "You have to fill atleast 2 fields";
			}
                        //if user is null 
			if(user == "") {
				message = "You have to fill atleast 2 fields";
			}
		}
                 //if meaaage is null 
		if(message == "") {
                        //if input is not null
			if(input != null) {
                                //get Question list based on title
				questionList = questionService.searchByTitle(input);
				//if question list is not empty
                                if(!questionList.isEmpty()){
                                        //initialize the list with question as data type 
					List<Question> questions = new ArrayList<Question>();
					//if tagname is not null
                                        if(tagName != null ) {
                                                //get Iterator Object 
						Iterator<Question> iterator = questionList.iterator();
						//itrator loop 
						while (iterator.hasNext()) {
                                                        //get tje Question in the question object using iterator 
							Question question = (Question) iterator.next();
                                                        //iterator for the tag tyope 
							Iterator<Tag> iterator2 = question.getTagList().iterator();
							        //iterator for the string type of tagList
								Iterator<String> iterator3 = tagsList.iterator();
								//loop till iterator have any value
                                                                while (iterator3.hasNext()) {
									String string = (String) iterator3.next();
									//get tag for the each question
                                                                        while (iterator2.hasNext()) {
										Tag tag = (Tag) iterator2.next();
									 //if the tag name is found what user has enterd then add question to questions list
									if(string.equals(tag.getTagName())) {
										questions.add(question);
										break;
									}
								}
								
							}
						}
						questionList = questions;
						//if user is not null
                                                if(user != "") {
							List<Question> list = new ArrayList<Question>();
							Iterator<Question> iterator2  = questions.iterator();
							//while the iterator has any value 
                                                        while (iterator2.hasNext()) {
                                                                //get the questions based on user 
								Question question = (Question) iterator2.next();
                                                                //if the question is posted by the requiered user then add it to the list
								if((question.getUser().getEmail()).equals(user)) {
									list.add(question);
								}
							}
                                                        //if list is not empty then add it to the list
							if(!list.isEmpty()) {
								questionList = list;
							}
						}
					} else {//getting the questions based on question title
						Iterator<Question> iterator = questionList.iterator();
						List<Question> questions2 = new ArrayList<Question>();
						while (iterator.hasNext()) {
							System.out.println("dsfRAVIKA");
							Question question = (Question) iterator.next();
							User user1 = question.getUser();
							if((user1.getEmail()).equals(user)) {
								questions2.add(question);
							}
						}
						questionList = questions2;
					}
				} else {//else no question is found on the criteria
					message = "No Search Result Found";
				}	
			}
		}
                //if questionList is empty then no result is found  
		if(questionList.isEmpty()) {
			message = "No Search Result Found";
		}
                //else put the message and list in model and send it to the view 
		model.put("message", message);
		model.put("questionList", questionList);
		
		return new ModelAndView("SearchPage", model);
	}
}
