<!DOCTYPE html>

<html>

<head>
	<meta charset="ISO-8859-1">
	<%@ page contentType="text/html;charset=UTF-8"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<title> Question Bank | About Us</title>
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="dist/js/standalone/selectize.js"></script>
<script src="js/index.js"></script>

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">


	<script type="text/javascript" src="js/question-bank.js"></script>
	<script type="text/javascript" src="js/indexQuestion.js"></script>
	<link rel="stylesheet"  href="css/question-bank-style.css"/>
	
	<link rel="stylesheet" href="dist/css/selectize.default.css">
	<link rel="stylesheet" href="css/normalize.css">
	<link rel="stylesheet" href="css/stylesheet.css">

<link rel="stylesheet" type="text/css" href="css/wmd.css"/>
<script type="text/javascript" src="js/wmd.js"></script>
<script type="text/javascript" src="js/showdown.js"></script>

<style type="text/css">
	.logo {
		font-size: 1500%;
	}
	
	.theme-selector {
		visibility: hidden !important;
		height: 0 !important;
	}
	.content-wrapper {
		font-size: 110%;
		text-align: justify;
	}
	.details {
		padding: 20px;
		border-left: 2px solid BLACK;
	}
</style>
</head>

<body>

	<%@include file="header.jsp"%>
	
	
	<div class="container content-wrapper about-content">
		<div class="row text-center">
			<div class="col-lg-5 col-md-5 col-sm-4 col-xs-12">
				<span class="glyphicon glyphicon-question-sign logo"></span>
				<h1 class="section-heading">Meta Trivia</h1>
				<h5 class="section-heading">You Ask Everyone Solves...</h5>
				<hr>
				<h4 class="section-heading">Email ID: metaquestionbank@gmail.com</h4>
				<h4 class="section-heading">Customer Care: +91-7415170199</h4>
				<h4 class="section-heading">Toll Free Help Line: 1800-180-1010</h4>
			</div>
			<div class="col-lg-7 col-md-7 col-sm-8 col-xs-12 details">
				<h3 class="section-heading"> About Us </h3>
				<p>Metacube is a software engineering services company that has deep
					experience in developing enterprise level products and applications
					for a wide spectrum of domains including global trade management,
					supply chain analytics, manufacturing analytics, business
					continuity planning, CRM, publishing and eCommerce. These
					applications have been developed on a variety of platforms. The
					common denominator in all our services has been our total customer
					focus, ensuring that each engagement is a success and provides the
					desired value to the customer. Metacube’s core competency is in
					working closely with software product companies to translate their
					ideas into products using a repeatable process. We bring in
					successful working experience with several product companies that
					have been financed by leading venture capital funds. These have
					been very demanding engagements challenging Metacube to deliver
					solutions that are generic, extensible, user friendly, and robust.
				</p>
				
				<h3 class="section-heading"> Senior Mentor: Devendra Natani </h3>
				
				<h3 class="section-heading"> Team Members </h3>
				<p>
					Gaurav Saini
					<br>
					Manish Sharma
					<br>
					Neha Bansal
					<br>
					Ravika Jain
					<br>
					Shishir Pareek
				</p>
				
			</div>
		</div>
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
				metaquestionbank@gmail.com
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