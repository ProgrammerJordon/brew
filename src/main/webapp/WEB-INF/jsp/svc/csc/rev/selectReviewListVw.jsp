<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    const cst = {

        searchParams : {},
        notList : [],

        init : () => {
            cst.selectNoticeList();
        },

        selectNoticeInsertVw : () => {
            let param = {}
            callModule.post(Util.getRequestUrl("/svc/csc/not/selectNoticeInsertVw.do"), param, 'get');
        },

        selectNoticeDtlsVw : (sn) => {
            let param = {sn : sn}
            callModule.post(Util.getRequestUrl("/svc/csc/not/selectNoticeDtlsVw.do"), param, 'get');
        },

        selectNoticeList : function() {
            let param = {}

            cst.searchParams = param;

            callModule.call(Util.getRequestUrl("/svc/csc/not/selectNoticeList.do"), param, (result) => {

                cst.notList = result.noticeVOList || [];

                $("#totCnt").text(cst.notList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(cst.notList.length == 0) {
                    let html = `<tr>
                                <td colspan="4">공지사항이 존재하지 않습니다.</td>
                              </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < cst.notList.length; i++) {
                    if(cst.notList[i].rnum > 10) break;

                    let html = `<tr onclick="cst.selectNoticeDtlsVw('\${cst.notList[i].sn}')">
                                <td>\${cst.notList[i].title}</td>
                                <td>\${cst.notList[i].inqCnt}</td>
                                <td>\${cst.notList[i].rgtrId}</td>
                                <td>\${cst.notList[i].rgtrDt}</td>
                              </tr>`

                    $("#tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(cst.notList), 'cst.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            cst.notList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr onclick="cst.selectConsultDtlsVw('\${vo.sn}')">
                                <td>\${vo.title}</td>
                                <td>\${vo.inqCnt}</td>
                                <td>\${vo.rgtrId}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(cst.cstList), 'cst.pageMove');
        },

    }

    $(()=> {
        cst.init();
        mmm.selectMenu("csc", "공지사항");
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
            <button class="btn__bluegreen" onclick="cst.selectNoticeInsertVw();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">공지사항 목록</caption>
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