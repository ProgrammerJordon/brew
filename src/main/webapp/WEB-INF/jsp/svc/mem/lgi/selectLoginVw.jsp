<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    const lgi = {
        kakaoKey : '${kakaoKey}',
        kakaoDirecturl : '${kakaoRedirectUrl}',

        googleKey : '${googleKey}',
        googleRedirectUrl : '${googleRedirectUrl}',
        googleClientId : '${googleClientId}',
        googleClientPassword : '${googleClientPassword}',

        selectIndexVw : () => {
            callModule.post(Util.getRequestUrl("/index.do"), {}, 'post')
        },

        kakaoLogin : () => {
            kakao.kakaoLogin(lgi.kakaoKey, lgi.kakaoDirecturl)
        },

        googleLogin: () => {
            google.googleLogin(lgi.googleClientId, lgi.googleRedirectUrl)
        }

    }
</script>

<div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
    <div style="width: 400px; height: 80px; text-align: center;">
        <a href="javascript:lgi.kakaoLogin()">
            <img src="<c:url value="/images/kakao/en/kakao_login_large_wide.png"/>" width="100%" height="100%">
        </a>
    </div>
    <br><br>
    <div style="width: 400px; height: 80px; text-align: center;">
        <a href="javascript:lgi.googleLogin();">
            <img src="<c:url value="/images/google/Web/svg/light/web_light_sq_SU.svg"/>"  width="100%" height="100%">
        </a>
    </div>
    <br><br>
    <div style="width: 400px; height: 80px; text-align: center;">
        <a href="javascript:">
            <img src="<c:url value=""/>">
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>