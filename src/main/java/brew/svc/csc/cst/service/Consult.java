package brew.svc.csc.cst.service;

import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consult {

    private List<ConsultVO> consultVOList;
    private ConsultVO consultVO;

}
