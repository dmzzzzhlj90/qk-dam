# dm-datastandards
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
v2021-07-27 17:30:14|2021-07-27 17:30:14|auto|@daomingzhu|Created by smart-doc



## 数据标准__excel导入导出功能接口
### 业务术语excel__全部导出数据
**URL:** http://172.31.0.80/excel/term/download

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 业务术语excel__全部导出数据

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/term/download
```

**Response-example:**
```
Doesn't return a value.
```

### 业务术语__excel导入数据
**URL:** http://172.31.0.80/excel/term/upload

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 业务术语__excel导入数据

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.80/excel/term/upload
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
  "retCode": "20092",
  "retMsg": "ozlsa2",
  "tips": "sjtu3y",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 标准基本信息excel__全部导出数据
**URL:** http://172.31.0.80/excel/basic/info/download

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 标准基本信息excel__全部导出数据

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/basic/info/download
```

**Response-example:**
```
Doesn't return a value.
```

### 标准基本信息excel__导出数据 (根据标准分类目录Id)
**URL:** http://172.31.0.80/excel/basic/info/download/dirDsdId

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 标准基本信息excel__导出数据 (根据标准分类目录Id)

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
dirDsdId|string|No comments found.|true|-

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/basic/info/download/dirDsdId --data 'dirDsdId=160'
```

**Response-example:**
```
Doesn't return a value.
```

### 标准基本信息excel__导入数据(默认根据Excel中选择的层级进行导入)
**URL:** http://172.31.0.80/excel/basic/info/upload

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 标准基本信息excel__导入数据(默认根据Excel中选择的层级进行导入)

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.80/excel/basic/info/upload
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
  "retCode": "20092",
  "retMsg": "cpk6jo",
  "tips": "pbn6lv",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 标准基本信息excel__导入数据 (根据标准分类目录Id)
**URL:** http://172.31.0.80/excel/basic/info/upload/dirDsdId

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 标准基本信息excel__导入数据 (根据标准分类目录Id)

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-
dirDsdId|string|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.80/excel/basic/info/upload/dirDsdId --data 'dirDsdId=160'
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
  "retCode": "20092",
  "retMsg": "02bj6e",
  "tips": "35m561",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 码表信息excel__全部导出数据
**URL:** http://172.31.0.80/excel/code/term/download

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 码表信息excel__全部导出数据

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/code/term/download
```

**Response-example:**
```
Doesn't return a value.
```

### 码表信息excel__导出数据 (根据码表分类目录Id)
**URL:** http://172.31.0.80/excel/code/term/download/codeDirId

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 码表信息excel__导出数据 (根据码表分类目录Id)

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
codeDirId|string|No comments found.|true|-

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/code/term/download/codeDirId --data 'codeDirId=160'
```

**Response-example:**
```
Doesn't return a value.
```

### 码表信息excel_导入数据(根据码表分类目录Id)
**URL:** http://172.31.0.80/excel/code/term/upload/codeDirId

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 码表信息excel_导入数据(根据码表分类目录Id)

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-
codeDirId|string|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.80/excel/code/term/upload/codeDirId --data 'codeDirId=160'
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
  "retCode": "20092",
  "retMsg": "3f19sn",
  "tips": "1svfs8",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 码表信息excel__导入数据(默认根据Excel中选择的码表层级进行导入)
**URL:** http://172.31.0.80/excel/code/term/upload

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 码表信息excel__导入数据(默认根据Excel中选择的码表层级进行导入)

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.80/excel/code/term/upload
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
  "retCode": "20092",
  "retMsg": "rlralx",
  "tips": "lr1lf2",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 标准基本信息excel__模板下载
**URL:** http://172.31.0.80/excel/basic/info/upload/template

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 标准基本信息excel__模板下载

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/basic/info/upload/template
```

**Response-example:**
```
Doesn't return a value.
```

