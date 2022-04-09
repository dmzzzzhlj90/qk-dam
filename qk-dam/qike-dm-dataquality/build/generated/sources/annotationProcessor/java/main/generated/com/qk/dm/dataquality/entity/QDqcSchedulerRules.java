package com.qk.dm.dataquality.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDqcSchedulerRules is a Querydsl query type for DqcSchedulerRules
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDqcSchedulerRules extends EntityPathBase<DqcSchedulerRules> {

    private static final long serialVersionUID = 1198429534L;

    public static final QDqcSchedulerRules dqcSchedulerRules = new QDqcSchedulerRules("dqcSchedulerRules");

    public final StringPath createUserid = createString("createUserid");

    public final StringPath databaseName = createString("databaseName");

    public final StringPath dataSourceName = createString("dataSourceName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath engineType = createString("engineType");

    public final StringPath executeSql = createString("executeSql");

    public final StringPath fields = createString("fields");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobId = createString("jobId");

    public final StringPath ruleId = createString("ruleId");

    public final StringPath ruleName = createString("ruleName");

    public final NumberPath<Long> ruleTempId = createNumber("ruleTempId", Long.class);

    public final StringPath ruleType = createString("ruleType");

    public final StringPath scanSql = createString("scanSql");

    public final StringPath scanType = createString("scanType");

    public final StringPath tables = createString("tables");

    public final NumberPath<Long> taskCode = createNumber("taskCode", Long.class);

    public final StringPath updateUserid = createString("updateUserid");

    public final StringPath warnExpression = createString("warnExpression");

    public QDqcSchedulerRules(String variable) {
        super(DqcSchedulerRules.class, forVariable(variable));
    }

    public QDqcSchedulerRules(Path<? extends DqcSchedulerRules> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDqcSchedulerRules(PathMetadata metadata) {
        super(DqcSchedulerRules.class, metadata);
    }

}

