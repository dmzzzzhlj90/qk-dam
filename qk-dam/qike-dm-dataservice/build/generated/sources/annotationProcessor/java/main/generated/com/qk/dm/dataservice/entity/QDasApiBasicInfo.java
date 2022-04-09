package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiBasicInfo is a Querydsl query type for DasApiBasicInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiBasicInfo extends EntityPathBase<DasApiBasicInfo> {

    private static final long serialVersionUID = 1636857230L;

    public static final QDasApiBasicInfo dasApiBasicInfo = new QDasApiBasicInfo("dasApiBasicInfo");

    public final StringPath apiId = createString("apiId");

    public final StringPath apiName = createString("apiName");

    public final StringPath apiPath = createString("apiPath");

    public final StringPath apiType = createString("apiType");

    public final StringPath createUserid = createString("createUserid");

    public final StringPath defInputParam = createString("defInputParam");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dirId = createString("dirId");

    public final StringPath dirName = createString("dirName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath protocolType = createString("protocolType");

    public final StringPath requestType = createString("requestType");

    public final StringPath status = createString("status");

    public final StringPath updateUserid = createString("updateUserid");

    public QDasApiBasicInfo(String variable) {
        super(DasApiBasicInfo.class, forVariable(variable));
    }

    public QDasApiBasicInfo(Path<? extends DasApiBasicInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiBasicInfo(PathMetadata metadata) {
        super(DasApiBasicInfo.class, metadata);
    }

}

