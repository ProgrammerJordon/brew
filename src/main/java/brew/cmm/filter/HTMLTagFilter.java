package brew.cmm.filter;

import brew.cmm.util.StringUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HTMLTagFilter implements Filter {

    @SuppressWarnings("unused")
    private FilterConfig config;

    private Set<String> excludeUrls;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String contextPath = httpRequest.getContextPath();
        String path = httpRequest.getRequestURI();

        //contextPath가 존재하는 경우 요청 url의 contextPath를 제거하고 비교함
        if (!StringUtil.isEmpty(contextPath)) {
            path = path.replaceFirst(contextPath, "");
        }

        //필터링 예외 처리
        if (excludeUrls.contains(path)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new HTMLTagFilterRequestWrapper((HttpServletRequest)request), response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;

        excludeUrls = new HashSet<>();
        //excludeUrls.add("/uss/sam/stp/StplatCnUpdt.do");
        // 필터링 예외 적용할 url 추가

    }
    public void destroy() {

    }
}
