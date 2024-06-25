<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/header_svc.jspf" %>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>

<script>
    const kakaoJsProperties = '${kakaoJsProperties}';

    const lgi = {

        selectIndexVw : () => {
            callModule.post(Util.getRequestUrl("/"), {}, 'post')
        },

        kakaoLogin : () => {
            Kakao.init(kakaoJsProperties);
            Kakao.Auth.login({
                success: function (response) {
                    Kakao.API.request({
                        url: '/v2/user/me',
                        success: function (response) {
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
                                MessageUtil.alert(result.loginVO.resultMessage, () => {
                                    callModule.call(Util.getRequestUrl("/svc/mem/lgi/selectKakaoLogin.do"), param, () => {
                                        lgi.selectIndexVw();
                                    })
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

<div>
    <a href="javascript:lgi.kakaoLogin()">
        <img src="<c:url value="/images/kakao/ko/kakao_login_large_wide.png"/>">
    </a>
    <br><br>
    <a href="javascript:">
        <img src="<c:url value=""/>">
    </a>
    <br><br>
    <a href="javascript:">
        <img src="<c:url value=""/>">
    </a>

</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/footer_svc.jspf" %>