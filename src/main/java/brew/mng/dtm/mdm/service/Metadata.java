package brew.mng.dtm.mdm.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    private List<MetadataVO> metadataVOList;
    private MetadataVO metadataVO;

}
