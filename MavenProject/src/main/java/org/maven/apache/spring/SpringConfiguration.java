package org.maven.apache.spring;

import org.maven.apache.spring.aspect.MapperAspect;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@Import(MyBatisAutoConfiguration.class)
public class SpringConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MapperAspect mapperAspect(){
        return new MapperAspect();
    }

}
