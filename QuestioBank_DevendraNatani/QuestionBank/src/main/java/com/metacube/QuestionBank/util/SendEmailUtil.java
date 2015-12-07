/**utility class for sending notification emails to users
 * 
 * @author Team Devendra
 */

package com.metacube.QuestionBank.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailUtil {

	/**method for sending the email to the user
	 * 
	 * @param to
	 * @param subject
	 * @param emailMessage
	 * @return
	 */
	public String sendEmail(String to, String subject, String emailMessage) {

		//email content
		String resultMessage;

		//email sender mail address
		String from = "metaquestionbank@gmail.com";

		//user name
		final String username = "metaquestionbank@gmail.com";

		//password
		final String password = "ujittmtryxpvwsyq";
		String host = "smtp.gmail.com";

		//mail sending properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(emailMessage);

			// Send message
			Transport.send(message);

			resultMessage = "Sent message successfully....";

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			resultMessage = "Mail not sents";
			throw new RuntimeException(e);
		}
		return resultMessage; 	
	}	
}