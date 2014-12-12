package de.codecentric.moviedatabase.security.sso;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class CookieAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    public static final String COOKIE_MOVIEDATABASE_ID = "moviedatabase-id";

	final static Logger LOGGER = LoggerFactory.getLogger(CookieAuthenticationFilter.class);
    
    private SsoTokenRedisRepository ssoTokenRepository;
    
    public CookieAuthenticationFilter(SsoTokenRedisRepository ssoTokenRepository) {
        this.ssoTokenRepository = ssoTokenRepository;
    }

    /**
     * Return fixed 'N/A' authentication credentials
     */
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

    /**
     * Try to authenticate user from cookie
     */
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_MOVIEDATABASE_ID)) {
                    if (user == null || user.isAuthenticated() == false) {
                        return ssoTokenRepository.retrieveUserForSsoSession(cookie.getValue());
                    } else if (user.isAuthenticated()) {
                        return user.getName();
                    }
                }
            }
        }
        return null;
    }
}
