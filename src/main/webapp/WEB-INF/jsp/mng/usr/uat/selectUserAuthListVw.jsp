<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    const uat = {
        init : () => {

        },
    }

    $(() => {
        uat.init();
        mmm.selectMenu("usr")
    })
</script>

<div>
    회원권한관리 페이지
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/footer_mng.jspf" %>