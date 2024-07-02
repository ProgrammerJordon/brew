<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    const lgi = {

        selectIndexVw : () => {
            callModule.post(Util.getRequestUrl("/index.do"), {}, 'post')
        },

        kakaoLogin : () => {
            Kakao.Auth.login({
                success: function (response) {
                    Kakao.API.request({
                        url: '/v2/user/me',
                        success: function (response) {
                            let accessToken = Kakao.Auth.getAccessToken();
                            Kakao.Auth.setAccessToken(accessToken);

                            let param = {
                                userId : response.id,
                                userNm : response.name || null,
                                nickNm : response.kakao_account.profile.nickname || null,
                                profileImgUrl : response.kakao_account.profile.profile_image_url || null,
                                thumbnailImgUrl : response.kakao_account.profile.thumbnail_image_url || null,
                                rgtrId : response.id,
                                mdfrId : response.id
                            }
                            callModule.call(Util.getRequestUrl("/svc/mem/lgi/insertKakaoLogin.do"), param, (result) =>{
                                callModule.call(Util.getRequestUrl("/svc/mem/lgi/selectKakaoLogin.do"), param, () => {
                                    lgi.selectIndexVw();
                                })
                            })

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
        },
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