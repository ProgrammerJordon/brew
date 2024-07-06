package brew.mng.uss.ull.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("LoginLogDAO")
@RequiredArgsConstructor
public class LoginLogDAO {

    private final SqlSessionTemplate sqlSession;

}