### 码表信息excel__模板下载
**URL:** http://172.31.0.80/excel/code/term/download/template

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 码表信息excel__模板下载

**Request-example:**
```
curl -X POST -i http://172.31.0.80/excel/code/term/download/template
```

**Response-example:**
```
Doesn't return a value.
```

## 数据标准__业务术语接口
### 查询业务术语信息
**URL:** http://172.31.0.80/term/query

**Type:** GET

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 查询业务术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
page|int32|No comments found.|false|-
size|int32|No comments found.|false|-
sortStr|string|No comments found.|false|-

**Request-example:**
```
curl -X GET -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/term/query --data '{
  "page": 1,
  "size": 10,
  "sortStr": "27csue"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-
└─total|int64|数据总量|-
└─pageNum|int32|当前页|-
└─pageSize|int32|每页大小|-
└─list|array|返回数据data|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|int32|ID编号|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─chineseName|string|中文名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─englishName|string|英文名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─shortEnglishName|string|英文简称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─rootName|string|词根名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─state|int32|状态|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "khlh69",
  "tips": "1gzo94",
  "data": {
    "total": 400,
    "pageNum": 284,
    "pageSize": 10,
    "list": [
      {
        "id": 51,
        "chineseName": "瑞霖.龙",
        "englishName": "瑞霖.龙",
        "shortEnglishName": "瑞霖.龙",
        "rootName": "瑞霖.龙",
        "state": 3
      }
    ]
  }
}
```

### 新增业务术语信息
**URL:** http://172.31.0.80/term/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 新增业务术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
chineseName|string|中文名称|true|-
englishName|string|英文名称|true|-
shortEnglishName|string|英文简称|true|-
rootName|string|词根名称|true|-
state|int32|状态|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/term/add --data '{
  "id": 308,
  "chineseName": "瑞霖.龙",
  "englishName": "瑞霖.龙",
  "shortEnglishName": "瑞霖.龙",
  "rootName": "瑞霖.龙",
  "state": 3
}'
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
  "retCode": "20092",
  "retMsg": "9lvnkd",
  "tips": "su616a",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 编辑业务术语信息
**URL:** http://172.31.0.80/term/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 编辑业务术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
chineseName|string|中文名称|true|-
englishName|string|英文名称|true|-
shortEnglishName|string|英文简称|true|-
rootName|string|词根名称|true|-
state|int32|状态|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/term/update --data '{
  "id": 732,
  "chineseName": "瑞霖.龙",
  "englishName": "瑞霖.龙",
  "shortEnglishName": "瑞霖.龙",
  "rootName": "瑞霖.龙",
  "state": 3
}'
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
  "retCode": "20092",
  "retMsg": "fvv4od",
  "tips": "glaxma",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 删除业务术语信息
**URL:** http://172.31.0.80/term/delete/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 删除业务术语信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/term/delete/1
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
  "retCode": "20092",
  "retMsg": "43rxua",
  "tips": "i7pnof",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

## 数据标准__标准基本信息接口
### 统一查询标准信息入口
**URL:** http://172.31.0.80/basic/info/query

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 统一查询标准信息入口

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
pagination|object|No comments found.|false|-
└─page|int32|No comments found.|false|-
└─size|int32|No comments found.|false|-
└─sortStr|string|No comments found.|false|-
dsdName|string|标准名称|false|-
dsdCode|string|标准代码|false|-
dsdLevel|string|标准层级|false|-
dsdLevelId|string|标准层级ID|false|-
beginDay|string|开始时间|false|-
endDay|string|结束时间|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/basic/info/query --data '{
  "pagination": {
    "page": 1,
    "size": 10,
    "sortStr": "qg8dgw"
  },
  "dsdName": "瑞霖.龙",
  "dsdCode": "20092",
  "dsdLevel": "c0r6fn",
  "dsdLevelId": "160",
  "beginDay": "u3vxpq",
  "endDay": "r75vz5"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-
