//package com.qk.dm.dataquality.groovy;
//
//import com.google.common.io.CharStreams;
//import groovy.lang.GroovyClassLoader;
//import groovy.lang.GroovyObject;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.com.qk.dm.groovy.util.HashMap;
//import java.com.qk.dm.groovy.util.Map;
//
///**
// * groovy脚本类 共享元工厂 (单次调用)
// *
// * @author wjq
// * @date 2021/12/15
// * @since 1.0.0
// */
//public class GroovyScriptFactory {
//
//    public static final String RESOURCES_FOLDER_GROOVY = "groovy/";
//    public static final String SUFFIX_GROOVY = ".groovy";
//    public static final String UTF_8 = "UTF-8";
//
//    private static Map<String, GroovyObject> scriptCache = new HashMap<>();
//    private static GroovyClassLoader classLoader = new GroovyClassLoader();
//
//    private static GroovyScriptFactory factory = new GroovyScriptFactory();
//
//    /**
//     * 设置为单例模式
//     */
//    private GroovyScriptFactory() {
//    }
//
//    public static GroovyScriptFactory getInstance() {
//        return factory;
//    }
//
//    public static GroovyObject getGroovyObject(String key) {
//        GroovyObject scriptInstance = null;
//        try {
//            //encrypt 加密工具类
//            String encodeStr = com.qk.dm.dataquality.groovy.EncryptUtil.SHA256(key);
//            // 存储脚本对象
//            if (scriptCache.containsKey(encodeStr)) {
//                return scriptCache.get(encodeStr);
//            } else {
//                // 脚本不存在则创建新的脚本
//                try (InputStream inputStream = GroovyScriptFactory.class.getClassLoader().getResourceAsStream(RESOURCES_FOLDER_GROOVY + key + SUFFIX_GROOVY)) {
//                    assert inputStream != null;
//                    String text = CharStreams.toString(new InputStreamReader(inputStream, UTF_8));
//                    Class groovyClass = classLoader.parseClass(text, key);
//                    scriptInstance = (GroovyObject) groovyClass.getDeclaredConstructor().newInstance();
//                    scriptCache.put(key, scriptInstance);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 每次脚本执行完之后，一定要清理掉内存 GroovyClassLoader
//        classLoader.clearCache();
//        return scriptInstance;
//    }
//
//}