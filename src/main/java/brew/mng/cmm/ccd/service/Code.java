package brew.mng.cmm.ccd.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Code {

    private List<CodeVO> codeVOList;
    private CodeVO codeVO;
}
