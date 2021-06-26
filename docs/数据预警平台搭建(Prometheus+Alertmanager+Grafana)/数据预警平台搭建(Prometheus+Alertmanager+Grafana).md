# 数据预警平台搭建

**prometheus 基础架构:**

![1624620746084](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624620746084.png)

### 1.下载和运行 Prometheus

#### 1.1 下载

promethus下载地址:https://prometheus.io/download/

```shell
##解压安装包 prometheus-2.28.0-rc.0.linux-amd64.tar.gz
tar xvfz prometheus-2.28.0-rc.0.linux-amd64.tar.gz
```

#### 1.2 配置文件

 prometheus.yml :

```yml
# my global config
global:
  scrape_interval:     15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      - 192.168.56.101:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"
  - "/usr/local/prometheus/rules/server_node_alarms.yml"         # 应用服务Server报警规则文件
  - "/usr/local/prometheus/rules/data_import_alarms.yml"         # 数据引入预警规则文件
  #- "/usr/local/prometheus/rules/node_down.yml"                 # 实例存活报警规则文件
  #- "/usr/local/prometheus/rules/memory_over.yml"               # 内存报警规则文件
  #- "/usr/local/prometheus/rules/disk_over.yml"                 # 磁盘报警规则文件
  #- "/usr/local/prometheus/rules/cpu_over.yml"                  # cpu报警规则文件

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'
    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.
    static_configs:
    - targets: ['192.168.56.101:9090']
#SpringBoot应用配置
  - job_name: 'actuator-mxserver' 
    scrape_interval: 5s
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['10.0.170.112:8777']
```

#### 1.3  启动 Prometheus 

```shell
##启动
cd /usr/local/prometheus/prometheus-2.28.0-rc.0.linux-amd64
./prometheus --config.file=prometheus.yml
##nohup启动方式
    nohup ./prometheus --config.file=prometheus.yml > /usr/local/prometheus/logs/prometheus.log 2>&1 &
##查看进程
ps -ef|grep prometheus |grep -v grep
```

#### 1.4 页面访问地址

url: http://192.168.56.101:9090/

![1624613768814](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624613768814.png)

### 2. Grafana安装配置

下载地址:https://grafana.com/grafana/download

#### 2.1 启动:

```shell
####grafana-8.0.3-1.x86_64.rpm
cd /usr/local/grafana
sudo service grafana-server start
```

配置grafana在服务器启动时启动 :

```shell
sudo /sbin/chkconfig --add grafana-server
```

 取消grafana在服务器启动时启动 :

```shell
sudo systemctl enable grafana-server.service
```

停止grafana:

```shell
sudo service grafana-server stop
```

#### 2.2  页面访问地址

url: http://192.168.56.101:3000/ 
账号：admin 密码 admin

需要修改端口等 grafana 配置，修改配置文件即可。

```shell
##grafana 日志文件位置：/var/log/grafana

##grafana 环境文件位置： /etc/sysconfig/grafana-server

##grafana 配置文件位置： /etc/grafana/grafana.ini
```

#### 2.3  **Grafana使用方法** 

添加数据源 

![1624615696259](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624615696259.png)

![1624615749655](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624615749655.png)

![1624615776921](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624615776921.png)

导入仪表盘模板

官网仪表盘地址: https://grafana.com/grafana/dashboards

![1624616371971](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624616371971.png)

![1624616301529](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624616301529.png)

![1624620843181](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624620843181.png)

添加panel

![1624619232908](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624619232908.png)

![1624619502699](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624619502699.png)

### 3.安装Alertmanager

#### 3.1 下载安装

下载:

```shell
 ##下载地址: [alertmanager-0.22.2.linux-amd64.tar.gz](https://github.com/prometheus/alertmanager/releases/download/v0.22.2/alertmanager-0.22.2.linux-amd64.tar.gz) 
```

安装:

```shell
tar -xvf alertmanager-0.22.2.linux-amd64.tar.gz
```

配置alertmanager.yml:

