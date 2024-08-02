package brew.mng.dtm.mdm.service.impl;

import brew.cmm.service.fms.service.FileVO;
import brew.cmm.service.fms.service.impl.FileManageDAO;
import brew.mng.dtm.mdm.service.Metadata;
import brew.mng.dtm.mdm.service.MetadataService;
import brew.mng.dtm.mdm.service.MetadataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MetadataService")
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {

    private final FileManageDAO fileManageDAO;

    private final MetadataDAO metadataDAO;

    @Override
    public Metadata insertMetadata(MetadataVO vo) {
        int result = metadataDAO.insertMetadata(vo);
        if(result == 1) {
            vo.setResultMessage("정상적으로 메타데이터를 등록하였습니다.");
        }else {
            vo.setResultMessage("메타데이터 등록을 실패하였습니다.");
        }
        return Metadata.builder()
                .metadataVO(vo)
                .build();
    }

    @Override
    public Metadata selectMetadataList(MetadataVO vo) {
        return Metadata.builder()
                .metadataVOList(metadataDAO.selectMetadataList(vo))
                .metadataVO(vo)
                .build();
    }

    @Override
    public Metadata selectMetadataDtls(MetadataVO vo) {
        return Metadata.builder()
                .metadataVO(metadataDAO.selectMetadataDtls(vo))
                .build();
    }

    @Override
    public Metadata updateMetadata(MetadataVO vo) {
        int result = metadataDAO.updateMetadata(vo);
        if(result == 1) {
            vo.setResultMessage("메타데이터가 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("메타데이터 수정에 실패하였습니다.");
        }
        return Metadata.builder()
                .metadataVO(vo)
                .build();
    }

    @Override
    public Metadata deleteMetadata(MetadataVO vo) throws Exception {

        if(vo.getAtchFileId() != null) {

            FileVO fvo = new FileVO();
            fvo.setAtchFileId(vo.getAtchFileId());

            // atchFileId로 조회한 fileList
            List<FileVO> fvoList = fileManageDAO.selectFileInfs(fvo);

            // 파일상세 제거
            for(int i = 0 ; i < fvoList.size() ; i++) {
                fileManageDAO.deleteFileInf(fvoList.get(i));
            }
            // 파일속성제거
            fileManageDAO.deleteCOMTNFILE(fvo);
        }

        int result = metadataDAO.deleteMetadata(vo);

        if(result == 1) {
            vo.setResultMessage("메타데이터가 정상적으로 삭제되어습니다.");
        }else {
            vo.setResultMessage("메타데이터 삭제를 실패하였습니다.");
        }

        return Metadata.builder()
                .metadataVO(vo)
                .build();
    }
}
