package brew.mng.trd.iim.service.impl;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewDateUtil;
import brew.cmm.util.BrewHttpUtil;
import brew.mng.trd.iim.service.ItemInfo;
import brew.mng.trd.iim.service.ItemInfoService;
import brew.mng.trd.iim.service.ItemInfoVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service("ItemInfoService")
@RequiredArgsConstructor
public class ItemInfoServiceImpl implements ItemInfoService {

    private final BrewHttpUtil brewHttpUtil;
    private final BrewDateUtil brewDateUtil;
    private final ItemInfoDAO itemInfoDAO;

    @Transactional
    @Override
    public ItemInfo insertItemInfoBatch(ItemInfoVO vo) throws JSONException, IOException {

        itemInfoDAO.updateTruncateItemInfo(vo);

        String url = "https://apis.data.go.kr/1160100/service/GetKrxListedInfoService/getItemInfo";

        Map<String, String> headers = new HashMap<>();

        Map<String, String> params = new HashMap<>();
        params.put("serviceKey", BrewProperties.getProperty("krx.api.key"));
        params.put("numOfRows", "3000");
        params.put("resultType", "json");
        params.put("likeBasDt", brewDateUtil.getYesterDate());

        StringBuilder response = brewHttpUtil.getHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode result = mapper.readTree(String.valueOf(response));

        int count = 0;

        JsonNode items = result.get("response").get("body").get("items").get("item");

        for (JsonNode item : items) {
            ItemInfoVO param = new ItemInfoVO();
            param.setBasDt(item.get("basDt").asText());

            String srtnCd = item.get("srtnCd").asText();
            if (srtnCd.length() > 1) {
                srtnCd = srtnCd.substring(1); // 첫 번째 'A' 문자 제거
            }

            param.setSrtnCd(srtnCd);
            param.setIsinCd(item.get("isinCd").asText());
            param.setMrktCtg(item.get("mrktCtg").asText());
            param.setItmsNm(item.get("itmsNm").asText());
            param.setCrno(item.get("crno").asText());
            param.setCorpNm(item.get("corpNm").asText());

            itemInfoDAO.insertItemInfoBatch(param);
            count++;
        }

        if(count == result.get("response").get("body").get("items").get("item").size()) {
            vo.setResultMessage("배치가 정상적으로 실행되었습니다.");
        }else {
            vo.setResultMessage("실행된 배치가 실패하였습니다.");
        }

        return ItemInfo.builder()
                .itemInfoVO(vo)
                .build();
    }
}
