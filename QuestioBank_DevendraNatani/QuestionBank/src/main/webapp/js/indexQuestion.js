function init(data, type, pillId){
			var questionTitleArray = [];
			var questionPostTimeArray = [];
			var questionAuthorArray = [];
			for(var i in data) {
				questionTitleArray.push(data[i].questionTitle);
				questionPostTimeArray.push(data[i].postTime);
			}
			
			
			var text="";
			var text="";
			if(data.length == 0) {
				text="<h3>There is No Question</h3>"
			}
			for (i = 0; i < data.length; i++) { 
				
				var tegData="";
				var date = new Date(data[i].postTime);
			  	for (j = 0; j < data[i].tagList.length; j++) {
					tegData+=  "<li class='related-tag'><a href=listQuestion.do?tagId="+data[i].tagList[j].tagId+"&page=1>"+
				            	data[i].tagList[j].tagName+
			                 	"</a></li>";
				} 
				
			    text += "<div class='question-wrapper'>"+
			            "<a href=getQuestion.do?questionId="+data[i].questionId+">"+
					    "<h4>" +questionTitleArray[i]+"</h4>"+
			            "</a>"+
			            "<ul class='related-tags-list'>"+
			             tegData+
			             "</ul>" +
				    	"<p class='question-author'>"+
				        "Asked by <a href='profile.do?userId="+data[i].user.userId+" &page=1'> <b>"+data[i].user.userName+" </b>"+
					    "</a> on"+ date.getFullYear()  + "-" +  (date.getMonth() + 1) + "-" + (date.getDate()) + " "+(date.getHours()) + ':' +(date.getMinutes()) + ':' +(date.getSeconds())+".0"+
			            "</p>" +
			            "</div>";	
			}
			
			return text;
		} 
		
		
			
			function getActiveQuestionList(type,pillId,tagId){
				$.ajax({
					type: "GET",
					url: "/QuestionBank/allQuestion.do?listType=" + type+"&tagId=0&page=1",
					datatype:'json',
					    success: function(data){
					    	mynew(init(data,type,pillId,tagId));
					    },error: function() {
				            alert("error");
				        }
				});
				function mynew(text){
					document.getElementById(pillId).innerHTML =text;
				}
		} 