(function ($) {
	jQuery.expr[':'].Contains = function(a,i,m){
		return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
	};

	function listFilter(header, list) {
		var form = $("<form>").attr({"class":"filterform","action":"#"}),
		input = $("<input>").attr({"class":"filterinput form-control","type":"text"});
		$(form).append(input).appendTo(header);

		$(input)
		.change( function () {
			var filter = $(this).val();
			if(filter) {
				$(list).find(".movieTitle:not(:Contains(" + filter + "))").parent().slideUp();
				$(list).find(".movieTitle:Contains(" + filter + ")").parent().slideDown();
			} else {
				$(list).find(".entry").slideDown();
			}
			return false;
		})
		.keyup( function () {
			$(this).change();
		});
	}

	$(function () {
		listFilter($("#header"), $("#list"));
	});
}(jQuery));