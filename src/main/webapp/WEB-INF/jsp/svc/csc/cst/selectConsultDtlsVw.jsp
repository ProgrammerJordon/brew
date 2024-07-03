<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    debugger;
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

        deleteConsult : () => {
            let param = {
                sn : cst.sn
            }

            MessageUtil.confirm("문의사항을 삭제 하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        sn : cst.sn
                    }

                    callModule.call(Util.getRequestUrl("/svc/csc/cst/deleteConsult.do"), param, (result) => {
                        MessageUtil.alert(result.consultVO.resultMessage, () => {
                            cst.selectConsultListVw();
                        })
                    })
                }
            }, "확인", "취소")
        },

        updateConsultVw : () => {
            let param = {
                sn : cst.sn,
            }

            callModule.post(Util.getRequestUrl("/svc/csc/cst/updateConsultVw.do"), param, "get");
        }

    }

    $(() => {
        cst.init();
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
                    <input type="text" id="title" name="title" readonly/>
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td>
                    <textarea id="contents" name="contents" readonly></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="cst.selectConsultListVw();">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="cst.deleteConsult();">
                    <span>삭제</span>
                </button>
                <button class="btn__bluegreen" onclick="cst.updateConsultVw();">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>