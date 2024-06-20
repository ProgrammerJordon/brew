package brew.cmm.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Class Name : CORSFilter.java
 * @Description : 크로스도메인 설정 필터
 * @author 함성원
 * @since 2023.08.30
 */
@Component
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse) res;
		
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Accept,x-requested-with,remember-me,Authorization,DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,If-Modified-Since,Cache-Control");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		chain.doFilter(req, res);
	}
	
	@Override
	public void destroy() {
		
	}
}