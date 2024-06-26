<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const uex = {

        searchParams : {},
        uexList : [],

        init : () => {

        },
        selectUserExitList : (pageIndex) => {
            let param = {
                pageInde : pageIndex || '1'
            }

            uex.searchParams = param;

            callModule.call(Util.getRequestUrl(""), param, (result) => {

                uex.uexList = result.userAuthVOList || [];

                $("#totCnt").text(uex.uexList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(uex.uexList.length == 0) {
                    let html = `<tr>
                                    <td colspan="6">등록된 회원이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < uex.uexList.length; i++) {
                    if(uex.uexList[i].rnum > 10) break;

                    let html = `<tr onclick="uat.selectUserAuthDtlsVw('\${uex.uexList[i].userSn}')">
                                    <td>\${uex.uexList[i].userId}</td>
                                    <td>\${uex.uexList[i].userNm != null ? uex.uexList[i].userNm : "-"}</td>
                                    <td>\${uex.uexList[i].nickNm != null ? uex.uexList[i].nickNm : "-"}</td>
                                    <td>\${uex.uexList[i].loginSe}</td>
                                    <td>\${uex.uexList[i].profileImgUrl}</td>
                                    <td>\${uex.uexList[i].rgtrDt}</td>
                                </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(uex.uexList), 'uex.pageMove');
            })
        },
        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            uex.uexList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr>
                                <td>\${vo.userId}</td>
                                <td>\${vo.userNm != null ? vo.userNm : "-"}</td>
                                <td>\${vo.nickNm != null ? vo.nickNm : "-"}</td>
                                <td>\${vo.loginSe}</td>
                                <td>\${vo.profileImgUrl}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(uex.uexList), 'uex.pageMove');
        },
    }
    $(() => {
        uex.init();
    })
</script>

<div>

</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>