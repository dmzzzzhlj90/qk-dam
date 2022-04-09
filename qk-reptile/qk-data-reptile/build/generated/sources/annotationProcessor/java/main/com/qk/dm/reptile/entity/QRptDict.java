package com.qk.dm.reptile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRptDict is a Querydsl query type for RptDict
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRptDict extends EntityPathBase<RptDict> {

    private static final long serialVersionUID = -837965662L;

    public static final QRptDict rptDict = new QRptDict("rptDict");

    public final StringPath code = createString("code");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath level = createBoolean("level");

    public final StringPath name = createString("name");

    public final NumberPath<Long> pid = createNumber("pid", Long.class);

    public final StringPath remark = createString("remark");

    public final BooleanPath status = createBoolean("status");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QRptDict(String variable) {
        super(RptDict.class, forVariable(variable));
    }

    public QRptDict(Path<? extends RptDict> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRptDict(PathMetadata metadata) {
        super(RptDict.class, metadata);
    }

}

