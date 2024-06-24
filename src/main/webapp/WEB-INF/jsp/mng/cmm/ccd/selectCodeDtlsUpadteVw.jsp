<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    let codeId = '${codeId}';
    let code = '${code}';

    const ccd = {
        init : () => {
            ccd.selectCodedtls();
        },

        selectCodeDtlsVw : () => {
            let param = {codeId : codeId}
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeDtlsVw.do"), param, 'get')
        },

        selectCodedtls : () => {
            let param = {
                codeId : codeId,
                code : code
            }
            callModule.call(Util.getRequestUrl("/mng/cmm/ccd/selectCodedtls.do"), param, (result) => {
                $("#codeNm").val(result.codeVO.codeNm);
                $("#useYn").val(result.codeVO.useYn);
                $("#codeDc").val(result.codeVO.codeDc);
            })
        },

        updateCodedtls : () => {

            var validationGroup = [
                {id: 'codeNm', name: '코드명', mandatory: true},
                {id: 'useYn', name: '사용여부', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("공통코드상세를 수정하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        codeId : codeId,
                        code : code,
                        codeNm : $("#codeNm").val(),
                        useYn : $("#useYn").val(),
                        codeDc : $("#codeDc").val(),
                    }
                    callModule.call(Util.getRequestUrl("/mng/cmm/ccd/updateCodedtls.do"), param, (result) => {
                        MessageUtil.alert(result.codeVO.resultMessage, () => {
                            ccd.selectCodeDtlsVw();
                        })
                    })
                }
            }, "수정", "취소")
        },
        deleteCodedtls : () => {

            MessageUtil.confirm("공통코드상세를 삭제하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        codeId : codeId,
                        code : code
                    }
                    callModule.call(Util.getRequestUrl("/mng/cmm/ccd/deleteCodedtls.do"), param, (result) => {
                        MessageUtil.alert(result.codeVO.resultMessage, () => {
                            ccd.selectCodeDtlsVw();
                        })
                    })
                }
            }, "삭제", "취소")
        }

    }

    $(() => {
        ccd.init();
        mmm.selectMenu('cmm');
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">공통코드상세 등록 화면</caption>
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
                <button class="btn__gray" onclick="ccd.selectCodeDtlsVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="ccd.deleteCodedtls();">
                    <span>삭제</span>
                </button>
                <button class="btn__bluegreen" onclick="ccd.updateCodedtls();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/footer_mng.jspf" %>