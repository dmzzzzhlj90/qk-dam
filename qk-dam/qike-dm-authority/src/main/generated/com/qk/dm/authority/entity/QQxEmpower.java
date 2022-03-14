package com.qk.dm.authority.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQxEmpower is a Querydsl query type for QxEmpower
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQxEmpower extends EntityPathBase<QxEmpower> {

    private static final long serialVersionUID = 1158162068L;

    public static final QQxEmpower qxEmpower = new QQxEmpower("qxEmpower");

    public final StringPath clientName = createString("clientName");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath createUsername = createString("createUsername");

    public final StringPath empoerId = createString("empoerId");

    public final StringPath empoerName = createString("empoerName");

    public final StringPath empoerType = createString("empoerType");

    public final StringPath empowerId = createString("empowerId");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> powerType = createNumber("powerType", Integer.class);

    public final StringPath resourceSign = createString("resourceSign");

    public final StringPath serviceId = createString("serviceId");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public final StringPath updateUsername = createString("updateUsername");

    public QQxEmpower(String variable) {
        super(QxEmpower.class, forVariable(variable));
    }

    public QQxEmpower(Path<? extends QxEmpower> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQxEmpower(PathMetadata metadata) {
        super(QxEmpower.class, metadata);
    }

}

