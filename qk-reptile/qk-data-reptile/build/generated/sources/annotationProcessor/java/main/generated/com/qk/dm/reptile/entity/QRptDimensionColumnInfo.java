package com.qk.dm.reptile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRptDimensionColumnInfo is a Querydsl query type for RptDimensionColumnInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRptDimensionColumnInfo extends EntityPathBase<RptDimensionColumnInfo> {

    private static final long serialVersionUID = 1301463230L;

    public static final QRptDimensionColumnInfo rptDimensionColumnInfo = new QRptDimensionColumnInfo("rptDimensionColumnInfo");

    public final NumberPath<Long> createUserid = createNumber("createUserid", Long.class);

    public final StringPath createUsername = createString("createUsername");

    public final StringPath description = createString("description");

    public final StringPath dimensionColumnCode = createString("dimensionColumnCode");

    public final StringPath dimensionColumnName = createString("dimensionColumnName");

    public final NumberPath<Long> dimensionId = createNumber("dimensionId", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> updateUserid = createNumber("updateUserid", Long.class);

    public final StringPath updateUsername = createString("updateUsername");

    public QRptDimensionColumnInfo(String variable) {
        super(RptDimensionColumnInfo.class, forVariable(variable));
    }

    public QRptDimensionColumnInfo(Path<? extends RptDimensionColumnInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRptDimensionColumnInfo(PathMetadata metadata) {
        super(RptDimensionColumnInfo.class, metadata);
    }

}

