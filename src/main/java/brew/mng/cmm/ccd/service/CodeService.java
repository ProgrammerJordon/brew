package brew.mng.cmm.ccd.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface CodeService {

    public Code selectCodeList(CodeVO vo);
    public Code insertCode(CodeVO vo);
    public Code selectCode(CodeVO vo);
    public Code updateCode(CodeVO vo);
    public Code deleteCode(CodeVO vo);
    public Code insertCodeDtls(CodeVO vo);
    public Code selectCodeDtlsList(CodeVO vo);
    public Code selectCodedtls(CodeVO vo);
    public Code updateCodedtls(CodeVO vo);
    public Code deleteCodedtls(CodeVO vo);
}
