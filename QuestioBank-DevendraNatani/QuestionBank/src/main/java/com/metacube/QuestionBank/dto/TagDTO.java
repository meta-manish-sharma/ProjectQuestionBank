/**class for defining the tag DTO (Data Transfer Object)
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.dto;

import lombok.Data;

//data annotation for generating getters and setters
@Data
public class TagDTO {
	
	private int tagId;
	private int questionCount;
	private String tagName;
}