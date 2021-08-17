// package com.qk.dm.datastandards.datasource_connect.conf;
//
// import cn.hutool.core.io.resource.ResourceUtil;
// import com.qk.dm.datastandards.vo.DataSourceJobVO;
// import com.qk.dm.datastandards.vo.DataSourceYamlVO;
// import org.yaml.snakeyaml.Yaml;
//
// import java.util.Objects;
//
/// **
// * 数据源配置
// *
// * @author wjq
// * @date 20210806
// * @since 1.0.0
// */
// public class DataSourceJobConf {
//    static {
//        dataSourceConf =
//                new Yaml().loadAs(ResourceUtil.getStream("datasource-connect-extractor-task.yml"),
// DataSourceYamlVO.class);
//    }
//
//    static final DataSourceYamlVO dataSourceConf;
//
//    private DataSourceJobConf() {
//        throw new IllegalStateException("Utility class");
//    }
//
//    public static DataSourceJobVO getMetadataJobYamlVO(String jobName) {
//        DataSourceJobVO yamlVO =
//                dataSourceConf.getDataSourceJobs().stream()
//                        .filter(dataSourceJobYamlVO ->
// jobName.equals(dataSourceJobYamlVO.getName()))
//                        .findFirst()
//                        .orElse(null);
//        Objects.requireNonNull(yamlVO, jobName + "获取的配置不存在");
//        return yamlVO;
//    }
// }
