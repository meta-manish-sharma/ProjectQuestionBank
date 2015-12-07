<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<meta charset="ISO-8859-1">
	<title> Question Bank | Home</title>
	
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="dist/js/standalone/selectize.js"></script>
<script src="js/index.js"></script>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">


	<script type="text/javascript" src="js/question-bank.js"></script>
	<script type="text/javascript" src="js/vote.js"></script>
	<link rel="stylesheet"  href="css/question-bank-style.css"/>
	
	<link rel="stylesheet" href="dist/css/selectize.default.css">
	<link rel="stylesheet" href="css/normalize.css">
	<link rel="stylesheet" href="css/stylesheet.css">
	
	<link rel="stylesheet" type="text/css" href="css/wmd.css"/>
<script type="text/javascript" src="js/wmd.js"></script>
<script type="text/javascript" src="js/showdown.js"></script>

</head>

<body>


	<%@include file="header.jsp"%>
	
	<div class="container content-wrapper">
		<form:form class="form-horizontal" role="form" modelAttribute="command" action = "postAnswer.do?questionId=${question.questionId}">
			<div class="row">
				<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
					<h3> ${question.questionTitle} </h3> 
					<p class="question-author"> Asked by <a href="profile.do?userId=${question.user.userId}&page=1"> <b> ${question.user.userName } </b> </a> on ${question.postTime} </p>
				</div>
				<c:set var="privilege" scope="session"
						value="${privilege}" />
				<c:if test="${privilege.equals('admin') || userObject.userId == question.user.userId }">
				
					<c:set var="status" scope="session"
						value="${question.questionDetail.status}" />
					<c:if test="${status.equals('open') }">
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
							<a href="closeQuestion.do?questionId=${question.questionId }" class="normal-link">Close Question</a>
						</div>
					</c:if>
					
					
				</c:if>
				<c:set var="status" scope="session"
						value="${question.questionDetail.status}" />
				<c:if test="${status.equals('closed') }">
						<h3>
								<span class=" icon-close"> Closed</span>
						</h3>
				</c:if>
			</div>
			<div class="row text-body">
				<div class="col-lg-11 col-md-11 col-sm-11 col-xs-11 question-wrapper">
					<p class="q-body">${question.questionBody}</p>
				</div>
				<div class="col-lg-1 col-md-1 col-sm-1 col-xs-1">
					<a href="javascript:questionUpvote(${question.questionId })"><span class="glyphicon glyphicon-thumbs-up vote-button"></span><span class="badge" id='questionUp${question.questionId }'>${question.popularity.upVotes }</span></a><br>
					<a href="javascript:questionDownvote(${question.questionId })"><span class="glyphicon glyphicon-thumbs-down vote-button"></span><span class="badge" id='questionDown${question.questionId }'>${question.popularity.downVotes }</span></a>
				</div>
			</div>
			<div>
				<ul class = "related-tags-list">
					<li class="related-tag">
					<c:forEach items="${question.tagList }" var ="tag">
						<a href='listQuestion.do?tagId=${tag.tagId }' >${tag.tagName}</a>
					</c:forEach>
					</li>
				</ul>
			</div>
			<hr class="separator">
			<c:forEach items="${question.answerList}" var="answer">
			<div class="row text-body">
				<div class="col-lg-9 col-md-8 col-sm-6 col-xs-6 answer-wrapper">
					<p class="q-body">
						${answer.answerBody}
					</p>
					<p class="question-author"> Posted by <a href="profile.do?userId=${answer.user.userId}"> <b> ${answer.user.userName} </b> </a> on ${answer.postTime }</p>
				</div>
				<div class="col-lg-1 col-md-1 col-sm-2 col-xs-2">
					<a href="javascript:answerUpvote(${answer.answerId })"><span class="glyphicon glyphicon-thumbs-up vote-button"></span><span class="badge" id='answerUp${answer.answerId }'>${answer.popularity.upVotes }</span></a><br>
					<a href="javascript:answerDownvote(${answer.answerId })"><span class="glyphicon glyphicon-thumbs-down vote-button"></span><span class="badge" id='answerDown${answer.answerId }'>${answer.popularity.downVotes }</span></a>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-1 col-xs-1">
						<c:set var="status" scope="session"
							value="${question.questionDetail.status}" />
						<c:if
							test="${privilege.equals('admin') || userObject.userId == question.user.userId }">
							<c:if test="${status.equals('open') }">
								<a
									href="/QuestionBank/acceptAnswer.do?answerId=${answer.answerId}"
									class="normal-link hidden-sm hidden-xs">Accept Answer</a>
								<a
									href="/QuestionBank/acceptAnswer.do?answerId=${answer.answerId}"
									class="normal-link hidden-lg hidden-md"><span
									class="glyphicon glyphicon-ok-sign"></span></a>
							</c:if>
						</c:if>
						<c:set var="status" scope="session"
							value="${answer.answerDetail.status}" />
						<c:if test="${status.equals('accepted') }">
							<h3>
								<span class="glyphicon glyphicon-ok icon-success"></span>
							</h3>

						</c:if>
					</div>
			</div>
			</c:forEach>
			<div class="row post-answer">
				<div class="col-lg-8 col-md-8 col-sm-10 col-xs-12">
				<c:set var="status" scope="session"
						value="${question.questionDetail.status}" />
				<c:if test="${status.equals('open') }">
					<h4> Post Your Answer: </h4>
						<div class="form-group">
							<form:label path = "answerBody" class="control-label col-sm-2" for="wmd-input"> Answer Body: </form:label>
							<div class="col-sm-10">
								<span class="text-danger"> ${errorMessage} </span>
								<div id="wmd-editor" class="wmd-panel">
									<div id="wmd-button-bar"></div>
									<form:textarea name="questionBody" path="answerBody"
										id="wmd-input" class="form-control" rows="5"
										placeholder="Enter answer body"></form:textarea>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<form:label path="" class="control-label col-sm-2"> Preview: </form:label>
							<div id="wmd-preview" class="wmd-panel"></div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary"> Submit </button>
								<button type="reset" class="btn btn-default"> Cancel </button>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</form:form>
	</div>
	
	
	<div class="container-fluid footer">
		<div class="row contact-details">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<h3 align="center"> Contact Us </h3>
			</div>
			<div class="col-lg-2 col-lg-push-2 col-md-4 col-sm-4 col-xs-12 text-center">
				<span class="glyphicon glyphicon-map-marker contact-icon"></span>
				<br class="hidden-xs">
				<hr class="hidden-xs">
				SP6, Phase IV, EPIP, Sitapura Industrial Area, Sitapura, Jaipur, Rajasthan 302022
			</div>
			<div class="col-lg-2 col-lg-push-3 col-md-4 col-sm-4 col-xs-12 text-center">
				<span class="glyphicon glyphicon glyphicon-envelope contact-icon"></span>
				<br class="hidden-xs">
				<hr class="hidden-xs">
				metaquestiobank@gmail.com
			</div>
			<div class="col-lg-2 col-lg-push-4 col-md-4 col-sm-4 col-xs-12 text-center">
				<span class="glyphicon glyphicon glyphicon-earphone contact-icon"></span>
				<br class="hidden-xs">
				<hr class="hidden-xs">
				+91-8233249261
			</div>
		</div>
		<div class="row copyrights">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				&copy Metacube Softwares Pvt. Ltd. | All Rights Reserved
			</div>
		</div>
	</div>

</body>

</html>