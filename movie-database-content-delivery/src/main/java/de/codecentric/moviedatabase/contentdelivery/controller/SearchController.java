package de.codecentric.moviedatabase.contentdelivery.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.codecentric.moviedatabase.contentdelivery.domain.ContentDocument;
import de.codecentric.moviedatabase.contentdelivery.repository.ContentDocumentRepository;

@RequestMapping("/search")
@RestController
public class SearchController {
	
	private ContentDocumentRepository contentDocumentRepository;
	
	public SearchController(ContentDocumentRepository contentDocumentRepository) {
		super();
		this.contentDocumentRepository = contentDocumentRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<ContentDocument> handleSearchRequest(){
		return contentDocumentRepository.findAll(new PageRequest(0,20));
	}

	@RequestMapping(method = RequestMethod.GET, params = "type")
	public Iterable<ContentDocument> handleSearchRequest(@RequestParam String type){
		return contentDocumentRepository.findByType(type, new PageRequest(0,20));
	}

}
