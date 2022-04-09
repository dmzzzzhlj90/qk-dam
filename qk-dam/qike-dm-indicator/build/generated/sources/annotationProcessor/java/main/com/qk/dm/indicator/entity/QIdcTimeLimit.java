package com.qk.dm.indicator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdcTimeLimit is a Querydsl query type for IdcTimeLimit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdcTimeLimit extends EntityPathBase<IdcTimeLimit> {

    private static final long serialVersionUID = -1619240492L;

    public static final QIdcTimeLimit idcTimeLimit = new QIdcTimeLimit("idcTimeLimit");

    public final StringPath baseTime = createString("baseTime");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath describe = createString("describe");

    public final StringPath end = createString("end");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath limitName = createString("limitName");

    public final NumberPath<Integer> limitType = createNumber("limitType", Integer.class);

    public final StringPath quickStart = createString("quickStart");

    public final StringPath start = createString("start");

    public QIdcTimeLimit(String variable) {
        super(IdcTimeLimit.class, forVariable(variable));
    }

    public QIdcTimeLimit(Path<? extends IdcTimeLimit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdcTimeLimit(PathMetadata metadata) {
        super(IdcTimeLimit.class, metadata);
    }

}

