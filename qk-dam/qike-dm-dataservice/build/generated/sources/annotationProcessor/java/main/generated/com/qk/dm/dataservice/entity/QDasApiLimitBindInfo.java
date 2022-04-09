package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiLimitBindInfo is a Querydsl query type for DasApiLimitBindInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiLimitBindInfo extends EntityPathBase<DasApiLimitBindInfo> {

    private static final long serialVersionUID = -1451175432L;

    public static final QDasApiLimitBindInfo dasApiLimitBindInfo = new QDasApiLimitBindInfo("dasApiLimitBindInfo");

    public final StringPath apiGroupRouteName = createString("apiGroupRouteName");

    public final StringPath apiGroupRoutePath = createString("apiGroupRoutePath");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> limitId = createNumber("limitId", Long.class);

    public final StringPath routeId = createString("routeId");

    public final StringPath updateUserid = createString("updateUserid");

    public QDasApiLimitBindInfo(String variable) {
        super(DasApiLimitBindInfo.class, forVariable(variable));
    }

    public QDasApiLimitBindInfo(Path<? extends DasApiLimitBindInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiLimitBindInfo(PathMetadata metadata) {
        super(DasApiLimitBindInfo.class, metadata);
    }

}

