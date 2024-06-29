package brew.mng.min.idx.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("MainDAO")
@RequiredArgsConstructor
public class MainDAO {

    private final SqlSessionTemplate sqlSession;

}