└─total|int64|数据总量|-
└─pageNum|int32|当前页|-
└─pageSize|int32|每页大小|-
└─list|array|返回数据data|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|int32|ID编号|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dsdName|string|标准名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dsdCode|string|标准代码|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─colName|string|字段名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dataType|string|数据类型|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dataCapacity|string|数据容量|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─useCodeLevel|string|引用码表|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeCol|string|码表字段|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dsdLevel|string|标准层级|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dsdLevelId|string|标准层级ID|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─dsdLevelName|string|标准层级目录名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─description|string|描述|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─gmtModified|string|修改时间|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "b8mpcc",
  "tips": "jexctq",
  "data": {
    "total": 734,
    "pageNum": 327,
    "pageSize": 10,
    "list": [
      {
        "id": 417,
        "dsdName": "瑞霖.龙",
        "dsdCode": "20092",
        "colName": "瑞霖.龙",
        "dataType": "q7x98o",
        "dataCapacity": "b1cesz",
        "useCodeLevel": "c8ngxk",
        "codeCol": "11eofk",
        "dsdLevel": "khej9w",
        "dsdLevelId": "160",
        "dsdLevelName": "瑞霖.龙",
        "description": "hbqqeh",
        "gmtModified": "2021-07-27 17:30:16"
      }
    ]
  }
}
```

### 新增标准信息
**URL:** http://172.31.0.80/basic/info/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 新增标准信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
dsdName|string|标准名称|true|-
dsdCode|string|标准代码|true|-
colName|string|字段名称|true|-
dataType|string|数据类型|false|-
dataCapacity|string|数据容量|false|-
useCodeLevel|string|引用码表|false|-
codeCol|string|码表字段|false|-
dsdLevel|string|标准层级|true|-
dsdLevelId|string|标准层级ID|true|-
dsdLevelName|string|标准层级目录名称|false|-
description|string|描述|false|-
gmtModified|string|修改时间|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/basic/info/add --data '{
  "id": 510,
  "dsdName": "瑞霖.龙",
  "dsdCode": "20092",
  "colName": "瑞霖.龙",
  "dataType": "o4bhmo",
  "dataCapacity": "zkygr9",
  "useCodeLevel": "azea50",
  "codeCol": "a5msys",
  "dsdLevel": "diekln",
  "dsdLevelId": "160",
  "dsdLevelName": "瑞霖.龙",
  "description": "846mmg",
  "gmtModified": "2021-07-27 17:30:16"
}'
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
  "retCode": "20092",
  "retMsg": "e11wbq",
  "tips": "9ezieo",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 编辑标准信息
**URL:** http://172.31.0.80/basic/info/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 编辑标准信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
dsdName|string|标准名称|true|-
dsdCode|string|标准代码|true|-
colName|string|字段名称|true|-
dataType|string|数据类型|false|-
dataCapacity|string|数据容量|false|-
useCodeLevel|string|引用码表|false|-
codeCol|string|码表字段|false|-
dsdLevel|string|标准层级|true|-
dsdLevelId|string|标准层级ID|true|-
dsdLevelName|string|标准层级目录名称|false|-
description|string|描述|false|-
gmtModified|string|修改时间|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/basic/info/update --data '{
  "id": 303,
  "dsdName": "瑞霖.龙",
  "dsdCode": "20092",
  "colName": "瑞霖.龙",
  "dataType": "zvvxw0",
  "dataCapacity": "vb3cfr",
  "useCodeLevel": "tp5uqj",
  "codeCol": "k2c7om",
  "dsdLevel": "mcrq9m",
  "dsdLevelId": "160",
  "dsdLevelName": "瑞霖.龙",
  "description": "yrak0j",
  "gmtModified": "2021-07-27 17:30:16"
}'
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
  "retCode": "20092",
  "retMsg": "c463oq",
  "tips": "te4p9z",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 删除标准信息
**URL:** http://172.31.0.80/basic/info/delete/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 删除标准信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/basic/info/delete/997
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
  "retCode": "20092",
  "retMsg": "j3syw9",
  "tips": "g1ee5e",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 批量删除标准信息
**URL:** http://172.31.0.80/basic/info/bulk/delete/{ids}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 批量删除标准信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
ids|string|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/basic/info/bulk/delete/ixsdvy
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
  "retCode": "20092",
  "retMsg": "2pdpb1",
  "tips": "slncwu",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 根据数据类型返回对应数据容量列表
**URL:** http://172.31.0.80/basic/info/query/dataCapacity/by/dataType/{dataType}

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据数据类型返回对应数据容量列表

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
dataType|string|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.80/basic/info/query/dataCapacity/by/dataType/1net57
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|array|返回数据|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "sjnzbr",
  "tips": "wnwjtd",
  "data": [
    "ay55kw",
    "qenn8d"
  ]
}
```

