package brew.svc.csc.cst.service.Impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("ConsultDAO")
@RequiredArgsConstructor
public class ConsultDAO {

    private final SqlSessionTemplate sqlSession;

}
