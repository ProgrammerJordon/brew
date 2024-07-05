<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    // debugger;
    const cst = {

        sn : '${vo.sn}',

        init : () => {
            cst.selectConsultDtVw();
        },

        //화면에 값 뿌리기
        selectConsultDtVw : () => {
            let param = {sn : cst.sn}

            callModule.call(Util.getRequestUrl("/svc/csc/cst/selectConsultDtVw.do"), param, (result) => {
                $("#title").val(result.consultVO.title);
                $("#contents").val(result.consultVO.contents);
            })
        },

        //목록으로 돌아가기
        selectConsultListVw : () => {
            let param = {}
            callModule.post(Util.getRequestUrl("/svc/csc/cst/selectConsultListVw.do"), param, "post");
        },

        backselectConsultDtlsVw : () => {
            let param = {
                sn : cst.sn
            }

            callModule.post(Util.getRequestUrl("/svc/csc/cst/selectConsultDtlsVw.do"), param, "get");
        },

        updateConsult : () => {
            var validationGroup = [
                {id: 'title', name: '제목', mandatory: true},
                {id: 'contents', name: '내용', mandatory: true},
            ];

            if (!Util.validateComponent(validationGroup)) return;

            MessageUtil.confirm("문의사항을 수정 하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        sn : cst.sn,
                        title : $("#title").val(),
                        contents : $("#contents").val()
                    }

                    callModule.call(Util.getRequestUrl("/svc/csc/cst/updateConsult.do"), param, (result) => {
                        MessageUtil.alert(result.consultVO.resultMessage, () => {
                            cst.selectConsultListVw();
                        })
                    })
                }
            }, "확인", "취소")
        }

    }

    $(() => {
        cst.init();
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">문의사항 게시글 수정 화면</caption>
            <colgroup>
                <col class="num" width="20%">
                <col class="num" width="80%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <th>제목</th>
                <td>
                    <input type="text" id="title" name="title"/>
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
            <div class="right">
                <button class="btn__red" onclick="cst.backselectConsultDtlsVw();">
                    <span>취소</span>
                </button>
                <button class="btn__bluegreen" onclick="cst.updateConsult();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>