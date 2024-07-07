package brew.mng.dtm.fdm.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDataMng {

    private List<FileDataMngVO> fileDataMngVOList;
    private FileDataMngVO fileDataMngVO;
}
