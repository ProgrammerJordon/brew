package brew.cmm.service.fms.service.impl;

import brew.cmm.service.fms.service.FileVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository("FileManageDAO")
@RequiredArgsConstructor
public class FileManageDAO {

	private final SqlSessionTemplate sqlSession;

	/**
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 */
	public String insertFileInfs(List<?> fileList) throws Exception {
		FileVO vo = (FileVO) fileList.get(0);
		String atchFileId = vo.getAtchFileId();

		sqlSession.insert("FileManageDAO.insertFileMaster", vo);

		Iterator<?> iter = fileList.iterator();
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();

			sqlSession.insert("FileManageDAO.insertFileDetail", vo);
		}

		return atchFileId;
	}

	/**
	 * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 */
	public void insertFileInf(FileVO vo) throws Exception {
		sqlSession.insert("FileManageDAO.insertFileMaster", vo);
		sqlSession.insert("FileManageDAO.insertFileDetail", vo);
	}

	/**
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
	 */
	public void updateFileInfs(List<?> fileList) throws Exception {
		FileVO vo;
		Iterator<?> iter = fileList.iterator();
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();
			sqlSession.insert("FileManageDAO.insertFileDetail", vo);
		}
	}

	/**
	 * 여러 개의 파일을 삭제한다.
	 */
	public void deleteFileInfs(List<?> fileList) throws Exception {
		Iterator<?> iter = fileList.iterator();
		FileVO vo;
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();

			sqlSession.delete("FileManageDAO.deleteFileDetail", vo);
		}
	}

	/**
	 * 하나의 파일을 삭제한다.
	 */
	public void deleteFileInf(FileVO fvo) throws Exception {
		sqlSession.delete("FileManageDAO.deleteFileDetail", fvo);
	}

	/**
	 * 파일에 대한 목록을 조회한다.
	 */
	public List<FileVO> selectFileInfs(FileVO vo) throws Exception {
		return sqlSession.selectList("FileManageDAO.selectFileList", vo);
	}

	/**
	 * 파일 구분자에 대한 최대값을 구한다.
	 */
	public int getMaxFileSN(FileVO fvo) throws Exception {
		return (Integer) sqlSession.selectOne("FileManageDAO.getMaxFileSN", fvo);
	}

	/**
	 * 파일에 대한 상세정보를 조회한다.
	 */
	public FileVO selectFileInf(FileVO fvo) throws Exception {
		return (FileVO) sqlSession.selectOne("FileManageDAO.selectFileInf", fvo);
	}

	/**
	 * 전체 파일을 삭제한다.
	 */
	public void deleteAllFileInf(FileVO fvo) throws Exception {
		sqlSession.update("FileManageDAO.deletex", fvo);
	}

	/**
	 * 파일명 검색에 대한 목록을 조회한다.
	 */
	public List<FileVO> selectFileListByFileNm(FileVO fvo) throws Exception {
		return sqlSession.selectList("FileManageDAO.selectFileListByFileNm", fvo);
	}

	/**
	 * 파일명 검색에 대한 목록 전체 건수를 조회한다.
	 */
	public int selectFileListCntByFileNm(FileVO fvo) throws Exception {
		return (Integer) sqlSession.selectOne("FileManageDAO.selectFileListCntByFileNm", fvo);
	}

	/**
	 * 이미지 파일에 대한 목록을 조회한다.
	 */
	public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
		return sqlSession.selectList("FileManageDAO.selectImageFileList", vo);
	}
}
