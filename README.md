

[接口文档](#接口文档)

+ [更新](#更新)
+ [网址模块](#网址模块)
  - [POST /url](#post-url)
  - [GET /url](#get-url)
+ [用户模块](#用户模块)
  - [POST /user/login](#post-userlogin)
  - [POST /user/register](#post-userregister)
  - [GET /user/logout](#get-userlogout)
  - [GET /user/current](#get-usercurrent)
  - [PUT /user/{id}](#put-userid)
  - [DELETE /user/{id}](#delete-userid)
+ [合同模块](#合同模块)
  - [GET /contract/crawl](#get-contractcrawl)
  - [GET /contract/list](#get-contractlist)
  - [GET /contract/{id}](#get-contractid)
  - [POST /contract](#post-contract)
  - [PUT /contract/{id}](#put-contractid)
  - [DELETE /contract/{id}](#delete-contractid)
  - [DELETE /contract](#delete-contract)

[项目部署](#项目部署)

---



# 接口文档

### 更新

- **GET /contract/crawl**

  - 目前总共支持 10 个爬虫，各个网站的测试数据如下：

    *各爬取2页的数据，单线程：

    | 网址             | 成功爬取条目数 | 耗时(秒) |
    | ---------------- | -------------- | -------- |
    | 江苏省政府采购网 | 100            | 3.33     |
    | 江西省政府采购网 | 32             | 3.51     |
    | 湖北省政府采购网 | 29             | 4.32     |
    | 浙江省政府采购网 | 25             | 4.57     |
    | 河北省政府采购网 | 99             | 5.32     |
    | 中国政府采购网   | 40             | 5.85     |
    | 天津市政府采购网 | 20             | 6.42     |
    | 北京市政府采购网 | 28             | 8.96     |
    | 安徽省政府采购网 | 21             | 14.39    |
    | 山东省政府采购网 | 19             | 32.3     |

  - 演示时最好不要用最后两个网站，太特么坑了

  - 由于不同网站提供的信息不同，北京、江西、江苏、山东的网站爬取的信息会出现部分字段为空。
  
  - 可以在参数中配置页码范围，指定页码进行爬虫。

---

### 网址模块

#### POST /url

- 将一些网址提交至后端，与user绑定，保存到数据库中。

请求地址：POST /url

请求参数：

| 参数名 | 类型     | 说明     | 必填 |
| ------ | -------- | -------- | ---- |
| urls   | String[] | url 数组 | 是   |

请求示例：

```json
{
    "urls": [
        "中国政府采购网", 
        "北京市政府采购网", 
        "湖北省政府采购网"
    ]
}
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

#### GET /url

- 获取当前用户绑定的网址(即上次配置后的网址)。
- 用户在进入"网址配置"页面时输入框中应填充用户之前的配置信息。

请求地址：GET /url

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": [
        "中国政府采购网",
        "北京市政府采购网",
        "湖北省政府采购网"
    ]
}
```

如果用户还未配置过他的 url，则会返回 `NOT FOUND`。

```json
{
    "code": 404,
    "msg": "Not found",
    "data": null
}
```

### 用户模块

#### POST /user/login

- 登录。

请求地址：POST /user/login

请求参数：

| 参数名   | 类型   | 说明   | 必填 |
| -------- | ------ | ------ | ---- |
| username | String | 用户名 | 是   |
| password | String | 密码   | 是   |

请求示例：

```json
{
    "username": "沖田総司",
    "password": "OkitaSouji"
}
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

```json
{
    "code": 403,
    "msg": "用户名或密码错误",
    "data": null
}
```

#### POST /user/register

- 注册用户。

请求地址：POST /user/register

请求参数：

| 参数名    | 类型   | 说明   | 必填      |
| --------- | ------ | ------ | --------- |
| username  | String | 用户名 | 是        |
| password  | String | 密码   | 是        |
| authority | int    | 权限   | 否，默认0 |

请求示例：

```json
{
    "username": "沖田総司",
    "password": "OkitaSouji",
    "authority": 6
}
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

```json
{
    "code": 403,
    "msg": "用户名不合法",
    "data": null
}
```

#### GET /user/logout

- 用户登出。

请求地址：GET /user/logout

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

```json
{
    "code": 403,
    "msg": "未登录用户",
    "data": null
}
```

#### GET /user/current

- 获取当前用户的信息

请求地址：GET /user/current

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": {
        "id": 23,
        "username": "沖田総司",
        "password": "OkitaSouji",
        "authority": 6,
        "careUrls": "[中国政府采购网, 北京市政府采购网, 湖北省政府采购网]",
        "urlsList": [
            "中国政府采购网",
            "北京市政府采购网",
            "湖北省政府采购网"
        ]
    }
}
```

当前没有用户登录时，会返回：

```json
{
    "code": 403,
    "msg": "用户未登录",
    "data": null
}
```

#### PUT /user/{id}

- 强行更新某个用户的数据，三个数据均为非必填项。

请求地址：PUT /user/{id}

请求参数：

| 参数名    | 类型   | 说明      | 必填 |
| --------- | ------ | --------- | ---- |
| password  | String | 密码      | 否   |
| authority | int    | 权限      | 否   |
| urls      | String | 配置的URL | 否   |

请求示例：

```json
{
    "authority": 100,
    "urls": [
        "中国政府采购网", 
        "北京市政府采购网", 
        "湖北省政府采购网"
    ]
}
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

当三个参数都为空时会返回：

```json
{
    "code": 400,
    "msg": "未输入更新项",
    "data": null
}
```

#### DELETE /user/{id}

- 删除某个用户

请求参数：无

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

```json
{
    "code": 403,
    "msg": "权限不足",
    "data": null
}
```



### 合同模块

#### GET /contract/crawl

- 请求后，后台将开始爬取并更新该用户当前配置的 url 下的合同数据

- 后端直至爬取完成，或遇到错误时，才会返回

- 由于不同网站提供的信息不同，部分字段可能为空。

请求地址：GET /contract/crawl

请求参数：

| 参数名 | 类型 | 说明             | 必填                     |
| ------ | ---- | ---------------- | ------------------------ |
| start  | int  | 起始页码         | 否，默认值由配置文件规定 |
| end    | int  | 终止页码         | 否，默认值由配置文件规定 |
| thread | int  | 开几个线程来爬取 | 否，默认值由配置文件规定 |

请求示例：

```url
http://localhost:8080/api/contract/crawl

http://localhost:8080/api/contract/crawl?start=5&end=10
```

响应示例：

1. 爬取成功，且更新了数据

```json
{
    "code": 200,
    "msg": "OK",
    "data": {
        "increment": 20
    }
}
```

2. 爬取成功，但是所有支持的 url 都没任何数据更新

```json
{
    "code": 404,
    "msg": "没爬到任何数据",
    "data": null
}
```

3. 爬取失败(错误)，通常为服务端异常

```json
{
    "code": 500,
    "msg": "server error",
    "data": null
}
```

#### GET /contract/list

- 请求当前用户配置的 url 的合同数据
- 如果参数 query 为空，则返回所有合同数据
- 如果参数 query 不为空，则返回筛选后的合同数据

请求地址：GET /contract/list

请求参数：

| 参数名 | 类型   | 说明                 | 必填 |
| ------ | ------ | -------------------- | ---- |
| query  | String | 查询关键词(空格分开) | 否   |

请求示例：

```url
http://localhost:8080/api/contract/list

http://localhost:8080/api/contract/list?query=国家卫星气象中心 中国建筑技术集团有限公司
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": [
        {
            "id": 2245,
            "url": "中国政府采购网",
            "contractNo": "FY3(02P)-SY-2005",
            "contractName": "风云三号02批地面应用系统工程无人机卫星定标与检验自动观测系统",
            "projectNo": "ZQC-R20172",
            "projectName": "风云三号02批气象卫星工程",
            "purchaser": "国家卫星气象中心",
            "purchaserTelNo": "010-62173767",
            "supplier": "北京华云星地通科技有限公司",
            "supplierTelNo": "010-68407953",
            "subjectName": "无人机观测系统",
            "subjectUnitPrice": "27967000",
            "contractValue": "2796.700000万元",
            "announceDate": "2020-12-07"
        },
        {
            "id": 2246,
            "url": "中国政府采购网",
            "contractNo": "FY3(02P)-QT-2005",
            "contractName": "FY-3/GNOS近实时GNSS精密星历解算软件系统研发",
            "projectNo": "ZQC-R20172",
            "projectName": "风云三号02批气象卫星工程",
            "purchaser": "国家卫星气象中心",
            "purchaserTelNo": "010-62173767",
            "supplier": "中国科学院国家空间科学中心",
            "supplierTelNo": "010-62557975",
            "subjectName": "GNOS系统",
            "subjectUnitPrice": "2000000",
            "contractValue": "200.000000万元",
            "announceDate": "2020-12-07"
        }
    ]
}
```

没有查到任何合同数据时，将返回：

```json
{
    "code": 404,
    "msg": "Not found",
    "data": null
}
```

#### GET /contract/{id}

- 按 id 查询单个合同信息

请求地址：GET /contract/{id}

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": {
        "id": 2250,
        "url": "中国政府采购网",
        "contractNo": "HT202010200850461094",
        "contractName": "Science",
        "projectNo": "CD2020-TS-03",
        "projectName": "重庆大学2020年外文数字资源SD采购",
        "purchaser": "重庆大学",
        "purchaserTelNo": "023-65104111　",
        "supplier": "中国教育图书进出口有限公司",
        "supplierTelNo": "010-57933034",
        "subjectName": "Elsevier",
        "subjectUnitPrice": "详见合同",
        "contractValue": "382.500000万元",
        "announceDate": "2020-12-07"
    }
}
```

查询不到时，返回 `NOT FOUND`。

```json
{
    "code": 404,
    "msg": "Not found",
    "data": null
}
```

#### POST /contract

- 增加一个合同

请求地址：POST /contract

请求参数：

| 参数名           | 类型   | 说明           | 必填   |
| ---------------- | ------ | -------------- | ------ |
| url              | String | 来源url        | 否     |
| contractNo       | String | 合同编号       | **是** |
| contractName     | String | 合同名称       | **是** |
| projectNo        | String | 项目编号       | 否     |
| projectName      | String | 项目名称       | 否     |
| purchaser        | String | 采购人         | 否     |
| purchaserTelNo   | String | 采购人联系方式 | 否     |
| supplier         | String | 供应商         | 否     |
| supplierTelNo    | String | 供应商联系方式 | 否     |
| subjectName      | String | 标的名称       | 否     |
| subjectUnitPrice | String | 标的单价       | 否     |
| contractValue    | String | 合同金额       | 否     |
| announceDate     | String | 发布日期       | 否     |

请求示例：

```json
{
    "url": "山东省政府采购网",
    "contractNo": "140063202000029_039",
    "contractName": "教学实验材料",
    "projectNo": "048-548001",
    "projectName": "教学实验材料",
    "purchaser": "山东中医药大学",
    "purchaserTelNo": "89628116"
}
```

响应示例：

添加成功：

```json
{
    "code": 200,
    "msg": "OK",
    "data": {
        "id": 11084,
        "url": "山东省政府采购网",
        "contractNo": "140063202000029_039",
        "contractName": "教学实验材料",
        "projectNo": "048-548001",
        "projectName": "教学实验材料",
        "purchaser": "山东中医药大学",
        "purchaserTelNo": "89628116",
        "supplier": null,
        "supplierTelNo": null,
        "subjectName": null,
        "subjectUnitPrice": null,
        "contractValue": null,
        "announceDate": null
    }
}
```

必填项为空：

```json
{
    "code": 403,
    "msg": "合同编号与合同名称不能为空",
    "data": null
}
```

已有一条相同 `contractNo` 和 `contractName` 的合同：

```json
{
    "code": 403,
    "msg": "已存在相同的合同",
    "data": null
}
```

#### PUT /contract/{id}

- 根据 id 修改一个合同信息

请求地址：PUT /contract/{id}

请求参数：

| 参数名           | 类型   | 说明           | 必填 |
| ---------------- | ------ | -------------- | ---- |
| url              | String | 来源url        | 否   |
| contractNo       | String | 合同编号       | 否   |
| contractName     | String | 合同名称       | 否   |
| projectNo        | String | 项目编号       | 否   |
| projectName      | String | 项目名称       | 否   |
| purchaser        | String | 采购人         | 否   |
| purchaserTelNo   | String | 采购人联系方式 | 否   |
| supplier         | String | 供应商         | 否   |
| supplierTelNo    | String | 供应商联系方式 | 否   |
| subjectName      | String | 标的名称       | 否   |
| subjectUnitPrice | String | 标的单价       | 否   |
| contractValue    | String | 合同金额       | 否   |
| announceDate     | String | 发布日期       | 否   |

请求示例：

```json
{
    "url": "山东省政府采购网",
    "contractName": "教学实验材料",
    "projectNo": "048-548001",
    "projectName": "教学实验材料",
    "purchaser": "山东中医药大学",
    "purchaserTelNo": "89628116"
}
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

存在 `contractNo` 和 `contractName` 且与另一合同重复：

```json
{
    "code": 403,
    "msg": "已存在相同的合同",
    "data": null
}
```

所有参数都为空：

```json
{
    "code": 403,
    "msg": "未输入更新项",
    "data": null
}
```


#### DELETE /contract/{id}

- 根据 id 删除一个合同

请求地址：DELETE /contract/{id}

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": null
}
```

当该 `id` 对应的合同不存在时，会返回：

```json
{
    "code": 404,
    "msg": "该合同不存在",
    "data": null
}
```

#### DELETE /contract

- 使用 body 传参，批量删除合同

请求地址：DELETE /contract

请求参数：

| 参数名 | 类型  | 说明    | 必填 |
| ------ | ----- | ------- | ---- |
| ids    | int[] | id 数组 | 是   |

请求示例：

```json
{
    "ids": [1464, 1465, 1466]
}
```

响应示例：

返回的 `data` 中带有删除成功和删除失败的条目数：

```json
{
    "code": 200,
    "msg": "OK",
    "data": {
        "success": 3,
        "failure": 0
    }
}
```

当所有 `id` 均删除失败时，会返回 `404` ：

```json
{
    "code": 404,
    "msg": "没有合同被删除",
    "data": null
}
```



# 项目部署

### 环境依赖

1. JDK/JRE 1.8
2. Maven
3. MySQL 5.7 (默认使用服务器的数据库)

### 运行部署

1. 下载项目源码

   ```shell
   git clone https://github.com/4dpFour/dataplatform.git
   ```

2. 在 `/src/main/resources` 目录下添加配置文件 `application.properties` 

   ```properties
   # 数据库连接字符串
   spring.datasource.url=
   # 数据库连接用户名
   spring.datasource.username=
   # 数据库连接密码
   spring.datasource.password=
   mybatis.config-location=classpath:mybatis-config.xml
   
   server.port=8080
   server.servlet.context-path=/api
   crawl.depth=2
   crawl.thread=1
   crawl.page.start=1
   crawl.page.end=3
   ```

3. 使用 Maven 下载依赖并构建项目

   ```shell
   mvn install
   ```

4. 运行项目

   ```shell
   java -jar /target/dataplatform-1.1.2.jar
   ```

