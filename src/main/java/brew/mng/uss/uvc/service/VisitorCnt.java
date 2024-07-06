package brew.mng.uss.uvc.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorCnt {

    private List<VisitorCntVO> visitorCntVOList;
    private VisitorCntVO visitorCntVO;

}
