(function () {
    window.kakao = {

        kakaoLogin : (key, redirectUri) => {
            Kakao.init(key);
            Kakao.isInitialized();
            Kakao.Auth.authorize({
                redirectUri: redirectUri
            });
        },
    }

    window.google = {

    }

    window.instagram = {

    }

    window.naver = {

    }

    window.sns = {
        logout : () => {
            callModule.post(Util.getRequestUrl("/logout.do"), null, 'get');
        }
    }
})();