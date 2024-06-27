<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let codeId = '${codeId}';

    const ccd = {

        searchParams : {},
        codeList : [],

        init : () => {
            ccd.selectCode();
            ccd.selectCodeDtlsList();
        },

        selectCodeListVw : () => {
          callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeListVw.do"), {}, 'get')
        },

        // 공통코드
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
        },

        deleteCode : () => {

            MessageUtil.confirm("공통코드를 삭제하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {codeId : codeId}
                    callModule.call(Util.getRequestUrl("/mng/cmm/ccd/deleteCode.do"), param, (result) => {
                        MessageUtil.alert(result.codeVO.resultMessage, () => {
                            ccd.selectCodeListVw();
                        })
                    })
                }
            }, "삭제", "취소")
        },

        // 공통코드 상세
        selectCodeDtlsInsertVw : () => {
            let param = {codeId : codeId}
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeDtlsInsertVw.do"), param, 'get')
        },

        selectCodeDtlsList : (pageIndex) => {
            let param = {
                codeId : codeId,
                pageIndex : pageIndex || '1'
            }

            ccd.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/cmm/ccd/selectCodeDtlsList.do"), param, (result) => {

                ccd.codeList = result.codeVOList || [];

                $("#totCnt").text(ccd.codeList.length.toLocaleString());

                if(ccd.codeList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 공통코드상세가 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody2").append(html);
                    return false;
                }

                gridModule.clear_grid("tbody2");

                for(let i = 0; i < ccd.codeList.length; i++) {
                    if (ccd.codeList[i].rnum > 10) break;

                    let html = `<tr onclick="ccd.selectCodeDtlsUpadteVw('\${ccd.codeList[i].codeId}', '\${ccd.codeList[i].code}');">
                                <td>\${ccd.codeList[i].code}</td>
                                <td>\${ccd.codeList[i].codeNm}</td>
                                <td>\${ccd.codeList[i].codeDc}</td>
                                <td>\${ccd.codeList[i].useYn}</td>
                              </tr>`

                    $("#tbody2").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(ccd.codeList), 'ccd.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody2");

            ccd.codeList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr onclick="ccd.selectCodeDtlsUpadteVw('\${vo.codeId}', '\${vo.code}');">
                                <td>\${vo.code}</td>
                                <td>\${vo.codeNm}</td>
                                <td>\${vo.codeDc}</td>
                                <td>\${vo.useYn}</td>
                           </tr>`
                $("#tbody2").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(ccd.codeList), 'ccd.pageMove');
        },

        selectCodeDtlsUpadteVw : (codeId, code) => {
            let param = {
                codeId : codeId,
                code : code
            }
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeDtlsUpadteVw.do"), param, 'get')
        }

    }

    $(() => {
        ccd.init();
        mmm.selectMenu('cmm', '공통코드관리');
    })
</script>

<div>
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
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="ccd.selectCodeListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="ccd.deleteCode();">
                    <span>삭제</span>
                </button>
            </div>
        </div>
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
            <button class="btn__bluegreen" onclick="ccd.selectCodeDtlsInsertVw();">
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
    <div class="paging-area">
        <div id="pagination" class="paging"></div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>