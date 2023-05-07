package fpc.tools.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@RequiredArgsConstructor
public class AddSingletonToApplicationContext implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final String beanName;

    private final Object singletonObject;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().registerSingleton(beanName, singletonObject);
    }
}
