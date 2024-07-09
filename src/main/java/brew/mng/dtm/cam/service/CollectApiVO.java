package brew.mng.dtm.cam.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class CollectApiVO  extends CommonVO implements Serializable {

    private String method;
    private String url;
    private Object headers;
    private Object params;
    private String res;
}
