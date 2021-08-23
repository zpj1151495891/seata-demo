package com.example.testorderservice.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        log.info("传递：{}",RootContext.getXID());
        template.header(RootContext.KEY_XID,RootContext.getXID());
    }
}
