<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const ull = {

        searchParams : {},
        ullList : [],

        init : () => {
            ull.selectLoginLogList();
        },
        selectLoginLogList : (pageIndex) => {
            let param = {
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }

            ull.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/uss/ull/selectLoginLogList.do"), param, (result) => {

                ull.ullList = result.logInLogVOList || [];

                $("#totCnt").text(ull.ullList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(ull.ullList.length == 0) {
                    let html = `<tr>
                                    <td colspan="5">등록된 로그인로그 이력이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < ull.ullList.length; i++) {
                    if (ull.ullList[i].rnum > 10) break;

                    let html = `<tr>
                                    <td>\${ull.ullList[i].logId}</td>
                                    <td>\${ull.ullList[i].loginSe}</td>
                                    <td>\${ull.ullList[i].userSn}</td>
                                    <td>\${ull.ullList[i].nickNm}</td>
                                    <td>\${ull.ullList[i].rgtrDt}</td>
                              </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(ull.ullList), 'ull.pageMove');
            })
        },
        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            ull.ullList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr>
                                <td>\${vo.logId}</td>
                                <td>\${vo.loginSe}</td>
                                <td>\${vo.userSn}</td>
                                <td>\${vo.nickNm}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(ull.ullList), 'ull.pageMove');
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
        <button class="btn__search icon" onclick="ull.selectLoginLogList();">
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