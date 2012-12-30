$(function() {
	setTimeout(function() {
		$(window).bind("popstate", function(e) {
			$('#pageContent').load(location.href);
		});
	}, 0);
  
	$("body").on("click","a.render-partial",function(e) {
		// users want to open the page in a new tab - let them do it!
		if (e.ctrlKey || e.metaKey) {
			return;
		}
		var url = this.href;
		$('#pageContent').load(url);
		history.pushState(null, null, url);
		return false;
	});
	
	$("body").on("submit", 'form[method="post"].render-partial', function(e){
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

	$("body").on("submit", 'form[method="post"].render-modal', function(e){
		e.preventDefault();
		var $form = $(this);
		var url = $form.attr("action");
		$.post(url, $form.serialize(), function(html, textStatus, jqXHR) {
	    	$("#genericModal .modal-body").html(html);
			$('#genericModal .render-partial').removeClass('render-partial').addClass('render-modal');
	    });
	});

	$("body").on("submit", 'form[method="get"].render-partial', function(e){
		e.preventDefault();
		var $form = $(this);
		var queryParams = $form.serialize();
		var url = $form.attr("action")+'?'+queryParams;
		$('#pageContent').load(url);
		history.pushState(null, null, url);
	});

	$("body").on("click", "a.render-modal", function(e){
		// users want to open the page in a new tab - let them do it!
		if (e.ctrlKey || e.metaKey) {
			return;
		}
		e.preventDefault();
    	$("#genericModal .modal-body").load(this.href, function(){
    		$('#genericModal .render-partial').removeClass('render-partial').addClass('render-modal');
    	});
		$('#genericModal').modal('show');
	});

});
