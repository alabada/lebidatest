
# 特别注意：
- 需要引入百度富文本第三方自定义包`webapp/WEB_INF/lib/ueditor-1.1.2.jar`

- 需要redis环境用来存储session

- 默认管理员帐号：admin，密码：123456 

- 数据库已经搬到服务器：106.41.71.184
  需要本地运行一个redis。
  Windows运行方法如下：
    - 下载redis包，解压到某目录下；
    - 在该目录下，打开命令行工具，执行：redis-server.exe redis.windows.conf；
    - redis启动后，保持在开启状态。

# 创建数据库：

1.创建数据库(以utf-8编码创建数据库)
    CREATE DATABASE lebida DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

2.创建表(命令行建表注释会乱码，使用Navicat软件来导入建表语句，能解决这个问题)
    source E:/00-self/Blbd/init/sql/lbd_create_tables.sql;
    source /root/sql/lbd_create_tables.sql;
    
3.为服务器返回客户端的结果指定字符集
    set names 'utf8';

4.插入初始化数据
    source E:/00-self/Blbd/init/sql/lbd_init_data.sql;
    source /root/sql/lbd_init_data.sql;


6.运行
    管理员帐号：admin
    密码：123456


7.图片管理
    图片上传成功将返回json字段如下：
    {"message":"上传完成","result":[{"fileServerKey":null,"fileName":"Penguins.jpg","saveFileName":"Penguins.jpg","filePath":"saveDirectory","fileSize":"777835","fileSource":"fileSource","code":200,"fileMd5":"9d377b10ce778c4938b3c7e2c63a229a","isCompress":1,"compressImageMsg":[{"fileSize":46868,"fileMd5":"88e36e51c0f5ebe4ece819df546b1656","filePath":"saveDirectory","fileName":"Penguins_557x500.jpg","fileSource":"fileSource"},{"fileSize":43322,"fileMd5":"af0ae182611add5a092a5c6dc4ee779d","filePath":"saveDirectory","fileName":"Penguins_720x360.jpg","fileSource":"fileSource"},{"fileSize":78812,"fileMd5":"88e973b1a9842df72e7a85878f28a66f","filePath":"saveDirectory","fileName":"Penguins_1890x270.jpg","fileSource":"fileSource"}]}],"code":200,"fileServerKey":null}
    
    图片下载路径示例：
    http://localhost:8082/lbd/download/downloadFile.shtml?filePath=fileSource/saveDirectory/zoro.jpg
    
    图片上传路径示例：post方式提交
    http://localhost:8082/lbd/upload/fileupload.shtml
##百度富文本使用
 - 引入相关包
 ```aidl
  <script type="text/javascript" charset="utf-8" src="${basePath}/ueditor.config.js"></script>
  <script type="text/javascript" charset="utf-8" src="${basePath}/ueditor.all.js"> </script>
  <script type="text/javascript" charset="utf-8" src="${basePath}/lang/zh-cn/zh-cn.js"></script>
```

 - 对应的html
 ```aidl
<script id="editor" type="text/plain" style="width:80%;height:400px;"></script>
```

- 几个DEMO
```aidl
 // 获得整个html的内容
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    // 获得内容
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    
     // 写入内容
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
        
     // 获得纯文本
    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    
```
>更多可以参考[Ueditor官方文档](http://ueditor.baidu.com/website/)

# 群发消息
main文件下有调试类

群发图文消息，务必保证服务器该文件夹下有该图片。
/home/lebida/filesystem/uploadFiles/thumbMedia/liangzehe.png

