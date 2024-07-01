<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_top.jspf" %>

<script>
    debugger;
    const cst = {

        sn : '${vo.sn}',
        vodt : {},

        init : () => {
            cst.selectConsultDtVw();
        },

        selectConsultDtVw : () => {
            let param = {sn : cst.sn}

            callModule.call(Util.getRequestUrl("/svc/csc/cst/selectConsultDtVw.do"), param, (result) => {
                cst.vodt = result.consultVO;
            })
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
                    ${cst.vodt.title}
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td>
                    ${cst.vodt.contents}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left">
                <button class="btn__gray" onclick="">
                    <span>목록</span>
                </button>
            </div>
            <div class="right">
                <button class="btn__red" onclick="">
                    <span>삭제</span>
                </button>
                <button class="btn__bluegreen" onclick="">
                    <span>수정</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/svc/template_bottom.jspf" %>