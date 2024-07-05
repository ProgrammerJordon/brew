package brew.svc.csc.cst.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface ConsultService {

    public Consult insertConsult(ConsultVO vo);

    public Consult selectConsultList(ConsultVO vo);

    public Consult selectConsultDtVw(ConsultVO vo);

    public Consult updateConsult(ConsultVO vo);

    public Consult deleteConsult(ConsultVO vo);
}
