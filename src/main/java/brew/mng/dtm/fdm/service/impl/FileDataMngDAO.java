package brew.mng.dtm.fdm.service.impl;

import brew.mng.dtm.fdm.service.FileDataMngVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("FileDataMngDAO")
@RequiredArgsConstructor
public class FileDataMngDAO {

    private final SqlSessionTemplate sqlSession;

    List<FileDataMngVO> selectFileDataMngList(FileDataMngVO vo) {
        return sqlSession.selectList("FileDataMngDAO.selectFileDataMngList", vo);
    }
}
