<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    const cst = {
        init : () => {

        },

        selectNoticeListVw : () => {
            let param = {}
            callModule.post(Util.getRequestUrl("/svc/csc/not/selectNoticeListVw.do"), param, 'get');
        },

        insertNotice : () => {

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

                    callModule.call(Util.getRequestUrl("/svc/csc/not/insertNotice.do"), param, (result) => {
                        MessageUtil.alert(result.noticeVO.resultMessage, () => {
                            cst.selectNoticeListVw();
                        });
                    })
                }
            }, "등록", "취소")
        }

    }

    $(()=> {
        cst.init();
        mmm.selectMenu("csc", "문의사항");
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
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="cst.selectNoticeListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__bluegreen" onclick="cst.insertNotice();">
                    <span>등록</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>