package brew.mng.cmm.org.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    private List<OrganizationVO> organizationVOList;
    private OrganizationVO organizationVO;

}
