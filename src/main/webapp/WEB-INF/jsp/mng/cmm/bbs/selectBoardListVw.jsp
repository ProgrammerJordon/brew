<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    const bbs = {
        init : () => {
            bbs.selectBoardList();
        },

        selectBoardList : () => {
          var param = {}
          callModule.call(Util.getRequestUrl("/mng/cmm/bbs/selectBoardList.do"), param, (result) => {

              let el = document.querySelector("#tbody");

              while (el.firstChild) {
                  el.removeChild(el.firstChild);
              }

              for(let i = 0; i < result.boardVOList.length; i++) {
                  let html = `<tr>
                                <td>\${result.boardVOList[i].title}</td>
                                <td>\${result.boardVOList[i].inqCnt}</td>
                                <td>\${result.boardVOList[i].rgtrId}</td>
                                <td>\${result.boardVOList[i].rgtrDt}</td>
                              </tr>`
                  $("tbody").append(html);
              }
          })
        },
    }

    $(function () {
        bbs.init();
    })
</script>

<div>
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
        <div class="paging-area">
            <div class="paging" id="pagination"></div>
        </div>
    </div>
</div>