package brew.mng.dtm.cam.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("CollectApiDAO")
@RequiredArgsConstructor
public class CollectApiDAO {

    private final SqlSessionTemplate sqlSession;


}
