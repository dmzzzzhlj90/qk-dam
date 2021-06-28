# dm-datastandards
Version |  Update Time  | Status | Author |  Description
---|---|---|---|---
v2021-06-25 13:37:35|2021-06-25 13:37:35|auto|@daomingzhu|Created by smart-doc



## 数据标准excel导入导出功能接口
### 业务术语excel导出
**URL:** http://172.31.0.44/excel/term/download

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 业务术语excel导出

**Request-example:**
```
curl -X POST -i http://172.31.0.44/excel/term/download
```

**Response-example:**
```
Doesn't return a value.
```

### 业务术语excel导入
**URL:** http://172.31.0.44/excel/term/upload

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 业务术语excel导入

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.44/excel/term/upload
```

**Response-example:**
```
{
	
}
```

### 数据标准基本信息excel导出
**URL:** http://172.31.0.44/excel/basic/info/download

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 数据标准基本信息excel导出

**Request-example:**
```
curl -X POST -i http://172.31.0.44/excel/basic/info/download
```

**Response-example:**
```
Doesn't return a value.
```

### 数据标准基本信息excel 导入
**URL:** http://172.31.0.44/excel/basic/info/upload

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 数据标准基本信息excel 导入

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.44/excel/basic/info/upload
```

**Response-example:**
```
{
	
}
```

### 码表信息excel导出
**URL:** http://172.31.0.44/excel/code/term/download

**Type:** POST

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 码表信息excel导出

**Request-example:**
```
curl -X POST -i http://172.31.0.44/excel/code/term/download
```

**Response-example:**
```
Doesn't return a value.
```

### 码表信息excel导入
**URL:** http://172.31.0.44/excel/code/term/upload

**Type:** POST

**Author:** wjq

**Content-Type:** multipart/form-data

**Description:** 码表信息excel导入

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
file|file|No comments found.|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: multipart/form-data' -i http://172.31.0.44/excel/code/term/upload
```

**Response-example:**
```
{
	
}
```

## 数据标准业务术语接口
### 查询业务术语信息
**URL:** http://172.31.0.44/term/query

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
curl -X GET -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/term/query --data '{
	"page": 1,
	"size": 10,
	"sortStr": "yl11gc"
}'
```

**Response-example:**
```
{
	
}
```

### 新增业务术语信息
**URL:** http://172.31.0.44/term/add

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
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/term/add --data '{
	"id": 122,
	"chineseName": "明杰.陆",
	"englishName": "明杰.陆",
	"shortEnglishName": "明杰.陆",
	"rootName": "明杰.陆",
	"state": 1
}'
```

**Response-example:**
```
{
	
}
```

### 编辑业务术语信息
**URL:** http://172.31.0.44/term/update

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
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/term/update --data '{
	"id": 683,
	"chineseName": "明杰.陆",
	"englishName": "明杰.陆",
	"shortEnglishName": "明杰.陆",
	"rootName": "明杰.陆",
	"state": 1
}'
```

**Response-example:**
```
{
	
}
```

### 删除业务术语信息
**URL:** http://172.31.0.44/term/delete/{id}

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
curl -X DELETE -i http://172.31.0.44/term/delete/233
```

**Response-example:**
```
{
	
}
```

## 数据标准标准信息接口
### 查询标准信息
**URL:** http://172.31.0.44/basic/info/query/{dsdLevelId};	http:/172.31.0.44/basic/info/query

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 查询标准信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
dsdLevelId|string|No comments found.|false|-

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
page|int32|No comments found.|false|-
size|int32|No comments found.|false|-
sortStr|string|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/basic/info/query/144?page=1&sortStr=3wezhi&size=10
```

**Response-example:**
```
{
	
}
```

### 新增标准信息
**URL:** http://172.31.0.44/basic/info/add

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
dataType|string|数据类型|true|-
dataCapacity|string|数据容量|true|-
useCodeId|string|引用码表|true|-
codeCol|string|码表字段|false|-
dsdLevel|string|标准层级|true|-
dsdLevelId|string|标准层级ID|false|-
description|string|描述|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/basic/info/add --data '{
	"id": 924,
	"dsdName": "明杰.陆",
	"dsdCode": "63908",
	"colName": "明杰.陆",
	"dataType": "tvolhs",
	"dataCapacity": "ywd577",
	"useCodeId": "144",
	"codeCol": "q9g1yc",
	"dsdLevel": "slpeko",
	"dsdLevelId": "144",
	"description": "kmcclr"
}'
```

**Response-example:**
```
{
	
}
```

### 编辑标准信息
**URL:** http://172.31.0.44/basic/info/update

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
dataType|string|数据类型|true|-
dataCapacity|string|数据容量|true|-
useCodeId|string|引用码表|true|-
codeCol|string|码表字段|false|-
dsdLevel|string|标准层级|true|-
dsdLevelId|string|标准层级ID|false|-
description|string|描述|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/basic/info/update --data '{
	"id": 22,
	"dsdName": "明杰.陆",
	"dsdCode": "63908",
	"colName": "明杰.陆",
	"dataType": "xpk04z",
	"dataCapacity": "f6z1o2",
	"useCodeId": "144",
	"codeCol": "ao3jrm",
	"dsdLevel": "gsncee",
	"dsdLevelId": "144",
	"description": "ggr2j3"
}'
```

**Response-example:**
```
{
	
}
```

