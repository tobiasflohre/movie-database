package de.codecentric.moviedatabase.actors.controller;

import java.util.Set;
import java.util.UUID;

public final class ActorsUtil {

	private ActorsUtil() {
		// pass
	}
	
	public static void convertSearchStringToSearchWords(String searchString, Set<String> searchWords, Set<UUID> movieIds) {
		// What I do here could probably be done a thousand times more elegant, but it's not the focus of this project...
		if (searchString != null){
			String[] splitAroundTags = searchString.split("movie:");
			for (String token: splitAroundTags){
				if (token.length()!=0){
					if (token.startsWith("'")){
						// It's a tag!
						String[] splitAroundQuotes = token.split("'");
						// first one is empty, the second one is the tag
						movieIds.add(UUID.fromString(splitAroundQuotes[1]));
						// if there is a rest they must be searchwords
						if (splitAroundQuotes.length > 2){
							for (int i = 2; i<splitAroundQuotes.length;i++){
								// split searchwords by blanks
								for (String searchWord: splitAroundQuotes[i].split(" ")){
									searchWords.add(searchWord.toLowerCase());
								}
							}
						}
					} else {
						// there's no tag
						// split searchwords by blanks
						for (String searchWord: token.split(" ")){
							searchWords.add(searchWord.toLowerCase());
						}
					}
				}
			}
		}
	}
}
