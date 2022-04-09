package com.qk.dm.dataquality.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDqcSchedulerResult is a Querydsl query type for DqcSchedulerResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDqcSchedulerResult extends EntityPathBase<DqcSchedulerResult> {

    private static final long serialVersionUID = -1517942634L;

    public static final QDqcSchedulerResult dqcSchedulerResult = new QDqcSchedulerResult("dqcSchedulerResult");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobId = createString("jobId");

    public final StringPath jobName = createString("jobName");

    public final StringPath ruleId = createString("ruleId");

    public final StringPath ruleMetaData = createString("ruleMetaData");

    public final StringPath ruleName = createString("ruleName");

    public final StringPath ruleParams = createString("ruleParams");

    public final StringPath ruleResult = createString("ruleResult");

    public final StringPath ruleTempId = createString("ruleTempId");

    public final NumberPath<Long> taskCode = createNumber("taskCode", Long.class);

    public final StringPath updateUserid = createString("updateUserid");

    public final StringPath warnResult = createString("warnResult");

    public QDqcSchedulerResult(String variable) {
        super(DqcSchedulerResult.class, forVariable(variable));
    }

    public QDqcSchedulerResult(Path<? extends DqcSchedulerResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDqcSchedulerResult(PathMetadata metadata) {
        super(DqcSchedulerResult.class, metadata);
    }

}

