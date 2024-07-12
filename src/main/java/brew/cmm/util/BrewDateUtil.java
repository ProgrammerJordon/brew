package brew.cmm.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class BrewDateUtil {

    public String getNowDate() {
        LocalDate today = LocalDate.now();

        // 원하는 형식 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 오늘의 날짜를 원하는 형식으로 변환
        String formattedDate = today.format(formatter);

        return formattedDate;
    }

    public String getYesterDate() {
        LocalDate today = LocalDate.now();

        // 하루를 뺀 날짜 얻기
        LocalDate yesterday = today.minusDays(1);

        // 원하는 형식 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 어제의 날짜를 원하는 형식으로 변환
        String formattedDate = yesterday.format(formatter);

        return formattedDate;
    }
}
