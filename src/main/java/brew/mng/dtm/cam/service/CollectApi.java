package brew.mng.dtm.cam.service;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectApi {

    private List<CollectApiVO> collectApiVOList;
    private CollectApiVO collectApiVO;
}
