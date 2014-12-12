package de.codecentric.moviedatabase.security.sso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	final static Logger LOGGER = LoggerFactory
			.getLogger(CustomLogoutSuccessHandler.class);

	private SsoTokenRedisRepository ssoTokenRepository;

	public CustomLogoutSuccessHandler(SsoTokenRedisRepository ssoTokenRepository) {
		this.ssoTokenRepository = ssoTokenRepository;
	}

	/**
	 * User is logged out: Destroy current Spring security context and delete sso
	 * cookie
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		if (authentication != null && authentication.getDetails() != null) {
			ssoTokenRepository.deleteSsoSessionForUser(authentication.getName());
			httpServletRequest.getSession().invalidate();
			Cookie[] cookies = httpServletRequest.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(CookieAuthenticationFilter.COOKIE_MOVIEDATABASE_ID)) {
						cookie.setMaxAge(0);
						cookie.setPath("/");
						httpServletResponse.addCookie(cookie);
						break;
					}
				}
			}
		}
		httpServletResponse.sendRedirect(httpServletResponse
				.encodeRedirectURL(httpServletRequest.getContextPath()));
	}
}
