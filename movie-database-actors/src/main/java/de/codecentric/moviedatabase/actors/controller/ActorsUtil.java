package de.codecentric.moviedatabase.actors.controller;

import java.util.Set;

public final class ActorsUtil {

	private ActorsUtil() {
		// pass
	}
	
	public static void convertSearchStringToSearchWords(String searchString, Set<String> searchWords) {
		if (searchString != null){
			for (String searchWord: searchString.split(" ")){
				searchWords.add(searchWord.toLowerCase());
			}
		}
	}
}
