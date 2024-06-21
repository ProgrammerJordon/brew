<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/header_mng.jspf" %>

<script>
    const bbs = {
        init : () => {

        },

        selectBoardListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/bbs/selectBoardListVw.do"), {}, 'get')
        },

        insertBoard : () => {
            MessageUtil.confirm("공지사항을 등록하시겠습니까?", (boolean) => {
                if(boolean) {
                    var param = {
                        title : $("#title").val(),
                        contents : $("#contents").val(),
                    }
                    callModule.call(Util.getRequestUrl("/mng/cmm/bbs/insertBoard.do"), param, (result) => {
                        MessageUtil.alert(result.boardVO.resultMessage, () => {
                            bbs.selectBoardListVw();
                        });
                    })
                }
            }, "등록", "취소")
        }
    }

    $(function() {
        bbs.init();
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">게시판 등록 화면</caption>
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
                <tr>
                    <th>첨부파일</th>
                    <td>
                        <input type="file" id="file" name="file" multiple />
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
                <button class="btn__bluegreen" onclick="bbs.insertBoard();">
                    <span>등록</span>
                </button>
            </div>
        </div>
    </div>
</div>