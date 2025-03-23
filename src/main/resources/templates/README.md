# 通讯录管理系统API文档

## 响应格式

接口响应由以下部分组成：

- 状态码`code`
- 消息`msg`
- 响应数据`data`

其中，状态码为0代表成功，非0表示失败，需要说明的会在消息字段中给出说明，需要返回数据的会在响应数据字段中给出返回的数据，另外失败的状态码通常遵会采用与HTTP状态码含义相同或接近的代码表示，比如找不到目标则会使用404来表示。

示例：

```json
{
	"code": 0,
	"msg": "成功",
	"data": {}
}
```

## 用户接口

### 注册接口

**请求地址：**/api/user

**请求方法：**POST

**请求参数：**

| 参数名   | 类型   | 所在位置 | 含义   |
| -------- | ------ | -------- | ------ |
| username | string | body     | 用户名 |
| password | string | body     | 密码   |

**请求示例：**

```json
POST /api/user

{
	"username": "admin",
	"password": "admin"
}
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "注册成功",
	"data": {}
}
```

### 登录接口

**请求地址：**/api/user/session

**请求方法：**POST

**请求参数：**

| 参数名   | 类型   | 所在位置 | 含义   |
| -------- | ------ | -------- | ------ |
| username | string | body     | 用户名 |
| password | string | body     | 密码   |

**请求示例：**

```json
POST /api/user/session

{
	"username": "admin",
	"password": "admin"
}
```

**响应示例：**

```json
Set-Cookie: session=MTY3MjU2MTc0OHxEdi1CQkF..

{
	"code": 0,
	"msg": "登录成功",
	"data": {}
}
```

**其他说明：**

登录成功后服务端会进行Session设置操作，通常无需手动处理，若未能自动设置则需要自行手动处理，后续的请求都需要携带该cookie。

### 获取用户信息接口

**请求地址：**/api/user/info

**请求方法：**GET

**请求参数：**

无

**请求示例：**

```
GET /api/user/info
Cookie: session=MTY3M...
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "",
	"data": {
		"username": "admin"
	}
}
```

### 修改密码接口

**请求地址：**/api/user/password

**请求方法：**PATCH

**请求参数：**

| 参数名      | 类型   | 所在位置 | 含义     |
| ----------- | ------ | -------- | -------- |
| oldPassword | string | body     | 当前密码 |
| newPassword | string | body     | 新密码   |

**请求示例：**

```json
PATCH /api/user/password
Cookie: session=MTY3M...

{
	"oldPassword": "admin",
	"newPassword": "admin123"
}
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "密码修改成功",
	"data": {}
}
```


## 分组管理接口

### 创建分组接口

**请求地址：**/api/group

**请求方法：**POST

**请求参数：**

| 参数名 | 类型   | 所在位置 | 含义     |
| ------ | ------ | -------- | -------- |
| name   | string | body     | 分组名称 |

**请求示例：**

```json
POST /api/group
Cookie: session=MTY3M...

{
	"name": "朋友",
}
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "创建成功",
	"data": {}
}
```

### 删除分组接口

**请求地址：**/api/group/{id}

**请求方法：**DELETE

**请求参数：**

| 参数名 | 类型 | 所在位置 | 含义   |
| ------ | ---- | -------- | ------ |
| id     | int  | path     | 分组ID |

**请求示例：**

```
DELETE /api/group/1
Cookie: session=MTY3M...
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "删除成功",
	"data": {}
}
```

### 修改分组接口

**请求地址：**/api/group/{id}

**请求方法：**PATCH

**请求参数：**

| 参数名 | 类型   | 所在位置 | 含义     |
| ------ | ------ | -------- | -------- |
| id     | int    | path     | 分组ID   |
| name   | string | body     | 分组名称 |

**请求示例：**

```json
PATCH /api/group/1
Cookie: session=MTY3M...

{
	"name": "家人"
}
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "修改成功",
	"data": {}
}
```

### 查询全部分组接口

**请求地址：**/api/group

**请求方法：**GET

**请求参数：**

无

**请求示例：**

```json
GET /api/group
Cookie: session=MTY3M...
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "",
	"data": [
		{
			"ID": 1,
			"CreatedAt": "2022-12-17T16:47:56.7406783+08:00",
			"UpdatedAt": "2022-12-17T16:47:56.7406783+08:00",
			"DeletedAt": null,
			"Name": "朋友",
			"UserID": 1
		},
		{
			"ID": 2,
			"CreatedAt": "2023-01-01T14:09:10.1485658+08:00",
			"UpdatedAt": "2023-01-01T14:09:10.1485658+08:00",
			"DeletedAt": null,
			"Name": "家人",
			"UserID": 1
		}
	]
}
```



## 联系人管理接口

### 创建联系人接口

**请求地址：**/api/contact

**请求方法：**POST

**请求参数：**

| 参数名   | 类型   | 所在位置 | 含义     |
| -------- | ------ | -------- | -------- |
| name     | string | body     | 姓名     |
| phone    | string | body     | 电话     |
| email    | string | body     | 电子邮箱 |
| comment  | string | body     | 备注     |
| group_id | int    | body     | 分组ID   |

