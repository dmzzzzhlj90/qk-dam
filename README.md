# qike 数据中台项目
## 项目目录结构
```lua
qk-dam
├── README.md                                   -- 文档说明
├── build.gradle                                -- 项目构建文件
├── common-lib                                  -- 通用域依赖lib
│   ├── dam-commons-core                        -- 通用域核心
│   ├── dam-data-redis                          -- 通用redis
│   ├── dam-datasource                          -- 通用数据源
│   ├── dam-distributed-id                      -- 分布式id
│   ├── dam-log                                 -- 通用日志工具封装
│   ├── dam-mvc-validation                      -- mvc及对象验证框架
│   ├── dam-mybatis-plus                        -- mybatis持久层框架
│   ├── dam-querydsl                            -- jpa+querydsl持久层框架
│   ├── dam-sql-loader                          -- 数据引入依赖的sql tar解析和导入
│   └── dam-sqlparser                           -- sql语言解析类
├── distro                                      -- 发布
│   ├── bin                                     -- 发布的执行文件
│   ├── conf                                    -- 发布的配置文件
│   └── target                                  -- 发布的项目文件
├── docs                                        -- 文档
│   ├── dm-dataingestion                        
│   └── dm-datastandards                        
├── etc                                         -- 环境配置
│   ├── checkstyle
│   ├── jpasup
│   └── nohttp
├── gradle                                      -- gradle wrapper
│   └── wrapper
├── gradle.properties                           -- gradle 版本依赖配置文件
├── gradlew                                     -- gradle wapper执行
├── gradlew.bat
├── qk-dam                                      -- qk数据资产项目
│   ├── qike-dm-dataingestion                   -- 数据引入
│   └── qike-dm-datastandards                   -- 数据标准
├── qk-dm-boots                                 -- 中台启动依赖项目
│   ├── qk-dam-dependencies                     -- 中台统一依赖
│   └── qk-dm-micro-services                    -- 中台微服务框架
└── settings.gradle                             -- gradle 配置settings配置
```
## 编译打包
```shell
 ./gradlew build -x test
```
## 发布工程
```shell
 ./gradlew makeReleaseJar
```
打包成功后在项目根目录distro下会有相应的jar文件
