# amundsen 官方测试用例

## 安装docker
##### 1、卸载旧版本(如果安装过旧版本的话)
```shell
sudo yum remove -y docker*
```
##### 2、安装需要的软件包， yum-com.qk.dm.groovy.util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的
```shell
yum install -y yum-utils
```
##### 3、设置yum源，并更新 yum 的包索引
```shell
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum makecache fast
```
##### 4、安装docker
```shell
yum install -y docker-ce-3:19.03.9-3.el7.x86_64 # 这是指定版本安装
```
##### 5、启动并加入开机启动
```shell
systemctl start docker && systemctl enable docker
```
##### 6、验证安装是否成功(有client和service两部分表示docker安装启动都成功了)
```shell
docker version
```
##### 7、配置docker镜像加速器,可自行去阿里云上获取
```shell
cd /etc/docker
vim daemon.json
{
  "registry-mirrors": ["https://xxx.mirror.aliyuncs.com"]
}
```
##### 8、保存/重启docker服务
```shell
systemctl daemon-reload
systemctl restart docker
```
##### 9、卸载docker
```shell
yum remove -y docker*
rm -rf /etc/systemd/system/docker.service.d
rm -rf /var/lib/docker
rm -rf /var/run/docker
```
## 安装docker-compose
##### 1、下载
```shell
sudo curl -L https://github.com/docker/compose/releases/download/1.28.6/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
```
##### 2、赋予权限
```shell
sudo chmod +x /usr/local/bin/docker-compose
```
##### 3、验证
```shell
docker-compose --version
```
##### 4、卸载
```shell
sudo rm /usr/local/bin/docker-compose
```
## 搭建Amundsen
##### 1、下载
```shell
git clone --recursive git@github.com:amundsen-io/amundsen.git
```
##### 2、调整内存大小
```shell
/etc/sysctl.conf
vm.max_map_count=262144
sysctl -p
```
##### 3、进入项目目录并运行
```shell
docker-compose -f docker-amundsen.yml up
```
##### 4、单独启一个窗口进入项目下的databuilder
```shell
docker-compose -f docker-amundsen.yml up
```
##### 5、构建python摄取器并摄取示例数据
```shell
 $ python3 -m venv venv
 $ source venv/bin/activate
 $ pip3 install --upgrade pip
 $ pip3 install -r requirements.txt
 $ python3 setup.py install
 $ python3 example/scripts/sample_data_loader.py
```
##### 6、查看UI，并尝试搜索test，它应该返回一些结果
```shell
http://localhost:5000
```
##### 建议：root操作权限来执行部署，不然容易权限或各种错误





















































































































































































































































