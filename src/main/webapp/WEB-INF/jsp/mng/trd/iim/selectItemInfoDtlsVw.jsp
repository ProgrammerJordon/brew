<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template_top.jspf" %>

<script>
    const iim  = {
        srtnCd : '${srtnCd}',
        init : () => {
            iim.selectItemInfoDtls();
        },
        selectItemInfoListVw : () => {
            let param = {}
            callModule.post(Util.getRequestUrl("/mng/trd/iim/selectItemInfoListVw.do"), param, 'post');
        },

        selectItemInfoDtls : () => {
            let param = {srtnCd : iim.srtnCd}
            callModule.call(Util.getRequestUrl("/mng/trd/iim/selectItemInfoDtls.do"), param, (result) => {

                //시장구분
                var marketCode = result.itemInfoVO.res.output.mket_id_cd;
                var displayText = "";

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
        }
    }

    $(() => {
        iim.init();
        mmm.selectMenu('trd')
    })
</script>

<div>
    <div class="table-box">
        <table>
            <caption class="hidden">게시판 상세조회 화면</caption>
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
    <div class="table-box">
        <table>
            <caption class="hidden">재무재표</caption>
            <colgroup>
                <col class="num" width="">
            </colgroup>
            <thead></thead>
            <tbody></tbody>
        </table>
    </div>
    <div class="btn">
        <div class="btn__box">
            <div class="left"></div>
            <div class="right">
                <button class="btn__gray" onclick="iim.selectItemInfoListVw();">
                    <span>목록</span>
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/jspf/tiles/mng/template-bottom.jspf" %>