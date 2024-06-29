<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const usi = {

        searchParams : {},
        usiList : [],

        init : () => {
            usi.selectUserSignInList();
        },

        selectUserSignInList : (pageIndex) => {

            let param = {
                authCd : $("#authCd").val(),
                searchKeyword : $("#searchKeyword").val(),
                pageInde : pageIndex || '1'
            }

            usi.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/usr/usi/selectUserSignInList.do"), param, (result) => {

                usi.usiList = result.userSignInVOList || [];

                $("#totCnt").text(usi.usiList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(usi.usiList.length == 0) {
                    let html = `<tr>
                                    <td colspan="8">가입된 회원이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < usi.usiList.length; i++) {

                    if(usi.usiList[i].rnum > 10) break;

                    let html = `<tr>
                                    <td><img src="\${usi.usiList[i].profileImgUrl}" alt="blank_" style="width: 25%; height: 25%; border-radius: 20px;" /></td>
                                    <td>\${usi.usiList[i].userId}</td>
                                    <td>\${usi.usiList[i].userNm != null ? usi.usiList[i].userNm : "-"}</td>
                                    <td>\${usi.usiList[i].nickNm != null ? usi.usiList[i].nickNm : "-"}</td>
                                    <td>\${usi.usiList[i].loginSe}</td>
                                    <td>\${usi.usiList[i].rgtrDt}</td>
                                </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(usi.usiList), 'usi.pageMove');
            })
        },
        pageMove: function(pageIndex) {

            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            usi.usiList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr>
                                <td><img src="\${vo.profileImgUrl}" alt="blank_" style="width: 25%; height: 25%; border-radius: 20px;" /></td>
                                <td>\${vo.userId}</td>
                                <td>\${vo.userNm != null ? vo.userNm : "-"}</td>
                                <td>\${vo.nickNm != null ? vo.nickNm : "-"}</td>
                                <td>\${vo.loginSe}</td>
                                <td>\${vo.rgtrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(usi.usiList), 'usi.pageMove');
        },
    }

    $(() => {
        usi.init();
        mmm.selectMenu('usr', '회원가입내역')
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
        <button class="btn__search icon" onclick="usi.selectUserSignInList();">
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
            <caption class="hidden">회원탈퇴 목록</caption>
            <colgroup>
                <col class="num" width="10%">
                <col class="num" width="20%">
                <col class="num" width="20%">
                <col class="num" width="20%">
                <col class="num" width="15%">
                <col class="num" width="15%">
            </colgroup>
            <thead>
            <tr>
                <th>프로필</th>
                <th>회원ID</th>
                <th>회원명</th>
                <th>닉네임</th>
                <th>연계SNS</th>
                <th>가입일자</th>
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