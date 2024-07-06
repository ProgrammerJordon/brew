<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const ull = {
        init : () => {

        },
    }

    $(() => {
        ull.init();
        mmm.selectMenu('uss', '로그인이력')
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">로그 검색</label>
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
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">로그인이력 목록</caption>
            <colgroup>
                <col class="num" width="25%">
                <col class="num" width="15%">
                <col class="num" width="25%">
                <col class="num" width="15%">
                <col class="num" width="20%">
            </colgroup>
            <thead>
            <tr>
                <th>로그ID</th>
                <th>연계SNS</th>
                <th>일련번호</th>
                <th>닉네임</th>
                <th>일시</th>
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