package com.qk.dm.authority.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQxResources is a Querydsl query type for QxResources
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQxResources extends EntityPathBase<QxResources> {

    private static final long serialVersionUID = 178605468L;

    public static final QQxResources qxResources = new QQxResources("qxResources");

    public final StringPath createName = createString("createName");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath path = createString("path");

    public final NumberPath<Long> pid = createNumber("pid", Long.class);

    public final StringPath resourcesid = createString("resourcesid");

    public final StringPath serviceId = createString("serviceId");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final StringPath updateName = createString("updateName");

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public QQxResources(String variable) {
        super(QxResources.class, forVariable(variable));
    }

    public QQxResources(Path<? extends QxResources> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQxResources(PathMetadata metadata) {
        super(QxResources.class, metadata);
    }

}