```yml
#注: alertmanager的webhook集成了钉钉报警,这里的webhook2为prometheus-webhook-dingtalk配置文件config.yml中的路由节点端点;
global:
  resolve_timeout: 1m
route:
  group_by: ['alertname']
  group_wait: 10s
  group_interval: 10s
  repeat_interval: 1m
  receiver: 'webhook2'
receivers:
- name: 'webhook2' 
  webhook_configs:
  - url: 'http://192.168.56.101:8060/dingtalk/webhook2/send'
    send_resolved: false
inhibit_rules:
  - source_match:
      alertname: 'ApplicationDown'
      severity: 'critical'
    target_match:
      severity: 'warning'
    equal: ['alertname',"target","job","instance"]
```

#### 3.2 启动

```shell
cd /usr/local/prometheus/alertmanager-0.22.2.linux-amd64
#启动
./alertmanager --config.file=alertmanager.yml
#nohup方式启动
nohup ./alertmanager --config.file=alertmanager.yml > /usr/local/prometheus/logs/alertmanager.log 2>&1 &

```

url地址:http://192.168.56.101:9093/

#### 3.3 配置rules报警规则

设置rules存放配置文件,server_node_alarms.yml

```yml
groups:
- name: 应用服务节点状态监控规则
  rules:
  - alert: 应用服务节点状态告警
    expr: up{job="actuator-mxserver"}!=1 ##匹配PromQL查询预警语句
    for: 10m
    labels:
      status: High
      team: 服务节点状态监控规则
    annotations:
      description: "请注意,应用服务名称为:{{ $labels.job }}的节点已断开! ! ! "
      value: '{{ $value }}'
```

修改prometheus.yml增加rules预警规则:

```yml
# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"
  - "/usr/local/prometheus/rules/server_node_alarms.yml"         # 应用服务Server报警规则文件
  - "/usr/local/prometheus/rules/data_import_alarms.yml"         # 数据引入预警规则文件
  #- "/usr/local/prometheus/rules/node_down.yml"                 # 实例存活报警规则文件
  #- "/usr/local/prometheus/rules/memory_over.yml"               # 内存报警规则文件
  #- "/usr/local/prometheus/rules/disk_over.yml"                 # 磁盘报警规则文件
  #- "/usr/local/prometheus/rules/cpu_over.yml"                  # cpu报警规则文件
```

页面效果展示:

prometheus:

![1624687603219](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624687603219.png)

alertmanager:

![1624687643917](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624687643917.png)

### 4.安装prometheus-webhook-dingtalk

#### 4.1 获取 prometheus-webhook-dingtalk 安装包

```shell
git clone https://github.com/timonwong/prometheus-webhook-dingtalk.git
```

#### 4.2 解决依赖

##### （1）yarn

```shell
curl --silent --location https://dl.yarnpkg.com/rpm/yarn.repo | sudo tee /etc/yum.repos.d/yarn.repo
curl --silent --location https://rpm.nodesource.com/setup_8.x | sudo bash -
yum install yarn
```

##### （2）go 环境依赖

```shell
cd /usr/local/prometheus/go
wget https://dl.google.com/go/go1.14.2.linux-amd64.tar.gz
tar zxf go1.14.2.linux-amd64.tar.gz
echo "export PATH=$PATH:/usr/local/prometheus/go/bin" >>~/.bashrc
source ~/.bashrc
```

##### （3）node.js 版本

```shell
wget https://nodejs.org/dist/v12.16.1/node-v12.16.1-linux-x64.tar.xz
下载node.js 新版本
tar xf node-v12.16.1-linux-x64.tar.xz
mv /usr/bin/node /usr/bin/node_bakv6.17.1
ln -s /usr/local/prometheus/node-v12.16.1-linux-x64/bin/node /usr/bin/node
```

#### 3.进行编译安装

```shell
##make出错没关系，这里执行了两次会出现prometheus-webhook-dingtalk启动执行文件
cd prometheus-webhook-dingtalk.git
make build
```

