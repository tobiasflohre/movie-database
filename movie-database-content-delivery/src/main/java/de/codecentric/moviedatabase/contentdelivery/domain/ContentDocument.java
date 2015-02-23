package de.codecentric.moviedatabase.contentdelivery.domain;

import static org.springframework.data.elasticsearch.annotations.FieldType.String;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "contentdocuments", type = "contentdocument", shards = 1, replicas = 0, refreshInterval = "-1", indexStoreType = "memory")
public class ContentDocument {

    @Id
    private String id;

    private String type;

    @Field(type = String, store = true)
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
