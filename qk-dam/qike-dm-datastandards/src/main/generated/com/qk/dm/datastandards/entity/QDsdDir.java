package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdDir is a Querydsl query type for DsdDir
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDsdDir extends EntityPathBase<DsdDir> {

    private static final long serialVersionUID = 1433218787L;

    public static final QDsdDir dsdDir = new QDsdDir("dsdDir");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dirDsdId = createString("dirDsdId");

    public final StringPath dirDsdName = createString("dirDsdName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath parentId = createString("parentId");

    public QDsdDir(String variable) {
        super(DsdDir.class, forVariable(variable));
    }

    public QDsdDir(Path<? extends DsdDir> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdDir(PathMetadata metadata) {
        super(DsdDir.class, metadata);
    }

}

