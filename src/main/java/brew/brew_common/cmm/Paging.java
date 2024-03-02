package brew.brew_common.cmm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Paging {

    /**
     * 현재 페이지
     */
    @Builder.Default
    private int pageIndex = 1;
    /**
     * 페이지 시작
     */
    @Builder.Default
    private int pageStart = 0;
    /**
     * 페이지 종료
     */
    @Builder.Default
    private int pageEnd = 0;
    /**
     * 전체 페이지 수
     */
    @Builder.Default
    private int totalPages = 0;
    /**
     * 전체 레코드 수
     */
    @Builder.Default
    private int totalRowCount = 0;
    /**
     * 페이징 UI에 표시할 페이지수
     */
    @Builder.Default
    private int rowCountPerPage = 10;

}
