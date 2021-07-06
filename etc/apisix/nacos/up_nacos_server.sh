#!/bin/bash
#上线数据标准
curl http://127.0.0.1:9080/apisix/admin/routes -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X POST -i -d '
{
    "uri": "/dsd/*",
    "name": "dm-datastandards",
    "upstream": {
        "service_name": "dm-datastandards",
        "type": "roundrobin",
        "discovery_type": "nacos",
        "discovery_args": {
          "namespace_id": "fb4b816f-64b3-4143-9954-33c45ce5dcf8",
          "group_name": "DM-GROUP"
        }
    }
}'
#上线数据引入
curl http://127.0.0.1:9080/apisix/admin/routes -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X POST -i -d '
{
    "uri": "/ddg/*",
    "name": "dm-dataingestion",
    "upstream": {
        "service_name": "dm-dataingestion",
        "type": "roundrobin",
        "discovery_type": "nacos",
        "discovery_args": {
          "namespace_id": "fb4b816f-64b3-4143-9954-33c45ce5dcf8",
          "group_name": "DM-GROUP"
        }
    }
}'