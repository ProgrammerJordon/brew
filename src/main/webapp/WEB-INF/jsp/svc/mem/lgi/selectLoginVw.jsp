<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    const lgi = {
        kakaoKey : '${kakaoKey}',
        kakaoDirecturl : '${kakaoDirecturl}',

        selectIndexVw : () => {
            callModule.post(Util.getRequestUrl("/index.do"), {}, 'post')
        },

        kakaoLogin : () => {
            kakao.kakaoLogin(lgi.kakaoKey, lgi.kakaoDirecturl)
        }

    }
</script>

<div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
    <div>
        <a href="javascript:lgi.kakaoLogin()">
            <img src="<c:url value="/images/kakao/ko/kakao_login_large_wide.png"/>">
        </a>
    </div>
    <br><br>
    <div>
        <a href="javascript:">
            <img src="<c:url value=""/>">
        </a>
    </div>
    <br><br>
    <div>
        <a href="javascript:">
            <img src="<c:url value=""/>">
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>