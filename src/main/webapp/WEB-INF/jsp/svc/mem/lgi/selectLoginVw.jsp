<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/header_svc.jspf" %>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>

<script>
    const kakaoJsProperties = '${kakaoJsProperties}';

    Kakao.init(kakaoJsProperties);
    function kakaoLogin() {
        Kakao.Auth.login({
            success: function (response) {
                Kakao.API.request({
                    url: '/v2/user/me',
                    success: function (response) {
                        alert(JSON.stringify(response))
                    },
                    fail: function (error) {
                        alert(JSON.stringify(error))
                    },
                })
            },
            fail: function (error) {
                alert(JSON.stringify(error))
            },
        })
    }
</script>

<div>
    로그인 페이지 (카카오, 구글, 애플)
    <a href="javascript:kakaoLogin()">
        <img src="<c:url value="/images/login/kakao_login_medium_narrow.png"/>" style="width: 200px">
    </a>

</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/footer_svc.jspf" %>