(function () {
    window.kakao = {

        kakaoLogin : () => {
            Kakao.init(kakaoJsProperties);
            Kakao.isInitialized();
            Kakao.Auth.login({
                success: function (response) {
                    Kakao.API.request({
                        url: '/v2/user/me',
                        success: function (response) {
                            var accessToken = Kakao.Auth.getAccessToken();
                            Kakao.Auth.setAccessToken(accessToken);
                            return response;
                        },
                        fail: function (error) {
                            console.log(JSON.stringify(error))
                        },
                    })
                },
                fail: function (error) {
                    console.log(JSON.stringify(error))
                },
            })
        },

        kakaoLogout : (param) => {
            Kakao.init(param);
            Kakao.isInitailized();
            if(!Kakao.Auth.getAccessToken()) {
                console.log("No AccessToken!");
                return false;
            }
            Kakao.Auth.logout(function() {
                console.log("Success Logout!");
                window.location.href = "/";
            })
        }
    }

    window.google = {

    }

    window.instagram = {

    }

    window.naver = {

    }
})();