package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModelSummaryIdc is a Querydsl query type for ModelSummaryIdc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModelSummaryIdc extends EntityPathBase<ModelSummaryIdc> {

    private static final long serialVersionUID = -823861747L;

    public static final QModelSummaryIdc modelSummaryIdc = new QModelSummaryIdc("modelSummaryIdc");

    public final StringPath dataType = createString("dataType");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath indicatorsCode = createString("indicatorsCode");

    public final StringPath indicatorsName = createString("indicatorsName");

    public final NumberPath<Integer> indicatorsType = createNumber("indicatorsType", Integer.class);

    public final StringPath itsNull = createString("itsNull");

    public final NumberPath<Long> summaryId = createNumber("summaryId", Long.class);

    public QModelSummaryIdc(String variable) {
        super(ModelSummaryIdc.class, forVariable(variable));
    }

    public QModelSummaryIdc(Path<? extends ModelSummaryIdc> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModelSummaryIdc(PathMetadata metadata) {
        super(ModelSummaryIdc.class, metadata);
    }

}

