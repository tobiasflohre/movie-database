package de.codecentric.moviedatabase.security.sso;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final static Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private SsoTokenRedisRepository ssoTokenRepository;

	public CustomAuthenticationSuccessHandler(
			SsoTokenRedisRepository ssoTokenRepository) {
		this.ssoTokenRepository = ssoTokenRepository;
	}

	/**
     * Set sso cookie on successful authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication)
            throws IOException, ServletException {
        String username = authentication.getName();
        UUID accessToken = UUID.randomUUID();
        Cookie ssoCookie = new Cookie(CookieAuthenticationFilter.COOKIE_MOVIEDATABASE_ID, accessToken.toString());
        ssoCookie.setPath("/");
        httpServletResponse.addCookie(ssoCookie);
        ssoTokenRepository.createSsoSessionForUser(username, accessToken.toString());
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        SavedRequest savedRequest = (SavedRequest) httpServletRequest.getSession()
                .getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null) {
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(savedRequest.getRedirectUrl()));
        } else {
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(httpServletRequest.getContextPath()));
        }
    }

}