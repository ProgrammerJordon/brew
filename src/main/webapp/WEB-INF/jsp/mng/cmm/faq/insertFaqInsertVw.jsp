<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const faq = {
        init : () => {

        },

        selectFaqListVw : () => {
          callModule.post(Util.getRequestUrl("/mng/cmm/faq/selectFaqListVw.do"), {}, 'post')
        },

        insertFaq : () => {

            var validationGroup = [
                {id: 'title', name: '제목', mandatory: true},
                {id: 'contents', name: '내용', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("FAQ를 등록하시겠습니까?", (boolean) => {
               if(boolean) {
                   let param = {
                       title : $("#title").val(),
                       contents : $("#contents").val()
                   }
                   callModule.call(Util.getRequestUrl("/mng/cmm/faq/insertFaq.do"), param, (result) => {

                       MessageUtil.alert(result.faqVO.resultMessage, () => {
                            faq.selectFaqListVw();
                       })
                   })
               }
            })
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
            <caption class="hidden">FAQ 등록 화면</caption>
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
                <button class="btn__bluegreen" onclick="faq.insertFaq();">
                    <span>등록</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>