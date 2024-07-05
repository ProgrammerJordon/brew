package brew.cmm.interceptor;

import brew.cmm.service.log.vst.service.BrewVisitorVO;
import brew.cmm.service.log.vst.service.impl.BrewVisitorDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class BrewVisitorLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        HttpSession session = request.getSession(true);

        Object obj = session.getAttribute("visitor");
        Boolean check = obj instanceof Boolean ? (Boolean) obj : true;

        if(check == null) {
            check = true;
        }

        if(BooleanUtils.isFalse(check.booleanValue())) {
            System.out.println("로그아웃한 사용자");
        }else {
            Object Obj = session.getAttribute("visitor");

            boolean visitYn = (Obj != null) ? (Boolean) Obj : false;

            if (BooleanUtils.isFalse(visitYn)) {

                // 세션에 userId 값이 없는 경우 즉, 세션이 새롭게 만들어지거나 로그인되지 않은상태
                WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
                BrewVisitorDAO visitorDAO = ctx.getBean(BrewVisitorDAO.class);

                String sessionId = session.getId();
                String clientIp = request.getRemoteAddr();

                BrewVisitorVO vo = new BrewVisitorVO();
                vo.setSessionId(sessionId);
                vo.setClientIp(clientIp);

                try {
                    visitorDAO.insertVisitorLog(vo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                session.setAttribute("visitor", true);
            }
        }
        return true;
    }
}
