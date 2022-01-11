package com.qk.dm.reptile.constant;

/**
 * 常量
 */
public class RptConstant {
    /**
     * 待配
     */
    public static final Integer WAITING = 0;
    /**
     * 爬虫
     */
    public static final Integer REPTILE = 1;
    /**
     * 历史
     */
    public static final Integer HISTORY = 2;

    /**
     * 主目录id设置
     */
    public static final Long DIRID = 0L;

    /**
     * 主目录名称
     */
    public static final String DIRNAME = "全部维度分类";


    /**
     * 爬虫接口返回的jobid
     */
    public static final String JOBID = "jobid";

    /**
     * 维度字段,下一步链接的编码
     */
    public static final String NEXTURL="more_url";

    /**
     * 运行状态—未启动
     */
    public static final Integer OFF_STARTED = 1;

    /**
     * 导入文件名称包含内同
     */
    public static  final String EXCEL_NAME="待配列基本信息";
    /**
     * 文件上传限制大小
     */
    public final static Integer FILE_SIZE = 10;

    /**
     * 文件上传限制单位（B,K,M,G）
     */
    public final static String FILE_UNIT = "M";

}
