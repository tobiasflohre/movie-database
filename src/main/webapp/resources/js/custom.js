$(function() {
	$("body").on("click","a",function(e) {
		// users want to open the page in a new tab - let them do it!
		if (e.ctrlKey || e.metaKey) {
			return;
		}
		var url = this.href;
		$('#pageContent').load(url);
		history.pushState(null, null, url);
		return false;
	});

	setTimeout(function() {
		$(window).bind("popstate", function(e) {
			$('#pageContent').load(location.href);
		});
	}, 0);
  
	$("body").on("submit", 'form[method="post"]', function(e){
		e.preventDefault();
		var $form = $(this);
		var url = $form.attr("action");
		$.post(url, $form.serialize(), function(html, textStatus, jqXHR) {
	    	$("#pageContent").html(html);
	    	// if the current url does not reflect the content, the correct url may be specified by a header attribute
	    	var redirectUrl = jqXHR.getResponseHeader('redirectUrl');
	    	if (redirectUrl){
				history.pushState(null, null, redirectUrl);
	    	} else {
				history.pushState(null, null, url);
	    	};
	    });
	});

	$("body").on("submit", 'form[method="get"]', function(e){
		e.preventDefault();
		var $form = $(this);
		var queryParams = $form.serialize();
		var url = $form.attr("action")+'?'+queryParams;
		$('#pageContent').load(url);
		history.pushState(null, null, url);
	});

});
