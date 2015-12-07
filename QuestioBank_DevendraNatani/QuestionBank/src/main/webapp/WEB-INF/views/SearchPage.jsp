<%@page import="javax.mail.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.metacube.QuestionBank.auth.GoogleAuthService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.metacube.QuestionBank.model.User"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="dist/js/standalone/selectize.js"></script>
<script src="js/index.js"></script>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">


	<script type="text/javascript" src="js/question-bank.js"></script>
	
	<link rel="stylesheet"  href="css/question-bank-style.css"/>
	
	<link rel="stylesheet" href="dist/css/selectize.default.css">
	<link rel="stylesheet" href="css/normalize.css">
	<link rel="stylesheet" href="css/stylesheet.css">

</head>

<body>

	<%@include file="header.jsp"%>

    <div class="container">
     	<div class="col-lg-12">
    	<h4>Search Results</h4>
    		<div>
    		<h3>${message }</h3>
    		<c:forEach items="${questionList}" var="current">
					<div class="question-wrapper">
						<a href="getQuestion.do?questionId=${current.questionId }">
							<h4>Question Title: <c:out value="${current.questionTitle}"></c:out></h4>
						</a>
						<div>
						<ul class="related-tags-list">

								<c:forEach
										items="${current.tagList }" var="tag">
										<li class="related-tag">
										<a href='listQuestion.do?tagId=${tag.tagId }'>${tag.tagName}</a></li>
									</c:forEach>
      						<p class="question-author">
							Asked by <a href="profile.do?userId=${current.user.userId }"> <b><c:out value="${current.user.userName}"></c:out></b>
							</a> on ${current.postTime}
						   </p>
							</ul>
							
							</div>
						
					</div>
					<br>
					</c:forEach>
    		</div>
    		
    		<input type="hidden" id="hiddenTags" value="${tagNames}" />
			<input type="hidden" id="hiddenUsers" value="${userNames}" />
    	</div>
    </div>
    
    <div class="container-fluid footer">
		<div class="row contact-details">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h3 align="center">Contact Us</h3>
			</div>
			<div
				class="col-lg-2 col-lg-push-2 col-md-4 col-sm-4 col-xs-12 text-center">
				<span class="glyphicon glyphicon-map-marker contact-icon"></span> <br
					class="hidden-xs">
				<hr class="hidden-xs">
				SP6, Phase IV, EPIP, Sitapura Industrial Area, Sitapura, Jaipur,
				Rajasthan 302022
			</div>
			<div
				class="col-lg-2 col-lg-push-3 col-md-4 col-sm-4 col-xs-12 text-center">
				<span class="glyphicon glyphicon glyphicon-envelope contact-icon"></span>
				<br class="hidden-xs">
				<hr class="hidden-xs">
				metaquestiobank@gmail.com
			</div>
			<div
				class="col-lg-2 col-lg-push-4 col-md-4 col-sm-4 col-xs-12 text-center">
				<span class="glyphicon glyphicon glyphicon-earphone contact-icon"></span>
				<br class="hidden-xs">
				<hr class="hidden-xs">
				+91-8233249261
			</div>
		</div>
		<div class="row copyrights">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				&copy Metacube Softwares Pvt. Ltd. | All Rights Reserved</div>
		</div>
	</div>
</body>
</html>