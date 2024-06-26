(function () {
    window.kakao = {

        kakaoLogin : (param) => {
            Kakao.init(param);
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
            if (Kakao.Auth.getAccessToken()) {
                Kakao.API.request({
                    url: '/v1/user/unlink',
                    success: function (response) {
                        console.log(response)
                    },
                    fail: function (error) {
                        console.log(error)
                    },
                })
                Kakao.Auth.setAccessToken(undefined)
            }
        }
    }

    window.google = {

    }

    window.instagram = {

    }

    window.naver = {

    }
})();