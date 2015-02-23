package de.codecentric.moviedatabase.contentdelivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import de.codecentric.moviedatabase.contentdelivery.domain.ContentDocument;

public interface ContentDocumentRepository extends ElasticsearchRepository<ContentDocument,String> {
	
	Page<ContentDocument> findByType(String type, Pageable pageable);
	
}
