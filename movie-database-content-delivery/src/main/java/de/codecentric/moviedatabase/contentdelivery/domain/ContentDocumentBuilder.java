package de.codecentric.moviedatabase.contentdelivery.domain;

import org.springframework.data.elasticsearch.core.query.IndexQuery;

import java.util.ArrayList;
import java.util.List;

public class ContentDocumentBuilder {

    private ContentDocument result;

    public ContentDocumentBuilder(String id) {
        result = new ContentDocument(id);
    }

    public ContentDocumentBuilder type(String type) {
        result.setType(type);
        return this;
    }

    public ContentDocumentBuilder url(String url) {
        result.setUrl(url);
        return this;
    }

    public ContentDocument build() {
        return result;
    }

    public ContentDocumentBuilder addTag(String tag) {
        List<String> tagsTmp = new ArrayList<String>();
        if(result.getTags()==null){
            result.setTags(tagsTmp);
        }else {
            tagsTmp = (List<String>) result.getTags();
        }
        tagsTmp.add(tag);
        return this;
    }

    public IndexQuery buildIndex() {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(result.getId());
        indexQuery.setObject(result);
        return indexQuery;
    }

}
