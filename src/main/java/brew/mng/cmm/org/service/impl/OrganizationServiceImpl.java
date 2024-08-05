package brew.mng.cmm.org.service.impl;

import brew.mng.cmm.org.service.Organization;
import brew.mng.cmm.org.service.OrganizationService;
import brew.mng.cmm.org.service.OrganizationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("OrganiztionService")
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDAO organizationDAO;


    @Override
    public Organization insertOranization(OrganizationVO vo) {

        int result = organizationDAO.insertOranization(vo);

        if(result == 1) {
            vo.setResultMessage("조직이 정상적으로 등록되었습니다.");
        }else {
            vo.setResultMessage("조직 등록에 실패하였습니다.");
        }
        return Organization.builder()
                .organizationVO(vo)
                .build();
    }
}
