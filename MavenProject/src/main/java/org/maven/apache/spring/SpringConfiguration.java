package org.maven.apache.spring;

import org.maven.apache.spring.aspect.PointCut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import(SpringMybatisConfig.class)
public class SpringConfiguration {

    @Bean
    public PointCut pointCut(){
        return new PointCut();
    }

}
