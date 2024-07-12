<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const bbs = {

        searchParams : {},
        boardList : [],

        init : () => {
            bbs.selectBoardList();
        },

        selectBoardInsertVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/bbs/insertBoardVw.do"), {}, 'get')
        },

        selectBoardDtlsVw : (bbsId) => {
            let param = {bbsId : bbsId}
            callModule.post(Util.getRequestUrl("/mng/cmm/bbs/selectBoardDtlsVw.do"), param, 'get')
        },

        selectBoardList : (pageIndex) => {
            let param = {
                pageIndex : pageIndex || '1'
            }

            bbs.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/cmm/bbs/selectBoardList.do"), param, (result) => {

                bbs.boardList = result.boardVOList || [];

                $("#totCnt").text(bbs.boardList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(bbs.boardList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 공지사항이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < bbs.boardList.length; i++) {
                    if (bbs.boardList[i].rnum > 10) break;

                    let html = `<tr onclick="bbs.selectBoardDtlsVw('\${bbs.boardList[i].bbsId}')">
                                    <td>\${bbs.boardList[i].title}</td>
                                    <td>\${bbs.boardList[i].inqCnt}</td>
                                    <td>\${bbs.boardList[i].rgtrId}</td>
                                    <td>\${bbs.boardList[i].rgtrDt}</td>
                               </tr>`

                    $("#tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(bbs.boardList), 'bbs.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            bbs.boardList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr onclick="bbs.selectBoardDtlsVw('\${vo.bbsId}')">
                                <td>\${vo.title}</td>
                                <td>\${vo.inqCnt}</td>
                                <td>\${vo.rgtrId}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("#tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(bbs.boardList), 'bbs.pageMove');
        },
    }

    $(function () {
        bbs.init();
        mmm.selectMenu('cmm', '공지사항관리');
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
            <button class="btn__bluegreen" onclick="bbs.selectBoardInsertVw();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">관리자 게시판 목록</caption>
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

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>
