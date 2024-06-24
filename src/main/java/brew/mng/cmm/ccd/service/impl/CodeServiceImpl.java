package brew.mng.cmm.ccd.service.impl;

import brew.mng.cmm.ccd.service.Code;
import brew.mng.cmm.ccd.service.CodeService;
import brew.mng.cmm.ccd.service.CodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("CodeService")
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeDAO codeDAO;

    @Override
    public Code selectCodeList(CodeVO vo) {
        return Code.builder()
                .codeVOList(codeDAO.selectCodeList(vo))
                .build();
    }

    @Override
    public Code insertCode(CodeVO vo) {
        int result = codeDAO.insertCode(vo);
        if(result == 1) {
            vo.setResultMessage("공통코드가 정상적으로 등록되었습니다.");
        }else {
            vo.setResultMessage("공통코드 등록에 실패하였습니다.");
        }
        return Code.builder()
                .codeVO(vo)
                .build();
    }

    @Override
    public Code selectCode(CodeVO vo) {
        return Code.builder()
                .codeVO(codeDAO.selectCode(vo))
                .build();
    }

    @Override
    public Code updateCode(CodeVO vo) {
        int result = codeDAO.updateCode(vo);
        if(result == 1) {
            vo.setResultMessage("공통코드가 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("공통코드 수정을 실패하였습니다.");
        }
        return Code.builder()
                .codeVO(vo)
                .build();
    }
}
