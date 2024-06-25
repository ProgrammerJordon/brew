package brew.mng.usr.usm.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("UserDAO")
@RequiredArgsConstructor
public class UserDAO {

    private final SqlSessionTemplate sqlSession;

}