**响应参数：**

| 参数名   | 类型   | 含义     |
| -------- | ------ | -------- |
| name     | string | 姓名     |
| phone    | string | 电话     |
| email    | string | 电子邮箱 |
| comment  | string | 备注     |
| user_id  | int    | 用户ID   |
| group_id | int    | 分组ID   |

**请求示例：**

```json
POST /api/contact
Cookie: session=MTY3M...

{
	"name": "李狗蛋",
	"phone": "10086",
	"email": "ligoudan@126.com",
	"comment": null,
	"group_id": 1
}
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "创建成功",
	"data": {
		"name": "李狗蛋",
		"phone": "10086",
		"email": "ligoudan@126.com",
		"comment": "",
		"user_id": 1,
		"group_id": 1
	}
}
```

### 删除联系人接口

**请求地址：**/api/contact/{id}

**请求方法：**DELETE

**请求参数：**

| 参数名 | 类型 | 所在位置 | 含义     |
| ------ | ---- | -------- | -------- |
| id     | int  | path     | 联系人ID |

**请求示例：**

```
DELETE /api/contact/1
Cookie: session=MTY3M...
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "删除成功",
	"data": {
        "ID": 4,
		"CreatedAt": "2022-12-17T16:53:36.7064056+08:00",
		"UpdatedAt": "2022-12-17T16:53:36.7064056+08:00",
		"DeletedAt": "2023-01-01T16:09:30.0481613+08:00",
		"name": "张三",
		"phone": "12345",
		"email": "123@example.com",
		"comment": "",
		"user_id": 1,
		"group_id": 1
	}
}
```

### 修改联系人接口

**请求地址：**/api/contact/{id}

**请求方法：**PUT

**请求参数：**

| 参数名 | 类型   | 所在位置 | 含义     |
| ------ | ------ | -------- | -------- |
| id     | int    | path     | 联系人ID   |
| name     | string | body | 姓名     |
| phone    | string | body | 电话     |
| email    | string | body | 电子邮箱 |
| comment  | string | body | 备注     |
| group_id | int    | body | 分组ID   |

**请求示例：**

```json
PUT /api/group/1
Cookie: session=MTY3M...

{
	"name": "李狗蛋",
	"phone": "10086",
	"email": "ligoudan@126.com",
	"comment": null,
	"group_id": 1
}
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "修改成功",
	"data": {}
}
```

### 查询全部联系人接口

**请求地址：**/api/contact

**请求方法：**GET

**请求参数：**

无

**请求示例：**

```json
GET /api/contact
Cookie: session=MTY3M...
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "",
	"data": [
		{
            "ID": 4,
			"CreatedAt": "2022-12-17T16:53:36.7064056+08:00",
			"UpdatedAt": "2022-12-17T16:53:36.7064056+08:00",
			"DeletedAt": null,
			"name": "李狗蛋",
			"phone": "10086",
			"email": "ligoudan@126.com",
			"comment": "",
			"user_id": 1,
			"group_id": 1
		}
	]
}
```

### 通过ID获取联系人接口

**请求地址：**/api/contact/{id}

**请求方法：**GET

**请求参数：**

无

**请求示例：**

```
GET /api/contact/1
Cookie: session=MTY3M...
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "",
	"data": {
		"ID": 5,
		"CreatedAt": "2022-12-17T16:53:53.6011065+08:00",
		"UpdatedAt": "2022-12-17T16:53:53.6011065+08:00",
		"DeletedAt": null,
		"name": "张三",
		"phone": "12345",
		"email": "123@example.com",
		"comment": "",
		"user_id": 1,
		"group_id": 1
	}
}
```

### 导出联系人接口

**请求地址：**/api/contact/export

**请求方法：**GET

**请求参数：**

无

**响应参数：**

Excel文件数据

**请求示例：**

```
GET /api/contact/export
Cookie: session=MTY3M...
```

**响应示例：**

```json
PKxl/worksheets/sheet1.xml���n�F��}���}M�3�"��n�����4����@Җ���p���iv��&���0���Z���veSgFf&q��ٗ�Sf�����k�1�i{iگ�ѹ>y�Nu��cߟ?�i�;������_�ӡi�����)�έ+�oU�Tg�eZem���C��p(w��=W��u��
..........
```

### 导入联系人接口

**请求地址：**/api/contact/import

**请求方法：**POST

**请求参数：**

| 参数名 | 类型 | 所在位置       | 含义      |
| ------ | ---- | -------------- | --------- |
| file   | file | multipart form | Excel文件 |

**请求示例：**

```json
POST /api/contact/import
Cookie: session=MTY3M...

--X-INSOMNIA-BOUNDARY
Content-Disposition: form-data; name="file"; filename="1671268069.xlsx"
Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet

PK     ! A7  n     .....
```

**响应示例：**

```json
{
	"code": 0,
	"msg": "导入成功",
	"data": {}
}
```

