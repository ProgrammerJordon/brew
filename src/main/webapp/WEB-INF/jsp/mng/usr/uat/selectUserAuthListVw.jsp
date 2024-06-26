<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const uat = {

        searchParams : {},
        uatList : [],

        init : () => {
            uat.selectUserAuthList();
        },

        selectUserAuthDtlsVw : (userSn) => {
            let param = {
                userSn : userSn,
            }
            callModule.post(Util.getRequestUrl("/mng/usr/uat/selectUserAuthDtlsVw.do"), param, 'get');
        },

        selectUserAuthList : (pageIndex) => {
            let param = {
                loginSe : $("#loginSe").val(),
                useYn : $("#useYn").val(),
                authCd : $("#authCd").val(),
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }

            uat.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/usr/uat/selectUserAuthList.do"), param, (result) => {

                uat.uatList = result.userAuthVOList || [];

                $("#totCnt").text(uat.uatList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(uat.uatList.length == 0) {
                    let html = `<tr>
                                    <td colspan="6">등록된 회원이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < uat.uatList.length; i++) {
                    if(uat.uatList[i].rnum > 10) break;

                    let html = `<tr onclick="uat.selectUserAuthDtlsVw('\${uat.uatList[i].userSn}')">
                                <td>\${uat.uatList[i].userId}</td>
                                <td>\${uat.uatList[i].userNm != null ? uat.uatList[i].userNm : "-"}</td>
                                <td>\${uat.uatList[i].nickNm != null ? uat.uatList[i].nickNm : "-"}</td>
                                <td>\${uat.uatList[i].loginSe}</td>
                                <td>\${uat.uatList[i].authCd}</td>
                                <td>\${uat.uatList[i].useYn}</td>
                              </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(uat.uatList), 'uat.pageMove');
            })
        },
        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            uat.uatList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let html = `<tr onclick="uat.selectUserAuthDtlsVw('\${vo.userSn}')">
                                <td>\${vo.userId}</td>
                                <td>\${vo.userNm != null ? vo.userNm : "-"}</td>
                                <td>\${vo.nickNm != null ? vo.nickNm : "-"}</td>
                                <td>\${vo.loginSe}</td>
                                <td>\${vo.authCd}</td>
                                <td>\${vo.useYn}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(uat.uatList), 'uat.pageMove');
        },
    }

    $(() => {
        uat.init();
        mmm.selectMenu("usr")
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__select">
                    <label for="loginSe" class="search__title">연계SNS</label>
                    <select id="loginSe" name="loginSe">
                        <option value="">전체</option>
                        <option value="KAKAO">KAKAO</option>
                        <option value="GOOGLE">GOOGLE</option>
                        <option value="APPLE">APPLE</option>
                    </select>
                </div>
                <div class="search__type__select">
                    <label for="useYn" class="search__title">활성화여부</label>
                    <select id="useYn" name="useYn">
                        <option value="">전체</option>
                        <option value="Y">활성화</option>
                        <option value="N">미활성화</option>
                    </select>
                </div>
            </li>
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
        <button class="btn__search icon" onclick="uat.selectUserAuthList();">
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
            <caption class="hidden">회원권한 목록</caption>
            <colgroup>
                <col class="num" width="30%">
                <col class="num" width="20%">
                <col class="num" width="20%">
                <col class="num" width="10%">
                <col class="num" width="10%">
                <col class="num" width="10%">
            </colgroup>
            <thead>
            <tr>
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