<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const faq = {

        faqId : '${faqId}',

        init : () => {
            faq.selectFaqDtls();
        },

        selectFaqListVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/faq/selectFaqListVw.do"), {}, 'post')
        },

        selectFaqDtls : () => {
            let param = {faqId : faq.faqId}
            callModule.call(Util.getRequestUrl("/mng/cmm/faq/selectFaqDtls.do"), param, (result) => {
                $("#title").val(result.faqVO.title);
                $("#contents").val(result.faqVO.contents);
            })
        },

        updateFaq : () => {

            var validationGroup = [
                {id: 'title', name: '제목', mandatory: true},
                {id: 'contents', name: '내용', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("FAQ를 수정하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        faqId : faq.faqId,
                        title : $("#title").val(),
                        contents : $("#contents").val()
                    }
                    callModule.call(Util.getRequestUrl("/mng/cmm/faq/updateFaq.do"), param, (result) => {
                        MessageUtil.alert(result.faqVO.resultMessage, () => {
                            faq.selectFaqListVw();
                        })
                    })
                }
            },  "수정", "취소")
        },

        deleteFaq : () => {
            MessageUtil.confirm("FAQ를 삭제하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {faqId : faq.faqId}
                    callModule.call(Util.getRequestUrl("/mng/cmm/faq/deleteFaq.do"), param, (result) => {
                        MessageUtil.alert(result.faqVO.resultMessage, () => {
                            faq.selectFaqListVw();
                        })
                    })
                }
            }, "삭제", "취소")
        }
    }

    $(() => {
        faq.init();
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
                <button class="btn__gray" onclick="faq.selectFaqListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="faq.deleteFaq();">
                    <span>삭제</span>
                </button>
                <button class="btn__bluegreen" onclick="faq.updateFaq();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>