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
            naverLgoin : (key, uri) => {
                let url = 'https://nid.naver.com/oauth2.0/authorize' +
                    '?response_type=code' +
                    '&client_id=' + key +
                    '&redirect_uri=' + uri +
                    '&state=STATE';

                window.location.href = url;
            }
    }

    window.sns = {
        logout : () => {
            callModule.post(Util.getRequestUrl("/logout.do"), null, 'get');
        }
    }
})();