<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    let codeId = '${codeId}';

    const ccd = {
        init : () => {
            ccd.selectCode();
        },

        selectCodeUpdateVw : (codeId) => {
            let param = {codeId : codeId}
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeUpdateVw.do"), param, 'get')
        },

        selectCode : () => {
            let param = {codeId : codeId}
            callModule.call(Util.getRequestUrl("/mng/cmm/ccd/selectCode.do"), param, (result) => {
                let html = `<tr onclick="ccd.selectCodeUpdateVw('\${result.codeVO.codeId}');">
                                <td>\${result.codeVO.codeId}</td>
                                <td>\${result.codeVO.codeNm}</td>
                                <td>\${result.codeVO.codeDc}</td>
                                <td>\${result.codeVO.useYn}</td>
                            </tr>`
                $("#tbody1").append(html);
            })
        }

    }

    $(() => {
        ccd.init();
        mmm.selectMenu('cmm');
    })
</script>

<div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
            </div>
            <div class="right">
                <button class="btn__red" onclick="">
                    <span>삭제</span>
                </button>
            </div>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">공통코드 목록</caption>
            <colgroup>
                <col class="num" width="25%">
                <col class="num" width="25%">
                <col class="num" width="35%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
            <tr>
                <th>공통코드</th>
                <th>코드명</th>
                <th>코드설명</th>
                <th>사용여부</th>
            </tr>
            </thead>
            <tbody id="tbody1"></tbody>
        </table>
    </div>
</div>
<br>
<div>
    <div class="search__results">
        <div>
            <span>총</span>
            <span class="num" id="totCnt">0</span>
            <span>건</span>
        </div>
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="bbs.selectBoardInsertVw();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">공통코드상세 목록</caption>
            <colgroup>
                <col class="num" width="25%">
                <col class="num" width="25%">
                <col class="num" width="35%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
            <tr>
                <th>코드상세</th>
                <th>코드명</th>
                <th>코드설명</th>
                <th>사용여부</th>
            </tr>
            </thead>
            <tbody id="tbody2"></tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/footer_mng.jspf" %>