<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="javax.mail.Session"%>
<%@ page import="com.metacube.QuestionBank.auth.GoogleAuthService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.metacube.QuestionBank.model.User"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question Bank | UserProfile</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="dist/js/standalone/selectize.js"></script>
<script src="js/index.js"></script>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">


<script type="text/javascript" src="js/question-bank.js"></script>

<link rel="stylesheet" href="css/question-bank-style.css" />

<link rel="stylesheet" href="dist/css/selectize.default.css">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/stylesheet.css">

<script>
	function getUserAnswerList(pillId, userId, noOfPages, currentPage) {

		$.ajax({
			type : "GET",
			url : "/QuestionBank/AnswerListInProfile.do?userId=" + userId
					+ "&page=" + currentPage,
			datatype : 'json',
			success : function(data) {

				mynew(init(data, pillId, noOfPages, currentPage, userId));

			},
			error : function() {
				alert("error");
			}
		});

		function mynew(text) {
			document.getElementById(pillId).innerHTML = text;
		}
	}

	function init(data, pillId, noOfPages, currentPage, userId) {

		var text = "";
		for (i = 0; i < data.length; i++) {

			var date = new Date(data[i].postTime);
			text += "<div class='row text-body'><div class='question-wrapper'>"
					+ "<a href=getQuestion.do?questionId="
					+ data[i].question.questionId
					+ ">"
					+ "<h4>"
					+ data[i].question.questionTitle
					+ "</h4>"
					+ "</a>"
					+ "</div></div>"
					+ "<div class='row text-body'><div class='answer-wrapper'><p class='q-body'>"
					+ data[i].answerBody
					+ "</p>"
					+ "<p class='question-author'>"
					+ "Asked by <a href='profile.do?userId="
					+ data[i].user.userId
					+ "&page=1'> <b>"
					+ data[i].user.userName
					+ " </b>"
					+ "</a> on"
					+ date.getFullYear()
					+ "-"
					+ (date.getMonth() + 1)
					+ "-"
					+ (date.getDate())
					+ " "
					+ (date.getHours())
					+ ':'
					+ (date.getMinutes())
					+ ':'
					+ (date.getSeconds()) + ".0" + "</p></div></div>";

		}

		text += "<div><h3>";

		text += "<table class='pagination-table'><tr>";

		if (currentPage != 1) {
			text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getUserAnswerList('"
					+ pillId
					+ "','"
					+ userId
					+ "','"
					+ noOfPages
					+ "',"
					+ (currentPage - 1) + ")>Previous</a></td>";
		}

		for (i = 1; i <= noOfPages; i++) {
			if (i == currentPage) {
				text += "<td class='page-number-td'><i class='page-number'>"
						+ i + "</i></td>";
			} else {
				text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getUserAnswerList('"
						+ pillId
						+ "','"
						+ userId
						+ "','"
						+ noOfPages
						+ "',"
						+ i + ")>" + i + "</a></td>";
			}

		}

		if (currentPage < noOfPages) {
			text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getUserAnswerList('"
					+ pillId
					+ "','"
					+ userId
					+ "','"
					+ noOfPages
					+ "',"
					+ (currentPage + 1) + ")>NEXT</a></td>";
		}
		text += "</tr></table>";

		"</h3></div>"

		return text;
	}
</script>


</head>
<body>
	<%@include file="header.jsp"%>


	<div class="container content-wrapper">
		<div class="row user-details">
			<div class="col-lg-3 col-md-3 col-sm-4 col-xs-4">
				<img alt="profile picture" class="img-responsive"
					src="<c:out value='${userProfile.imageURL}'></c:out>" />
			</div>
			<div class="col-lg-6 col-md-6 col-sm-8 col-xs-8">
				<h3>
					<c:out value="${userProfile.userName}"></c:out>
				</h3>
				<label class="control-label"> Mail ID:<c:out
						value="${userProfile.email}"></c:out></label>
				<c:if test="${privilege.equals('admin') }">
					<c:if test="${!(userProfile.email).equals(userObject.email)}">

						<c:if test="${(userProfile.userStatus).equals('Unblocked') }">
							<a href="blockOrUnblockUser.do?userId=${userProfile.userId}"
								class="normal-link"> Block</a>
							<c:if test="${!(userProfile.isAdmin) }">
								<a href="addAdmin.do?userId=${userProfile.userId}"
									class="normal-link">Make Admin </a>
							</c:if>
						</c:if>
						<c:if test="${(userProfile.userStatus).equals('blocked') }">
							<a href="blockOrUnblockUser.do?userId=${userProfile.userId}"
								class="normal-link"> Unblock </a>
						</c:if>

					</c:if>
				</c:if>
			</div>
		</div>
		<div class="container">
			<ul class="nav nav-pills">
				<li class="active"><a data-toggle="pill" href="#pill1"> <c:out
							value="${userProfile.userName}"></c:out>'s Questions
				</a></li>
				<li><a data-toggle="pill" href="#pill2"
					onclick="getUserAnswerList('tagTemp','${userProfile.userId}','${AnswerNoOfPages}',1)">
						<c:out value="${userProfile.userName}"></c:out>'s Answers
				</a></li>
			</ul>
			<div class="tab-content">

				<div id="pill1" class="tab-pane fade in active">

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
								Asked by <a href="profile.do?userId=${question.user.userId} &page=1">
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
											href="profile.do?userId=${userProfile.userId}&page=${currentPage - 1}"
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
													href="profile.do?userId=${userProfile.userId}&page=${i}"
													class="page-number">${i}</a></td>
											</c:otherwise>
										</c:choose>
									</c:forEach>


									<%--For displaying Next link --%>
									<c:if test="${currentPage lt noOfPages}">
										<td class="page-number-td"><a
											href="profile.do?userId=${userProfile.userId}&page=${currentPage + 1}"
											class="page-number">Next</a></td>
									</c:if>
								</tr>
							</table>
						</h3>

					</div>
				</div>
				<div id="pill2" class="tab-pane fade">
					<!-- <div
						class="col-lg-10 col-md-10 col-sm-10 col-xs-10 question-wrapper">-->
						<div id="tagTemp">
							
						</div> 
					</div>
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