<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let authSn = '${userSn}';

    const uat = {
        init : () => {
            uat.selectUserAuthDtls();
        },

        selectUserAuthListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/usr/uat/selectUserAuthListVw.do"), {}, 'post')
        },

        selectUserAuthDtls : () => {
            let param = {userSn: authSn}
            callModule.call(Util.getRequestUrl("/mng/usr/uat/selectUserAuthDtls.do"), param, (result) => {
                $("#userId").text(result.userAuthVO.userId);
                $("#userNm").text(result.userAuthVO.userNm || "-");
                $("#nickNm").text(result.userAuthVO.nickNm || "-");
                $("#authCd").val(result.userAuthVO.authCd);
            })
        },

        updateUserAuth : () => {

            MessageUtil.confirm("회원권한을 수정하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        userSn : authSn,
                        authCd: $("#authCd").val()
                    }
                    callModule.call(Util.getRequestUrl("/mng/usr/uat/updateUserAuth.do"), param, (result) => {
                        MessageUtil.alert(result.userAuthVO.resultMessage, () => {
                            uat.selectUserAuthListVw();
                        })
                    })
                }
            })
        }
    }

    $(() => {
        uat.init();
        mmm.selectMenu("usr")
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">사용자 권한 상세조회 화면</caption>
            <colgroup>
                <col class="num" width="20%">
                <col class="num" width="30%">
                <col class="num" width="20%">
                <col class="num" width="30%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <th>사용자ID</th>
                <td>
                    <span id="userId" name="userId"></span>
                </td>
                <th>사용자명</th>
                <td>
                    <span id="userNm" name="userNm"></span>
                </td>
            </tr>
            <tr>
                <th>닉네임</th>
                <td>
                    <span id="nickNm" name="nickNm"></span>
                </td>
                <th>권한구분</th>
                <td>
                    <select class="selectBox" id="authCd" name="authCd">
                        <option value="A">관리자</option>
                        <option value="Z">일반회원</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="uat.selectUserAuthListVw()">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__bluegreen" onclick="uat.updateUserAuth();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>