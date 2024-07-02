<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const spl = {
        init : () => {

        }
    }

    $(() => {
        spl.init();
        mmm.selectMenu("cmm")
    })
</script>

<div>
    샘플페이지입니다.
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>