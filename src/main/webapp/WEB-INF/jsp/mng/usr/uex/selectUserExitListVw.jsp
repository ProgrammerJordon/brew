<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const uex = {

        searchParams : {},
        uexList : [],
        chk : [],

        init : () => {
            uex.selectUserExitList();
        },

        chkArray : (exitSn, userSn) => {
            var vl = {
                exitSn : exitSn,
                userSn : userSn
            }

            let index = uex.chk.findIndex(item => item.exitSn === vl.exitSn);

            if (index !== -1) {
                uex.chk.splice(index, 1);
            } else {
                uex.chk.push(vl);
            }
        },

        selectUserExitList : (pageIndex) => {

            let param = {
                authCd : $("#authCd").val(),
                searchKeyword : $("#searchKeyword").val(),
                pageInde : pageIndex || '1'
            }

            uex.searchParams = param;
            uex.chk = [];

            callModule.call(Util.getRequestUrl("/mng/usr/uex/selectUserExitList.do"), param, (result) => {

                uex.uexList = result.userExitVOList || [];

                $("#totCnt").text(uex.uexList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(uex.uexList.length == 0) {
                    let html = `<tr>
                                    <td colspan="9">등록된 회원이 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < uex.uexList.length; i++) {

                    if(uex.uexList[i].rnum > 10) break;

                    let checkBox;

                    if(uex.uexList[i].revivalYn == 'N') {
                        checkBox = `<input type="checkbox" name="chk" onclick='uex.chkArray("\${uex.uexList[i].exitSn}", "\${uex.uexList[i].userSn}");'/>`;
                    }else{
                        checkBox = ``
                    }

                    let html = `<tr>
                                    <td>\${checkBox}</td>
                                    <td><img src="\${uex.uexList[i].profileImgUrl}" alt="blank_" style="width: 25%; height: 25%; border-radius: 20px;" /></td>
                                    <td>\${uex.uexList[i].userId}</td>
                                    <td>\${uex.uexList[i].userNm != null ? uex.uexList[i].userNm : "-"}</td>
                                    <td>\${uex.uexList[i].nickNm != null ? uex.uexList[i].nickNm : "-"}</td>
                                    <td>\${uex.uexList[i].loginSe}</td>
                                    <td>\${uex.uexList[i].rgtrDt}</td>
                                    <td>\${uex.uexList[i].revivalYn == 'Y' ? "복구" : "미복구"}</td>
                                    <td>\${uex.uexList[i].mdfrDt == uex.uexList[i].rgtrDt ? "-" : uex.uexList[i].mdfrDt}</td>
                                </tr>`

                    $("tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(uex.uexList), 'uex.pageMove');
            })
        },
        pageMove: function(pageIndex) {

            if (!pageIndex) return;

            uex.chk = [];

            gridModule.clear_grid("tbody");

            uex.uexList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {

                let checkBox;

                if(vo.revivalYn == 'N') {
                    checkBox = `<input type="checkbox" name="chk" onclick='uex.chkArray("\${vo.exitSn}", "\${vo.userSn}");' />`;
                }else{
                    checkBox = ``
                }

                let html = `<tr>
                                <td>\${checkBox}</td>
                                <td><img src="\${vo.profileImgUrl}" alt="blank_" style="width: 25%; height: 25%; border-radius: 20px;" /></td>
                                <td>\${vo.userId}</td>
                                <td>\${vo.userNm != null ? vo.userNm : "-"}</td>
                                <td>\${vo.nickNm != null ? vo.nickNm : "-"}</td>
                                <td>\${vo.loginSe}</td>
                                <td>\${vo.rgtrDt}</td>
                                <td>\${vo.revivalYn == 'Y' ? "복구" : "미복구"}</td>
                                <td>\${vo.mdfrDt == vo.rgtrDt ? "-" : vo.mdfrDt}</td>
                           </tr>`
                $("tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(uex.uexList), 'uex.pageMove');
        },

        updateUserExit : () => {

            MessageUtil.confirm("탈퇴한 회원을 활성화로 수정하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {chk : uex.chk}
                    callModule.call(Util.getRequestUrl("/mng/usr/uex/updateUserExit.do"), param, (result) => {
                        MessageUtil.alert(result.userExitVO.resultMessage, () => {
                            uex.selectUserExitList();
                        })
                    })
                }
            })
        }
    }

    $(() => {
        uex.init();
        mmm.selectMenu('usr', '회원탈퇴내역');
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
        <div class="btn__box">
            <button class="btn__bluegreen" onclick="uex.updateUserExit();">
                <span>데이터 복구</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">회원탈퇴 목록</caption>
            <colgroup>
                <col class="num" width="10%">
                <col class="num" width="10%">
                <col class="num" width="15%">
                <col class="num" width="15%">
                <col class="num" width="10%">
                <col class="num" width="10%">
                <col class="num" width="10%">
                <col class="num" width="10%">
            </colgroup>
            <thead>
                <tr>
                    <th></th>
                    <th>프로필</th>
                    <th>회원ID</th>
                    <th>회원명</th>
                    <th>닉네임</th>
                    <th>연계SNS</th>
                    <th>탈퇴일자</th>
                    <th>복구</th>
                    <th>복구일자</th>
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