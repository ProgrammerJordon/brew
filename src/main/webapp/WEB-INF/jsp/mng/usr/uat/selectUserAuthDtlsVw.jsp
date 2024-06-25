<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    const authSn = '${userSn}';

    const uat = {
        init : () => {

        },
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
                    <input type="text" id="userId" name="userId" readonly />
                </td>
                <th>사용자명</th>
                <td>
                    <input type="text" id="userNm" name="userNm" readonly />
                </td>
            </tr>
            <tr>
                <th>닉네임</th>
                <td>
                    <input type="text" id="nickNm" name="nickNm" readonly />
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
                <button class="btn__gray" onclick="">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__bluegreen" onclick="">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/footer_mng.jspf" %>