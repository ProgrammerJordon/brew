package brew.mng.trd.ord.web;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewHttpUtil;
import brew.mng.trd.ord.service.StockOrder;
import brew.mng.trd.ord.service.StockOrderService;
import brew.mng.trd.ord.service.StockOrderVO;
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
        params.put("PRCS_DVSN", "00");
        params.put("CTX_AREA_FK100", "");
        params.put("CTX_AREA_NK100", "");

        StringBuilder res = brewHttpUtil.getHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(String.valueOf(res));

        vo.setRes(result);

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/buyDomesticStock.do")
    @ResponseBody
    public StockOrder buyDomesticStock(@RequestBody StockOrderVO vo) {

        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/order-cash";

        java.util.Map<String, String> headers = new HashMap<>();

        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("custtype", "P"); // B : 법인 / P : 개인
        headers.put("tr_id", "TTTC0802U");

        Map<String, String> params = new HashMap<>();

        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("PDNO", vo.getPdno());
        params.put("ORD_DVSN", vo.getOrdDvsn()); // 00 : 지정가 / 01 : 시장가
        params.put("ORD_QTY", vo.getOrdQty());
        params.put("ORD_UNPR", vo.getOrdUnpr()); // 1주당 가격을 공란으로 비우지 않음 "0"으로 입력 권고

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }

    @RequestMapping("/sellDomesticStock.do")
    @ResponseBody
    public StockOrder sellDomesticStock(@RequestBody StockOrderVO vo) {

        String url = BrewProperties.getProperty("kis.dev.url") + "/uapi/domestic-stock/v1/trading/order-cash";

        java.util.Map<String, String> headers = new HashMap<>();

        headers.put("authorization", BrewProperties.getProperty("kis.dev.accessToken"));
        headers.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        headers.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));
        headers.put("content-type", "application/json; charset=utf-8");
        headers.put("custtype", "P"); // B : 법인 / P : 개인
        headers.put("tr_id", "TTTC0801U");

        Map<String, String> params = new HashMap<>();

        params.put("CANO", vo.getAccount());
        params.put("ACNT_PRDT_CD", "01");
        params.put("PDNO", vo.getPdno());
        params.put("ORD_DVSN", vo.getOrdDvsn()); // 00 : 지정가 / 01 : 시장가
        params.put("ORD_DVSN", vo.getOrdDvsn());
        params.put("ORD_QTY", vo.getOrdQty());
        params.put("ORD_UNPR", vo.getOrdUnpr()); // 1주당 가격을 공란으로 비우지 않음 "0"으로 입력 권고

        return StockOrder.builder()
                .stockOrderVO(vo)
                .build();
    }
}
