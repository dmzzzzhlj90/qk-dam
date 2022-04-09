package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiAppManageInfo is a Querydsl query type for DasApiAppManageInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiAppManageInfo extends EntityPathBase<DasApiAppManageInfo> {

    private static final long serialVersionUID = -857042106L;

    public static final QDasApiAppManageInfo dasApiAppManageInfo = new QDasApiAppManageInfo("dasApiAppManageInfo");

    public final StringPath apiGroupRoutePath = createString("apiGroupRoutePath");

    public final StringPath apiType = createString("apiType");

    public final StringPath appId = createString("appId");

    public final StringPath appName = createString("appName");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath routeId = createString("routeId");

    public final StringPath serviceId = createString("serviceId");

    public final StringPath updateUserid = createString("updateUserid");

    public final StringPath upstreamId = createString("upstreamId");

    public QDasApiAppManageInfo(String variable) {
        super(DasApiAppManageInfo.class, forVariable(variable));
    }

    public QDasApiAppManageInfo(Path<? extends DasApiAppManageInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiAppManageInfo(PathMetadata metadata) {
        super(DasApiAppManageInfo.class, metadata);
    }

}

