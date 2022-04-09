package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiDir is a Querydsl query type for DasApiDir
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiDir extends EntityPathBase<DasApiDir> {

    private static final long serialVersionUID = 220047775L;

    public static final QDasApiDir dasApiDir = new QDasApiDir("dasApiDir");

    public final StringPath createUserid = createString("createUserid");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dirId = createString("dirId");

    public final StringPath dirName = createString("dirName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final StringPath updateUserid = createString("updateUserid");

    public QDasApiDir(String variable) {
        super(DasApiDir.class, forVariable(variable));
    }

    public QDasApiDir(Path<? extends DasApiDir> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiDir(PathMetadata metadata) {
        super(DasApiDir.class, metadata);
    }

}

