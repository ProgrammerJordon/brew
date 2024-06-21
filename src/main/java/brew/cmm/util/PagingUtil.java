package brew.cmm.util;

import brew.cmm.vo.CommonVO;

import java.util.Map;

public class PagingUtil {

    public static void setPaging(CommonVO commonVO) {
        int firstIndex = (commonVO.getPageIndex() - 1) * commonVO.getRecordCountPerPage();
        commonVO.setFirstIndex(firstIndex);

        int pageSize = (commonVO.getTotalRecordCount() - 1) / commonVO.getRecordCountPerPage() + 1;
        commonVO.setPageSize(pageSize);
    }

    public static void setPaging(Map<String, Object> map) {
        int firstIndex = (Integer.parseInt((String) map.get("pageIndex")) - 1) * Integer.parseInt((String)map.get("recordCountPerPage"));
        map.put("firstIndex", firstIndex);

        int pageSize = (Integer.parseInt((String) map.get("totalRecordCount")) - 1) / Integer.parseInt((String) map.get("recordCountPerPage")) + 1;
        map.put("pageSize", pageSize);
    }
}
