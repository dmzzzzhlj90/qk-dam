package com.qk.dm.dataquality.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDqcSchedulerBasicInfo is a Querydsl query type for DqcSchedulerBasicInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDqcSchedulerBasicInfo extends EntityPathBase<DqcSchedulerBasicInfo> {

    private static final long serialVersionUID = 801810147L;

    public static final QDqcSchedulerBasicInfo dqcSchedulerBasicInfo = new QDqcSchedulerBasicInfo("dqcSchedulerBasicInfo");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath dirId = createString("dirId");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobId = createString("jobId");

    public final StringPath jobName = createString("jobName");

    public final StringPath notifyLevel = createString("notifyLevel");

    public final StringPath notifyState = createString("notifyState");

    public final StringPath notifyThemeId = createString("notifyThemeId");

    public final StringPath notifyType = createString("notifyType");

    public final NumberPath<Long> processDefinitionCode = createNumber("processDefinitionCode", Long.class);

    public final NumberPath<Integer> runInstanceState = createNumber("runInstanceState", Integer.class);

    public final StringPath schedulerState = createString("schedulerState");

    public final StringPath updateUserid = createString("updateUserid");

    public QDqcSchedulerBasicInfo(String variable) {
        super(DqcSchedulerBasicInfo.class, forVariable(variable));
    }

    public QDqcSchedulerBasicInfo(Path<? extends DqcSchedulerBasicInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDqcSchedulerBasicInfo(PathMetadata metadata) {
        super(DqcSchedulerBasicInfo.class, metadata);
    }

}

