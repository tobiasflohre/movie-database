package de.codecentric.moviedatabase.cms.domain;

import java.util.ArrayList;
import java.util.Collection;

public class ContentDocumentResponse {

    private long totalElements;

    private Collection<ContentDocument> content = new ArrayList<ContentDocument>();

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public Collection<ContentDocument> getContent() {
		return content;
	}

	public void setContent(Collection<ContentDocument> content) {
		this.content = content;
	}
    
}
