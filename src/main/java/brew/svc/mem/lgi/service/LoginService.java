package brew.svc.mem.lgi.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginService {

    public Login insertKakaoLogin(LoginVO vo);

    public Login selectKakaoLogin(@RequestBody LoginVO vo);
}
