package brew.brew_common.util;

import brew.brew_common.cmm.Common;
import brew.brew_common.cmm.Paging;

public class PagingUtil {

    /**
     * 페이징 설정
     * @param pageVO
     */

    public static void setPaging(Paging pageVO) {
        if(pageVO.getTotalRowCount() == 0) {
            pageVO.setTotalPages(1);
        }else if(pageVO.getTotalRowCount() % pageVO.getRowCountPerPage() > 0) {
            pageVO.setTotalPages(pageVO.getTotalRowCount() / pageVO.getRowCountPerPage() + 1);
        }else {
            pageVO.setTotalPages(pageVO.getTotalRowCount() / pageVO.getRowCountPerPage());
        }
        pageVO.setPageStart(pageVO.getRowCountPerPage() * (pageVO.getPageIndex() - 1) + 1);
        pageVO.setPageEnd(pageVO.getRowCountPerPage() * pageVO.getPageIndex());
    }

    /**
     * 페이징 설정
     * @param commonVO - 페이징 VO
     * @param rowCountPerPage - 페이지별 레코드 수
     */
    public static void setPaging(Common commonVO, int rowCountPerPage) {

        if(rowCountPerPage > 0) {
            commonVO.setRowCountPerPage(rowCountPerPage);
        }
        if(commonVO.getTotalRowCount() == 0) {
            commonVO.setTotalPages(1);
        }else if (commonVO.getTotalRowCount() % commonVO.getRowCountPerPage() > 0) {
            commonVO.setTotalPages(commonVO.getTotalRowCount() / commonVO.getRowCountPerPage() + 1);
        }else {
            commonVO.setTotalPages(commonVO.getTotalRowCount() / commonVO.getRowCountPerPage());
        }

        commonVO.setPageStart(commonVO.getRowCountPerPage() * (commonVO.getPageIndex() - 1) + 1);
        commonVO.setPageEnd(commonVO.getRowCountPerPage() * commonVO.getPageIndex());
    }

    /**
     * 페이징 설정
     * @param commonVO
     */
    public static void setPaging(Common commonVO) {
        PagingUtil.setPaging(commonVO, 0);
    }
}
