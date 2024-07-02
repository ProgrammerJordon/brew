package brew.cmm.interceptor;

import brew.cmm.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrewAuthenticInterceptor implements HandlerInterceptor {

    private static final String[] EXCLUDED_URLS = {""};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(this.isExcludedUrl(request)) {
            return true;
        }

        // 서비스 or 관리 페이지에서 사용할 로직 추가하면 됨

        return true; // 세션에 ID가 있으면 요청을 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 필요한 경우 후처리 로직 추가
        if (isPopUpRequest(request)) {
            modelAndView.addObject("POPUPID", request.getHeader("POPUPID"));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 필요한 경우 완료 후 로직 추가
    }

    private boolean isExcludedUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        //contextPath가 존재하는 경우 요청 url의 contextPath를 제거하고 비교함
        if (!StringUtil.isEmpty(contextPath)) {
            requestURI = requestURI.replaceFirst(contextPath, "");
        }

        for (String excludedUrl : EXCLUDED_URLS) {
            Pattern pattern = Pattern.compile(excludedUrl);
            Matcher matcher = pattern.matcher(requestURI);

            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }

    /**
     * AJAX 요청여부 확인
     * @param request HTTP 요청 객체
     * @return AJAX 요청여부
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("AJAX") != null && request.getHeader("AJAX").equals(Boolean.TRUE.toString());
    }

    /**
     * 팝업화면 요청여부 확인
     *
     * @param request HTTP 요청 객체
     * @return 팝업화면 요청여부
     */
    private boolean isPopUpRequest(HttpServletRequest request) {
        return !StringUtil.isEmpty(request.getHeader("POPUPID"));
    }

}
