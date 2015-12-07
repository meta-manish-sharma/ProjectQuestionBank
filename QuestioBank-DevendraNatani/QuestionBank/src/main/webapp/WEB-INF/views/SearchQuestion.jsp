<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="javax.mail.Session"%>
<%@ page import="com.metacube.QuestionBank.auth.GoogleAuthService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.metacube.QuestionBank.model.User"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question Bank|Users</title>


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="dist/js/standalone/selectize.js"></script>
<script src="js/index.js"></script>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">


	<script type="text/javascript" src="js/question-bank.js"></script>
	<script type="text/javascript" src="js/searchQuestion.js"></script>
	<link rel="stylesheet"  href="css/question-bank-style.css"/>
	
	<link rel="stylesheet" href="dist/css/selectize.default.css">
	<link rel="stylesheet" href="css/normalize.css">
	<link rel="stylesheet" href="css/stylesheet.css">
	
</head>
<body>
		<%@include file="header.jsp"%>


	<div class="container content-wrapper">
	
	<ul class="nav nav-pills">
			<li class="active"><a data-toggle="pill" href="#pill1" >Recent </a></li>
			<li><a data-toggle="pill" href="#pill2" onclick="getActiveQuestionList('Active','temp2','${tagId}','${activeQuestionNoOfPages}','1')"> Active </a></li>
			<li><a data-toggle="pill" href="#pill3" onclick="getActiveQuestionList('Closed','temp3','${tagId}','${closedQuestionNoOfPages}','1')"> Closed </a></li>
			<li><a data-toggle="pill" href="#pill4" onclick="getActiveQuestionList('Popular','temp4','${tagId}','${popularQuestionNoOfPages}','1')"> Popular </a></li>
			<li><a data-toggle="pill" href="#pill5" onclick="getActiveQuestionList('Unanswered','temp5','${tagId}','${unAnsweredQuestionNoOfPages}','1')"> Unanswered </a></li>		
	</ul>
		
		
		<div class="tab-content">
			<div id="pill1" class="tab-pane fade in active ">
				<h4 class="section-heading">Most Recent Questions</h4>
				<c:if test="${fn:length(questionList) == 0}">
						<h3>There is no question</h3>
				</c:if>
				<c:forEach items="${questionList}" var="question">
					<div class="question-wrapper"">
						<a href="getQuestion.do?questionId=${question.questionId}">
							<h4>
								<c:out value="${question.questionTitle}"></c:out>
							</h4>
						</a>
						<ul class="related-tags-list">
							<c:forEach items="${question.tagList}" var="tag">
								<li class="related-tag"><a
									href='listQuestion.do?tagId=${tag.tagId} &page=1'> <c:out
											value="${tag.tagName}"></c:out>
								</a></li>
							</c:forEach>
						</ul>

						<p class="question-author">
							Asked by <a href="profile.do?userId=${question.user.userId}&page=1">
								<c:out value="${question.user.userName}">
								</c:out>
							</a> on
							<c:out value="${question.postTime}"></c:out>
						</p>
						
					</div>
				</c:forEach>

				<div class="text-center " >
					<%--For displaying Previous link except for the 1st page --%>
					<h3>
						<table class="pagination-table">
							<tr>
								<c:if test="${currentPage != 1}">
									<td class="page-number-td"><a
										href="listQuestion.do?tagId=${tagId}&page=${currentPage - 1}"
										class="page-number">Previous</a></td>
								</c:if>
								<%--For displaying Page numbers. 
						The when condition does not display a link for the current page--%>

								<c:forEach begin="1" end="${noOfPages}" var="i">
									<c:choose>
										<c:when test="${currentPage eq i}">
											<td class="page-number-td"><i class="page-number">${i}</i></td>
										</c:when>
										<c:otherwise>
											<td class="page-number-td"><a
												href="listQuestion.do?tagId=${tagId}&page=${i}"
												class="page-number">${i}</a></td>
										</c:otherwise>
									</c:choose>
								</c:forEach>


								<%--For displaying Next link --%>
								<c:if test="${currentPage lt noOfPages}">
									<td class="page-number-td"><a
										href="listQuestion.do?tagId=${tagId}&page=${currentPage + 1}"
										class="page-number">Next</a></td>
								</c:if>
							</tr>
						</table>
					</h3>

				</div>
			</div>

			<div id="pill2" class="tab-pane fade">
				<h4 class="section-heading">Active Questions</h4>
				<div  id="temp2">
					
				</div>
		</div>
		<div id="pill3" class="tab-pane fade">
				<h4 class="section-heading">Closed Questions</h4>
				<div  id="temp3">
					
				</div>
		</div>
		
		<div id="pill4" class="tab-pane fade">
				<h4 class="section-heading">Popular Questions</h4>
				<div  id="temp4">
					
				</div>
		</div>
		
		<div id="pill5" class="tab-pane fade">
				<h4 class="section-heading">UnAnswered Questions</h4>
				<div  id="temp5">
					
				</div>
		</div>
		
		
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
</body>
</html>