/**utility class for performing the validations
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import com.metacube.QuestionBank.dto.QuestionDTO;

public class Validation {

	/**method for checking the question posted by the user
	 * and validating it
	 * 
	 * @param questionDTO
	 * @return
	 */
	public static String questionPostValidation(QuestionDTO questionDTO) {
		String errorMessage = "";
		if(questionDTO.getQuestionTitle()=="" || questionDTO.getQuestionTitle()==null) {

			errorMessage += "Please fill the Question Title.";
		} else if(questionDTO.getQuestionTitle().length() < 20) {
			errorMessage += " Question Title  should be of minimum lenght 15.";

		}
		if(questionDTO.getQuestionBody().equals("") || questionDTO.getQuestionBody()==null) {
			errorMessage += "Please fill the Question Body.\n";

		} else if(questionDTO.getQuestionBody().length() < 30){
			errorMessage += "Question Body should be of minimum lenght 30.";

		}
		if(questionDTO.getTagList() =="" || questionDTO.getTagList() == null) {
			errorMessage += "Please fill the Question Tags.";

		}
		return errorMessage;
	}
}