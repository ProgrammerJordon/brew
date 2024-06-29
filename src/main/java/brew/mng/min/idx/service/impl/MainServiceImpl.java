package brew.mng.min.idx.service.impl;

import brew.mng.min.idx.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("MainService")
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final MainDAO mainDAO;

}
