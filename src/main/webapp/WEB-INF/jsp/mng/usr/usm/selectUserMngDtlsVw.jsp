<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const usm = {

        userSn : '${userSn}',

        init : () => {
            usm.selectUserMngDtls();
        },

        selectUserMngDtls : () => {
            let param = {userSn : usm.userSn}
            callModule.call(Util.getRequestUrl("/mng/usr/usm/selectUserMngDtls.do"), param, (result) => {

                $("#userId").text(result.userVO.userId);
                $("#userNm").text(result.userVO.userNm != null ? result.userVO.userNm : "-");
                $("#nickNm").val(result.userVO.nickNm);
                $("#authCd").text(result.userVO.authCd == 'A' ? "관리자" : "일반회원");
                $("#loginSe").text(result.userVO.loginSe);
                $("#useYn").val(result.userVO.useYn);
                $("#rgtrDt").text(result.userVO.rgtrDt);

                var profileImgUrl = result.userVO.profileImgUrl;
                $("#profileImgUrl").attr("src", profileImgUrl);
            })
        }
    }

    $(() => {
        usm.init();
        mmm.selectMenu('usr')
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">회원정보관리 상세조회 화면</caption>
            <colgroup>
                <col class="num" width="30%">
                <col class="num" width="20%">
                <col class="num" width="50%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <td rowspan="6">
                    <div>
                        <div>
                            <img id=profileImgUrl alt="blank_" style="width: 320px; height: 320px;" />
                        </div>
                        <br>
                        <div>
                            <div>
                                <span>연계SNS</span>
                                <span> : </span>
                                <span id="loginSe" name="loginSe"></span>
                            </div>
                            <div>
                                <span>ID</span>
                                <span> : </span>
                                <span id="userId" name="userId"></span>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th>사용자명</th>
                <td class="left">
                    <span id="userNm" name="userNm"></span>
                </td>
            </tr>
            <tr>
                <th>닉네임</th>
                <td class="left">
                    <input type="text" id="nickNm" name="nickNm" />
                </td>
            </tr>
            <tr>
                <th>권한코드</th>
                <td class="left">
                    <span type="text" id="authCd" name="authCd"></span>
                </td>
            </tr>
            <tr>
                <th>활성화여부</th>
                <td>
                    <select class="selectBox" id="useYn" name="useYn">
                        <option value="Y">활성화</option>
                        <option value="N">비활성화</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>가입일자</th>
                <td class="left">
                    <span id="rgtrDt" name="rgtrDt"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="">
                    <span>회원탈퇴</span>
                </button>
                &nbsp;
                <button class="btn__bluegreen" onclick="">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>




<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>