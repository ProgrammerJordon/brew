package brew.brew_common.cmm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Common {

    /**
     * 로그인 유저정보
     */
    private Login loginUser;
    /**
     * 등록 유저 번호
     */
    private String createdBy;
    /**
     * 최종수정 유저 번호
     */
    private String lastModifiedBy;
    /**
     * 등록일시
     */
    private String createdDate;
    /**
     * 최종수정일시
     */
    private String lastModifiedDate;
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
    /**
     * 탭인덱스
     */
    @Builder.Default
    private int tabIndex = 0;
    /**
     * 수정가능여부
     */
    @Builder.Default
    private String enableUpdateYn = "Y";

    private BigDecimal bigDecimal;
    private String seachListYn;

}
