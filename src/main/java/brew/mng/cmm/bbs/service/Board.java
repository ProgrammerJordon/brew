package brew.mng.cmm.bbs.service;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private List<BoardVO> boardVOList;
    private BoardVO boardVO;
}
