package brew.cmm.config;

import brew.cmm.interceptor.BrewVisitorLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import brew.cmm.interceptor.BrewAuthenticInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BrewAuthenticInterceptor())
                .addPathPatterns("/mng/**"); // 모든 요청에 대해 인터셉터 적용

        registry.addInterceptor(new BrewVisitorLogInterceptor())
                .addPathPatterns("/index.do");
    }
}