## 数据标准__目录接口
### 获取数据标准分类目录树
**URL:** http://172.31.0.80/dir/tree

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取数据标准分类目录树

**Request-example:**
```
curl -X GET -i http://172.31.0.80/dir/tree
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|array|返回数据|-
└─id|int32|No comments found.|-
└─dirDsdId|string|No comments found.|-
└─dirDsdName|string|No comments found.|-
└─parentId|string|No comments found.|-
└─dsdDirLevel|string|No comments found.|-
└─children|array|No comments found.|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "vf75f4",
  "tips": "ql3m1d",
  "data": [
    {
      "id": 354,
      "dirDsdId": "160",
      "dirDsdName": "瑞霖.龙",
      "parentId": "160",
      "dsdDirLevel": "zpcp8w",
      "children": [
        {
          "$ref": ".."
        }
      ]
    }
  ]
}
```

### 新增数据标准分类目录
**URL:** http://172.31.0.80/dir/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 新增数据标准分类目录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|主键ID|false|-
dirDsdId|string|数据标准分类编号|false|-
dirDsdName|string|数据标准分类名称|true|-
parentId|string|父级id|true|-
dsdDirLevel|string|目录节点层级|true|-
description|string|描述|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/dir/add --data '{
  "id": 445,
  "dirDsdId": "160",
  "dirDsdName": "瑞霖.龙",
  "parentId": "160",
  "dsdDirLevel": "wigjfj",
  "description": "srfwd1"
}'
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
  "retCode": "20092",
  "retMsg": "rseqme",
  "tips": "n1ya26",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 编辑数据标准分类目录
**URL:** http://172.31.0.80/dir/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 编辑数据标准分类目录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|主键ID|false|-
dirDsdId|string|数据标准分类编号|false|-
dirDsdName|string|数据标准分类名称|true|-
parentId|string|父级id|true|-
dsdDirLevel|string|目录节点层级|true|-
description|string|描述|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/dir/update --data '{
  "id": 678,
  "dirDsdId": "160",
  "dirDsdName": "瑞霖.龙",
  "parentId": "160",
  "dsdDirLevel": "w84a54",
  "description": "npppdc"
}'
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
  "retCode": "20092",
  "retMsg": "ggz93c",
  "tips": "t1r32d",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 标准目录单子节点删除方式
**URL:** http://172.31.0.80/dir/delete/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 标准目录单子节点删除方式

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/dir/delete/283
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
  "retCode": "20092",
  "retMsg": "wadvu9",
  "tips": "lh4oj6",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 标准目录支持根节点关联删除子节点方式
**URL:** http://172.31.0.80/dir/delete/root/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 标准目录支持根节点关联删除子节点方式

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/dir/delete/root/381
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
  "retCode": "20092",
  "retMsg": "d9zi04",
  "tips": "7rehzu",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

