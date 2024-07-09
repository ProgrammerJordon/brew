package brew.mng.dtm.cam.service;

import org.json.JSONException;

import java.io.IOException;

public interface CollectApiService {

    public CollectApi selectCollectApiResponse(CollectApiVO vo) throws JSONException, IOException;
}
