<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const usi = {
        init : () => {
            usi.selectUserSignInList();
        },

        selectUserSignInList : () => {

        }
    }

    $(() => {
        usi.init();
    })
</script>

<div>

</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>