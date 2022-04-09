package com.qk.dm.dataquality.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDqcRuleTemplate is a Querydsl query type for DqcRuleTemplate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDqcRuleTemplate extends EntityPathBase<DqcRuleTemplate> {

    private static final long serialVersionUID = -784827272L;

    public static final QDqcRuleTemplate dqcRuleTemplate = new QDqcRuleTemplate("dqcRuleTemplate");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dimensionType = createString("dimensionType");

    public final StringPath dirId = createString("dirId");

    public final StringPath engineType = createString("engineType");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath publishState = createString("publishState");

    public final StringPath ruleType = createString("ruleType");

    public final StringPath tempName = createString("tempName");

    public final StringPath tempResult = createString("tempResult");

    public final StringPath tempSql = createString("tempSql");

    public final StringPath tempType = createString("tempType");

    public final StringPath updateUserid = createString("updateUserid");

    public QDqcRuleTemplate(String variable) {
        super(DqcRuleTemplate.class, forVariable(variable));
    }

    public QDqcRuleTemplate(Path<? extends DqcRuleTemplate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDqcRuleTemplate(PathMetadata metadata) {
        super(DqcRuleTemplate.class, metadata);
    }

}

