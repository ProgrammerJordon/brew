<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const mml = {
        init : () => {

        }
    }

    $(function() {
        mml.init();
        mmm.selectMenu('sys');
    })

</script>

<div>메뉴 시스템관리</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>