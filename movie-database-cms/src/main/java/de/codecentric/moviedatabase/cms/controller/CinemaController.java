package de.codecentric.moviedatabase.cms.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import de.codecentric.moviedatabase.cms.domain.Cinema;
import de.codecentric.moviedatabase.cms.domain.ContentDocument;
import de.codecentric.moviedatabase.cms.domain.ContentDocumentResponse;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.LinkBuilder;

@RequestMapping("/cinemas")
public class CinemaController {
	
	private String contentDeliveryBaseUrl;
	private String navigationBaseUrl;
	private RestTemplate restTemplate;
	
	public CinemaController(String contentDeliveryBaseUrl, String navigationBaseUrl) {
		super();
		this.contentDeliveryBaseUrl = contentDeliveryBaseUrl;
		this.navigationBaseUrl = navigationBaseUrl;
		this.restTemplate = new RestTemplate();
	}

	@ModelAttribute("linkNavigation")
	public Link getLinkNavigation(HttpServletRequest request){
		// Currently searching is not possible on cms sites
		LinkBuilder navigationLinkBuilder = linkTo(navigationBaseUrl).path(CmsPathFragment.NAVIGATION).requestParam(CmsRequestParameter.SEARCH_URL, "#")
				.requestParam(CmsRequestParameter.ACTIVE, "cinemas");
		return navigationLinkBuilder.withRel(CmsRelation.NAVIGATION);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showAllCinemas(Model model){
		ContentDocumentResponse contentDocuments = restTemplate.getForObject(contentDeliveryBaseUrl+"/search?type=cinema", ContentDocumentResponse.class);
		List<Cinema> cinemas = new ArrayList<Cinema>();
		for (ContentDocument contentDocument: contentDocuments.getContent()){
			cinemas.add(restTemplate.getForObject(contentDeliveryBaseUrl+"/content/"+contentDocument.getUrl(), Cinema.class));
		}
		model.addAttribute("cinemas", cinemas);
		return "/cinemas";
	}

}
