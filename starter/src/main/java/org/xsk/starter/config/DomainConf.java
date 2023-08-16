package org.xsk.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xsk.domain.common.spring.SpringIntegration;
import org.xsk.starter.Application;

@Configuration
public class DomainConf {

    @Bean
    public SpringIntegration integration() {
        return new SpringIntegration();
    }

    @Bean
    public SpringIntegration.MainProcessCompletionSubscriberPointTrigger mainProcessCompletionSubscriberPointTrigger(SpringIntegration integration) {
        return integration.new MainProcessCompletionSubscriberPointTrigger();
    }

    @Bean
    public SpringIntegration.DomainRegistryItemInitialization domainRegistryItemInitialization(SpringIntegration integration) {
        return integration.new DomainRegistryItemInitialization(Application.BASE_PACKAGE);
    }
}
