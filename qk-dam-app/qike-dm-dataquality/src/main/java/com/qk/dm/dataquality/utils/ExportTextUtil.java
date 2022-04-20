package com.qk.dm.dataquality.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;

/**
 * @author shenpengjie
 */
public class ExportTextUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportTextUtil.class);

    /**
     * 导出文本文件
     *
     * @param response
     * @param jsonString
     * @param fileName
     */
    public static void writeToTxt (HttpServletResponse response, String jsonString, String fileName) {//设置响应的字符集
        response.setCharacterEncoding("utf-8");
        //设置响应内容的类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader(
                "Content-Disposition",
                "attachment; filename="
                        + genAttachmentFileName(fileName, "JSON_FOR_UCC_")
                        + ".txt");//通过后缀可以下载不同的文件格式
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(delNull(jsonString).getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            LOGGER.error("导出文件文件出错，e:{}", e);
        } finally {
            try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
                LOGGER.error("关闭流对象出错 e:{}", e);
            }
        }
    }

    /**
     * 如果字符串对象为 null，则返回空字符串，否则返回去掉字符串前后空格的字符串
     *
     * @param str
     * @return
     */
    public static String delNull (String str) {
        String returnStr = "";
        if (!StringUtils.isEmpty(str)) {
            returnStr = str.trim();
        }
        return returnStr;
    }

    /**
     * 生成导出附件中文名。应对导出文件中文乱码
     * <p>
     * response.addHeader("Content-Disposition", "attachment; filename=" + cnName);
     *
     * @param cnName
     * @param defaultName
     * @return
     */
    public static String genAttachmentFileName (String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }
}