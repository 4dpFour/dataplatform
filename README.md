# 接口文档

[toc]

---

### 更新

- **DELETE /contract**

  完成批量删除接口。
  
- **GET /contract/list**

  现在返回的条目会按从新到旧排序。



---

### 网址模块

#### POST /url

- 将一些网址提交至后端，与user绑定，保存到数据库中。

请求地址：POST /url

请求参数：

| 参数名 | 类型   | 说明                       | 必填 |
| ------ | ------ | -------------------------- | ---- |
| urls   | String | String数组形式的Json字符串 | 是   |

请求示例：

```json
{
    "urls": ["http://www.aaa.com", "http://www.bbb.com", "http://www.ccc.com"]
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
        "http://htgs.ccgp.gov.cn/GS8/contractpublish",
        "http://www.bbb.com",
        "http://www.ccc.com"
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
        "careUrls": "[http://www.aaa.com, http://www.bbb.com, http://www.ccc.com]"
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

- 强行更新某个用户的数据，一般只有管理员才能这么做，三个数据均为非必填项。

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
    "urls": ["http://www.aaa.com", "http://www.bbb.com", "http://www.ccc.com"]
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
- 后端单线程地爬取，直至爬取完成，或遇到错误时，才会返回

请求地址：GET /contract/crawl

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
http://localhost:8080/contract/list

http://localhost:8080/contract/list?query=中国科学院 北京大学 118.700000万元
```

响应示例：

```json
{
    "code": 200,
    "msg": "OK",
    "data": [
        {
            "id": 1476,
            "url": "http://htgs.ccgp.gov.cn/GS8/contractpublish",
            "contractNo": "GSZCSC-HT-2020-220251",
            "contractName": "甘肃省中医院网上商城合同",
            "projectNo": "GSZCSC-HT-2020-220251",
            "projectName": "甘肃省中医院网上商城合同",
            "purchaser": "甘肃省中医院",
            "purchaserTelNo": "无",
            "supplier": "北京中际远华科贸有限公司",
            "supplierTelNo": "无",
            "subjectName": "电冰箱",
            "subjectUnitPrice": "1745.03 1648.03",
            "contractValue": "3.228257万元",
            "announceDate": "2020-12-14"
        },
        {
            "id": 1475,
            "url": "http://htgs.ccgp.gov.cn/GS8/contractpublish",
            "contractNo": "GSZCSC-HT-2020-221567",
            "contractName": "甘肃省中医院网上商城合同",
            "projectNo": "GSZCSC-HT-2020-221567",
            "projectName": "甘肃省中医院网上商城合同",
            "purchaser": "甘肃省中医院",
            "purchaserTelNo": "无",
            "supplier": "兰州四通电子技术有限公司",
            "supplierTelNo": "无",
            "subjectName": "移动硬盘/U盘",
            "subjectUnitPrice": "550.00元 140.00元",
            "contractValue": "0.250000万元",
            "announceDate": "2020-12-14"
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
        "id": 265,
        "url": "http://htgs.ccgp.gov.cn/GS8/contractpublish",
        "contractNo": "CSU-U-CG-2020-0022",
        "contractName": "显示屏采购合同",
        "projectNo": "0722-206FE1486FZO",
        "projectName": "显示屏采购招标项目",
        "purchaser": "中国科学院空间应用工程与技术中心",
        "purchaserTelNo": "010-82178863",
        "supplier": "北京朗迪锋科技有限公司",
        "supplierTelNo": "15301027313",
        "subjectName": "显示屏",
        "subjectUnitPrice": "3098250",
        "contractValue": "5749800.000000万元",
        "announceDate": "2020-12-02"
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
    "url": "test-url",
    "purchaser": "test-purchaser",
    "contractValue": "test-contractValue"
}
```

响应示例：

添加成功将返回该合同的信息，**并带有该合同的 id**。

```json
{
    "code": 200,
    "msg": "OK",
    "data": {
        "id": 1469,
        "url": "test-url",
        "contractNo": null,
        "contractName": null,
        "projectNo": null,
        "projectName": null,
        "purchaser": "test-purchaser",
        "purchaserTelNo": null,
        "supplier": null,
        "supplierTelNo": null,
        "subjectName": null,
        "subjectUnitPrice": null,
        "contractValue": "test-contractValue",
        "announceDate": null
    }
}
```

当所有参数都没有时，会返回：

```json
{
    "code": 403,
    "msg": "请至少填入一个字段",
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
    "url": "update-url",
    "projectNo": "update-projectNo",
    "projectName": "update-projectName"
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

当所有参数都没有时，会返回：

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
| ids    | Array | id 数组 | 是   |

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
