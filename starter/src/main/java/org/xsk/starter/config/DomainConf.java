package org.xsk.starter.config;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xsk.domain.common.spring.SpringIntegration;

@Configuration
public class DomainConf {

    @Bean
    public SpringIntegration integration() {
        return new SpringIntegration(SpringUtil.getApplicationContext());
    }

    @Bean
    public SpringIntegration.MainProcessCompletionSubscriberPointTrigger mainProcessCompletionSubscriberPointTrigger(SpringIntegration integration) {
        return integration.new MainProcessCompletionSubscriberPointTrigger();
    }
}
