function init(data, type, pillId, tagId, noOfPages, currentPage){
			var questionTitleArray = [];
			var questionPostTimeArray = [];
			var questionAuthorArray = [];
			for(var i in data) {
				questionTitleArray.push(data[i].questionTitle);
				questionPostTimeArray.push(data[i].postTime);
			}
			
			
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
			
			text += "<div><h3>";
			
				text += "<table class='pagination-table'><tr>";
				
				if(currentPage!=1){
					text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getActiveQuestionList('"+type+"','"+pillId+"','"+tagId+"','"+noOfPages+"','"+currentPage+"')>Previous</a></td>";
				}
				
				for (i = 1; i <= noOfPages; i++) {
					if(i==currentPage) {
						text += "<td class='page-number-td'><i class='page-number'>"+i+"</i></td>";
					}
					else {
						text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getActiveQuestionList('"+type+"','"+pillId+"','"+tagId+"','"+noOfPages+"','"+i+"')>"+i+"</a></td>";	
					}
					
				}
					
					if(currentPage < noOfPages) {
						text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getActiveQuestionList('"+type+"','"+pillId+"','"+tagId+"','"+noOfPages+"','"+currentPage+1 +"')>NEXT</a></td>"; 
					}
					text += "</tr></table>";
					
					
					"</h3></div>"
			
			
			
			return text;
		} 
		
		
			
			function getActiveQuestionList(type,pillId,tagId,noOfPages,currentPage){
				$.ajax({
					type: "GET",
					url: "/QuestionBank/allQuestion.do?listType=" + type+"&tagId="+tagId+"&page="+currentPage,
					datatype:'json',
					    success: function(data){
					    	mynew(init(data,type,pillId,tagId,noOfPages,currentPage));
					    },error: function() {
				            alert("error");
				        }
				});
				function mynew(text){
					document.getElementById(pillId).innerHTML =text;
				}
		} 