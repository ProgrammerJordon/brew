// =============================================== KAKAO LOGIN ===============================================

// 카카오 로그인 앱키 1cf7949d3eb1d9695896d35734ccd915
// SDK를 초기화 합니다. 사용할 앱의 JavaScript 키를 설정해야 합니다.
Kakao.init('1cf7949d3eb1d9695896d35734ccd915');
Kakao.isInitialized();
// SDK 초기화 여부를 판단합니다.
console.log(Kakao.isInitialized());
function loginWithKakao() {
Kakao.Auth.login({
    // scpope에 들어갈수 있는 항목
    // name(이름), phone_number(전화번호),
    // birthyear(생년), account_ci(연계정보), shipping_address(배송지정보), plusfriends(카카오플러스친추)
    scope:'profile_nickname, account_email, gender, age_range, birthday, friends',
    success: function (authObj) {
        console.log(authObj); // access토큰 값
        Kakao.Auth.setAccessToken(authObj.access_token); // access토큰값 저장
        getInfo(authObj);
    },
    fail: function (err) {
        console.log(err);
    }
});
}

function getInfo(authObj) {
Kakao.API.request({
    url: '/v2/user/me',
    success: function (res) {

        // authObj 접근 토큰
        let access_token = authObj.access_token;

        // res 기본정보 조회
        let id = res.id;
        let connected_at = res.connected_at;
        let email = res.kakao_account.email;
        let age_range = res.kakao_account.age_range;
        let gender = res.kakao_account.gender;
        let profile_nickname = res.kakao_account.profile.nickname;
        let birthday = res.kakao_account.birthday;
        let birthday_type = res.kakao_account.birthday_type;
        let profile_image_url = res.kakao_account.profile.profile_image_url;

        let data = {
            "access_token" : access_token,
            "id" : id,
            "connected_at" : connected_at,
            "email" : email,
            "gender" : gender,
            "profile_nickname": profile_nickname,
            "birthday" : birthday,
            "age_range" : age_range,
            "birthday_type" : birthday_type,
            "profile_image_url" : profile_image_url
        }

        let callback = () => {
            console.log("============ 카카오 세션 충전 완료============")
            window.location.href = '/home';
        }
        console.log("============ 카카오 세션 충전 중============")

        RequestUrl("/login/kakaoSession", "POST", data, callback);
    },
    fail: function (error) {
        alert('카카오 로그인에 실패했습니다. 관리자에게 문의하세요.' + JSON.stringify(error));
    }
});
}

function kakaoLogout() {
    if (!Kakao.Auth.getAccessToken()) {
        alert('Not logged in.');
    return;
    }
    Kakao.Auth.logout(function() {
        alert('logout ok access token -> ' + Kakao.Auth.getAccessToken());
    });
}



// =============================================== GOOGLE LOGIN ===============================================
// {"web":{"client_id":"587091627099-m8d7ok8kut3di0hdk82h43fmsconuups.apps.googleusercontent.com","project_id":"cashewnut","auth_uri":"https://accounts.google.com/o/oauth2/auth","token_uri":"https://oauth2.googleapis.com/token","auth_provider_x509_cert_url":"https://www.googleapis.com/oauth2/v1/certs","client_secret":"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","redirect_uris":["http://localhost/google/oauth"]}}
// sercurePassword : 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
// API KEY : AIzaSyAqQNw8z9o9VSp5IEhKMz2Y1THcqZ-bkhQ


/*
function onSignIn(googleUser) {

    let profile = googleUser.getBasicProfile();

    console.log("profile : " + profile);

    let data = {
        id : profile.getId(),
        name : profile.getName(),
        imageUrl : profile.getImageUrl(),
        email : profile.getEmail(),
    }

    console.log("data : " + data);

    let callback = () => {
        console.log("============ 구글 세션 충전 완료============")
        window.location.href = '/home';
    }
    console.log("============ 구글 세션 충전 중============")

    RequestUrl("/login/googleSession", "POST", data, callback);
}
*/



// function init() {
//     gapi.load('auth2', function() {
//         gapi.auth2.init();
//         options = new gapi.auth2.SigninOptionsBuilder();
//         options.setPrompt('select_account');
//         // 추가는 Oauth 승인 권한 추가 후 띄어쓰기 기준으로 추가
//         options.setScope('email profile openid https://www.googleapis.com/auth/user.birthday.read');
//         // 인스턴스의 함수 호출 - element에 로그인 기능 추가
//         // GgCustomLogin은 li태그안에 있는 ID, 위에 설정한 options와 아래 성공,실패시 실행하는 함수들
//         gapi.auth2.getAuthInstance().attachClickHandler('googleLogin', options, onSignIn, onSignInFailure);
//     })
// }
//
// function onSignIn(googleUser) {
//     let access_token = googleUser.getAuthResponse().access_token
//     $.ajax({
//         // people api를 이용하여 프로필 및 생년월일에 대한 선택동의후 가져온다.
//         url: 'https://people.googleapis.com/v1/people/me'
//         // key에 자신의 API 키를 넣습니다.
//         , data: {personFields:'birthdays', key:'AIzaSyAqQNw8z9o9VSp5IEhKMz2Y1THcqZ-bkhQ', 'access_token': access_token}
//         , method:'GET'
//     })
//         .done(function(e){
//             //프로필을 가져온다.
//             let profile = googleUser.getBasicProfile();
//             console.log(profile)
//
//             let data = {
//                 id : profile.getId(),
//                 name : profile.getName(),
//                 imageUrl : profile.getImageUrl(),
//                 email : profile.getEmail(),
//             }
//
//             let callback = () => {
//                 console.log("============ 구글 세션 충전 완료============")
//                 window.location.href = '/home';
//             }
//             console.log("============ 구글 세션 충전 중============")
//
//             RequestUrl("/login/googleSession", "POST", data, callback);
//         })
//         .fail(function(e){
//             console.log(e);
//         })
// }
// function onSignInFailure(t){
//     console.log(t);
// }