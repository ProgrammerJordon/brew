package brew.mng.dtm.fdm.service.impl;

import brew.mng.dtm.fdm.service.FileDataMng;
import brew.mng.dtm.fdm.service.FileDataMngService;
import brew.mng.dtm.fdm.service.FileDataMngVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("FileDataMngService")
@RequiredArgsConstructor
public class FileDataMngServiceImpl implements FileDataMngService {

    private final FileDataMngDAO fileDataMngDAO;

    @Override
    public FileDataMng selectFileDataMngList(FileDataMngVO vo) {
        return FileDataMng.builder()
                .fileDataMngVOList(fileDataMngDAO.selectFileDataMngList(vo))
                .build();
    }
}
