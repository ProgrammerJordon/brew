package brew.svc.csc.cst.service.Impl;

import brew.svc.csc.cst.service.Consult;
import brew.svc.csc.cst.service.ConsultService;
import brew.svc.csc.cst.service.ConsultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ConsultService")
@RequiredArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final ConsultDAO consultDAO;


    @Override
    public Consult insertConsult(ConsultVO vo) {
        int result = consultDAO.insertConsult(vo);

        if(result == 1) {
            vo.setResultMessage("문의사항 등록이 정상적으로 완료되었습니다.");
        }else {
            vo.setResultMessage("문의사항 등록이 실패하였습니다.");
        }

        return Consult.builder()
                .consultVO(vo)
                .build();
    }

    @Override
    public Consult selectConsultList(ConsultVO vo) {
        return Consult.builder()
                .consultVOList(consultDAO.selectConsultList(vo))
                .build();
    }

    @Override
    public Consult selectConsultDtVw(ConsultVO vo) {
        return Consult.builder()
                .consultVO(consultDAO.selectConsultDtVw(vo))
                .build();
    }

}
