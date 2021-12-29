## 部署hive hooks

### 官方说明

https://atlas.apache.org/#/HookHive

Atlas Hive hook 向 Hive 注册以侦听创建/更新/删除操作，并通过 Kafka 通知更新 Atlas 中的元数据，以了解 Hive 中的更改。按照以下说明在 Hive 中设置 Atlas 挂钩：

- 通过添加以下内容在 hive-site.xml 中设置 Atlas 挂钩：

```xml
<property>
    <name>hive.exec.post.hooks</name>
      <value>org.apache.atlas.hive.hook.HiveHook</value>
  </property>
```

- 解压 apache-atlas-${project.version}-hive-hook.tar.gz
- cd apache-atlas-hive-hook-${project.version}
- 将文件夹 apache-atlas-hive-hook-${project.version}/hook/hive 的全部内容复制到`<atlas package>`/hook/hive
- `<atlas package>`在 hive 配置的 hive-env.sh 中添加“export HIVE_AUX_JARS_PATH= /hook/hive”
- 将`<atlas-conf>`/atlas-application.properties复制到 hive conf 目录。

atlas-application.properties 中的以下属性控制线程池和通知详细信息：

```properties
atlas.hook.hive.synchronous=false 
atlas.hook.hive.numRetries=3   
atlas.hook.hive.queueSize=10000 
atlas.cluster.name=primary
atlas.kafka.zookeeper.connect=localhost:2181
atlas.kafka.zookeeper.connection.timeout.ms=30000
atlas.kafka.zookeeper.session.timeout.ms=60000    
atlas.kafka.zookeeper.sync.time.ms=20       
```

## 部署样例测试

>注意此版本使用hive2，hive3需要另外提供，却hive2环境以来的common-configration为1.6版本需替换1.10版本

1.在测试及上建立文件夹/etc/atlas/conf，将配置文件 atlas-application.properties copy进去

2.在hive 环境目录上配置上述的 hive-site.xml hook，配置HIVE_AUX_JARS_PATH为插件目录

3.接下apache-atlas-hiveserver2-hook.tar.gz 插件目录与HIVE_AUX_JARS_PATH一致即可

4.运行sh hook-bin/import-hive.sh 输入用户名密码即可
