package brew.svc.csc.psr.service.Impl;

import brew.svc.csc.psr.service.PersonalRanking;
import brew.svc.csc.psr.service.PersonalRankingService;
import brew.svc.csc.psr.service.PersonalRankingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("PersonalRankingService")
@RequiredArgsConstructor
public class PersonalRankingServiceImpl implements PersonalRankingService {

    private PersonalRankingDAO personalRankingDAO;

    @Override
    public PersonalRanking selectPersonalRankingList(PersonalRankingVO vo) {
        return PersonalRanking.builder()
                .personalrankingvolist(personalRankingDAO.selectPersonalRankingList(vo))
                .build();
    }
}
