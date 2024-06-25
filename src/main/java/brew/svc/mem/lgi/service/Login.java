package brew.svc.mem.lgi.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    private List<LoginVO> loginVOList;
    private LoginVO loginVO;
}
