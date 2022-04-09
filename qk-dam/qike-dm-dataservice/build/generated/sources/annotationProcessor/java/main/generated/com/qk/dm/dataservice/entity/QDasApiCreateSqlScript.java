package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiCreateSqlScript is a Querydsl query type for DasApiCreateSqlScript
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiCreateSqlScript extends EntityPathBase<DasApiCreateSqlScript> {

    private static final long serialVersionUID = -92447569L;

    public static final QDasApiCreateSqlScript dasApiCreateSqlScript = new QDasApiCreateSqlScript("dasApiCreateSqlScript");

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

    public final StringPath sqlPara = createString("sqlPara");

    public QDasApiCreateSqlScript(String variable) {
        super(DasApiCreateSqlScript.class, forVariable(variable));
    }

    public QDasApiCreateSqlScript(Path<? extends DasApiCreateSqlScript> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiCreateSqlScript(PathMetadata metadata) {
        super(DasApiCreateSqlScript.class, metadata);
    }

}

