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
            ord.selectItemInfo("삼성전자", "005930");
            ord.closeItemSearchPop();
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
            let param = {srtnCd : ord.srtnCd}

            callModule.call(Util.getRequestUrl("/mng/trd/iim/selectItemInfoDtls.do"), param, (result) => {

                $("#itemInfoDtls").show();
                //시장구분
                var marketCode = result.itemInfoVO.res.output.mket_id_cd;

                if (marketCode === "KNX") {
                    market = "코넥스";
                } else if (marketCode === "KSQ") {
                    market = "코스닥";
                } else if (marketCode === "STK") {
                    market = "코스피";
                }

                $("#mketIdCd").text(market);

                $("#prdtAbrvName").text(result.itemInfoVO.res.output.prdt_abrv_name);
                $("#prdtEngName").text(result.itemInfoVO.res.output.prdt_eng_name);
                $("#lstgStqt").text(parseInt(result.itemInfoVO.res.output.lstg_stqt).toLocaleString());
                $("#cpta").text(parseInt(result.itemInfoVO.res.output.cpta).toLocaleString());
                $("#papr").text(parseInt(result.itemInfoVO.res.output.papr).toLocaleString());

                // 상장일
                var sctsMketLstgDt = result.itemInfoVO.res.output.scts_mket_lstg_dt;
                var kosdaqMketLstgDt = result.itemInfoVO.res.output.kosdaq_mket_lstg_dt;

                function formatDate(dateStr) {
                    if (dateStr.length === 8) {
                        return dateStr.substring(0, 4) + '/' + dateStr.substring(4, 6) + '/' + dateStr.substring(6);
                    }
                    return dateStr;
                }

                var formattedSctsMketLstgDt = formatDate(sctsMketLstgDt);
                var formattedKosdaqMketLstgDt = formatDate(kosdaqMketLstgDt);

                $("#sctsMketLstgDt").text(formattedSctsMketLstgDt);
                $("#kosdaqMketLstgDt").text(formattedKosdaqMketLstgDt);

                // 보통주 / 우선주
                var val = result.itemInfoVO.res.output.stck_kind_cd;

                var rs = "";
                if (val == "101") {
                    rs = "보통주";
                } else if (val == "201") {
                    rs = "우선주";
                }

                $("#stckKindCd").text(rs);

                // 거래정지 관리종목
                var trStopYn = result.itemInfoVO.res.output.tr_stop_yn;
                var admnItemYn = result.itemInfoVO.res.output.admn_item_yn;

                if (trStopYn == "N") {
                    $("#trStopYn").text("-");
                } else if (trStopYn == "Y") {
                    $("#trStopYn").text("거래정지");
                }

                if (admnItemYn == "Y") {
                    $("#admnItemYn").text("-");
                } else if (admnItemYn == "Y") {
                    $("#admnItemYn").text("관리종목");
                }

                // 업종
                $("#stdIdstClsfCdName").text(result.itemInfoVO.res.output.std_idst_clsf_cd_name);

                // 결산일
                var setlMmdd = result.itemInfoVO.res.output.setl_mmdd;

                if (setlMmdd.length === 4) {
                    setlMmdd = setlMmdd.substring(0, 2) + '/' + setlMmdd.substring(2);
                }
                $("#setlMmdd").text(setlMmdd);
            })
        },
        // market, limit
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

        // orderable shares
        calAvailable : () => {

            let price = $("#price").val() || null;
            let cash = $("#dncaTotAmt").text() || null;

            let priceFlag = false;
            let cashFlag = false;

            if(price == null || price ==  "") {
                MessageUtil.alert("주문가격을 입력하세요.")
            }else {
                priceFlag = true;
            }

            if(cash == null || cash == "") {
                MessageUtil.alert("주문가능한 현금이 없습니다.")
            }else if($("#dncaTotAmt").text() == '0') {
                MessageUtil.alert("주문가능한 현금이 없습니다.")
            }else {
                cashFlag = true;
            }

            if(priceFlag == true && cashFlag == true) {
                let quntity = Math.floor(parseInt(cash) / parseInt(price));
                $("#searchAmount").val(quntity);
                $("#amount").val(quntity);

                ord.setTotalPrice();
            }

        },
        setTotalPrice : () => {
            let price = $("#price").val() || null;
            let amount = $("#amount").val() || null;

            if(price != null && amount != null) {
                let totalPrice = parseInt(price) * parseInt(amount);
                if(totalPrice != null && totalPrice != "") {
                    $("#totalPrice").val(totalPrice);
                }
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
    <div id="itemInfoDtls" class="table-box" style="display: none;">
        <table>
            <caption class="hidden">종목정보 상세조회 화면</caption>
            <colgroup>
                <col class="num" width="10%">
                <col class="num" width="40%">
                <col class="num" width="10%">
                <col class="num" width="40%">
            </colgroup>
            <thead></thead>
            <tbody>
            <tr>
                <th>마켓</th>
                <td><span id="mketIdCd"></span></td>
                <th>종목명</th>
                <td>
                    <span id="prdtAbrvName"></span><span>(</span> <span id="prdtEngName"></span> <span>)</span>
                </td>
            </tr>
            <tr>
                <th>상장주식수</th>
                <td><span id="lstgStqt"></span></td>
                <th>자본금</th>
                <td><span id="cpta"></span></td>
            </tr>
            <tr>
                <th>액면가</th>
                <td><span id="papr"></span></td>
                <th>상장일자</th>
                <td><span id="sctsMketLstgDt"></span><span id="kosdaqMketLstgDt"></span></td>
            </tr>
            <tr>
                <th>구분</th>
                <td><span id="stckKindCd"></span></td>
                <th>특이사항</th>
                <td><span id="trStopYn"></span> <span id="admnItemYn"></span></td>
            </tr>
            <tr>
                <th>업종</th>
                <td><span id="stdIdstClsfCdName"></span></td>
                <th>결산일</th>
                <td><span id="setlMmdd"></span></td>
            </tr>
            </tbody>
        </table>
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
            <br>
            <div>
                <div>
                    <select id="scale-type">
                        <option value="linear" selected>Linear</option>
                        <option value="logarithmic">Logarithmic</option>
                    </select>
                    &nbsp;&nbsp;&nbsp;
                    <select id="mixed">
                        <option value="true">Yes</option>
                        <option value="false" selected>No</option>
                    </select>
                    &nbsp;&nbsp;&nbsp;
                    <button id="update">Update</button>
                    &nbsp;&nbsp;&nbsp;
                    <button id="randomizeData">Randomize Data</button>
                </div>
                <br>
                <div>
                    <canvas id="candleChart" style="width: 1100px; height: 450px;"></canvas>
                </div>
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
                            <col class="num" style="width: 100%">
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
                                    <div class="search__type__input" style="align-items: center;">
                                        <label for="price" class="search__title">Price</label>
                                        <input type="number" id="price" name="price" onchange="ord.setTotalPrice();" min="0" step="1" style="text-align: right;" />
                                        &nbsp;&nbsp;&nbsp;<span>(won)</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input" style="align-items: center;">
                                        <label for="amount" class="search__title">Amount</label>
                                        <input type="number" id="amount" name="amount" onchange="ord.setTotalPrice();" min="0" step="1" style="text-align: right;" />
                                        &nbsp;&nbsp;&nbsp;<span>(shares)</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="search__type__input" style="align-items: center;">
                                        <button class="btn__black__line" onclick="ord.calAvailable();">Available</button>
                                        <input id="searchAmount" name="searchAmount" style="text-align: right;" readonly />
                                        &nbsp;&nbsp;&nbsp;<span>(shares)</span>
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
                                        <input id="totalPrice" style="text-align: right;" readonly />
                                        &nbsp;&nbsp;&nbsp;<span>(won)</span>
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
                        <col class="col-num" style="width: 40%">
                        <col class="col-num" style="width: 40%">
                        <col class="col-num" style="width: 20%">
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
    <div class="popup__body" style="min-width: unset; width: 1400px; height: 750px;">
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

<script>
    var barCount = 60;
    var initialDateStr = new Date().toUTCString();

    var ctx = document.getElementById('candleChart');

    var barData = new Array(barCount);
    var lineData = new Array(barCount);

    getRandomData(initialDateStr);

    var chart = new Chart(ctx, {
        type: 'candlestick',
        data: {
            datasets: [{
                label: "",
                data: barData,
            }, {
                label: '10days',
                type: 'line',
                data: lineData,
                hidden: true,
            }, {
                label: '60days',
                type: 'line',
                data: lineData,
                hidden: true,
            }, {
                label: '120days',
                type: 'line',
                data: lineData,
                hidden: true,
            }, {
                label: '224days',
                type: 'line',
                data: lineData,
                hidden: true,
            }
            ]
        }
    });

    function randomNumber(min, max) {
        return Math.random() * (max - min) + min;
    }

    function randomBar(target, index, date, lastClose) {
        var open = +randomNumber(lastClose * 0.95, lastClose * 1.05).toFixed(2);
        var close = +randomNumber(open * 0.95, open * 1.05).toFixed(2);
        var high = +randomNumber(Math.max(open, close), Math.max(open, close) * 1.1).toFixed(2);
        var low = +randomNumber(Math.min(open, close) * 0.9, Math.min(open, close)).toFixed(2);

        if (!target[index]) {
            target[index] = {};
        }

        Object.assign(target[index], {
            x: date.valueOf(),
            o: open,  // 시가
            h: high, // 최고가
            l: low, // 최저가
            c: close // 종가
        });

    }

    function getRandomData(dateStr) {
        var date = luxon.DateTime.fromRFC2822(dateStr);

        for (let i = 0; i < barData.length;) {
            date = date.plus({days: 1});
            if (date.weekday <= 5) {
                randomBar(barData, i, date, i === 0 ? 30 : barData[i - 1].c);
                lineData[i] = {x: barData[i].x, y: barData[i].c};
                i++;
            }
        }
    }

    var update = function() {

        var dataset = chart.config.data.datasets[0];

        // candlestick
        chart.config.type = 'candlestick';

        // 가중평균 / 지수평균
        var scaleType = document.getElementById('scale-type').value;
        chart.config.options.scales.y.type = scaleType;

        // 캔들색상
        chart.config.data.datasets[0].backgroundColors = {
            up: '#01ff01',
            down: '#fe0000',
            unchanged: '#999',
        };
        // 최고최저라인
        dataset.borderColors;


        // 선이랑 캔들이랑 같이
        var mixed = document.getElementById('mixed').value;
        if (mixed === 'true') {
            chart.config.data.datasets[1].hidden = false;
        } else {
            chart.config.data.datasets[1].hidden = true;
        }

        chart.update();
    };

    [...document.getElementsByTagName('select')].forEach(element => element.addEventListener('change', update));

    document.getElementById('update').addEventListener('click', update);

    document.getElementById('randomizeData').addEventListener('click', function() {
        getRandomData(initialDateStr, barData);
        update();
    });
</script>