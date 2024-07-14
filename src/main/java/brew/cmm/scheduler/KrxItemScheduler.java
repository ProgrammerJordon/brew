package brew.cmm.scheduler;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewDateUtil;
import brew.cmm.util.BrewHttpUtil;
import brew.mng.trd.iim.service.ItemInfoService;
import brew.mng.trd.iim.service.ItemInfoVO;
import brew.mng.trd.iim.service.impl.ItemInfoDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KrxItemScheduler {

    private final ItemInfoService itemInfoService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void getItemInfoScheduler() throws JSONException, IOException {

        ItemInfoVO vo = new ItemInfoVO();
        itemInfoService.insertItemInfoBatch(vo);

    }
}
