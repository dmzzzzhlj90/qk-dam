package com.qk.dm.dataquality.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class JPAQueryFactoryConf {
    @Bean
    public JPAQueryFactory jpaQueryFactory(final EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}
