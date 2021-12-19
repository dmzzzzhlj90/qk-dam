//package com.qk.dm.dataquality.groovy;
//
//import cn.hutool.log.Log;
//import cn.hutool.log.LogFactory;
//import com.google.common.io.CharStreams;
//import groovy.lang.GroovyClassLoader;
//import groovy.lang.GroovyObject;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.com.qk.dm.groovy.util.Objects;
//import java.com.qk.dm.groovy.util.concurrent.ConcurrentHashMap;
//
///**
// * groovy脚本共享资源公共信息类
// *
// * @author wjq
// * @date 2021/12/15
// * @since 1.0.0
// */
//@Component
//public class GroovyScriptResource {
//    private static final Log LOG = LogFactory.get("groovy脚本共享资源公共信息类");
//
//    public static final String CLASSPATH_GROOVY_GROOVY = "classpath:groovy/*.groovy";
//    public static final String RESOURCES_FOLDER_GROOVY = "groovy/";
//    public static final String UTF_8 = "UTF-8";
//
//    public static GroovyClassLoader classLoader;
//
//    public static final ConcurrentHashMap<String, GroovyObject> groovyObjects = new ConcurrentHashMap<>();
//
//    static {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        classLoader = new GroovyClassLoader(GroovyScriptResource.class.getClassLoader());
//        try {
//            //适配读取classpath下面的*.groovy文件列表
//            Resource[] resources = resolver.getResources(CLASSPATH_GROOVY_GROOVY);
//            if (resources.length == 0) {
//                LOG.info("======未获取到groovy文件======");
//            } else {
//                for (Resource resource : resources) {
//                    try (InputStream inputStream = GroovyScriptResource.class.getClassLoader().getResourceAsStream(RESOURCES_FOLDER_GROOVY + resource.getFilename())) {
//                        String text = CharStreams.toString(new InputStreamReader(inputStream, UTF_8));
//                        Class groovyClass = classLoader.parseClass(text, resource.getFilename());
//                        GroovyObject scriptInstance = (GroovyObject) groovyClass.newInstance();
//                        groovyObjects.put(Objects.requireNonNull(resource.getFilename()).split("\\.")[0], scriptInstance);
//                        LOG.info("======已完成加载groovy文件名称为:【{}】======",resource.getFilename());
//                    }
//                }
//            }
//            //清理掉内存 GroovyClassLoader
//            classLoader.clearCache();
//        } catch (Exception e) {
//            LOG.info("======groovy 引擎初始化失败！！！！！！！======", e.getMessage());
//        }
//    }
//
//}