function answerUpvote(val){ 
			xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var currentUp = document.getElementById("answerUp"+val).innerHTML;
					var currentDown = document.getElementById("answerDown"+val).innerHTML;
						if(xhttp.responseText==currentUp) {
							return;
						}
						else {
							document.getElementById("answerUp"+val).innerHTML = xhttp.responseText;
							if(currentDown!=0){
								document.getElementById("answerDown"+val).innerHTML = currentDown-1;
							}
						}
					}
				}
			
			xhttp.open("GET", "/QuestionBank/answerUpVote.do?answerId=" + val, true);
			xhttp.send();
		}
		
		function answerDownvote(val){
			xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var currentUp = document.getElementById("answerUp"+val).innerHTML;
					var currentDown = document.getElementById("answerDown"+val).innerHTML;
					if(xhttp.responseText==currentDown) {
						return;
					}
					else {
						document.getElementById("answerDown"+val).innerHTML = xhttp.responseText;
						if(currentUp!=0){
							document.getElementById("answerUp"+val).innerHTML = currentUp-1;
						}
					}
				}				}
			
			xhttp.open("GET", "/QuestionBank/answerDownVote.do?answerId=" + val, true);
			xhttp.send();
		}
		
		function questionUpvote(val){ 
			xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var currentUp = document.getElementById("questionUp"+val).innerHTML;
					var currentDown = document.getElementById("questionDown"+val).innerHTML;
						if(xhttp.responseText==currentUp) {
							return;
						}
						else {
							document.getElementById("questionUp"+val).innerHTML = xhttp.responseText;
							if(currentDown!=0){
								document.getElementById("questionDown"+val).innerHTML = currentDown-1;
							}
						}
					}
			}
			xhttp.open("GET", "/QuestionBank/questionUpVote.do?questionId=" + val, true);
			xhttp.send();
		}
		
		function questionDownvote(val){ 
			xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var currentUp = document.getElementById("questionUp"+val).innerHTML;
					var currentDown = document.getElementById("questionDown"+val).innerHTML;
					if(xhttp.responseText==currentDown) {
						return;
					}
					else {
						document.getElementById("questionDown"+val).innerHTML = xhttp.responseText;
						if(currentUp!=0){
							document.getElementById("questionUp"+val).innerHTML = currentUp-1;
						}
					}
				}
			}
			xhttp.open("GET", "/QuestionBank/questiondDownVote.do?questionId=" + val, true);
			xhttp.send();
		}