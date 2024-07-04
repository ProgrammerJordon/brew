<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const bbs = {
        init : () => {

        },

        selectBoardListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/bbs/selectBoardListVw.do"), {}, 'get')
        },

        insertBoard : () => {

            var validationGroup = [
                {id: 'title', name: '제목', mandatory: true},
                {id: 'contents', name: '내용', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

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
        mmm.selectMenu('cmm');
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
                    <th><label for="file-upload">첨부파일</label></th>
                    <td class="left" colspan="3">
                        <div>
                            <c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
                                <c:param name="atchFileId" value="" />
                                <c:param name="ext" value="image/gif,image/jpeg,image/png,jpg,jpeg,png,xls,xlsx,hwp,pdf,brf,docx,pptx,csv,zip,txt" />
                                <c:param name="fileSize" value="100" />
                                <c:param name="fileCount" value="10" />
                                <c:param name="filePath" value="BBS/" />
                                <c:param name="fileKey" value="BBS_" />
                            </c:import>
                        </div>
                        <div>
                            <span style="font-size: 10px; color: red;">* 대용량의 파일은 ZIP파일로 압축 후 업로드하세요.</span>
                        </div>
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

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>