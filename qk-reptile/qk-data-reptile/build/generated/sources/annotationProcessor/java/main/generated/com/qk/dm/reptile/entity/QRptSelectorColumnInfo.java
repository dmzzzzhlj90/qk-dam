package com.qk.dm.reptile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRptSelectorColumnInfo is a Querydsl query type for RptSelectorColumnInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRptSelectorColumnInfo extends EntityPathBase<RptSelectorColumnInfo> {

    private static final long serialVersionUID = -2091162033L;

    public static final QRptSelectorColumnInfo rptSelectorColumnInfo = new QRptSelectorColumnInfo("rptSelectorColumnInfo");

    public final StringPath afterPrefix = createString("afterPrefix");

    public final StringPath beforePrefix = createString("beforePrefix");

    public final StringPath columnCode = createString("columnCode");

    public final StringPath columnName = createString("columnName");

    public final NumberPath<Long> configId = createNumber("configId", Long.class);

    public final NumberPath<Integer> elementType = createNumber("elementType", Integer.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath requestAfterPrefix = createString("requestAfterPrefix");

    public final StringPath requestBeforePrefix = createString("requestBeforePrefix");

    public final NumberPath<Integer> selector = createNumber("selector", Integer.class);

    public final StringPath selectorVal = createString("selectorVal");

    public final StringPath sourceAfterPrefix = createString("sourceAfterPrefix");

    public final StringPath sourceBeforePrefix = createString("sourceBeforePrefix");

    public QRptSelectorColumnInfo(String variable) {
        super(RptSelectorColumnInfo.class, forVariable(variable));
    }

    public QRptSelectorColumnInfo(Path<? extends RptSelectorColumnInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRptSelectorColumnInfo(PathMetadata metadata) {
        super(RptSelectorColumnInfo.class, metadata);
    }

}

