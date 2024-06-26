<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const usm = {

        userSn : '${userSn}',

        init : () => {
            usm.selectUserMngDtls();
        },

        selectUserMngListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/usr/usm/selectUserMngListVw.do"), {}, 'post')
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
        },

        updateUserMng : () => {

            var validationGroup = [
                {id: 'nickNm', name: '닉네임', mandatory: true},
                {id: 'useYn', name: '사용여부', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("회원정보를 수정하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        userSn : usm.userSn,
                        nickNm : $("#nickNm").val(),
                        useYn : $("#useYn").val()
                    }
                    callModule.call(Util.getRequestUrl("/mng/usr/usm/updateUserMng.do"), param, (result) => {
                        MessageUtil.alert(result.userVO.resultMessage, () => {
                            usm.selectUserMngListVw();
                        })
                    })
                }
            })
        },
        exitUserMng : () => {
            MessageUtil.confirm("회원탈퇴를 진행하시겠습니까?", (boolean) => {
                if(boolean) {
                    MessageUtil.confirm("회원탈퇴시 이전 데이터를 복구할 수 없습니다.\n 그래도 진행하시겠습니까?", (boolean) => {
                        if(boolean) {
                            let param = {
                                userSn : usm.userSn
                            }
                            callModule.call(Util.getRequestUrl("/mng/usr/usm/exitUserMng.do"), param, (result) => {
                                MessageUtil.alert(result.userVO.resultMessage, () => {
                                    usm.selectUserMngListVw();
                                })
                            })
                        }
                    }, "탈퇴", "취소")
                }
            }, "진행", "취소")
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
                <button class="btn__gray" onclick="usm.selectUserMngListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="usm.exitUserMng();">
                    <span>회원탈퇴</span>
                </button>
                &nbsp;
                <button class="btn__bluegreen" onclick="usm.updateUserMng();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>




<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>