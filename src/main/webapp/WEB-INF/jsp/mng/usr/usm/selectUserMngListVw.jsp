<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    const usm = {
        init : () => {

        },
    }

    $(() => {
        usm.init();
        mmm.selectMenu("usr")
    })
</script>

<div>
    회원관리페이지(정보수정)
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/footer_mng.jspf" %>