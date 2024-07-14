package brew.svc.csc.not.service;

import brew.svc.csc.cst.service.ConsultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private List<NoticeVO> NoticeVOList;
    private NoticeVO NoticeVO;
}
