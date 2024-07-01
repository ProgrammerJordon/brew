package brew.cmm.service.fms.impl;
import brew.cmm.service.fms.FileMngService;
import brew.cmm.service.fms.FileVO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("FileMngService")
@RequiredArgsConstructor
public class FileMngServiceImpl implements FileMngService {

    private final FileManageDAO fileMngDAO;

    /**
     * 여러 개의 파일을 삭제한다.
     */
    public void deleteFileInfs(List<?> fvoList) throws Exception {
        fileMngDAO.deleteFileInfs(fvoList);
    }

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     */
    public String insertFileInf(FileVO fvo) throws Exception {
        String atchFileId = fvo.getAtchFileId();

        fileMngDAO.insertFileInf(fvo);

        return atchFileId;
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     */
    public String insertFileInfs(List<?> fvoList) throws Exception {
        String atchFileId = "";

        if (fvoList.size() != 0) {
            atchFileId = fileMngDAO.insertFileInfs(fvoList);
        }
        if (atchFileId == "") {
            atchFileId = null;
        }
        return atchFileId;
    }

    /**
     * 파일에 대한 목록을 조회한다.
     */
    public List<FileVO> selectFileInfs(FileVO fvo) throws Exception {
        return fileMngDAO.selectFileInfs(fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     */
    public void updateFileInfs(List<?> fvoList) throws Exception {
        //Delete & Insert
        fileMngDAO.updateFileInfs(fvoList);
    }

    /**
     * 하나의 파일을 삭제한다.
     */
    public void deleteFileInf(FileVO fvo) throws Exception {
        File file = new File(fvo.getFileStrePath() + fvo.getStreFileNm());

        // 파일이 실제로 존재하는지 확인
        if (file.exists()) {
            // 파일 삭제 시도
            file.delete();
        }


        fileMngDAO.deleteFileInf(fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     */
    public FileVO selectFileInf(FileVO fvo) throws Exception {
        return fileMngDAO.selectFileInf(fvo);
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     */
    public int getMaxFileSN(FileVO fvo) throws Exception {
        return fileMngDAO.getMaxFileSN(fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     */
    public void deleteAllFileInf(FileVO fvo) throws Exception {
        fileMngDAO.deleteAllFileInf(fvo);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     */
    public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws Exception {
        List<FileVO> result = fileMngDAO.selectFileListByFileNm(fvo);
        int cnt = fileMngDAO.selectFileListCntByFileNm(fvo);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("resultList", result);
        map.put("resultCnt", Integer.toString(cnt));

        return map;
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     */
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
        return fileMngDAO.selectImageFileList(vo);
    }
}

