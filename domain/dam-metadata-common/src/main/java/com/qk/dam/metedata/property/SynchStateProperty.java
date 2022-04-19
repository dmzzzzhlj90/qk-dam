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
  public static class TypeName{
    public static final String MYSQL_DB = "mysql_db";
    public static final String HIVE_DB = "hive_db";
    public static final String HIVE_TABLE = "hive_table";
    public static final String DB = "db";
    public static final String TABLE = "table";
    public static final String COLUMN = "column";

  }
}
