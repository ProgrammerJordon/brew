package brew.mng.trd.iim.service;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface ItemInfoService {

    public ItemInfo selectItemInfoList(ItemInfoVO vo);
    public ItemInfo insertItemInfoBatch(ItemInfoVO vo) throws JSONException, IOException;
}
