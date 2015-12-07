function init(data,type,pillId,noOfPages,currentPage){
			
			var tagNames = [];
			var questionCounts = [];
			
			for(var i in data) {
				tagNames.push(data[i].tagName);
				questionCounts.push(data[i].questionCount);
			}
			
			
			
			var text="";
			for (i = 0; i < data.length; i++) { 
				text += "<div class='col-lg-4 col-md-4 col-sm-5 col-xs-12 user-card'>"
				
				 +"<h4>Name: " +"<a href ='listQuestion.do?tagId= "+data[i].tagId+ "&page=1' >"+ tagNames[i] + "</a>" + "</h4>"+
				"<label class='control-label'>No. of Questions:"+questionCounts[i]+"</label> </div>";
				
				
			  
			}
			
	 
			text += "<div><h3>";
			
			text += "<table class='pagination-table'><tr>";
			
			if(currentPage!=1){
				text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getTagList('"+type+"','"+pillId+"','"+noOfPages+"','"+currentPage+"')>Previous</a></td>";
			}
			
			for (i = 1; i <= noOfPages; i++) {
				if(i==currentPage) {
					text += "<td class='page-number-td'><i class='page-number'>"+i+"</i></td>";
				}
				else {
					text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getTagList('"+type+"','"+pillId+"','"+noOfPages+"','"+i+"')>"+i+"</a></td>";	
				}
				
			}
				
				if(currentPage < noOfPages) {
					text += "<td class='page-number-td'><a class='page-number' href='#' onclick=getTagList('"+type+"','"+pillId+"','"+noOfPages+"','"+currentPage+1+"')>NEXT</a></td>"; 
				}
				text += "</tr></table>";
				
				
				"</h3></div></div>"
		 
		
			return text;
		} 

			function getTagList(type,pillId,noOfPages,currentPage){ 
			
				$.ajax({
					type: "GET",
					url: "/QuestionBank/getTags.do?listType=" + type+"&page="+currentPage,
					datatype:'json',
				    	success: function(data){	
				    		mynew(init(data,type,pillId,noOfPages,currentPage));
				    	},error: function() {
			            	alert("error");
			        	}
				});
			
				function mynew(text){
					document.getElementById(pillId).innerHTML =text;
				}
		} 