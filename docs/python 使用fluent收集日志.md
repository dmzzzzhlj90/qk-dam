# **python 使用fluent收集日志**

通过以下方式安装库：fluent-loggerpip

```python
$ pip install fluent-logger
```

初始化并发布记录，示例如下所示：test.py

```python
from fluent import sender
from fluent import event

sender.setup('data_center_log', host='172.31.0.80', port=24224)

event.Event('api-log', {
 'apiPath':'/pp/api/v1/test',
 'clientIp':'192.168.0.1',
 'logname':'test_log'
})
```

```python
python test.py
```

执行脚本会将日志发送到 Fluentd转发器

转发器会在发送给Fluentd收集器，收集器处理完日志最终输出到ES

在es中查看：

![image-20210624161824403](/Users/daomingzhu/Library/Application Support/typora-user-images/image-20210624161824403.png)