package brew.cmm.aspect;

import brew.cmm.vo.CommonVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RegisterModifierAspect {

    private final HttpServletRequest request;

    //@Before("(execution(* brew.mng..*Impl.*(..)) || execution(* brew.svc..*Impl.*(..))) && args(vo,..)")
    @Before("(execution(* brew.mng..*Impl.insert*(..)) || execution(* brew.mng..*Impl.update*(..)) " +
            "|| execution(* brew.svc..*Impl.insert*(..)) || execution(* brew.svc..*Impl.update*(..))) && args(vo,..)")
    public void setRegisterAndModifier(CommonVO vo) throws Throwable {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String userSn = (String) session.getAttribute("userSn");
            vo.setRgtrId(userSn);
            vo.setMdfrId(userSn);
        }
    }
}