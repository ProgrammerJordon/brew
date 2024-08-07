<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const usm = {

        searchParams : {},
        usmList : [],

        init : () => {
            usm.selectUserMngList();
        },

        selectUserMngDtlsVw : (userSn) => {
            let param = {userSn : userSn}
            callModule.post(Util.getRequestUrl("/mng/usr/usm/selectUserMngDtlsVw.do"), param, 'get')
        },

        selectUserMngList : (pageIndex) => {
            let param = {
                authCd : $("#authCd").val(),
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }

            usm.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/usr/usm/selectUserMngList.do"), param, (result) => {

                usm.usmList = result.userVOList || [];

                $("#totCnt").text(usm.usmList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(usm.usmList.length == 0) {
                    let html = `<tr>
                                    <td colspan="7">등록된 회원이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < usm.usmList.length; i++) {
                    if(usm.usmList[i].rnum > 10) break;

                    let html = `<tr onclick="usm.selectUserMngDtlsVw('\${usm.usmList[i].userSn}')">
                                    <td><img src="\${usm.usmList[i].profileImgUrl}" alt="blank_" style="width: 25%; height: 25%; border-radius: 20px;" /></td>
                                    <td>\${usm.usmList[i].userId}</td>
                                    <td>\${usm.usmList[i].userNm != null ? usm.usmList[i].userNm : "-"}</td>
                                    <td>\${usm.usmList[i].nickNm != null ? usm.usmList[i].nickNm : "-"}</td>
                                    <td>\${usm.usmList[i].loginSe}</td>
                                    <td>\${usm.usmList[i].authCd == 'A' ? "관리자" : "일반회원"}</td>
                                    <td>\${usm.usmList[i].useYn == 'Y' ? "활성화" : "비황성화"}</td>
                              </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(usm.usmList), 'usm.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            usm.usmList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr onclick="usm.selectUserMngDtlsVw('\${vo.userSn}')">
                                <td><img src="\${vo.profileImgUrl}" alt="blank_" style="width: 25%; height: 25%; border-radius: 20px;" /></td>
                                <td>\${vo.userId}</td>
                                <td>\${vo.userNm != null ? vo.userNm : "-"}</td>
                                <td>\${vo.nickNm != null ? vo.nickNm : "-"}</td>
                                <td>\${vo.loginSe}</td>
                                <td>\${vo.authCd == 'A' ? "관리자" : "일반회원"}</td>
                                <td>\${vo.useYn == 'Y' ? "활성화" : "비황성화"}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(uat.uatList), 'uat.pageMove');
        },
    }

    $(() => {
        usm.init();
        mmm.selectMenu("usr", "회원정보관리")
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__select">
                    <label for="authCd" class="search__title">권한코드</label>
                    <select id="authCd" name="authCd">
                        <option value="">전체</option>
                        <option value="A">관리자</option>
                        <option value="Z">일반회원</option>
                    </select>
                </div>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">유저검색</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="usm.selectUserMngList();">
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
            <caption class="hidden">회원정보관리 목록</caption>
            <colgroup>
                <col class="num" width="10%">
                <col class="num" width="20%">
                <col class="num" width="20%">
                <col class="num" width="20%">
                <col class="num" width="10%">
                <col class="num" width="10%">
                <col class="num" width="10%">
            </colgroup>
            <thead>
            <tr>
                <th>프로필</th>
                <th>회원ID</th>
                <th>회원명</th>
                <th>닉네임</th>
                <th>연계SNS</th>
                <th>권한코드</th>
                <th>활성화여부</th>
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