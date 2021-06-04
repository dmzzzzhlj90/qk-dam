package com.qk.dam.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author daomingzhu
 * @date 2021/06/02
 * 基础通用JPA配置类
 */
public interface BaseRepository<T,ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {

}
