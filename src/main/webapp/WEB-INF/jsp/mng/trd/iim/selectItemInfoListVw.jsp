<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>


<script>
    const iim = {

        searchParams : {},
        iimList : [],

        init : () => {
            iim.selectItemInfoList();
        },
        insertItemInfoBatch : () => {
            MessageUtil.confirm("종목정보 배치를 실행하시겠습니까?", (boolean) => {
                if(boolean) {
                    let param = {};
                    callModule.call(Util.getRequestUrl("/mng/trd/iim/insertItemInfoBatch.do"), param, (result) => {
                        MessageUtil.alert(result.itemInfoVO.resultMessage, () => {
                        })
                    })
                }
            }, "실행", "취소")
        },
        selectItemInfoList : (pageIndex) => {
            let param = {
                mrktCtg : $("#mrktCtg").val(),
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }

            iim.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/trd/iim/selectItemInfoList.do"), param, (result) => {

                iim.iimList = result.itemInfoVOList || [];

                $("#totCnt").text(iim.iimList.length.toLocaleString());

                gridModule.clear_grid("tbody");

                if(iim.iimList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">등록된 종목정보가 존재하지 않습니다.</td>
                                </tr>`
                    $("tbody").append(html);
                    return false;
                }

                for(let i = 0; i < iim.iimList.length; i++) {
                    if (iim.iimList[i].rnum > 10) break;

                    let html = `<tr onclick="iim.selectItemInfoDtlsVw('\${iim.iimList[i].srtnCd}')">
                                    <td>\${iim.iimList[i].mrktCtg}</td>
                                    <td>\${iim.iimList[i].itmsNm}</td>
                                    <td>\${iim.iimList[i].srtnCd}</td>
                               </tr>`

                    $("#tbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(iim.iimList), 'iim.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("tbody");

            iim.iimList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr onclick="iim.selectItemInfoDtlsVw('\${vo.srtnCd}')">
                                <td>\${vo.mrktCtg}</td>
                                <td>\${vo.itmsNm}</td>
                                <td>\${vo.srtnCd}</td>
                           </tr>`
                $("#tbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(iim.iimList), 'iim.pageMove');
        },
        selectItemInfoDtlsVw : (srtnCd) => {
            let param = {
                srtnCd : srtnCd
            }
            callModule.post(Util.getRequestUrl("/mng/trd/iim/selectItemInfoDtlsVw.do"), param, 'get');
        }
    }

    $(() => {
        iim.init();
        mmm.selectMenu("trd")
    })
</script>

<div>
    <div class="search-box">
        <ul style="margin-right: 2%;">
            <li>
                <div class="search__type__select">
                    <label for="mrktCtg" class="search__title">Market</label>
                    <select id="mrktCtg" name="mrktCtg">
                        <option value="">ALL</option>
                        <option value="KOSPI">KOSPI</option>
                        <option value="KOSDAQ">KOSDAQ</option>
                        <option value="KONEX">KONEX</option>
                    </select>
                </div>
                <div class="search__type__input">
                    <label for="searchKeyword" class="search__title">Search</label>
                    <input id="searchKeyword" name="searchKeyword" />
                </div>
            </li>
        </ul>
        <button class="btn__search icon" onclick="iim.selectItemInfoList();">
            <span>Search</span>
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
            <button class="btn__bluegreen" onclick="iim.insertItemInfoBatch();">
                <span>ItemInfo Batch</span>
            </button>
        </div>
    </div>
    <div class="table-box">
        <table>
            <caption class="hidden">종목 리스트</caption>
            <colgroup>
                <col class="num" width="33%">
                <col class="num" width="33%">
                <col class="num" width="33%">
            </colgroup>
            <thead>
                <tr>
                    <th>Market</th>
                    <th>Item</th>
                    <th>Ticker</th>
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