package com.qk.dm.indicator.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdcBusiness is a Querydsl query type for IdcBusiness
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdcBusiness extends EntityPathBase<IdcBusiness> {

    private static final long serialVersionUID = 878539290L;

    public static final QIdcBusiness idcBusiness = new QIdcBusiness("idcBusiness");

    public final StringPath applicationScenario = createString("applicationScenario");

    public final StringPath bIndicatorAlias = createString("bIndicatorAlias");

    public final StringPath bIndicatorCode = createString("bIndicatorCode");

    public final StringPath bIndicatorName = createString("bIndicatorName");

    public final StringPath calculationFormula = createString("calculationFormula");

    public final StringPath caliberModifier = createString("caliberModifier");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath dimStatistical = createString("dimStatistical");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath indicatorDefinition = createString("indicatorDefinition");

    public final StringPath indicatorDepart = createString("indicatorDepart");

    public final StringPath indicatorPersonLiable = createString("indicatorPersonLiable");

    public final NumberPath<Integer> indicatorStatus = createNumber("indicatorStatus", Integer.class);

    public final StringPath measureObject = createString("measureObject");

    public final StringPath measureUnit = createString("measureUnit");

    public final StringPath remarks = createString("remarks");

    public final StringPath setPurpose = createString("setPurpose");

    public final StringPath statisticalCycle = createString("statisticalCycle");

    public final StringPath techIndicator = createString("techIndicator");

    public QIdcBusiness(String variable) {
        super(IdcBusiness.class, forVariable(variable));
    }

    public QIdcBusiness(Path<? extends IdcBusiness> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdcBusiness(PathMetadata metadata) {
        super(IdcBusiness.class, metadata);
    }

}

