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
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script type="text/javascript" src="js/question-bank.js"></script>
	<link rel="stylesheet"  href="css/question-bank-style.css"/>
	
</head>
<body>
	<%@include file="header.jsp"%>
		<div class="container content-wrapper">
		<div class="row">
			<div class="col-lg-5 col-md-6 col-sm-10 col-xs-12">
				<div class="input-group">
					<input type="text" class="form-control"
						placeholder="Search questions Here..."> <span
						class="input-group-btn">
						<button class="btn btn-default" type="button">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</span>
				</div>
			</div>
		</div>
		
		<div class="tab-content">
			<div id="pill1" class="tab-pane fade in active ">
				<h4 class="section-heading">Most Recent Questions</h4>


				<c:forEach items="${userQuestionList}" var="question">
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
							Asked by <a href="profile.do?userId=${question.user.userId}">
								<c:out value="${question.user.userName}">
								</c:out>
							</a> on
							<c:out value="${question.postTime}"></c:out>
						</p>
						<hr>
					</div>
				</c:forEach>

				<div class="text-center">
					<%--For displaying Previous link except for the 1st page --%>
					<h3>
						<table class="pagination-table">
							<tr>
								<c:if test="${currentPage != 1}">
									<td class="page-number-td"><a
										href="myAnswers.do?page=${currentPage - 1}"
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
												href="myAnswers.do?page=${i}"
												class="page-number">${i}</a></td>
										</c:otherwise>
									</c:choose>
								</c:forEach>


								<%--For displaying Next link --%>
								<c:if test="${currentPage lt noOfPages}">
									<td class="page-number-td"><a
										href="myAnswers.do?page=${currentPage + 1}"
										class="page-number">Next</a></td>
								</c:if>
							</tr>
						</table>
					</h3>

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