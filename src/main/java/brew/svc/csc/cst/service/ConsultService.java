package brew.svc.csc.cst.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface ConsultService {

    public Consult insertConsult(ConsultVO vo);

    // 컨트롤러 함수 요청 -> 인터페이스 함수를 골라줘 -> 임플리먼트 실행을해

    public Consult selectConsultList(ConsultVO vo);
}
