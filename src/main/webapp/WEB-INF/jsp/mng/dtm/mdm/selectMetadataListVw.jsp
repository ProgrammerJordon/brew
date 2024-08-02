<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>


<script>
    let mdm = {

        searchParams : {},
        mdmList : [],

        init : () => {
            mdm.selectMetadataList();
        },
        insertMetadataVw : () => {
            callModule.post(Util.getRequestUrl("/mng/dtm/mdm/insertMetadataVw.do"), {}, 'get');
        },

        selectMetadataDtlsVw : (mdmId) => {
            let param = {mdmId : mdmId}
            callModule.post(Util.getRequestUrl("/mng/dtm/mdm/selectMetadataDtlsVw.do"), param, 'get');
        },
        selectMetadataList : (pageIndex) => {

            let param = {
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }

            mdm.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/dtm/mdm/selectMetadataList.do"), param, (result) => {

                mdm.mdmList = result.metadataVOList || [];

                $("#totCnt").text(mdm.mdmList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(mdm.mdmList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 공지사항이 존재하지 않습니다.</td>
                                </tr>`
                    $("#tbody").append(html);
                    return false;
                }

                for(let i = 0; i < mdm.mdmList.length; i++) {
                    if (mdm.mdmList[i].rnum > 10) break;

                    let html = `<tr onclick="mdm.selectMetadataDtlsVw('\${mdm.mdmList[i].mdmId}')">
                                    <td>
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="width: 60%;">
                                                <div>\${mdm.mdmList[i].datasetNm} <span>(</span><em>\${mdm.mdmList[i].datasetEngNm}</em><span>)</span></div>
                                            </div>
                                            <div style="width: 40%; display: flex; justify-content: space-around;">
                                                <div><span>조회수 : </span>&nbsp;&nbsp;\${mdm.mdmList[i].inqCnt}&nbsp;&nbsp;<span>회</span></div>
                                                <div>\${mdm.mdmList[i].rgtrDt}</div>
                                                <div><span>부서 : </span>&nbsp;&nbsp;\${mdm.mdmList[i].deptNm}</div>
                                            </div>
                                        </div>
                                    </td>
                               </tr>`;
                    $("#tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(mdm.mdmList), 'mdm.pageMove');
            })
        },
        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            mdm.mdmList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr onclick="mdm.selectMetadataDtlsVw('\${vo.mdmId}')">
                                <td>
                                   <div style="display: flex; justify-content: space-between;">
                                        <div style="width: 60%;">
                                            <div>\${vo.datasetNm} <span>(</span><em>\${vo.datasetEngNm}</em><span>)</span></div>
                                        </div>
                                        <div style="width: 40%; display: flex; justify-content: space-around;"">
                                            <div><span>조회수 : </span>&nbsp;&nbsp;\${vo.inqCnt}&nbsp;&nbsp;<span>회</span></div>
                                            <div>\${vo.rgtrDt}</div>
                                            <div><span>부서 : </span>&nbsp;&nbsp;\${vo.deptNm}</div>
                                        </div>
                                    </div>
                                </td>
                           </tr>`
                $("#tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(mdm.mdmList), 'mdm.pageMove');
        },
    }

    $(() => {
        mdm.init();
        mmm.selectMenu("dtm", "메타데이터 관리")
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">검색어</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="">
            <span>조회</span>
        </button>
    </div>
    <br>
    <div class="search__results">
        <div>
            <span>총</span>
            <span class="num" id="totCnt">0</span>
            <span>건</span>
        </div>
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="mdm.insertMetadataVw();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">메타데이터 관리 목록</caption>
            <colgroup>
                <col class="num" width="15%">
            </colgroup>
            <thead></thead>
            <tbody id="tbody"></tbody>
        </table>
    </div>
    <div class="paging-area">
        <div id="pagination" class="paging"></div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>