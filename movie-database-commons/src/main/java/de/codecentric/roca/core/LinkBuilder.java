package de.codecentric.roca.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class LinkBuilder {
	
	private LinkBuilder(){
		// nothing to do
	}
	
	private LinkBuilder(String baseUrl) {
		href.append(baseUrl);
	}

	private StringBuilder href = new StringBuilder();
	private Map<RequestParameter,String> requestParams = new HashMap<>();
	
	public static LinkBuilder linkTo(String baseUrl){
		return new LinkBuilder(baseUrl);
	}

	public static LinkBuilder linkTo(PathFragment path){
		return new LinkBuilder().path(path);
	}

	public LinkBuilder path(PathFragment path) {
		href.append("/").append(path.getName());
		return this;
	}

	public LinkBuilder path(Object path) {
		href.append("/").append(Objects.toString(path));
		return this;
	}

	public LinkBuilder requestParam(RequestParameter param, String value) {
		try {
			requestParams.put(param, URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return this;
	}
	
	public Link withRel(Relation relation){
		boolean firstParam = true;
		for (Entry<RequestParameter,String> entry:requestParams.entrySet()){
			if (firstParam){
				href.append("?");
				firstParam = false;
			} else {
				href.append("&");
			}
			href.append(entry.getKey().getName()).append("=").append(entry.getValue());
		}
		return new Link(href.toString(),relation.getName());
	}
	
}
