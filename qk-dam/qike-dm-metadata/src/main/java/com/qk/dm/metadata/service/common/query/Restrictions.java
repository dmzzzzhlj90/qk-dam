package com.qk.dm.metadata.service.common.query;

import java.util.Collection;
import java.util.Objects;

/**
 * @author wangzp
 * @date 2021/7/31 15:26
 * @since 1.0.0
 *
 */
public class Restrictions {
    /**
     * 等于
     *
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression eq(String fieldName, Object value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.EQ);
    }

    /**
     * 不等于
     *
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression ne(String fieldName, Object value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.NE);
    }

    /**
     * 模糊匹配
     *
     * @param fieldName
     * @param value
     * @param
     * @return
     */
    public static SimpleExpression like(String fieldName, String value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.LIKE);
    }


    /**
     * 大于
     *
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression gt(String fieldName, Object value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.GT);
    }

    /**
     * 小于
     *
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression lt(String fieldName, Object value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.LT);
    }

    /**
     * 大于等于
     *
     * @param fieldName
     * @param value
     * @return
     */
    public static SimpleExpression lte(String fieldName, Object value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.LTE);
    }

    /**
     * 小于等于
     *
     * @param fieldName
     * @param value
     * @param
     * @return
     */
    public static SimpleExpression gte(String fieldName, Object value) {
        if (Objects.isNull(value)) return null;
        return new SimpleExpression(fieldName, value, Criterion.Operator.GTE);
    }


    /**
     * 并且
     *
     * @param criterions
     * @return
     */
    public static LogicalExpression and(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.AND);
    }

    /**
     * 或者
     *
     * @param criterions
     * @return
     */
    public static LogicalExpression or(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.OR);
    }

    /**
     * 包含于
     *
     * @param fieldName
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Criterion.Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.OR);
    }

    /**
     * 不包含于
     *
     * @param fieldName
     * @param value
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression notin(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Criterion.Operator.NE);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.AND);
    }

    /**
     * between
     *
     * @param fieldName
     * @param object1
     * @param object2
     * @return
     */
    public static LogicalExpression between(String fieldName, Object object1, Object object2) {
        if (Objects.isNull(object1)|| Objects.isNull(object2)) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[2];
        ses[0] = new SimpleExpression(fieldName, object1, Criterion.Operator.GTE);
        ses[1] = new SimpleExpression(fieldName, object2, Criterion.Operator.LTE);
        return new LogicalExpression(ses, Criterion.Operator.AND);
    }
}
