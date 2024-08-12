<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    const psr = {

        searchParams : {},
        notList : [],

        init : () => {
            psr.selectPersonalRankingList();
        },

        selectNoticeInsertVw : () => {
            let param = {}
            callModule.post(Util.getRequestUrl("/svc/csc/not/selectNoticeInsertVw.do"), param, 'get');
        },

        selectNoticeDtlsVw : (sn) => {
            let param = {sn : sn}
            callModule.post(Util.getRequestUrl("/svc/csc/not/selectNoticeDtlsVw.do"), param, 'get');
        },

        selectPersonalRankingList : function() {
            let param = {}

            psr.searchParams = param;

            callModule.call(Util.getRequestUrl("/svc/csc/psr/selectPersonalRankingList.do"), param, (result) => {

                psr.notList = result.noticeVOList || [];

                $("#totCnt").text(psr.notList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(psr.notList.length == 0) {
                    let html = `<tr>
                                <td colspan="4">공지사항이 존재하지 않습니다.</td>
                              </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < psr.notList.length; i++) {
                    if(psr.notList[i].rnum > 10) break;

                    let html = `<tr onclick="psr.selectNoticeDtlsVw('\${psr.notList[i].sn}')">
                                <td>\${psr.notList[i].title}</td>
                                <td>\${psr.notList[i].inqCnt}</td>
                                <td>\${psr.notList[i].rgtrId}</td>
                                <td>\${psr.notList[i].rgtrDt}</td>
                              </tr>`

                    $("#tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(psr.notList), 'psr.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            psr.notList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr onclick="psr.selectConsultDtlsVw('\${vo.sn}')">
                                <td>\${vo.title}</td>
                                <td>\${vo.inqCnt}</td>
                                <td>\${vo.rgtrId}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(psr.cstList), 'psr.pageMove');
        },

    }

    $(() => {
        psr.init();
        mmm.selectMenu("psr", "개인랭킹");
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
            <button class="btn__bluegreen" onclick="psr.selectNoticeInsertVw();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">IT/웹/통신 목록</caption>
            <colgroup>
                <col class="num" width="60%">
                <col class="num" width="10%">
                <col class="num" width="15%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
            <tr>
                <th>제목</th>
                <th>조회수</th>
                <th>등록자</th>
                <th>등록일자</th>
            </tr>
            </thead>
            <tbody id="tbody"></tbody>
        </table>
    </div>
    <div class="paging-area">
        <div id="pagination" class="paging"></div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>