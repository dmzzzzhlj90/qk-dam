package com.qk.dm.dataservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDasApiRoute is a Querydsl query type for DasApiRoute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDasApiRoute extends EntityPathBase<DasApiRoute> {

    private static final long serialVersionUID = 1025628891L;

    public static final QDasApiRoute dasApiRoute = new QDasApiRoute("dasApiRoute");

    public final StringPath apiRouteId = createString("apiRouteId");

    public final StringPath apiRoutePath = createString("apiRoutePath");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDasApiRoute(String variable) {
        super(DasApiRoute.class, forVariable(variable));
    }

    public QDasApiRoute(Path<? extends DasApiRoute> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDasApiRoute(PathMetadata metadata) {
        super(DasApiRoute.class, metadata);
    }

}

