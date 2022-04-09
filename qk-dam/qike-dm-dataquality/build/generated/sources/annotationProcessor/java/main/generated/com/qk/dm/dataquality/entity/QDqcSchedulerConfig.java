package com.qk.dm.dataquality.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDqcSchedulerConfig is a Querydsl query type for DqcSchedulerConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDqcSchedulerConfig extends EntityPathBase<DqcSchedulerConfig> {

    private static final long serialVersionUID = -1938308165L;

    public static final QDqcSchedulerConfig dqcSchedulerConfig = new QDqcSchedulerConfig("dqcSchedulerConfig");

    public final StringPath createUserid = createString("createUserid");

    public final StringPath cron = createString("cron");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> effectiveTimeEnt = createDateTime("effectiveTimeEnt", java.util.Date.class);

    public final DateTimePath<java.util.Date> effectiveTimeStart = createDateTime("effectiveTimeStart", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobId = createString("jobId");

    public final StringPath schedulerCycle = createString("schedulerCycle");

    public final NumberPath<Integer> schedulerId = createNumber("schedulerId", Integer.class);

    public final StringPath schedulerIntervalTime = createString("schedulerIntervalTime");

    public final StringPath schedulerTime = createString("schedulerTime");

    public final StringPath schedulerType = createString("schedulerType");

    public final StringPath updateUserid = createString("updateUserid");

    public QDqcSchedulerConfig(String variable) {
        super(DqcSchedulerConfig.class, forVariable(variable));
    }

    public QDqcSchedulerConfig(Path<? extends DqcSchedulerConfig> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDqcSchedulerConfig(PathMetadata metadata) {
        super(DqcSchedulerConfig.class, metadata);
    }

}

