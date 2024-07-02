package brew.cmm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BrewAuthenticInterceptor implements HandlerInterceptor {

    private static final String[] EXCLUDED_URLS = {""};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : EXCLUDED_URLS) {
            if (pathMatcher.match(pattern, requestURI)) {
                return true;
            }
        }

        // 세션에 ID가 있는지 확인(로직 변경 필요)
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("userId") == null) {
//            response.sendRedirect("/svc/mem/lgi/selectLoginVw.do"); // 세션에 ID가 없으면 로그인 페이지로 리다이렉트
//            return false;
//        }

        return true; // 세션에 ID가 있으면 요청을 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 필요한 경우 후처리 로직 추가
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 필요한 경우 완료 후 로직 추가
    }

}
