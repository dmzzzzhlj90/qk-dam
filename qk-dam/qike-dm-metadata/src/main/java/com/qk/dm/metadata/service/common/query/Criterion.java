package com.qk.dm.metadata.service.common.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
public interface Criterion {

     enum Operator {
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR
    }

     Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder);
}
