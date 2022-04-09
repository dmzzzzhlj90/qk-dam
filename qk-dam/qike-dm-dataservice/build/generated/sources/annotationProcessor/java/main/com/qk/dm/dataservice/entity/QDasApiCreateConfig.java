package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiCreateConfig is a Querydsl query type for DasApiCreateConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiCreateConfig extends EntityPathBase<DasApiCreateConfig> {

    private static final long serialVersionUID = -52702580L;

    public static final QDasApiCreateConfig dasApiCreateConfig = new QDasApiCreateConfig("dasApiCreateConfig");

    public final StringPath accessMethod = createString("accessMethod");

    public final StringPath apiId = createString("apiId");

    public final StringPath apiOrderParas = createString("apiOrderParas");

    public final StringPath apiRequestParas = createString("apiRequestParas");

    public final StringPath apiResponseParas = createString("apiResponseParas");

    public final StringPath connectType = createString("connectType");

    public final StringPath dataBaseName = createString("dataBaseName");

    public final StringPath dataSourceName = createString("dataSourceName");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tableName = createString("tableName");

    public QDasApiCreateConfig(String variable) {
        super(DasApiCreateConfig.class, forVariable(variable));
    }

    public QDasApiCreateConfig(Path<? extends DasApiCreateConfig> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiCreateConfig(PathMetadata metadata) {
        super(DasApiCreateConfig.class, metadata);
    }

}

