<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>


<script>
    const org = {
       init : () => {

       }
    }

    $(() => {
        org.init();
        mmm.selectMenu("cmm", "조직관리 등록")
    })
</script>

<div>
    조직관리 등록
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>