<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let org = {
        init : () => {
            org.selectOrganizationList();
        },
        selectOrganizationList : (pageIndex) => {
            let param = {
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }
            callModule.call(Util.getRequestUrl("/mng/cmm/org/selectOrganizationList.do"), param, (result) => {

                for(var i = 0; i < result.organizationVOList.length; i++) {
                    // 1depth
                    if(result.organizationVOList[i].orgId == '10000000') {
                        var html = `<tr>
                                        <td>\${result.organizationVOList[i].orgNm}</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>`
                        $("#tbody").append(html);
                    }
                    // 2depth
                    if((result.organizationVOList[i].orgId).substring(0,4) != '1000' && (result.organizationVOList[i].orgId).substring(4,8) == '0000') {
                        var html = `<tr>
                                        <td></td>
                                        <td class="left"><span>↳</span>&nbsp;&nbsp;&nbsp;\${result.organizationVOList[i].orgNm}</td>
                                        <td></td>
                                        <td></td>
                                    </tr>`
                        $("#tbody").append(html);
                    }
                    // 3depth
                    if((result.organizationVOList[i].orgId).substring(4,6) != '00' && (result.organizationVOList[i].orgId).substring(6,8) == '00') {
                        var html = `<tr>
                                        <td></td>
                                        <td></td>
                                        <td class="left"><span>↳</span>&nbsp;&nbsp;&nbsp;\${result.organizationVOList[i].orgNm}</td>
                                        <td></td>
                                    </tr>`
                        $("#tbody").append(html);
                    }
                    // 4depth
                    if((result.organizationVOList[i].orgId).substring(4,6) != '00' && (result.organizationVOList[i].orgId).substring(6,8) != '00') {
                        var html = `<tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td class="left"><span>↳</span>&nbsp;&nbsp;&nbsp;\${result.organizationVOList[i].orgNm}</td>
                                    </tr>`
                        $("#tbody").append(html);
                    }
                }
            })
        },
        insertOranization : () => {
            MessageUtil.confirm("조직을 등록하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {
                        orgNm : $("#orgNm").val(),
                        telNo : $("#telNo").val()
                    }
                    callModule.call(Util.getRequestUrl("/mng/cmm/org/insertOranization.do"), param, (result) => {

                        MessageUtil.alert(result.organizationVO.resultMessage, () => {
                            org.updateOrgInsertPop();
                            org.closeOrgInsertPop();
                        })
                    })
                }
            }, "등록", "취소");
        },
        updateOrgInsertPop : () => {
          $("#orgNm").val("");
          $("#telNo").val("");
        },
        closeOrgInsertPop : () => {
            var pop = document.getElementById("orgInsertPop");

            var display = pop.style.display;

            if (display === "none" || display === "") {
                pop.style.display = "block";
            } else {
                pop.style.display = "none";
            }
        },
    }

    $(() => {
        org.init();
        mmm.selectMenu("cmm", "조직관리")
    })

</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__select">
                    <label for="deptCd" class="search__title">부서</label>
                    <select id="deptCd">
                        <option value="">전체</option>
                        <option value="01">관리부</option>
                        <option value="99">기타</option>
                    </select>
                </div>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">검색어</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="">
            <span>조회</span>
        </button>
    </div>
    <br>
    <div class="search__results">
        <div>
            <span>총</span>
            <span class="num" id="totCnt">0</span>
            <span>건</span>
        </div>
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="org.closeOrgInsertPop();">
                <span>등록</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">조직관리 목록</caption>
            <colgroup>
                <col class="num" width="25%">
                <col class="num" width="25%">
                <col class="num" width="25%">
                <col class="num" width="25%">
            </colgroup>
            <thead>
                <tr>
                    <th>1depth</th>
                    <th>2depth</th>
                    <th>3depth</th>
                    <th>4depth</th>
                </tr>
            </thead>
            <tbody id="tbody"></tbody>
        </table>
    </div>
    <div class="paging-area">
        <div id="pagination" class="paging"></div>
    </div>
</div>


<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>


<div class="popup__window widget pop" id="orgInsertPop" style="left:380px; top:350px; display:none;">
    <div class="popup__header">
        <p class="title">조직관리 등록</p>
        <button class="btn__close" onclick="org.closeOrgInsertPop()"><span class="hidden">닫기</span></button>
    </div>
    <div class="popup__body" style="min-width: unset; width: 1150px; height: 180px;">
        <div style="margin-top: 0; gap: 5px; justify-content: center;">
            <div class="table-box">
                <table>
                    <caption class="hidden">조직관리 등록</caption>
                    <colgroup>
                        <col class="col-num" style="width: 20%;">
                        <col class="col-num" style="width: 30%;">
                        <col class="col-num" style="width: 20%;">
                        <col class="col-num" style="width: 30%;">
                    </colgroup>
                    <thead></thead>
                    <tbody>
                        <tr>
                            <th>조직명</th>
                            <td>
                                <input type="text" id="orgNm" name="orgNm" />
                            </td>
                            <th>TEL.</th>
                            <td>
                                <input type="text" id="telNo" name="telNo" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <br>
            <div class="btn">
                <div class="btn__box">
                    <div class="left">
                    </div>
                    <div class="right">
                        <button class="btn__blue" onclick="org.insertOranization();">
                            <span>저장</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>