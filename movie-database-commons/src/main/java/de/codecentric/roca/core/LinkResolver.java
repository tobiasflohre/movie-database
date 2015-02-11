package de.codecentric.roca.core;

import org.springframework.core.env.Environment;

public class LinkResolver {
	
	private static final String URL_TYPE_EXTERNAL = "external";
	private static final String URL_TYPE_APPLICATION = "application";
	
	private Environment env;
	
	public LinkResolver(Environment env) {
		super();
		this.env = env;
	}

	public String resolve(String type, String url, String application){
		if (URL_TYPE_APPLICATION.equals(type)){
			String baseUrl = env.getProperty("moviedatabase."+application+".url.base");
			if (baseUrl == null){
				throw new IllegalArgumentException("For application "+application+" there's no base url registered in the properties.");
			} else {
				return baseUrl+url;
			}
		} else if (URL_TYPE_EXTERNAL.equals(type)){
			return url;
		} else {
			throw new IllegalArgumentException("Type "+type+" is invalid.");
		}
	}

}
