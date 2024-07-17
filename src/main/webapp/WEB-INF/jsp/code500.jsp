<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="wrap type01">
    <div class="error__box">
        <div>
            <img src="${pageContext.request.contextPath}/images/img-error.svg" alt="오류">
        </div>
        <div>
            <span>500 오류</span>
            <span class="en">Error</span>
            <div>
                <p>
                    요청하신 페이지를 처리 중에 오류가 발생했습니다. 서비스 이용에 불편을 드려 죄송합니다.<br>
                    입력하신 주소가 정확한지 확인 후 다시 시도해 주시기 바랍니다.
                </p>
                <p>
                    We have encountered a system error while processing your request.<br>
                    We apologize for the inconvenience. Please check URL and try again.
                </p>
            </div>
        </div>
    </div>
    <div class="btn__box center">
        <a style="text-align: center; text-decoration: none;" href="javascript:history.go(-1);" class="btn__gray">이전페이지로</a>
        <a style="text-align: center; text-decoration: none;" href="/index.do" class="btn__blue">홈으로</a>
    </div>
</div>
<style>
    .wrap.type01{
        padding: 50px 32px;
    }

    .error__box{
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 80px;
    }
    .error__box>div>span{
        font-weight: 600;
        font-size: 48px;
    }
    .error__box>div>span.en{
        font-size: 38px;
        color: #979EA6;
        margin-left: 16px;
    }
    .error__box>div>div{
        font-size: 16px;
        color: #666;
        margin-top: 40px;
        position: relative;
        padding-left: 26px;
    }
    .error__box>div>div::before{
        content: '';
        display: block;
        position: absolute;
        left: 0;
        height: 100%;
        width: 10px;
        background-color: #407BFF;
        border-radius: 50px;
    }
    .error__box>div>div>p{
        margin-top: 16px;
    }

    /* btn__box */
    .btn__box{
        margin-top: 40px;
        display: flex;
        justify-content: space-between;
        position: relative;
        min-height: 43px;
    }
    .btn__box.center{
        justify-content: center;
        gap: 20px;
    }
    .btn__blue{
        background-color: #0082E5;
    }
    .btn__turquoise{
        background-color: #00C9C8;
    }
    .btn__box [class*="btn__"]{
        min-width: 90px;
        padding: 12px;
        border-radius: 5px;
        border: 0;
        color: #fff;
        font-weight: 500;
        font-size: 14px;
    }
    .btn__gray{
        background-color: #999;
    }
    .btn__gray__line{
        background-color: #fff;
        border: 1px solid #999!important;
        color: #999!important;
    }
    .btn__box [class*="__box"] .btn__naver,.btn__box [class*="__box"] .btn__kakao,.btn__box [class*="__box"] .btn__facebook{
        min-width: unset;
        border: 0;
        padding: 0;

    }
</style>