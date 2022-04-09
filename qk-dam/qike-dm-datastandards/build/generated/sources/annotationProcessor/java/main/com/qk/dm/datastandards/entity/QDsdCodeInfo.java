package com.qk.dm.datastandards.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsdCodeInfo is a Querydsl query type for DsdCodeInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsdCodeInfo extends EntityPathBase<DsdCodeInfo> {

    private static final long serialVersionUID = -867294299L;

    public static final QDsdCodeInfo dsdCodeInfo = new QDsdCodeInfo("dsdCodeInfo");

    public final StringPath codeDirId = createString("codeDirId");

    public final StringPath codeDirLevel = createString("codeDirLevel");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tableCode = createString("tableCode");

    public final StringPath tableConfFields = createString("tableConfFields");

    public final StringPath tableDesc = createString("tableDesc");

    public final StringPath tableName = createString("tableName");

    public QDsdCodeInfo(String variable) {
        super(DsdCodeInfo.class, forVariable(variable));
    }

    public QDsdCodeInfo(Path<? extends DsdCodeInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsdCodeInfo(PathMetadata metadata) {
        super(DsdCodeInfo.class, metadata);
    }

}

