package brew.cmm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import brew.cmm.interceptor.BrewAuthenticInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BrewAuthenticInterceptor())
                .addPathPatterns("/**") // 모든 요청에 대해 인터셉터 적용
                .excludePathPatterns("/svc/**") // 예외 서비스 URL 패턴
                .excludePathPatterns("/css/**") // 예외 CSS URL 패턴
                .excludePathPatterns("/js/**") // 예외 자바스크립트 URL 패턴
                .excludePathPatterns("/plugin/**") // 예외 플러그인 URL 패턴
                .excludePathPatterns("/images/**"); // 예외 이미지 URL 패턴
    }
}
