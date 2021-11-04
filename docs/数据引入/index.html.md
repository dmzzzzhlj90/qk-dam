# dm-dataingestion
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
v2021-07-27 17:30:16|2021-07-27 17:30:16|auto|@daomingzhu|Created by smart-doc



## 定时同步数据文件
### 定时同步阿里云数据到腾讯云,执行数据脚本;
**URL:** http://172.31.0.44/timer/sync/files/data

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 定时同步阿里云数据到腾讯云,执行数据脚本;

**Request-example:**
```
curl -X GET -i http://172.31.0.44/timer/sync/files/data
```

**Response-example:**
```
Doesn't return a value.
```

## 
### 
**URL:** http://172.31.0.44/sqlloader/exec/{update}

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
update|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/exec/2021-07-27
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "6wiu7a",
  "tips": "mckkj6",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 
**URL:** http://172.31.0.44/sqlloader/exec/pici/{pici}

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
pici|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/exec/pici/173
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "wvwik6",
  "tips": "dkmh4t",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 
**URL:** http://172.31.0.44/sqlloader/exec/tablename/{tableName}

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
tableName|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/exec/tablename/建辉.钟
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "j1e0fk",
  "tips": "xqlh72",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 
**URL:** http://172.31.0.44/sqlloader/exec/tablename/pici/{tableName}/{pici}

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
tableName|string|No comments found.|true|-
pici|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/exec/tablename/pici/建辉.钟/826
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "tlm1nh",
  "tips": "bhl83r",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 
**URL:** http://172.31.0.44/sqlloader/exec/writeDest/{date}

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
date|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/exec/writeDest/2021-07-27
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "j7v4bs",
  "tips": "s0ii6j",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 
**URL:** http://172.31.0.44/sqlloader/refreshCosKeys

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/refreshCosKeys
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "eaxgae",
  "tips": "sdjemj",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 
**URL:** http://172.31.0.44/sqlloader/procUpdateToUpdated1

**Type:** GET

**Author:** daomingzhu

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sqlloader/procUpdateToUpdated1
```

**Response-example:**
```
Doesn't return a value.
```

## 原始层数据同步
### 同步阿里云数据到腾讯云
**URL:** http://172.31.0.44/sync/rizhi/files/data

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 同步阿里云数据到腾讯云

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
frontTabNamePatter|string|No comments found.|false|-
batchNum|string|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/sync/rizhi/files/data?batchNum=4d5vp4&frontTabNamePatter=pie5q4
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "9olvt9",
  "tips": "8uxdsl",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

## 操作数据引入日志表
### 更新日志表is_hive_updated状态
**URL:** http://172.31.0.44/etl/task/log/modify/is_hive/by/{tableName}/{pici}

**Type:** PUT

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 更新日志表is_hive_updated状态

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
tableName|string|No comments found.|true|-
pici|string|No comments found.|true|-

**Request-example:**
```
curl -X PUT -i http://172.31.0.44/etl/task/log/modify/is_hive/by/建辉.钟/jzksbp
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "s2pzzo",
  "tips": "cr7wyx",
  "data": 397
}
```

## 同步获取COS客户端文件信息
### 获取COS任务文件信息
**URL:** http://172.31.0.44/cos/task/files/info/{dataDay}

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取COS任务文件信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
dataDay|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/cos/task/files/info/aqay4b
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-
└─tableName|string|表名称|-
└─pici|string|批次|-
└─filePath|string|文件下载路径地址|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "n608ql",
  "tips": "d3lirn",
  "data": {
    "tableName": "建辉.钟",
    "pici": "5g4578",
    "filePath": "kcpusz"
  }
}
```

### 设置COS任务文件_objectMetadata_Header信息
**URL:** http://172.31.0.44/cos/task/files/metadata/header/info/{dataDay}

**Type:** PUT

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 设置COS任务文件_objectMetadata_Header信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
dataDay|string|No comments found.|true|-

**Request-example:**
```
curl -X PUT -i http://172.31.0.44/cos/task/files/metadata/header/info/2c69hn
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-
└─tableName|string|表名称|-
└─pici|string|批次|-
└─filePath|string|文件下载路径地址|-

**Response-example:**
```
{
  "retCode": "83997",
  "retMsg": "qnvzjv",
  "tips": "2fk8vd",
  "data": {
    "tableName": "建辉.钟",
    "pici": "35idad",
    "filePath": "cw0snk"
  }
}
```


