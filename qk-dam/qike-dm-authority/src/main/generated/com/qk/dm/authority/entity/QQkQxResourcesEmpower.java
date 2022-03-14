package com.qk.dm.authority.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQkQxResourcesEmpower is a Querydsl query type for QkQxResourcesEmpower
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQkQxResourcesEmpower extends EntityPathBase<QkQxResourcesEmpower> {

    private static final long serialVersionUID = -1265119397L;

    public static final QQkQxResourcesEmpower qkQxResourcesEmpower = new QQkQxResourcesEmpower("qkQxResourcesEmpower");

    public final StringPath empowerUuid = createString("empowerUuid");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath resourceUuid = createString("resourceUuid");

    public QQkQxResourcesEmpower(String variable) {
        super(QkQxResourcesEmpower.class, forVariable(variable));
    }

    public QQkQxResourcesEmpower(Path<? extends QkQxResourcesEmpower> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQkQxResourcesEmpower(PathMetadata metadata) {
        super(QkQxResourcesEmpower.class, metadata);
    }

}

