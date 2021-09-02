package com.qk.dam.metedata.property;

/**
 * @author shenpj
 * @date 2021/8/27 4:04 下午
 * @since 1.0.0
 */
public class AtlasSearchProperty {

    public static class AttributeName {
        public static final String TYPENAME = "__typeName";
        public static final String NAME = "name";
        public static final String LABELS = "__labels";

    }

    public static class Operator {
        public static final String EQ = "eq";
        public static final String CONTAINS = "contains";

    }
}
