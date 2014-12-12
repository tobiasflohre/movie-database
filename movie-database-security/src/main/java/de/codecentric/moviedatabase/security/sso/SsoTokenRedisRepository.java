package de.codecentric.moviedatabase.security.sso;

import org.springframework.data.redis.core.StringRedisTemplate;

public class SsoTokenRedisRepository {
	
	private StringRedisTemplate redisTemplate;

	public SsoTokenRedisRepository(StringRedisTemplate redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}
	
	public void createSsoSessionForUser(String user, String sessionIdentifier){
		redisTemplate.opsForValue().set(sessionIdentifier, user);
		redisTemplate.opsForValue().set(user, sessionIdentifier);
	}
	
	public String retrieveUserForSsoSession(String sessionIdentifier){
		return redisTemplate.opsForValue().get(sessionIdentifier);
	}
	
	public void deleteSsoSessionForUser(String user){
		String sessionIdentifier = redisTemplate.opsForValue().get(user);
		redisTemplate.delete(sessionIdentifier);
		redisTemplate.delete(user);
	}

}
