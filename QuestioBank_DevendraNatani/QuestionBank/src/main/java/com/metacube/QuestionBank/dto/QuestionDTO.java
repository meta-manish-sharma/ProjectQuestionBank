/**class for defining the question DTO (Data Transfer Object)
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dto;

import lombok.Data;

//data annotation for generating getters and setters
@Data
public class QuestionDTO {
	
	private String questionTitle;
	private String questionBody;
	private String tagList;
}