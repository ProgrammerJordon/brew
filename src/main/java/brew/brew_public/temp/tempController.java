package brew.brew_public.temp;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class tempController {

    @GetMapping("/api/temp")
    public String temp() {
       return "JSON VIEW";
    }

    @GetMapping("/api/data")
    public List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("Test 1");
        data.add("Test 2");
        data.add("Test 3");
        return data;
    }

    @GetMapping("/nowij")
    public String Nowij() {
        return "Spring Boot and React 연동 테스트 \n";
    }
}
