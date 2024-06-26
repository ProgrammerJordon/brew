<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let sn = '${sn}';

   const bbs = {
       init: () => {
           bbs.selectBoardDtls();
       },

       selectBoardListVw : () => {
           callModule.post(Util.getRequestUrl("/mng/cmm/bbs/selectBoardListVw.do"), {}, 'get')
       },

       selectBoardDtls : () => {
            let param = {sn : sn}
           callModule.call(Util.getRequestUrl("/mng/cmm/bbs/selectBoardDtls.do"), param, (result) => {
               $("#title").val(result.boardVO.title);
               $("#contents").val(result.boardVO.contents);
           })
       },

       updateBoard : () => {

           var validationGroup = [
               {id: 'title', name: '제목', mandatory: true},
               {id: 'contents', name: '내용', mandatory: true},
           ];

           if (!Util.validateComponent(validationGroup)) return;

           MessageUtil.confirm("공지사항을 수정하시겠십니까?", (boolean) => {
               if(boolean) {
                   let param = {
                       sn : sn,
                       title : $("#title").val(),
                       contents : $("#contents").val()
                   }
                   callModule.call(Util.getRequestUrl("/mng/cmm/bbs/updateBoard.do"), param, (result) => {
                        MessageUtil.alert(result.boardVO.resultMessage, () => {
                            bbs.selectBoardListVw();
                        })
                   })
               }
           }, "수정", "취소")
       },

       deleteBoard : () => {
           MessageUtil.confirm("공지사항을 삭제하시겠습니까?", (boolean) => {
               if(boolean) {
                   let param = {sn : sn}
                   callModule.call(Util.getRequestUrl("/mng/cmm/bbs/deleteBoard.do"), param, (result) => {
                       MessageUtil.alert(result.boardVO.resultMessage, () => {
                           bbs.selectBoardListVw();
                       })
                   })
               }
           })
       }
   }

   $(function() {
       bbs.init();
       mmm.selectMenu('cmm');
   })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">게시판 상세조회 화면</caption>
            <colgroup>
                <col class="num" width="20%">
                <col class="num" width="80%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <th>제목</th>
                <td>
                    <input type="text" id="title" name="title" />
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td>
                    <textarea id="contents" name="contents"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="bbs.selectBoardListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="bbs.deleteBoard();">
                    <span>삭제</span>
                </button>
                <button class="btn__bluegreen" onclick="bbs.updateBoard();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>