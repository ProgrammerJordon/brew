package brew.mng.trd.iim.service;

import org.json.JSONException;

import java.io.IOException;

public interface ItemInfoService {

    public ItemInfo insertItemInfoBatch(ItemInfoVO vo) throws JSONException, IOException;
}
