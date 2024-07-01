package brew.cmm.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SessionTimeoutCookieFilter implements Filter {

	@SuppressWarnings("unused")
	private FilterConfig config;

	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        long serverTime = System.currentTimeMillis();
        long sessionExpireTime = serverTime + httpRequest.getSession().getMaxInactiveInterval() * 1000;
        Cookie cookie = new Cookie("LatestServerTime", "" + serverTime);
        boolean secure = request.isSecure();
        if ( secure ) cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
        cookie = new Cookie("ExpireSessionTime", "" + sessionExpireTime);
        if ( secure ) cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        
        httpResponse.addCookie(cookie);

        chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {

	}
}