#### 4.配置 & 启动

config.yml

注:添加targets下的webhook节点信息的时候,修改钉钉机器人生成的access_token;同时如果配置加密方式选择对应的secret方式或者关键词{"content": "数据预警平台"}方式;

```shell
## Request timeout
# timeout: 5s

## Uncomment following line in order to write template from scratch (be careful!)
#no_builtin_template: true

## Customizable templates path
#templates:
#  - contrib/templates/legacy/template.tmpl

## You can also override default template using `default_message`
## The following example to use the 'legacy' template from v0.3.0
#default_message:
#  title: '{{ template "legacy.title" . }}'
#  text: '{{ template "legacy.content" . }}'

## Targets, previously was known as "profiles"
targets:
  webhook1:
    url: https://oapi.dingtalk.com/robot/send?access_token=af8903856888567d4cb486dfb8cbc0e48f33e6ac28b8f3a36f9b948e4ae4954e
    # secret for signature
    secret: SEC000000000000000000000
  webhook2:
    url: https://oapi.dingtalk.com/robot/send?access_token=2159e318c7e78eaf3cd1338050810febc63a52f58a5fc97b816f61a2116020a7
    message:
      # Use legacy template
      title: '{"content": "数据预警平台"}'
      text: '{{ template "legacy.content" . }}'
  webhook_legacy:
    url: https://oapi.dingtalk.com/robot/send?access_token=af8903856888567d4cb486dfb8cbc0e48f33e6ac28b8f3a36f9b948e4ae4954e
    # Customize template content
    message:
      # Use legacy template
      title: '{{ template "legacy.title" . }}'
      text: '{{ template "legacy.content" . }}'
  webhook_mention_all:
    url: https://oapi.dingtalk.com/robot/send?access_token=af8903856888567d4cb486dfb8cbc0e48f33e6ac28b8f3a36f9b948e4ae4954e
    mention:
      all: true
  webhook_mention_users:
    url: https://oapi.dingtalk.com/robot/send?access_token=af8903856888567d4cb486dfb8cbc0e48f33e6ac28b8f3a36f9b948e4ae4954e
    mention:
      mobiles: ['156xxxx8827', '189xxxx8325']

```

启动:

```shell
##启动
cd /usr/local/prometheus/prometheus-webhook-dingtalk/prometheus-webhook-dingtalk
./prometheus-webhook-dingtalk --config.file=config.yml
##查看
ps -ef|grep prometheus-webhook-dingtalk |grep -v grep
##nohup启动方式
nohup ./prometheus-webhook-dingtalk --config.file=config.yml > /usr/local/prometheus/logs/prometheus-webhook-dingtalk.log 2>&1 &
```

效果:

通过Prometheus(Alerts)进行预警 -----输送------>Alertmanager------send----->prometheus-webhook-dingtalk

![1624689792495](数据预警平台搭建(Prometheus+Alertmanager+Grafana).assets/1624689792495.png)

### 5. Spring Boot集成

依赖配置 actuator-mxserver.gradle

```yml
dependencies {
    implementation group: 'io.micrometer', name: 'micrometer-registry-prometheus', version: '1.7.0'
}
```

bootstrap.yml对应prometheus.yml 的scrape_configs下的节点信息;

```yml
management:
  endpoints:
    enabled-by-default: true
    web:
      base-path: /actuator
      exposure:
        include: "*"
  endpoint:
    info:
      enabled: true
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        pushgateway:
          enabled: true
          base-url: http:192.168.56.100:9090 ##Prometheus的Server地址
          job: dm-spring-mx
        enabled: true
        descriptions: true
    tags:
      application: ${spring.application.name}
```

注入MeterRegistry:

```java
public class PrometheusConfig {   
@Bean   
MeterRegistryCustomizer<MeterRegistry>configurer(@Value("${spring.application.name}")String applicationName) {  
    return (registry) -> registry.config().commonTags("application", applicationName);    }}
```







