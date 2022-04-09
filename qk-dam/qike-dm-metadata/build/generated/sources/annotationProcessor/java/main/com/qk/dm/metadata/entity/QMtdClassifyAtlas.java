package com.qk.dm.metadata.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMtdClassifyAtlas is a Querydsl query type for MtdClassifyAtlas
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMtdClassifyAtlas extends EntityPathBase<MtdClassifyAtlas> {

    private static final long serialVersionUID = 940733652L;

    public static final QMtdClassifyAtlas mtdClassifyAtlas = new QMtdClassifyAtlas("mtdClassifyAtlas");

    public final StringPath classify = createString("classify");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath guid = createString("guid");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> synchStatus = createNumber("synchStatus", Integer.class);

    public QMtdClassifyAtlas(String variable) {
        super(MtdClassifyAtlas.class, forVariable(variable));
    }

    public QMtdClassifyAtlas(Path<? extends MtdClassifyAtlas> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMtdClassifyAtlas(PathMetadata metadata) {
        super(MtdClassifyAtlas.class, metadata);
    }

}

