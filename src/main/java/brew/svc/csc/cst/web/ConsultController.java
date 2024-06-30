package brew.svc.csc.cst.web;

import brew.svc.csc.cst.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/svc/csc/cts")
@RequiredArgsConstructor
public class ConsultController {

    private final ConsultService consultService;

}
