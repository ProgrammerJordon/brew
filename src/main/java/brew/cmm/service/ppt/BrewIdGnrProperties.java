package brew.cmm.service.ppt;

import brew.cmm.util.BrewCommonUtil;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("BrewIdGnrDAO")
@RequiredArgsConstructor
@Transactional
public class BrewIdGnrProperties {

    private final SqlSessionTemplate sqlSession;

    @Transactional
    public String getNextFileId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextFileId");
        sqlSession.update("BrewIdGnrDAO.updateNextFileId");
        return String.format("FILE_%015d", nextValue + 1);
    }

    public String getNextLogId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextLogId");
        sqlSession.update("BrewIdGnrDAO.updateNextLogId");
        return String.format("LOG_%016d", nextValue + 1);
    }

    public String getNextLoginLogId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextLoginLogId");
        sqlSession.update("BrewIdGnrDAO.updateNextLoginLogId");
        return String.format("LGI_%016d", nextValue + 1);
    }

    public String getNextBbsId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextBbsId");
        sqlSession.update("BrewIdGnrDAO.updateNextBbsId");
        return String.format("BBS_%016d", nextValue + 1);
    }

    public String getNextFaqId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextFaqId");
        sqlSession.update("BrewIdGnrDAO.updateNextFaqId");
        return String.format("FAQ_%016d", nextValue + 1);
    }

    public String getNextCodeId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextCodeId");
        sqlSession.update("BrewIdGnrDAO.updateNextCodeId");
        return String.format("CODE_%03d", nextValue + 1);
    }

    public String getNextUserId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextUserId");
        sqlSession.update("BrewIdGnrDAO.updateNextUserId");
        return String.format("USER_%015d", nextValue + 1);
    }

    public String getNextExitId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextExitId");
        sqlSession.update("BrewIdGnrDAO.updateNextExitId");
        return String.format("EXIT_%015d", nextValue + 1);
    }

    public String getNextAdvId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextAdvId");
        sqlSession.update("BrewIdGnrDAO.updateNextAdvId");
        return String.format("ADV_%016d", nextValue + 1);
    }

    public String getNextMdmId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextMdmId");
        sqlSession.update("BrewIdGnrDAO.updateNextMdmId");
        return String.format("MDM_%016d", nextValue + 1);
    }

    public String getNextOrgId() {
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextOrgId");
        sqlSession.update("BrewIdGnrDAO.updateNextOrgId");
        return String.format("%02d", nextValue + 1);
    }

}
