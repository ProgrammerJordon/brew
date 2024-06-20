package brew.cmm.module.file.service.impl;

import brew.cmm.module.file.service.FileVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository("FileDAO")
public class FileDAO {

    private final SqlSession sqlSession;

    @Autowired
    public FileDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * @param fileList
     * @return
     * @throws Exception
     */
    public String insertFileInfs(List<?> fileList) throws Exception {
        FileVO vo = (FileVO) fileList.get(0);
        String atchFileId = vo.getAtchFileId();

        Iterator<?> iter = fileList.iterator();
        while (iter.hasNext()) {
            vo = (FileVO) iter.next();

            sqlSession.insert("FileDAO.insertFileDetail", vo);
        }

        return atchFileId;
    }

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * @param vo
     * @throws Exception
     */
    public void insertFileInf(FileVO vo) throws Exception {
        sqlSession.insert("FileDAO.insertFileDetail", vo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     * @param fileList
     * @throws Exception
     */
    public void updateFileInfs(List<?> fileList) throws Exception {
        FileVO vo;
        Iterator<?> iter = fileList.iterator();
        while (iter.hasNext()) {
            vo = (FileVO) iter.next();
            sqlSession.insert("FileDAO.insertFileDetail", vo);
        }
    }

    /**
     * 여러 개의 파일을 삭제한다.
     * @param fileList
     * @throws Exception
     */
    public void deleteFileInfs(List<?> fileList) throws Exception {
        Iterator<?> iter = fileList.iterator();
        FileVO vo;
        while (iter.hasNext()) {
            vo = (FileVO) iter.next();

            sqlSession.delete("FileDAO.deleteFileDetail", vo);
        }
    }

    /**
     * 하나의 파일을 삭제한다.
     * @param fvo
     * @throws Exception
     */
    public void deleteFileInf(FileVO fvo) throws Exception {
        sqlSession.delete("FileDAO.deleteFileDetail", fvo);
    }

    /**
     * 파일에 대한 목록을 조회한다.
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectFileInfs(FileVO vo) throws Exception {
        return sqlSession.selectList("FileDAO.selectFileList", vo);
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     * @param fvo
     * @return
     * @throws Exception
     */
    public int getMaxFileSN(FileVO fvo) throws Exception {
        return (Integer) sqlSession.selectOne("FileDAO.getMaxFileSN", fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public FileVO selectFileInf(FileVO fvo) throws Exception {
        return (FileVO) sqlSession.selectOne("FileDAO.selectFileInf", fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     *
     * @param fvo
     * @throws Exception
     */
    public void deleteAllFileInf(FileVO fvo) throws Exception {
        sqlSession.update("FileDAO.deleteAllFileInf", fvo);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectFileListByFileNm(FileVO fvo) throws Exception {
        return sqlSession.selectList("FileDAO.selectFileListByFileNm", fvo);
    }

    /**
     * 파일명 검색에 대한 목록 전체 건수를 조회한다.
     * @param fvo
     * @return
     * @throws Exception
     */
    public int selectFileListCntByFileNm(FileVO fvo) throws Exception {
        return (Integer) sqlSession.selectOne("FileDAO.selectFileListCntByFileNm", fvo);
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     * @param vo
     * @return
     * @throws Exception
     */
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
        return sqlSession.selectList("FileDAO.selectImageFileList", vo);
    }
}
