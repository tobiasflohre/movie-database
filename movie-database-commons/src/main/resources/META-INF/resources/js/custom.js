;(function($, undefined) {
	$(function() {
		
		// enabling back button with JavaScript
		setTimeout(function() {
			$(window).bind("popstate", function(e) {
				$("#pageContent").load(location.href);
			});
		}, 0);
	  
		// partial content rendering when clicking on a link
		$("body").on("click","a.render-partial", allowNewTabShortcuts(function(e) {
			var url = this.href;
			$("#pageContent").load(url);
			history.pushState(null, null, url);
		}));
		
		// partial content rendering when submitting a form via post
		$("body").on("submit", "form[method='post'].render-partial", function(e){
			e.preventDefault();
			var form = $(this);
			var url = form.attr("action");
			$.post(url, form.serialize(), function(html, textStatus, jqXHR) {
				// custom event that anybody can listen to
		    	form.trigger("submitComplete");
		    	
		    	$("#pageContent").html(html);
		    	// if the current url does not reflect the content, the correct url may be specified by a header attribute
		    	var redirectUrl = jqXHR.getResponseHeader("redirectUrl");
		    	if (redirectUrl){
					history.pushState(null, null, redirectUrl);
		    	} else {
					history.pushState(null, null, url);
		    	};
		    });
		});
	
		// content rendering in a modal window when submitting a form via post
		$("body").on("submit", "form[method='post'].render-modal", function(e){
			e.preventDefault();
			var form = $(this);
			var url = form.attr("action");
			$.post(url, form.serialize(), function(html, textStatus, jqXHR) {
				// custom event that anybody can listen to
				form.trigger("submitComplete");
				
				$("#genericModal .modal-body").html(html);
				$("#genericModal .render-partial").removeClass("render-partial").addClass("render-modal");
		    });
		});
	
		// partial content rendering when submitting a form via get
		$("body").on("submit", "form[method='get'].render-partial", function(e){
			e.preventDefault();
			var form = $(this);
			var queryParams = form.serialize();
			var url = form.attr("action") + "?" + queryParams;
			$("#pageContent").load(url);
			history.pushState(null, null, url);
		});
	
		// content rendering in a modal window when clicking on a link
		$("body").on("click", "a.render-modal", allowNewTabShortcuts(function(e){
	    	$("#genericModal .modal-body").load(this.href, function(){
	    		$("#genericModal .render-partial").removeClass("render-partial").addClass("render-modal");
	    	});
			$("#genericModal").modal("show");
		}));
		
		// clicking on a tag link in the sidebar shall add it to the search input, clicking on home shall remove it
		$("#searchbar, #tagsAll").on("click", "a", function(e){
			var searchString = getParameterByName(this.href,"searchString");
			$("#searchString").attr("value", searchString);
		});
		
		// after form submission of deleteTag and addTag the tags in the sidebar need to be refreshed
		$("body").on("submitComplete","#deleteTag, #addTag",function(e){
			$("#tagsAll").load($("#tagsAll").data("link-tags-all"));
		});
		
	});
	
	function allowNewTabShortcuts(callback) {
		return function(e) {
			// users want to open the page in a new tab - let them do it!
			if (!e.ctrlKey && !e.metaKey) {
				e.preventDefault();
				callback.apply(this, arguments);
				
			}
		};
	}
	
	function getParameterByName(uri, name) {
		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regexS = "[\\?&]" + name + "=([^&#]*)";
		var regex = new RegExp(regexS);
		var results = regex.exec(uri);
		if(results == null) {
			return "";
		}
		
		return decodeURIComponent(results[1].replace(/\+/g, " "));
	}
})(jQuery);