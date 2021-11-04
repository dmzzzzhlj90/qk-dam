# amundsen 连接本地数据库
#### 1、如果毫无问题地启动默认版本的 Amundsen，先停止并删除
```shell
docker-compose -f docker-amundsen.yml down
```
#### 2、启动本地开发所需的容器（该-d选项在后台启动）
```shell
docker-compose -f docker-amundsen-local.yml up -d
```
##### 2.1 ERROR: Could not install packages due to an EnvironmentError: HTTPSConnectionPool(host=‘files.pythonhosted.org’, port=443)
```shell
pip install --user xxx # 可以先下载下来
```
##### 2.2 Dockerfile执行命令报错The command 'binsh -c apt-get install -y git' returned a non-zero code 100解决
```shell
# 更改 Dockerfile.metadata.public
RUN apt-get update && apt-get install git -y
```
##### 2.3 gyp ERR! find Python
```shell
#修改npm配置为阿里云的,从阿里云下载,更改npm为cnpm
npm install -g cnpm --registry=https://registry.npm.taobao.
```
#### 2.4 如果因为各种原因导致amundsensearch、amundsenmetadata、amundsenfrontend模块无法编译启动成功，那么可以仿照docker-amundsen.yml直接指定镜像
```shell
image: amundsendev/amundsen-search:2.4.1
image: amundsendev/amundsen-metadata:3.3.0
image: amundsendev/amundsen-frontend:3.1.0
```
#### 3、复制并更改sample_mysql_loader.py
```shell
cp databuilder/example/scripts/sample_mysql_loader.py databuilder/example/scripts/sample_mysql_loader_test.py
```
##### 3.1  引入os,指定es与neo4j端口及地址
```shell
import os
es_host = os.getenv('CREDENTIALS_ELASTICSEARCH_PROXY_HOST', 'localhost')
neo_host = os.getenv('CREDENTIALS_NEO4J_PROXY_HOST', 'localhost')

es_port = os.getenv('CREDENTIALS_ELASTICSEARCH_PROXY_PORT', 9200)
neo_port = os.getenv('CREDENTIALS_NEO4J_PROXY_PORT', 7687)

es = Elasticsearch([
    {'host': es_host, 'port': es_port},
])

NEO4J_ENDPOINT = f'bolt://{neo_host}:{neo_port}'

```

##### 3.2 修改数据库连接信息
```shell
def connection_string():
    user = 'root'
    host = 'localhost'
    password = 'root'
    port = '3306'
    db = 'tlmall_oauth'
    return "mysql+pymysql://%s:%s@%s:%s/%s" % (user, password, host, port, db)

    where_clause_suffix = textwrap.dedent("""
        where c.table_schema = 'tlmall_oauth'
    """)

```
##### 3.3 因为python3不支持mysql，改用pymysql，那么需要下载插件
```shell
pip3 install pymysql
```




























































































