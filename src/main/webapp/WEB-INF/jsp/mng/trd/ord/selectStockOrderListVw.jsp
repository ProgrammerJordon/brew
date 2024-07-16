<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const ord = {

        searchParams : {},
        ordList : [],
        srtnCd : "",

        account : `${account}`,
        ordDvsn : "00",
        dwmy : "D",

        init : () => {
            ord.selectDomesticAccount();
            ord.selectItemInfo("삼성전자", "005930");
            ord.closeItemSearchPop();

            //setInterval(ord.selectItemInfoChart, 5000);
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
        // 종목 조회
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
        UTCtime : (dateStr) => {
            // 입력된 dateString을 "YYYYMMDD" 형식에서 "YYYY-MM-DD" 형식으로 변환
            var formattedDateString = dateStr.replace(/^(\d{4})(\d{2})(\d{2})$/, '$1-$2-$3');
            // 변환된 날짜 문자열로 Date 객체 생성
            var dateObject = new Date(formattedDateString);
            // UTC 형식의 문자열로 변환
            var utcString = dateObject.toUTCString();
            return utcString;
        },
        // 종목정보
        selectItemInfo : (itmsNm, srtnCd, close) => {

            ord.closeItemSearchPop();
            if(close == 'close') {
                ord.closeItemSearchPop();
            }
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

                ord.selectItemInfoChart(ord.srtnCd)
            })
        },
        selectItemInfoChart: (srtnCd, type) => {
            var ctx = document.getElementById('candleChart');
            var chart = Chart.getChart(ctx); // 기존 차트 가져오기
            if (!chart) {
                chart = new Chart(ctx, {
                    type: 'candlestick',
                    data: {
                        datasets: [
                            {
                                label: "price",
                                data: [],  // 초기에는 빈 배열로 설정
                                backgroundColors: {
                                    up: '#01ff01',
                                    down: '#fe0000',
                                    unchanged: '#999',
                                }
                            }, {
                                label: '10days',
                                type: 'line',
                                data: [],
                            }, {
                                label: '20days',
                                type: 'line',
                                data: [],
                            }, {
                                label: '30days',
                                type: 'line',
                                data: [],
                            }
                        ]
                    }
                });
            }

            let param = {
                pdno: srtnCd,
                dwmy: type || ord.dwmy
            }

            callModule.call(Util.getRequestUrl("/mng/trd/ord/selectItemInfoChart.do"), param, (result) => {
                chart.data.datasets[0].data = [];
                for (var i = 0; i < result.stockOrderVO.res.output2.length; i++) {
                    chart.data.datasets[0].data.push({
                        x: luxon.DateTime.fromRFC2822(ord.UTCtime(result.stockOrderVO.res.output2[i].stck_bsop_date)).valueOf(),
                        o: result.stockOrderVO.res.output2[i].stck_oprc,
                        h: result.stockOrderVO.res.output2[i].stck_hgpr,
                        l: result.stockOrderVO.res.output2[i].stck_lwpr,
                        c: result.stockOrderVO.res.output2[i].stck_clpr
                    });
                }

                chart.data.datasets[1].data = [];
                for (var i = 0; i <= result.stockOrderVO.res.output2.length - 10; i += 10) {
                    var sum = 0;
                    for (var j = 0; j < 10; j++) {
                        sum += parseInt(result.stockOrderVO.res.output2[i + j].stck_clpr);
                    }
                    var average = sum / 10;
                    chart.data.datasets[1].data.push({
                        x: luxon.DateTime.fromRFC2822(ord.UTCtime(result.stockOrderVO.res.output2[i].stck_bsop_date)).valueOf(),
                        y: Math.floor(average)
                    });
                }

                chart.data.datasets[2].data = [];
                for (var i = 0; i <= result.stockOrderVO.res.output2.length - 20; i += 20) {
                    var sum = 0;
                    for (var j = 0; j < 20; j++) {
                        sum += parseInt(result.stockOrderVO.res.output2[i + j].stck_clpr);
                    }
                    var average = sum / 20;
                    chart.data.datasets[2].data.push({
                        x: luxon.DateTime.fromRFC2822(ord.UTCtime(result.stockOrderVO.res.output2[i].stck_bsop_date)).valueOf(),
                        y: Math.floor(average)
                    });
                }

                chart.data.datasets[3].data = [];
                for (var i = 0; i <= result.stockOrderVO.res.output2.length - 30; i += 30) {
                    var sum = 0;
                    for (var j = 0; j < 30; j++) {
                        sum += parseInt(result.stockOrderVO.res.output2[i + j].stck_clpr);
                    }
                    var average = sum / 30;
                    chart.data.datasets[3].data.push({
                        x: luxon.DateTime.fromRFC2822(ord.UTCtime(result.stockOrderVO.res.output2[i].stck_bsop_date)).valueOf(),
                        y: Math.floor(average)
                    });
                }
                chart.update(); // 차트 업데이트
            });
        },
        calculateAverage : (data, daysPerGroup) => {
            var averageValues = [];

            // 데이터를 daysPerGroup 단위로 묶어 평균값을 계산
            for (var i = 0; i <= data.length - daysPerGroup; i += daysPerGroup) {
                var sum = 0;
                for (var j = i; j < i + daysPerGroup; j++) {
                    sum += data[j].stck_clpr;
                }
                var average = sum / daysPerGroup;
                averageValues.push(average);
            }

            return averageValues;
        },
        // 시장가 / 지정가
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
        // 계좌조회
        selectDomesticAccount : () => {
            let param = {account : ord.account}
            callModule.call(Util.getRequestUrl("/mng/trd/ord/selectDomesticAccount.do"), param, (result) => {

                var tbody = document.querySelector("#tbody1");

                var html = `<tr>
                                <td colspan="7" style="align-items: center;"><span>there is no holding shares</span></td>
                            </tr>`;
                $("#tbody1").append(html);

                // holoding shares
                if(result.stockOrderVO.res.output1.length != 0) {
                    while (tbody.firstChild) {
                        tbody.removeChild(tbody.firstChild);
                    }
                    for(var i = 0; i < result.stockOrderVO.res.output1.length; i++) {

                        if(result.stockOrderVO.res.output1[i].hldg_qty != "0") {
                            var html = `<tr onclick="ord.selectItemInfo('\${result.stockOrderVO.res.output1[i].prdt_name}', '\${result.stockOrderVO.res.output1[i].pdno}', 'close')">
                                            <td><span>\${result.stockOrderVO.res.output1[i].prdt_name}</span> <span>(</span> <span>\${result.stockOrderVO.res.output1[i].pdno}</span> <span>)</span></td>
                                            <td><span>\${Math.floor(result.stockOrderVO.res.output1[i].pchs_avg_pric)}</span></td>
                                            <td><span>\${result.stockOrderVO.res.output1[i].ord_psbl_qty}</span></td>
                                            <td><span>\${result.stockOrderVO.res.output1[i].pchs_amt}</span></td>
                                            <td><span>\${result.stockOrderVO.res.output1[i].evlu_pfls_amt}</span>&nbsp;<span>(won)</span></td>
                                            <td><span>\${result.stockOrderVO.res.output1[i].evlu_pfls_rt}</span>&nbsp;<span>%</span></td>
                                            <td>
                                                <div class="btn__box">
                                                    <button class="btn__blue" onclick="ord.sellDomesticStock('market', '\${result.stockOrderVO.res.output1[i].pdno}', '\${result.stockOrderVO.res.output1[i].ord_psbl_qty}')">Market</button>
                                                </div>
                                            </td>
                                        </tr>`;
                        }
                        $("#tbody1").append(html);
                    }
                }

                // cash
                $("#pchsAmtSmtlAmt").text(result.stockOrderVO.res.output2[0].pchs_amt_smtl_amt.toLocaleString());
                $("#evluAmtSmtlAmt").text(result.stockOrderVO.res.output2[0].evlu_amt_smtl_amt.toLocaleString());
                $("#evluPflsSmtlAmt").text(result.stockOrderVO.res.output2[0].evlu_pfls_smtl_amt.toLocaleString());
                $("#dncaTotAmt").text(result.stockOrderVO.res.output2[0].prvs_rcdl_excc_amt.toLocaleString());

                ord.selectStockOrderList();
            })
        },
        // 주문내역
        selectStockOrderList : () => {
            let param = {account : ord.account}
            callModule.call(Util.getRequestUrl("/mng/trd/ord/selectStockOrderList.do"), param, (result) => {

                var tbody = document.querySelector("#tbody2");

                while (tbody.firstChild) {
                    tbody.removeChild(tbody.firstChild);
                }

                if(result.stockOrderVO.res.output.length != 0) {
                    for(var i = 0; i < result.stockOrderVO.res.output.length; i++) {
                        var html = `<tr>
                                    <td><span>\${result.stockOrderVO.res.output[i].rvse_cncl_dvsn_name}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output[i].odno}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output[i].pdno}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output[i].ord_unpr}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output[i].ord_qty}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output[i].ord_tmd}</span></td>
                                    <td><span>\${result.stockOrderVO.res.output[i].tot_ccld_qty}</span></td>
                                    <td style="display: flex; justify-content: center;">
                                        <div>
                                            <button class="btn__blue" onclick="ord.cancleOrder('\${result.stockOrderVO.res.output[i].ord_gno_brno}', '\${result.stockOrderVO.res.output[i].odno}', '\${result.stockOrderVO.res.output[i].ord_dvsn_cd}')">Cancle</button>
                                        </div>
                                    </td>
                                </tr>`;

                        $("#tbody2").append(html);
                    }
                }if(result.stockOrderVO.res.output.length == 0) {

                    var html = `<tr>
                                    <td colspan="8" style="text-align: center;"><span>There is no order!</span></td>
                                </tr>`;

                    $("#tbody2").append(html);
                }
            })
        },
        // 취소
        cancleOrder : (ordGnoBrno ,odno, ordDvsnCd) => {
           let param = {
               account : ord.account,
               ordGnoBrno : ordGnoBrno,
               odno : odno,
               ordDvsnCd : ordDvsnCd

           }
           callModule.call(Util.getRequestUrl("/mng/trd/ord/cancleOrder.do"), param, (result) => {
               MessageUtil.alert(result.stockOrderVO.res.msg1, () => {
                   ord.selectDomesticAccount();
               })
           })
        },
        // 매수
        buyDomesticStock : () => {
            let param = {
                account : ord.account, // 계좌번호
                pdno : ord.srtnCd, // 종목번호
                ordDvsn : ord.ordDvsn, // ordDvsn, // 시장가 / 지정가
                ordUnpr : $("#price").val(), // 주문가격
                ordQty : $("#amount").val() // 주문수량
            }
            callModule.call(Util.getRequestUrl("/mng/trd/ord/buyDomesticStock.do"), param, (result) => {
                MessageUtil.alert(result.stockOrderVO.res.msg1, () => {
                    ord.selectDomesticAccount();
                })
            })
        },
        // 매도
        sellDomesticStock : (type, pdno, ordQty) => {
            let param = {
                account : ord.account, // 계좌번호
                pdno : ord.srtnCd, // 종목번호
                ordDvsn : ord.ordDvsn, // ordDvsn, // 시장가 / 지정가
                ordUnpr : $("#price").val(), // 주문가격
                ordQty : $("#amount").val() // 주문수량
            }

            if(type == 'market') {
                param = {
                    account : ord.account, // 계좌번호
                    pdno : pdno, // 종목번호
                    ordDvsn : "01", // ordDvsn, // 시장가 / 지정가
                    ordUnpr : "0", // 주문가격
                    ordQty : ordQty // 주문수량
                }
            }

            callModule.call(Util.getRequestUrl("/mng/trd/ord/sellDomesticStock.do"), param, (result) => {
                MessageUtil.alert(result.stockOrderVO.res.msg1, () => {
                    ord.selectDomesticAccount();
                })
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
                <div style="display: flex; justify-content: right;">
                    <div class="btn__box">
                        <button class="btn__black__line" onclick="ord.selectItemInfoChart(ord.srtnCd, 'D')">Day</button>
                        &nbsp;&nbsp;
                        <button class="btn__black__line" onclick="ord.selectItemInfoChart(ord.srtnCd, 'W')">Week</button>
                        &nbsp;&nbsp;
                        <button class="btn__black__line" onclick="ord.selectItemInfoChart(ord.srtnCd, 'M')">Month</button>
                        &nbsp;&nbsp;
                        <button class="btn__black__line" onclick="ord.selectItemInfoChart(ord.srtnCd, 'Y')">Year</button>
                    </div>
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
                    <button class="btn__red" onclick="ord.buyDomesticStock();">Buy</button>
                </div>
                <div class="btn__box">
                    <button class="btn__blue" onclick="ord.sellDomesticStock();">Sell</button>
                </div>
            </div>
        </div>
    </div>
</div>
<br>
<br>
<div>
    <div class="table-box">
        <table>
            <caption class="hidden">정정/취소 목록</caption>
            <colgroup>
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
                <col class="col-num" width="12.5%" />
            </colgroup>
            <thead>
                <tr>
                    <th>Order Type</th>
                    <th>Order NO.</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Amount</th>
                    <th>Time</th>
                    <th>Completed Amount</th>
                    <th>Cancle</th>
                </tr>
            </thead>
            <tbody id="tbody2"></tbody>
        </table>
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