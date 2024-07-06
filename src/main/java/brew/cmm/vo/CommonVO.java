package brew.cmm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonVO {

    private String frstRegistPnttm;

    private String frstRegisterId;

    private String lastUpdtPnttm;

    private String lastUpdusrId;

    private String rgtrId;

    private String rgtrDt;

    private String mdfrId;

    private String mdfrDt;

    private String bgDate;

    private String endDate;

    private String usrId;

    private String searchCondition = "";

    private String searchKeyword = "";

    private String searchUseYn = "";

    private int pageIndex = 1;

    private int pageUnit = 10;

    private int pageSize = 10;

    private int firstIndex = 1;

    private int lastIndex = 1;

    private int recordCountPerPage = 10;

    private int totalRecordCount;

    private String resultMessage = "";
}