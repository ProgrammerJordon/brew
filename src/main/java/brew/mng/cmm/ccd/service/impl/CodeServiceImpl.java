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

    @Override
    public Code deleteCode(CodeVO vo) {

        int count = codeDAO.selectCodedtlsCnt(vo);

        if(count == 0) {
            int result = codeDAO.deleteCode(vo);
            if(result == 1) {
                vo.setResultMessage("공통코드가 정상적으로 삭제되었습니다.");
            }else {
                vo.setResultMessage("공통코드 삭제가 실패하였습니다.");
            }
        }else {
            vo.setResultMessage("공통코드 하위에 공통코드상세가 존재합니다.");
        }

        return Code.builder()
                .codeVO(vo)
                .build();
    }

    @Override
    public Code insertCodeDtls(CodeVO vo) {
        int result = codeDAO.insertCodeDtls(vo);
        if(result == 1) {
            vo.setResultMessage("공통코드가 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("공통코드 수정을 실패하였습니다.");
        }
        return Code.builder()
                .codeVO(vo)
                .build();
    }

    @Override
    public Code selectCodeDtlsList(CodeVO vo) {
        return Code.builder()
                .codeVOList(codeDAO.selectCodeDtlsList(vo))
                .build();
    }

    @Override
    public Code selectCodedtls(CodeVO vo) {
        return Code.builder()
                .codeVO(codeDAO.selectCodedtls(vo))
                .build();
    }

    @Override
    public Code updateCodedtls(CodeVO vo) {
        int result = codeDAO.updateCodedtls(vo);
        if(result == 1) {
            vo.setResultMessage("공통코드상세가 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("공통코드상세 수정을 실패하였습니다.");
        }
        return Code.builder()
                .codeVO(vo)
                .build();
    }

    @Override
    public Code deleteCodedtls(CodeVO vo) {
        int result = codeDAO.deleteCodedtls(vo);
        if(result == 1) {
            vo.setResultMessage("공통코드상세가 정상적으로 삭제되었습니다.");
        }else {
            vo.setResultMessage("공통코드상세 삭제를 실패하였습니다.");
        }
        return Code.builder()
                .codeVO(vo)
                .build();
    }
}
