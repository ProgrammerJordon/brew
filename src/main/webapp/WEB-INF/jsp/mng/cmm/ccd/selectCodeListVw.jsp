<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const ccd = {

        searchParams : {},
        codeList : [],

        init : () => {
            ccd.selectCodeList();
        },

        selectCodeInsertVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeInsertVw.do"), {}, 'get')
        },

        selectCodeDtlsVw : (codeId) => {
            let param = {codeId : codeId}
            callModule.post(Util.getRequestUrl("/mng/cmm/ccd/selectCodeDtlsVw.do"), param, 'get')
        },

        selectCodeList : (pageIndex) => {
            let param = {
                pageIndex : pageIndex || '1'
            }

            ccd.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/cmm/ccd/selectCodeList.do"), param, (result) => {

                ccd.codeList = result.codeVOList || [];

                $("#totCnt").text(ccd.codeList.length.toLocaleString());

                if(ccd.codeList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 공통코드가 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                gridModule.clear_grid("tbody");

                for(let i = 0; i < ccd.codeList.length; i++) {
                    if (ccd.codeList[i].rnum > 10) break;

                    let html = `<tr onclick="ccd.selectCodeDtlsVw('\${ccd.codeList[i].codeId}');">
                                <td>\${ccd.codeList[i].codeId}</td>
                                <td>\${ccd.codeList[i].codeNm}</td>
                                <td>\${ccd.codeList[i].codeDc}</td>
                                <td>\${ccd.codeList[i].useYn}</td>
                              </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(ccd.codeList), 'ccd.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            ccd.codeList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr onclick="ccd.selectCodeDtlsVw('\${vo.codeId}');">
                                <td>\${vo.codeId}</td>
                                <td>\${vo.codeNm}</td>
                                <td>\${vo.codeDc}</td>
                                <td>\${vo.useYn}</td>
                           </tr>`
                $("#tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(ccd.codeList), 'ccd.pageMove');
        },
    }

    $(()=> {
        ccd.init();
        mmm.selectMenu('cmm');
    })
</script>

<div>
    <div class="search__results">
        <div>
            <span>총</span>
            <span class="num" id="totCnt">0</span>
            <span>건</span>
        </div>
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="ccd.selectCodeInsertVw();">
                <span>등록</span>
            </button>
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
            <tbody id="tbody"></tbody>
        </table>
    </div>
    <div class="paging-area">
        <div id="pagination" class="paging"></div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>
