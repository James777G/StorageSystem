package org.maven.apache.spring;

import org.maven.apache.spring.aspect.PointCut;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@Import(SpringMybatisConfig.class)
public class SpringConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public PointCut pointCut(){
        return new PointCut();
    }

}
