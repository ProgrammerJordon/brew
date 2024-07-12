<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const ord = {

        searchParams : {},
        ordList : [],
        srtnCd : "",

        account : `${account}`,
        ordDvsn : "00",

        init : () => {
            ord.selectDomesticAccount();
        },
        closeItemSearchPop : () => {
            var pop = document.getElementById("ItemSearchPop");

            var display = pop.style.display;

            if (display === "none" || display === "") {
                pop.style.display = "block";
            } else {
                pop.style.display = "none";
            }
        },
        selectItemInfoList : (pageIndex) => {
            let param = {
                mrktCtg : $("#mrktCtg").val(),
                searchKeyword : $("#searchKeyword").val(),
                pageIndex : pageIndex || '1'
            }

            ord.searchParams = param;

            callModule.call(Util.getRequestUrl("/mng/trd/iim/selectItemInfoList.do"), param, (result) => {

                ord.closeItemSearchPop();

                ord.ordList = result.itemInfoVOList || [];

                $("#totCnt").text(ord.ordList.length.toLocaleString());

                gridModule.clear_grid("popbody");

                if(ord.ordList.length == 0) {
                    let html = `<tr>
                                    <td colspan="4">조회된 종목정보가 존재하지 않습니다.</td>
                                </tr>`
                    $("popbody").append(html);
                    return false;
                }

                for(let i = 0; i < ord.ordList.length; i++) {
                    if (ord.ordList[i].rnum > 10) break;

                    let html = `<tr>
                                    <td>\${ord.ordList[i].mrktCtg}</td>
                                    <td>\${ord.ordList[i].itmsNm} <span>(</span>\${ord.ordList[i].srtnCd}<span>)</span></td>
                                    <td>
                                        <div>
                                            <button class="btn__black__line" onclick="ord.selectItemInfo('\${ord.ordList[i].itmsNm}', '\${ord.ordList[i].srtnCd}');">Choose</button>
                                        </div>
                                    </td>
                               </tr>`

                    $("#popbody").append(html);
                }
                $('#pagination').page(1, gridModule.getPageSize(ord.ordList), 'ord.pageMove');
            })
        },

        pageMove: function(pageIndex) {
            if (!pageIndex) return;

            gridModule.clear_grid("popbody");

            ord.ordList.filter(vo => vo.rnum >= ((pageIndex - 1) * 10 + 1) && vo.rnum <= (pageIndex * 10)).forEach(vo => {
                let html = `<tr>
                                <td>\${vo.mrktCtg}</td>
                                <td>\${vo.itmsNm} <span>(</span> \${vo.srtnCd} <span>)</span></td>
                                <td>
                                    <div>
                                        <button class="btn__black__line" onclick="ord.selectItemInfo('\${vo.itmsNm}', '\${vo.srtnCd}');">Choose</button>
                                    </div>
                                </td>
                           </tr>`
                $("#popbody").append(html);
            });
            $('#pagination').page(pageIndex, gridModule.getPageSize(ord.ordList), 'ord.pageMove');
        },

        selectItemInfo : (itmsNm, srtnCd) => {
            // 팝업닫기
            ord.closeItemSearchPop();
            // 글로벌 종목코드 변경
            ord.srtnCd = srtnCd;
            // 종목명 부여
            $("#itmsNm").text(itmsNm);
            $("#srtnCd").text("(" + srtnCd + ")");

            // 종목코드로 종목관련 정보 조회
            let param = {
                srtnCd : ord.srtnCd
            }
            //callModule.call(Util.getRequestUrl("/mng/trd/ord/selectItemInfo.do"), param, (result) => {})
        },

        chageOrdDvsn : (param) => {
            ord.ordDvsn = param;

            if(param == '00') {
                $("#limit").addClass('btn__blue').removeClass('btn__black__line');
                $("#market").removeClass('btn__blue').addClass('btn__black__line');
            }

            if(param == '01') {
                $("#limit").removeClass('btn__blue').addClass('btn__black__line');
                $("#market").addClass('btn__blue').removeClass('btn__black__line');
            }
        },

        calAvailable : (cash, price) => {

            let quntity = 0;

            if(cash != "" && price != "") {
                quntity = Math.floor(parseInt(cash) / price);
                $("#searchAmount").text(quntity);
            }else {
                quntity = Math.floor(parseInt($("#dncaTotAmt").text()) / price);
                $("#searchAmount").text(quntity);
            }
        },

        selectDomesticAccount : () => {
            let param = {
                account : ord.account
            }
            callModule.call(Util.getRequestUrl("/mng/trd/ord/selectDomesticAccount.do"), param, (result) => {

                // holoding shares
                if(result.stockOrderVO.res.output1.length != 0) {

                    for(var i = 0; i < result.stockOrderVO.res.output1.length; i++) {
                        var html = `<tr>
                                    <td><span>\${result.stockOrderVO.res.output1[i].prdt_name}</span> <span>(</span> <span>\${result.stockOrderVO.res.output1[i].pdno}</span> <span>)</span></td>
                                    <td><span>\${result.stockOrderVO.res.output1[i].pchs_avg_pric}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output1[i].hldg_qty}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output1[i].pchs_amt}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output1[i].evlu_pfls_amt}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output1[i].evlu_pfls_rt}</span></td>
                                    <td>
                                        <div class="btn__box">
                                            <button class="btn__blue">Sell</button>
                                        </div>
                                    </td>
                                </tr>`;
                    }
                    $("#tbody1").append(html);
                }

                // cash
                $("#pchsAmtSmtlAmt").text(result.stockOrderVO.res.output2[0].pchs_amt_smtl_amt.toLocaleString());
                $("#evluAmtSmtlAmt").text(result.stockOrderVO.res.output2[0].evlu_amt_smtl_amt.toLocaleString());
                $("#evluPflsSmtlAmt").text(result.stockOrderVO.res.output2[0].evlu_pfls_smtl_amt.toLocaleString());
                $("#dncaTotAmt").text(result.stockOrderVO.res.output2[0].dnca_tot_amt.toLocaleString());

            })
        }

    }

    $(() => {
        ord.init();
        mmm.selectMenu('trd')
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
        <button class="btn__search icon" onclick="ord.selectItemInfoList();">
            <span>Search</span>
        </button>
    </div>
    <br>
    <div style="display: flex; justify-content: space-between;">
        <div style="width: 65%">
            <div>
                <div style="font-size: 1.5em;">
                    <span id="itmsNm"></span>
                    <span id="srtnCd"></span>
                </div>
            </div>
            <div>
                <canvas id="chart" height="400px;"></canvas>
            </div>
            <br>
            <div>
                <div class="table-box">
                    <table>
                        <caption class="hidden">Account Status</caption>
                        <colgroup>
                            <col class="num" style="width: 25%;">
                            <col class="num" style="width: 25%;">
                            <col class="num" style="width: 25%;">
                            <col class="num" style="width: 25%;">
                        </colgroup>
                        <thead>
                            <tr>
                                <th>Purchase Amount</th>
                                <th>Evaluation Amount</th>
                                <th>Profit</th>
                                <th>Cash</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <span id="pchsAmtSmtlAmt"></span>
                                </td>
                                <td>
                                    <span id="evluAmtSmtlAmt"></span>
                                </td>
                                <td>
                                    <span id="evluPflsSmtlAmt"></span>
                                </td>
                                <td>
                                    <span id="dncaTotAmt"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div style="width: 30%;">
            <div>
                <div class="table-box">
                    <table>
                        <caption class="hidden">Order</caption>
                        <colgroup>
                            <col class="num" style="width: ">
                            <col class="num" style="width: ">
                        </colgroup>
                        <thead></thead>
                        <tbody>
                            <tr>
                                <td>
                                    <div class="search__type__select">
                                        <label for="account" class="search__title">Account</label>
                                        <select id="account" name="account">
                                            <option value="${account}">${account}<span>-01</span></option>
                                        </select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input">
                                        <label for="price" class="search__title">Price</label>
                                        <input id="price" name="price" style="text-align: right;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input">
                                        <label for="amount" class="search__title">Amount</label>
                                        <input id="amount" name="amount" style="text-align: right;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input btn__box">
                                        <button class="btn__black__line" onclick="ord.calAvailable();">Available</button>
                                        <input id="searchAmount" name="searchAmount" readonly />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input btn__box">
                                        <button class="btn__blue" id="limit" onclick="ord.chageOrdDvsn('00')">Limit</button>
                                        <button class="btn__black__line" id="market" onclick="ord.chageOrdDvsn('01')">Market</button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input">
                                        <label for="totalPrice" class="search__title">Total Price</label>
                                        <input id="totalPrice" name="totalPrice" style="text-align: right;" readonly />
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <br>
            <div style="display: flex; justify-content: space-around;">
                <div class="btn__box">
                    <button class="btn__red">Buy</button>
                </div>
                <div class="btn__box">
                    <button class="btn__blue">Sell</button>
                </div>
            </div>
        </div>
    </div>
</div>
<br>
<br>
<div>
    <div style="display: flex; justify-content: space-between;">
        <div style="width: 65%">
            <div class="search__results">
                <div>
                    <span>Holding Shares</span>
                </div>
            </div>
            <div class="table-box">
                <table>
                    <colgroup class="hidden"></colgroup>
                    <colgroup>
                        <col class="col-num" style="width: 20%">
                        <col class="col-num" style="width: 20%">
                        <col class="col-num" style="width: 10%">
                        <col class="col-num" style="width: 10%">
                        <col class="col-num" style="width: 20%">
                        <col class="col-num" style="width: 10%">
                        <col class="col-num" style="width: 10%">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Purchase Average Price</th>
                            <th>Quntity</th>
                            <th>Purchase Amount</th>
                            <th>Profit Amount</th>
                            <th>Profit Ratio</th>
                            <th>Sell</th>
                        </tr>
                    </thead>
                    <tbody id="tbody1"></tbody>
                </table>
            </div>
        </div>
        <div style="width: 30%">
            <div class="search__results">
                <div>
                    <span>Condition Search</span>
                </div>
            </div>
            <div class="table-box">
                <table>
                    <colgroup class="hidden"></colgroup>
                    <colgroup>
                        <col class="num" style="width: ">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>Name(Ticker)</th>
                            <th>Price</th>
                            <th>Buy</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>

<div class="popup__window widget pop" id="ItemSearchPop" style="left:450px; top:230px; display:none;">
    <div class="popup__header">
        <p class="title">Search Items</p>
        <button class="btn__close" onclick="ord.closeItemSearchPop()"><span class="hidden">닫기</span></button>
    </div>
    <div class="popup__body scroll-list" style="min-width: unset; width: 1400px; height: 800px;">
        <div style="margin-top: 0; gap: 5px; justify-content: center;">
            <div class="table-box">
                <table>
                    <caption class="hidden">종목 검색 목록</caption>
                    <colgroup>
                        <col class="col-num" style="width: 20%;">
                        <col class="col-num" style="width: 60%;">
                        <col class="col-num" style="width: 20%;">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>Market</th>
                            <th>Item</th>
                            <th>Search</th>
                        </tr>
                    </thead>
                    <tbody id="popbody"></tbody>
                </table>
                <div class="paging-area">
                    <div id="pagination" class="paging"></div>
                </div>
            </div>
        </div>
    </div>
</div>