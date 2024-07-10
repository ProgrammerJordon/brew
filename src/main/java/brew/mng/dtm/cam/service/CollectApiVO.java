package brew.mng.dtm.cam.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class CollectApiVO  extends CommonVO implements Serializable {

    private String protocol;
    private String method;
    private String url;
    private Object headers;
    private Object params;
    private String res;
}
