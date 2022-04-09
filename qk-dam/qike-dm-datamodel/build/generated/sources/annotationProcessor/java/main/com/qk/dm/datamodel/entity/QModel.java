package com.qk.dm.datamodel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModel is a Querydsl query type for Model
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModel extends EntityPathBase<Model> {

    private static final long serialVersionUID = -853743989L;

    public static final QModel model = new QModel("model");

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModified = createDateTime("gmtModified", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath layeredName = createString("layeredName");

    public final StringPath modelName = createString("modelName");

    public final NumberPath<Integer> modelType = createNumber("modelType", Integer.class);

    public QModel(String variable) {
        super(Model.class, forVariable(variable));
    }

    public QModel(Path<? extends Model> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModel(PathMetadata metadata) {
        super(Model.class, metadata);
    }

}

