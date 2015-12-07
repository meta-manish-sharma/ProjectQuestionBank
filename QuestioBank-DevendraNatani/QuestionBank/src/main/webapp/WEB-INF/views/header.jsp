<div class="container-fluid header">
		<div class="row">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#my-nav-bar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse" id="my-nav-bar">
					<ul class="nav navbar-nav">
						<li><a href="getTag.do?page=1"> Tags </a></li>
						<li><a href="listQuestion.do?tagId=0&page=1"> Questions </a></li>
						<c:choose>
							<c:when test="${not empty session}">
								<li><a href="askQuestion.do">Ask Question</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="Javascript:alertUser()">Ask Question</a></li>
							</c:otherwise>
						</c:choose>

						<li><a href="getUsers.do?page=1"> Users </a></li>
						<li><a href="getAboutUs.do"> About Us </a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:choose>
							<c:when test="${not empty session}">
								<li><a class="dropdown-toggle" data-toggle="dropdown"
									href="#">Hello <c:out value="${userObject.userName}"></c:out>
										<span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="myQuestions.do?page=1">My Questions</a></li>
										<li><a href="myAnswers.do?page=1">My Answers</a></li>
										<li><a href="logout.do">Logout</a></li>
									</ul></li>
							</c:when>
							<c:otherwise>
								<li><a href="${loginURL}"> <span
										class="glyphicon glyphicon-log-in"></span> Login
								</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
			</nav>
		</div>
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
				<a href="getURL.do" class="site-name"> METATRIVIA </a>
			</div>
		</div>
		<div class="row search-row">
			<div
				class="col-lg-4 col-lg-push-4 col-md-4 col-md-push-4 col-sm-6 col-sm-push-3 col-xs-6 col-xs-push-3">
				<form action="searchByTitle.do">	
				<div class="from-group">
					<input type="text" class="form-control" name="search"
						placeholder="Search Question Here..."/> <!-- <span
						class="input-group-btn">
					<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</span> -->
				</div>
				</form>
			</div>
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center advanced-search-row">
				<a href="#search-modal" data-toggle="modal"
					data-target="#search-modal" class="advanced-search active">
					Advanced Search </a>
				<div class="modal fade" id="search-modal" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h3>Advanced Search</h3>
							</div>
							<form class="form-horizontal advanced-search-form" role="form" action="advanceSearch.do" method="POST">
								<div class="form-group">
									<label class="control-label col-sm-2" for="question-title">
										Question Title: </label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="question-title" name="questionTitle"
											placeholder="Enter question title">
									</div>
								</div>
								
								<input type="hidden" id="hiddenTags" value="${tagNames}" />
								<input type="hidden" id="hiddenUsers" value="${userNames}" />
								<div class="form-group">
									<label class="control-label col-sm-2" for="tags"> Tags:
									</label>
									<div class="col-sm-10 col-lg-8">
										<select type="text" id="tags" name="tag"
											class="demo-default"
											placeholder="Specify tags related to your question"></select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="users"> User:
									</label>
									<div class="col-sm-10 col-lg-8">
										<select type="text" id="users"
											class="demo-default" name="user"
											placeholder="Specify user related to your question"></select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-primary">Submit
										</button>
										<button type="reset" class="btn btn-default">Cancel</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>