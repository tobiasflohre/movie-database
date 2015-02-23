package de.codecentric.moviedatabase.cms.domain;

import java.util.ArrayList;
import java.util.Collection;

public class ContentDocument {

    private String id;

    private String type;

    private Collection<String> tags = new ArrayList<String>();
    
    private String url;

    public ContentDocument() {
    }

    public ContentDocument(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
}
