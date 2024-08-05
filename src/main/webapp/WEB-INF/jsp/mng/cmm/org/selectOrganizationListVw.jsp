<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    let org = {
        init : () => {

        },
        selectOrganizationInsertVw : () => {
            callModule.post(Util.getRequestUrl("/mng/cmm/org/selectOrganizationInsertVw.do"), {}, 'post');
        },
        insertOranization : () => {
            let param = {
                orgNm : $("#orgNm").val(),
                telNo : $("#telNo").val()
            }
            callModule.call(Util.getRequestUrl("/mng/cmm/org/insertOranization.do"), param, (result) => {
                debugger;
                console.log(result)
            })
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
                <col class="num" width="60%">
                <col class="num" width="10%">
                <col class="num" width="15%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
            <tr>
                <th>제목</th>
                <th>조회수</th>
                <th>등록자</th>
                <th>등록일자</th>
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