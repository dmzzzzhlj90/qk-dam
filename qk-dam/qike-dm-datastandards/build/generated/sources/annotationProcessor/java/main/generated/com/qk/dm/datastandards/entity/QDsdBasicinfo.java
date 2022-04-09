package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdBasicinfo is a Querydsl query type for DsdBasicinfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsdBasicinfo extends EntityPathBase<DsdBasicinfo> {

    private static final long serialVersionUID = -1667333198L;

    public static final QDsdBasicinfo dsdBasicinfo = new QDsdBasicinfo("dsdBasicinfo");

    public final StringPath codeCol = createString("codeCol");

    public final StringPath codeDirId = createString("codeDirId");

    public final StringPath colName = createString("colName");

    public final StringPath dataCapacity = createString("dataCapacity");

    public final StringPath dataType = createString("dataType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final StringPath dsdCode = createString("dsdCode");

    public final StringPath dsdLevel = createString("dsdLevel");

    public final StringPath dsdLevelId = createString("dsdLevelId");

    public final StringPath dsdName = createString("dsdName");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sortField = createNumber("sortField", Integer.class);

    public final StringPath useCodeLevel = createString("useCodeLevel");

    public QDsdBasicinfo(String variable) {
        super(DsdBasicinfo.class, forVariable(variable));
    }

    public QDsdBasicinfo(Path<? extends DsdBasicinfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdBasicinfo(PathMetadata metadata) {
        super(DsdBasicinfo.class, metadata);
    }

}

