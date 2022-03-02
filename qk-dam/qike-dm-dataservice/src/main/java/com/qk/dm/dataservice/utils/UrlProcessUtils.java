package com.qk.dm.dataservice.utils;

import com.qk.dam.dataservice.spi.pojo.RouteData;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UrlProcessUtils URL处理工具类
 *
 * @author wjq
 * @date 2022/03/01
 * @since 1.0.0
 */
public class UrlProcessUtils {

    /**
     * 检测路由信息是否包含需要发布的URL信息
     *
     * @param routeInfoList
     * @param backendPath
     * @return
     */
    public static boolean checkUrlIsContain(List<RouteData> routeInfoList, String backendPath) {
        boolean checkUrlFlag = false;
        for (RouteData routeData : routeInfoList) {
            if (checkUrlFlag) {
                break;
            }
            String routeDataUri = routeData.getUri();
            if (!ObjectUtils.isEmpty(routeDataUri)) {
                String rootUrl = routeDataUri.split("\\*")[0];
                checkUrlFlag = compare(backendPath, rootUrl);
            }
        }
        return checkUrlFlag;
    }

    /**
     * 执行比较
     *
     * @param URL
     * @param rootURL
     * @return
     */
    public static boolean compare(String URL, String rootURL) {
        String str = rootURL + ".*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(URL);
        return m.matches();
    }

//
//    public static void main(String[] args) {
//        System.out.println(compare("/getUserInfo", "/getUserInfo"));
//    }

}