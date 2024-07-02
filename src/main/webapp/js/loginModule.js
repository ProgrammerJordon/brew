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
        googleLogin : (key, redirectUri) => {
            let url = 'https://accounts.google.com/o/oauth2/v2/auth?client_id=' +
                key +
                '&redirect_uri=' +
                redirectUri +
                '&response_type=code' +
                '&scope=email profile';
            window.location.href = url;
        }
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