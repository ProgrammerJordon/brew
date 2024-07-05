package brew.cmm.service.ppt;

import brew.cmm.util.BrewCommonUtil;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("BrewIdGnrDAO")
@RequiredArgsConstructor
public class BrewIdGnrProperties {

    private final SqlSessionTemplate sqlSession;

    @Transactional
    public String getNextFileId() {
        // 현재 FILE_ID 값 조회
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextFileId");
        // FILE_ID 값 +1
        sqlSession.update("BrewIdGnrDAO.updateNextFileId");
        // 현재 FILE_ID 값에서 +1 값 반환
        return String.format("FILE_%015d", nextValue + 1);
    }

    @Transactional
    public String getNextLogId() {
        // 현재 LOG_ID 값 조회
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextLogId");
        // LOG_ID 값 +1
        sqlSession.update("BrewIdGnrDAO.updateNextLogId");
        // 현재 LOG_ID 값에서 +1 값 반환
        return String.format("LOG_%016d", nextValue + 1);
    }

    @Transactional
    public String getNextLoginLogId() {
        // 현재 LOG_ID 값 조회
        int nextValue = sqlSession.selectOne("BrewIdGnrDAO.getNextLoginLogId");
        // LOG_ID 값 +1
        sqlSession.update("BrewIdGnrDAO.updateNextLoginLogId");
        // 현재 LOG_ID 값에서 +1 값 반환
        return String.format("LGI_%016d", nextValue + 1);
    }

}