## 数据标准__码表术语接口
### 统一查询标准信息入口
**URL:** http://172.31.0.80/code/term/query

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 统一查询标准信息入口

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
pagination|object|No comments found.|false|-
└─page|int32|No comments found.|false|-
└─size|int32|No comments found.|false|-
└─sortStr|string|No comments found.|false|-
codeDirId|string|码表分类编码|false|-
codeId|string|码表编码|false|-
codeName|string|码表名称|false|-
beginDay|string|开始时间|false|-
endDay|string|结束时间|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/term/query --data '{
  "pagination": {
    "page": 1,
    "size": 10,
    "sortStr": "wbjhd8"
  },
  "codeDirId": "160",
  "codeId": "160",
  "codeName": "瑞霖.龙",
  "beginDay": "5ltnw3",
  "endDay": "8fc5gs"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-
└─total|int64|数据总量|-
└─pageNum|int32|当前页|-
└─pageSize|int32|每页大小|-
└─list|array|返回数据data|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|int32|ID编号|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeTableChnName|string|中文表名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeTableEnName|string|英文表名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeId|string|码表编码|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeName|string|码表名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeDirLevel|string|码表层级|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeDirId|string|码表目录Id|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─codeDirLevelName|string|码表层级目录名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─termId|int32|数据类型编码|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─description|string|描述|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─gmtModified|string|修改时间|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "owe4o7",
  "tips": "c7akh6",
  "data": {
    "total": 207,
    "pageNum": 768,
    "pageSize": 10,
    "list": [
      {
        "id": 519,
        "codeTableChnName": "瑞霖.龙",
        "codeTableEnName": "瑞霖.龙",
        "codeId": "160",
        "codeName": "瑞霖.龙",
        "codeDirLevel": "imlv3s",
        "codeDirId": "160",
        "codeDirLevelName": "瑞霖.龙",
        "termId": 100,
        "description": "4kfoap",
        "gmtModified": "2021-07-27 17:30:16"
      }
    ]
  }
}
```

### 新增数据标准码表术语信息
**URL:** http://172.31.0.80/code/term/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 新增数据标准码表术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
codeTableChnName|string|中文表名称|true|-
codeTableEnName|string|英文表名称|true|-
codeId|string|码表编码|true|-
codeName|string|码表名称|true|-
codeDirLevel|string|码表层级|true|-
codeDirId|string|码表目录Id|true|-
codeDirLevelName|string|码表层级目录名称|false|-
termId|int32|数据类型编码|false|-
description|string|描述|false|-
gmtModified|string|修改时间|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/term/add --data '{
  "id": 950,
  "codeTableChnName": "瑞霖.龙",
  "codeTableEnName": "瑞霖.龙",
  "codeId": "160",
  "codeName": "瑞霖.龙",
  "codeDirLevel": "n6akdf",
  "codeDirId": "160",
  "codeDirLevelName": "瑞霖.龙",
  "termId": 759,
  "description": "e477q7",
  "gmtModified": "2021-07-27 17:30:16"
}'
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
  "retCode": "20092",
  "retMsg": "c8yxfh",
  "tips": "xteuoo",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 批量新增数据标准码表术语信息
**URL:** http://172.31.0.80/code/term/bulk/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 批量新增数据标准码表术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
codeTableChnName|string|中文表名称|true|-
codeTableEnName|string|英文表名称|true|-
codeId|string|码表编码|true|-
codeName|string|码表名称|true|-
codeDirLevel|string|码表层级|true|-
codeDirId|string|码表目录Id|true|-
codeDirLevelName|string|码表层级目录名称|false|-
termId|int32|数据类型编码|false|-
description|string|描述|false|-
gmtModified|string|修改时间|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/term/bulk/add --data '[
  {
    "id": 904,
    "codeTableChnName": "瑞霖.龙",
    "codeTableEnName": "瑞霖.龙",
    "codeId": "160",
    "codeName": "瑞霖.龙",
    "codeDirLevel": "kc995n",
    "codeDirId": "160",
    "codeDirLevelName": "瑞霖.龙",
    "termId": 840,
    "description": "flijik",
    "gmtModified": "2021-07-27 17:30:16"
  }
]'
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
  "retCode": "20092",
  "retMsg": "xqan1l",
  "tips": "uzauqx",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 编辑数据标准码表术语信息
**URL:** http://172.31.0.80/code/term/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 编辑数据标准码表术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
codeTableChnName|string|中文表名称|true|-
codeTableEnName|string|英文表名称|true|-
codeId|string|码表编码|true|-
codeName|string|码表名称|true|-
codeDirLevel|string|码表层级|true|-
codeDirId|string|码表目录Id|true|-
codeDirLevelName|string|码表层级目录名称|false|-
termId|int32|数据类型编码|false|-
description|string|描述|false|-
gmtModified|string|修改时间|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/term/update --data '{
  "id": 924,
  "codeTableChnName": "瑞霖.龙",
  "codeTableEnName": "瑞霖.龙",
  "codeId": "160",
  "codeName": "瑞霖.龙",
  "codeDirLevel": "w4rf1x",
  "codeDirId": "160",
  "codeDirLevelName": "瑞霖.龙",
  "termId": 148,
  "description": "lzsuul",
  "gmtModified": "2021-07-27 17:30:16"
}'
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
  "retCode": "20092",
  "retMsg": "zf2hbe",
  "tips": "jyigey",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 批量编辑数据标准码表术语信息TODO 批量更新考虑效率问题
**URL:** http://172.31.0.80/code/term/bulk/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 批量编辑数据标准码表术语信息
TODO 批量更新考虑效率问题

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
codeTableChnName|string|中文表名称|true|-
codeTableEnName|string|英文表名称|true|-
codeId|string|码表编码|true|-
codeName|string|码表名称|true|-
codeDirLevel|string|码表层级|true|-
codeDirId|string|码表目录Id|true|-
codeDirLevelName|string|码表层级目录名称|false|-
termId|int32|数据类型编码|false|-
description|string|描述|false|-
gmtModified|string|修改时间|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/term/bulk/update --data '[
  {
    "id": 80,
    "codeTableChnName": "瑞霖.龙",
    "codeTableEnName": "瑞霖.龙",
    "codeId": "160",
    "codeName": "瑞霖.龙",
    "codeDirLevel": "mmmd18",
    "codeDirId": "160",
    "codeDirLevelName": "瑞霖.龙",
    "termId": 920,
    "description": "s2kju3",
    "gmtModified": "2021-07-27 17:30:16"
  }
]'
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
  "retCode": "20092",
  "retMsg": "x2qbrb",
  "tips": "7bn66a",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 删除数据标准码表术语信息
**URL:** http://172.31.0.80/code/term/delete/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 删除数据标准码表术语信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/code/term/delete/239
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
  "retCode": "20092",
  "retMsg": "iwgzat",
  "tips": "m3jwno",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 批量删除标准信息
**URL:** http://172.31.0.80/code/term/bulk/delete/{ids}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 批量删除标准信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
ids|string|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/code/term/bulk/delete/dqwvaa
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
  "retCode": "20092",
  "retMsg": "3npy4n",
  "tips": "hc280s",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 根据id获取码表详情信息
**URL:** http://172.31.0.80/code/term/get/by/{id}

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 根据id获取码表详情信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X GET -i http://172.31.0.80/code/term/get/by/711
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|object|返回数据|-
└─id|int32|ID编号|-
└─codeTableChnName|string|中文表名称|-
└─codeTableEnName|string|英文表名称|-
└─codeId|string|码表编码|-
└─codeName|string|码表名称|-
└─codeDirLevel|string|码表层级|-
└─codeDirId|string|码表目录Id|-
└─codeDirLevelName|string|码表层级目录名称|-
└─termId|int32|数据类型编码|-
└─description|string|描述|-
└─gmtModified|string|修改时间|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "mc4gxz",
  "tips": "ev181w",
  "data": {
    "id": 119,
    "codeTableChnName": "瑞霖.龙",
    "codeTableEnName": "瑞霖.龙",
    "codeId": "160",
    "codeName": "瑞霖.龙",
    "codeDirLevel": "e6cqkd",
    "codeDirId": "160",
    "codeDirLevelName": "瑞霖.龙",
    "termId": 409,
    "description": "knhtpp",
    "gmtModified": "2021-07-27 17:30:16"
  }
}
```

