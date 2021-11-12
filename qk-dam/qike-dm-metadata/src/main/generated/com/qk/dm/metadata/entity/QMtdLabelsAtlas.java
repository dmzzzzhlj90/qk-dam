package com.qk.dm.metadata.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMtdLabelsAtlas is a Querydsl query type for MtdLabelsAtlas
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMtdLabelsAtlas extends EntityPathBase<MtdLabelsAtlas> {

    private static final long serialVersionUID = -184001671L;

    public static final QMtdLabelsAtlas mtdLabelsAtlas = new QMtdLabelsAtlas("mtdLabelsAtlas");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final StringPath guid = createString("guid");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath labels = createString("labels");

    public final NumberPath<Integer> synchStatus = createNumber("synchStatus", Integer.class);

    public QMtdLabelsAtlas(String variable) {
        super(MtdLabelsAtlas.class, forVariable(variable));
    }

    public QMtdLabelsAtlas(Path<? extends MtdLabelsAtlas> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMtdLabelsAtlas(PathMetadata metadata) {
        super(MtdLabelsAtlas.class, metadata);
    }

}

