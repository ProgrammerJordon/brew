<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const ord = {

        account : `${account}`,
        ordDvsn : "00",

        init : () => {
            ord.selectDomesticAccount();
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
    <div style="display: flex; justify-content: space-between;">
        <div style="width: 65%">
            <div>
                <div style="font-size: 1.5em;">
                    <span id="">XXXXX</span>
                    <span>(</span>
                    <span id="">012345</span>
                    <span>)</span>
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
                        <col class="num" style="width: 20%">
                        <col class="num" style="width: 20%">
                        <col class="num" style="width: 10%">
                        <col class="num" style="width: 10%">
                        <col class="num" style="width: 20%">
                        <col class="num" style="width: 10%">
                        <col class="num" style="width: 10%">
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
                            <th>Name</th>
                            <th>Ticker</th>
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