package brew.mng.usr.uat.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("UserAuthDAO")
@RequiredArgsConstructor
public class UserAuthDAO {

    private final SqlSessionTemplate sqlSession;

}
