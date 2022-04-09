package com.qk.dm.authority.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQxService is a Querydsl query type for QxService
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQxService extends EntityPathBase<QxService> {

    private static final long serialVersionUID = 471320556L;

    public static final QQxService qxService = new QQxService("qxService");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath createUsername = createString("createUsername");

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath redionid = createString("redionid");

    public final StringPath serviceid = createString("serviceid");

    public final StringPath serviceName = createString("serviceName");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public final StringPath updateUsername = createString("updateUsername");

    public QQxService(String variable) {
        super(QxService.class, forVariable(variable));
    }

    public QQxService(Path<? extends QxService> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQxService(PathMetadata metadata) {
        super(QxService.class, metadata);
    }

}

