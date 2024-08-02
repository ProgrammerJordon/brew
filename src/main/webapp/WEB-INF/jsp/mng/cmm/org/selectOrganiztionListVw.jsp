<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let org = {
        init : () => {

        },
    }

    $(() => {
        org.init();
        mmm.selectMenu("cmm", "조직관리")
    })

</script>

<div>
    조직관리 화면
</div>


<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>