var string = "";
		
		
	
		$(document).ready(function() {
			var str = [];
			var tagNam = $('#hiddenTags').val();
			var arr = String(tagNam);
			arr = arr.replace("[", "");
			arr = arr.replace("]", "");
			var spl = arr.split(',');
	
			for ( var s in spl) {
				str.push({title : spl[s]});
			}
	
			$('#related-tags').selectize({
				maxItems : 5,
				valueField : 'title',
				labelField : 'title',
				searchField : 'title',
				options : str,
				create : true
			});
	
		});
		
		$(document).ready(function() {
			var str = [];
			var tagNam = $('#hiddenTags').val();
			var arr = String(tagNam);
			arr = arr.replace("[", "");
			arr = arr.replace("]", "");
			var spl = arr.split(',');
	
			for ( var s in spl) {
				str.push({title : spl[s]});
			}
	
			$('#tags').selectize({
				maxItems : 5,
				valueField : 'title',
				labelField : 'title',
				searchField : 'title',
				options : str,
				create : false
			});
	
		});
		
		$(document).ready(function() {
			var str = [];
			var tagNam = $('#hiddenUsers').val();
			var arr = String(tagNam);
			arr = arr.replace("[", "");
			arr = arr.replace("]", "");
			var spl = arr.split(',');
	
			for ( var s in spl) {
				str.push({title : spl[s]});
			}
	
			$('#users').selectize({
				maxItems : 1,
				valueField : 'title',
				labelField : 'title',
				searchField : 'title',
				options : str,
				create : false
			});
	
		});
		
		function alertUser() {
			alert("Please Login First!!!");
		}
	