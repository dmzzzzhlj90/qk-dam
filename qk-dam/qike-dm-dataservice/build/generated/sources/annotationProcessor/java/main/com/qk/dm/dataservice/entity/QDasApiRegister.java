package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiRegister is a Querydsl query type for DasApiRegister
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiRegister extends EntityPathBase<DasApiRegister> {

    private static final long serialVersionUID = -582589231L;

    public static final QDasApiRegister dasApiRegister = new QDasApiRegister("dasApiRegister");

    public final StringPath apiId = createString("apiId");

    public final StringPath apiRouteId = createString("apiRouteId");

    public final StringPath backendConstants = createString("backendConstants");

    public final StringPath backendHost = createString("backendHost");

    public final StringPath backendPath = createString("backendPath");

    public final StringPath backendRequestParas = createString("backendRequestParas");

    public final StringPath backendTimeout = createString("backendTimeout");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath protocolType = createString("protocolType");

    public final StringPath requestType = createString("requestType");

    public final StringPath updateUserid = createString("updateUserid");

    public QDasApiRegister(String variable) {
        super(DasApiRegister.class, forVariable(variable));
    }

    public QDasApiRegister(Path<? extends DasApiRegister> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiRegister(PathMetadata metadata) {
        super(DasApiRegister.class, metadata);
    }

}

