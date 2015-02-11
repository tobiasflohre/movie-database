package de.codecentric.moviedatabase.cms.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.LinkBuilder;

@RequestMapping("/cms")
public class CmsController {
	
	private String contentDeliveryBaseUrl;
	private String navigationBaseUrl;
	private RestTemplate restTemplate;
	
	public CmsController(String contentDeliveryBaseUrl, String navigationBaseUrl) {
		super();
		this.contentDeliveryBaseUrl = contentDeliveryBaseUrl;
		this.navigationBaseUrl = navigationBaseUrl;
		this.restTemplate = new RestTemplate();
	}

	@ModelAttribute("linkNavigation")
	public Link getLinkNavigation(HttpServletRequest request){
		// Currently searching is not possible on cms sites
		LinkBuilder navigationLinkBuilder = linkTo(navigationBaseUrl).path(CmsPathFragment.NAVIGATION).requestParam(CmsRequestParameter.SEARCH_URL, "#")
				.requestParam(CmsRequestParameter.ACTIVE, "cms");
		return navigationLinkBuilder.withRel(CmsRelation.NAVIGATION);
	}

	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String handleGetRequest(Model model, HttpServletRequest request){
	    String pathWithinHandlerMapping = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	    // Remove leading /cms
	    String path = pathWithinHandlerMapping.substring(4);
		Map<?,?> cmsDocument = restTemplate.getForObject(contentDeliveryBaseUrl+path+".json", Map.class);
		// HTML will be fetched by Thymeleafs UrlTemplateResolver at the following contentUrl
		model.addAttribute("contentUrl", contentDeliveryBaseUrl+path+cmsDocument.get("contentHtml"));
		return (String) cmsDocument.get("type");
	}

}