## 数据标准__码表目录接口
### 获取数据标准码表分类目录树
**URL:** http://172.31.0.80/code/dir/tree

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取数据标准码表分类目录树

**Request-example:**
```
curl -X GET -i http://172.31.0.80/code/dir/tree
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
retCode|string|返回码|-
retMsg|string|返回说明|-
tips|string|提示|-
data|array|返回数据|-
└─id|int32|No comments found.|-
└─codeDirId|string|No comments found.|-
└─codeDirName|string|No comments found.|-
└─parentId|string|No comments found.|-
└─codeDirLevel|string|No comments found.|-
└─children|array|No comments found.|-

**Response-example:**
```
{
  "retCode": "20092",
  "retMsg": "mugkid",
  "tips": "lgs1o5",
  "data": [
    {
      "id": 460,
      "codeDirId": "160",
      "codeDirName": "瑞霖.龙",
      "parentId": "160",
      "codeDirLevel": "q92638",
      "children": [
        {
          "$ref": ".."
        }
      ]
    }
  ]
}
```

### 新增码表分类目录
**URL:** http://172.31.0.80/code/dir/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 新增码表分类目录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|主键ID|false|-
codeDirId|string|码表标准分类ID|false|-
codeDirName|string|码表标准分类名称|true|-
parentId|string|父级ID|true|-
codeDirLevel|string|码表目录层级|true|-
description|string|描述|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/dir/add --data '{
  "id": 188,
  "codeDirId": "160",
  "codeDirName": "瑞霖.龙",
  "parentId": "160",
  "codeDirLevel": "dqh9sw",
  "description": "auekpg"
}'
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
  "retCode": "20092",
  "retMsg": "v2rnyu",
  "tips": "1b9v6d",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 编辑码表分类目录
**URL:** http://172.31.0.80/code/dir/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 编辑码表分类目录

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|主键ID|false|-
codeDirId|string|码表标准分类ID|false|-
codeDirName|string|码表标准分类名称|true|-
parentId|string|父级ID|true|-
codeDirLevel|string|码表目录层级|true|-
description|string|描述|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.80/code/dir/update --data '{
  "id": 37,
  "codeDirId": "160",
  "codeDirName": "瑞霖.龙",
  "parentId": "160",
  "codeDirLevel": "5hvei4",
  "description": "8csb5m"
}'
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
  "retCode": "20092",
  "retMsg": "cpdcym",
  "tips": "ifneay",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 码表目录单叶子节点删除方式
**URL:** http://172.31.0.80/code/dir/delete/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 码表目录单叶子节点删除方式

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/code/dir/delete/480
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
  "retCode": "20092",
  "retMsg": "fmzm3p",
  "tips": "tsi0pr",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```

### 码表目录支持根节点删除关联删除叶子节点方式
**URL:** http://172.31.0.80/code/dir/delete/root/{id}

**Type:** DELETE

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 码表目录支持根节点删除关联删除叶子节点方式

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|No comments found.|true|-

**Request-example:**
```
curl -X DELETE -i http://172.31.0.80/code/dir/delete/root/917
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
  "retCode": "20092",
  "retMsg": "9fm6im",
  "tips": "5cb601",
  "data": {
    "waring": "You may have used non-display generics."
  }
}
```


