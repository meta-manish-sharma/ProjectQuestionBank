<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question Bank|Ask Question</title>

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

<link rel="stylesheet" type="text/css" href="css/wmd.css"/>
<script type="text/javascript" src="js/wmd.js"></script>
<script type="text/javascript" src="js/showdown.js"></script>

</head>
<body>
	<%@include file="header.jsp"%>

	<div class="container content-wrapper">
		<h3>Ask A Question:</h3>
		<form:form class="form-horizontal" method="POST" role="form"
			modelAttribute="command" action="postQuestion.do">
			
 
			<div class="form-group">
				
				<span class="text-danger"> ${errorMessage} </span>
				<form:label path="questionTitle" class="control-label col-sm-2"
					for="question-title">
					Question Title: </form:label>
				<div class="col-sm-10">
				
				
					<form:input path="questionTitle" type="text" class="form-control"
						id="question-title" placeholder="Enter question title" />
				</div>

			</div>

			
 			<div class="form-group">
 			
 			
				<form:label path="questionBody" for="wmd-input"
					class="control-label col-sm-2"> Question Body: </form:label>
					
				<div class="col-sm-10">

					<div id="wmd-editor" class="wmd-panel">
						<div id="wmd-button-bar"></div>
						<form:textarea name="questionBody" path="questionBody"
							id="wmd-input" class="form-control"
							placeholder="Enter question body"></form:textarea>
					</div>
					
				</div>
			</div>

			<div class="form-group">
				<form:label path="questionBody" class="control-label col-sm-2"> Preview: </form:label>
				<div id="wmd-preview" class="wmd-panel"></div>
			</div>
			
			<div class="form-group">
			
				<form:label path="tagList" class="control-label col-sm-2"
					for="related-tags">
					Related Tags: </form:label>
				<div class="col-sm-10 col-lg-8">
					<form:select path="tagList" type="text" id="related-tags"
						class="demo-default"
						placeholder="Specify tags related to your question"></form:select>
				</div>
				
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<form:button type="submit" class="btn btn-primary">Submit</form:button>
					<form:button type="reset" class="btn btn-default">Cancel</form:button>
				</div>
			</div>
		</form:form>
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