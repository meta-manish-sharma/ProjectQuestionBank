/**controller class for tag management
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.metacube.QuestionBank.dto.TagDTO;
import com.metacube.QuestionBank.model.Question;
import com.metacube.QuestionBank.model.Tag;
import com.metacube.QuestionBank.model.User;
import com.metacube.QuestionBank.service.TagService;
import com.metacube.QuestionBank.service.UserService;
import com.metacube.QuestionBank.util.PopularityComparator;
import com.metacube.QuestionBank.util.TagNameComparator;
import com.metacube.QuestionBank.util.TagPopularityComparator;

@Controller
public class TagController {

	//tag service object
	@Autowired
	private TagService tagService;

	//user service object
	@Autowired
	private UserService userService;

	
	/**method to show the list of all tags on the screen
	 * 
	 * @param request
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value="/getTag.do",method=RequestMethod.GET)
	public ModelAndView getUsers(HttpServletRequest request,@RequestParam("page") int pageNo){
		HttpSession session = request.getSession(false);

		//pagination data begin
		int page = 1;
		int recordsPerPage = 9;
		page = pageNo;
		int noOfRecords = 0;
		//pagination data end
		
		//model and view type object
		ModelAndView mav = new ModelAndView();
		
		//showing paginated results
		List<Tag> tags = tagService.listTagsByPage(((page - 1) * recordsPerPage), recordsPerPage);
		
		//showing sorted results based on popularity
		Collections.sort(tags, new TagPopularityComparator());

		noOfRecords = tagService.listTags().size();
		
		//pagination
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		

		//checking the session value
		if(session != null && session.getAttribute("userObject") != null) {	//if session is not null
			
			//getting the user object
			User user = (User) session.getAttribute("userObject");
			
			//getting the user privileges
			String privilege = (String)session.getAttribute("privilege");
			
			//adding the user details to the session object
			mav.addObject("userObject", user);
			mav.addObject("privilege",privilege);
		}
		
		//google oAuth service type object
		final GoogleAuthService authService = new GoogleAuthService();
		
		//login URL for login using google oAuth
		String loginURL = authService.buildLoginUrl();
		
		//adding the login URL to the mav object
		mav.addObject("loginURL", loginURL);
		
		//getting list of tag names and adding to the mav object
		List<String> tagNames = tagService.getTagList();
		mav.addObject("tagNames", tagNames);

		//getting list of user names and adding to the mav object
		List<String> userNames = userService.getUserList();
		mav.addObject("userNames", userNames);
		
		//adding all the details to the mav object
		mav.addObject("tagList", tags);
		mav.setViewName("SearchTag");
		mav.addObject("noOfPages", noOfPages);
		mav.addObject("currentPage", page);

		//returning the mav object
		return mav;
	}

	/**method for getting all the questions related to a particular tag
	 * 
	 * @param listType
	 * @return
	 */
	@RequestMapping(value = "/getTags.do",method=RequestMethod.GET)
	public @ResponseBody List<TagDTO> allQuestion(@RequestParam("listType") String listType) {
		
		//getting list of all tags from the database
		List<Tag> tags = tagService.listTags();
		
		//new list of tag Data Transfer Objects (DTOs)
		List<TagDTO> tagDTOlist = new ArrayList<TagDTO>();
		
		//set of tags
		Set<Tag> tagList = new HashSet<Tag>(tags);
		tags = new ArrayList<Tag>(tagList);
		
		//sorting the tags based on tag name
		Collections.sort(tags, new TagNameComparator());

		//getting the list of questions related to all the tags using iterator
		for (Iterator<Tag> iterator = tags.iterator(); iterator.hasNext();) {
			
			//new tag DTO object
			Tag tag = (Tag) iterator.next();
			TagDTO tagDTO = new TagDTO();
			
			//setting the values in tag DTO
			tagDTO.setTagId(tag.getTagId());
			tagDTO.setQuestionCount(tag.getQuestionList().size());
			tagDTO.setTagName(tag.getTagName());

			//adding tag DTO to tagDTO list
			tagDTOlist.add(tagDTO);
		}

		//returning the tag DTO list
		return tagDTOlist;
	}
}