### 删除标准信息
**URL:** http://172.31.0.44/basic/info/delete/{id}

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
curl -X DELETE -i http://172.31.0.44/basic/info/delete/105
```

**Response-example:**
```
{
	
}
```

## 数据标准目录接口
### 获取数据标准分类目录树
**URL:** http://172.31.0.44/dir/tree

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取数据标准分类目录树

**Request-example:**
```
curl -X GET -i http://172.31.0.44/dir/tree
```

**Response-example:**
```
{
	
}
```

### 新增数据标准分类目录
**URL:** http://172.31.0.44/dir/add

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
description|string|描述|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/dir/add --data '{
	"id": 647,
	"dirDsdId": "144",
	"dirDsdName": "明杰.陆",
	"parentId": "144",
	"description": "ybqb53"
}'
```

**Response-example:**
```
{
	
}
```

### 编辑数据标准分类目录
**URL:** http://172.31.0.44/dir/update

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
description|string|描述|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/dir/update --data '{
	"id": 704,
	"dirDsdId": "144",
	"dirDsdName": "明杰.陆",
	"parentId": "144",
	"description": "8kz69w"
}'
```

**Response-example:**
```
{
	
}
```

### 标准目录单子节点删除方式
**URL:** http://172.31.0.44/dir/delete/{id}

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
curl -X DELETE -i http://172.31.0.44/dir/delete/892
```

**Response-example:**
```
{
	
}
```

### 标准目录支持根节点关联删除子节点方式
**URL:** http://172.31.0.44/dir/delete/root/{id}

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
curl -X DELETE -i http://172.31.0.44/dir/delete/root/270
```

**Response-example:**
```
{
	
}
```

## 数据标准码表术语接口
### 查询数据标准码表术语信息
**URL:** http://172.31.0.44/code/term/query/{codeDirId};	http:/172.31.0.44/code/term/query

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 查询数据标准码表术语信息

**Path-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
codeDirId|string|No comments found.|false|-

**Query-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
page|int32|No comments found.|false|-
size|int32|No comments found.|false|-
sortStr|string|No comments found.|false|-

**Request-example:**
```
curl -X GET -i http://172.31.0.44/code/term/query/144?sortStr=xoj7ae&size=10&page=1
```

**Response-example:**
```
{
	
}
```

### 新增数据标准码表术语信息
**URL:** http://172.31.0.44/code/term/add

**Type:** POST

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 新增数据标准码表术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
codeDirId|string|码表分类编码|true|-
codeId|string|码表编码|true|-
codeName|string|码表名称|true|-
termId|int32|数据类型编码|false|-
description|string|描述|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/code/term/add --data '{
	"id": 355,
	"codeDirId": "144",
	"codeId": "144",
	"codeName": "明杰.陆",
	"termId": 416,
	"description": "pngced"
}'
```

**Response-example:**
```
{
	
}
```

### 编辑数据标准码表术语信息
**URL:** http://172.31.0.44/code/term/update

**Type:** PUT

**Author:** wjq

**Content-Type:** application/json; charset=utf-8

**Description:** 编辑数据标准码表术语信息

**Body-parameters:**

Parameter | Type|Description|Required|Since
---|---|---|---|---
id|int32|ID编号|false|-
codeDirId|string|码表分类编码|true|-
codeId|string|码表编码|true|-
codeName|string|码表名称|true|-
termId|int32|数据类型编码|false|-
description|string|描述|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/code/term/update --data '{
	"id": 179,
	"codeDirId": "144",
	"codeId": "144",
	"codeName": "明杰.陆",
	"termId": 270,
	"description": "itc6nd"
}'
```

**Response-example:**
```
{
	
}
```

### 删除数据标准码表术语信息
**URL:** http://172.31.0.44/code/term/delete/{id}

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
curl -X DELETE -i http://172.31.0.44/code/term/delete/846
```

**Response-example:**
```
{
	
}
```

## 数据标准码表目录接口
### 获取数据标准码表分类目录树
**URL:** http://172.31.0.44/code/dir/tree

**Type:** GET

**Author:** wjq

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 获取数据标准码表分类目录树

**Request-example:**
```
curl -X GET -i http://172.31.0.44/code/dir/tree
```

**Response-example:**
```
{
	
}
```

### 新增码表分类目录
**URL:** http://172.31.0.44/code/dir/add

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
description|string|描述|false|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/code/dir/add --data '{
	"id": 912,
	"codeDirId": "144",
	"codeDirName": "明杰.陆",
	"parentId": "144",
	"description": "zcn6o1"
}'
```

**Response-example:**
```
{
	
}
```

### 编辑码表分类目录
**URL:** http://172.31.0.44/code/dir/update

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
description|string|描述|false|-

**Request-example:**
```
curl -X PUT -H 'Content-Type: application/json; charset=utf-8' -i http://172.31.0.44/code/dir/update --data '{
	"id": 964,
	"codeDirId": "144",
	"codeDirName": "明杰.陆",
	"parentId": "144",
	"description": "dmvxeo"
}'
```

**Response-example:**
```
{
	
}
```

### 码表目录单叶子节点删除方式
**URL:** http://172.31.0.44/code/dir/delete/{id}

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
curl -X DELETE -i http://172.31.0.44/code/dir/delete/987
```

**Response-example:**
```
{
	
}
```

### 码表目录支持根节点删除关联删除叶子节点方式
**URL:** http://172.31.0.44/code/dir/delete/root/{id}

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
curl -X DELETE -i http://172.31.0.44/code/dir/delete/root/197
```

**Response-example:**
```
{
	
}
```


