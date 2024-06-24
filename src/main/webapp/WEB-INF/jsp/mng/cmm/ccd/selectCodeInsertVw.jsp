<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    const ccd = {
        init : () => {

        },

        selectCodeListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeListVw.do"), {}, 'get')
        },

        insertCode : () => {

            var validationGroup = [
                {id: 'codeNm', name: '코드명', mandatory: true},
                {id: 'useYn', name: '사용여부', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("공통코드를 등록하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        codeNm : $("#codeNm").val(),
                        useYn : $("#useYn").val(),
                        codeDc : $("#codeDc").val()
                    }
                    callModule.call(Util.getRequestUrl("/mng/cmm/ccd/insertCode.do"), param, (result) => {
                        MessageUtil.alert(result.codeVO.resultMessage, () => {
                            ccd.selectCodeListVw();
                        })
                    })
                }
            }, "등록", "취소")
        },
    }

    $(() => {
        ccd.init();
        mmm.selectMenu('cmm');
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">공통코드 등록 화면</caption>
            <colgroup>
                <col class="num" width="20%">
                <col class="num" width="30%">
                <col class="num" width="20%">
                <col class="num" width="30%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <th>코드명</th>
                <td>
                    <input type="text" id="codeNm" name="codeNm" />
                </td>
                <th>사용여부</th>
                <td>
                    <select class="selectBox" id="useYn" name="useYn">
                        <option value="Y">사용</option>
                        <option value="N">미사용</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>코드설명</th>
                <td colspan="3">
                    <textarea id="codeDc" name="codeDc"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="ccd.selectCodeListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__bluegreen" onclick="ccd.insertCode();">
                    <span>등록</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/footer_mng.jspf" %>