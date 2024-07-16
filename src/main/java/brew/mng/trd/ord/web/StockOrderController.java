package brew.mng.trd.ord.web;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewDateUtil;
import brew.cmm.util.BrewHttpUtil;
import brew.mng.trd.ord.service.StockOrder;
import brew.mng.trd.ord.service.StockOrderService;
import brew.mng.trd.ord.service.StockOrderVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mng/trd/ord")
@RequiredArgsConstructor
public class StockOrderController {

    private final BrewHttpUtil brewHttpUtil;
    private final BrewDateUtil brewDateUtil;
    private final StockOrderService stockOrderService;

    @RequestMapping("/selectStockOrderListVw.do")
    public String selectStockOrderListVw(Model model) {
        model.addAttribute("account", BrewProperties.getProperty("kis.dev.account"));
        return "/mng/trd/ord/selectStockOrderListVw";
    }

    @RequestMapping("/selectDomesticAccount.do")
    @ResponseBody
    public StockOrder selectAccount(@RequestBody StockOrderVO vo) throws JSONException, IOException {
        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/inquire-balance";

        java.util.Map<String, String> headers = new HashMap<>();

        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("custtype", "P"); // B : 법인 / P : 개인
        headers.put("tr_id", "TTTC8434R");

        Map<String, String> params = new HashMap<>();

        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("AFHR_FLPR_YN", "N");
        params.put("OFL_YN", "");
        params.put("INQR_DVSN", "02");
        params.put("UNPR_DVSN", "01");
        params.put("FUND_STTL_ICLD_YN", "N");
        params.put("FNCG_AMT_AUTO_RDPT_YN", "N");
        params.put("PRCS_DVSN", "01");
        params.put("CTX_AREA_FK100", "");
        params.put("CTX_AREA_NK100", "");

        StringBuilder response = brewHttpUtil.getHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(response));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/buyDomesticStock.do")
    @ResponseBody
    public StockOrder buyDomesticStock(@RequestBody StockOrderVO vo) throws JSONException, IOException {

        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/order-cash";

        java.util.Map<String, String> headers = new HashMap<>();

        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("custtype", "P"); // B : 법인 / P : 개인
        headers.put("tr_id", "TTTC0802U"); // 현금주문

        Map<String, String> params = new HashMap<>();

        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("PDNO", vo.getPdno());
        params.put("ORD_DVSN", vo.getOrdDvsn()); // 00 : 지정가 / 01 : 시장가
        params.put("ORD_QTY", vo.getOrdQty());

        if("01".equals(vo.getOrdDvsn())) {
            params.put("ORD_UNPR", "0");
        }else if ("00".equals(vo.getOrdDvsn())) {
            params.put("ORD_UNPR", vo.getOrdUnpr());
        }
        // 1주당 가격을 공란으로 비우지 않음 "0"으로 입력 권고

        StringBuilder response = brewHttpUtil.postHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(response));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/sellDomesticStock.do")
    @ResponseBody
    public StockOrder sellDomesticStock(@RequestBody StockOrderVO vo) throws JSONException, IOException {

        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/order-cash";

        java.util.Map<String, String> headers = new HashMap<>();

        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("custtype", "P"); // B : 법인 / P : 개인
        headers.put("tr_id", "TTTC0801U"); // 현금매도

        Map<String, String> params = new HashMap<>();

        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("PDNO", vo.getPdno());
        params.put("ORD_DVSN", vo.getOrdDvsn()); // 00 : 지정가 / 01 : 시장가
        params.put("ORD_QTY", vo.getOrdQty());

        if("01".equals(vo.getOrdDvsn())) {
            params.put("ORD_UNPR", "0");
        }else if ("00".equals(vo.getOrdDvsn())) {
            params.put("ORD_UNPR", vo.getOrdUnpr());
        }
        // 1주당 가격을 공란으로 비우지 않음 "0"으로 입력 권고

        StringBuilder response = brewHttpUtil.postHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(response));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/selectStockOrderList.do")
    @ResponseBody
    public StockOrder selectStockOrderList(@RequestBody StockOrderVO vo) throws JSONException, IOException {
        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/inquire-psbl-rvsecncl";

        java.util.Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("tr_id", "TTTC8036R");

        Map<String, String> params = new HashMap<>();
        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("CTX_AREA_FK100", "");
        params.put("CTX_AREA_NK100", "");
        params.put("INQR_DVSN_1", "1"); // 조회순서 : 0 / 주문순 : 1 / 종목순 : 2
        params.put("INQR_DVSN_2", "0"); // 전체 : 0 / 매도 : 1 / 매수 : 2


        StringBuilder response = brewHttpUtil.getHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(response));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/cancleOrder.do")
    @ResponseBody
    public StockOrder cancleOrder(@RequestBody StockOrderVO vo) throws IOException, JSONException {

        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/order-rvsecncl";

        java.util.Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("tr_id", "TTTC0803U"); // 취소주문

        Map<String, String> params = new HashMap<>();
        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("KRX_FWDG_ORD_ORGNO", vo.getOrdGnoBrno());
        params.put("ORGN_ODNO", vo.getOdno());
        params.put("ORD_DVSN", vo.getOrdDvsnCd());
        params.put("RVSE_CNCL_DVSN_CD", "02");
        params.put("ORD_QTY", "0");
        params.put("ORD_UNPR", "0");
        params.put("QTY_ALL_ORD_YN", "Y");

        StringBuilder response = brewHttpUtil.postHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(response));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/selectItemInfoChart.do")
    @ResponseBody
    public StockOrder selectItemInfoChart(@RequestBody StockOrderVO vo) throws JSONException, IOException {

        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice";

        java.util.Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("tr_id", "FHKST03010100");
        headers.put("custtype", "P");

        Map<String, String> params = new HashMap<>();
        params.put("FID_COND_MRKT_DIV_CODE", "J");
        params.put("FID_INPUT_ISCD", vo.getPdno());
        params.put("FID_INPUT_DATE_1", "19900101");
        params.put("FID_INPUT_DATE_2", brewDateUtil.getNowDate());
        params.put("FID_PERIOD_DIV_CODE", vo.getDwmy());
        params.put("FID_ORG_ADJ_PRC", "0");

        StringBuilder response = brewHttpUtil.getHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(response));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }
}
