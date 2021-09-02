package com.qk.dam.metedata.property;

/**
 * @author shenpj
 * @date 2021/9/2 5:04 下午
 * @since 1.0.0
 */
public class SynchStateProperty {
    public static class LabelsAtlas {
        public static final Integer DELETE = -1;
        public static final Integer NOT_SYNCH = 0;
        public static final Integer SYNCH = 1;
    }

    public static class ClassifyAtlas {
        public static final Integer DELETE = -1;
        public static final Integer NOT_SYNCH = 0;
        public static final Integer SYNCH = 1;
    }

    public static class Classify {
        public static final Integer DELETE = -1;
        public static final Integer NOT_SYNCH = 0;
        public static final Integer SYNCH = 1;
        public static final Integer ADD = 2;
    }

}
