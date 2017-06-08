
# 社团管理系统

根路径：http://112.74.214.252:8080/acloud/

[toc]

## 用户模块

### 登录

url : /login/web

| 字段      | 类型|必须|   含义 |
| :--------: |:---:|:------: |:--------:|
| userId    | Email| true| 用户登录Id(还没有做邮箱验证啊)  |
| password    | string| true|密码，长度大于6  |

### 注册

url : /register

| 字段      | 类型|必须|   含义 |
| :--------: |:---:|:------: |:--------:|
| userId    | Email| true| 用户登录Id  |
| password    | string| true|密码，长度大于6  |

### 获取当前用户的详细信息

url: /user/info 
method : GET

返回格式：
```json
{
	"gender": 0,
	"major": 1,
	"nickName": "丿灬文丶少",
	"phone": 123,
	"realName": "赖远文",
	"stuId": "140202021006",
	"userId": "helloworld.wen@gmail.com"
}
```

### 更新用户信息（暂时不能更新密码

url: /user/update
method : POST

需要什么，更新什么
| 字段      | 类型|必须|   含义 |
| :--------: |:---:|:------: |:--------:|
| realName | string | false | 真实姓名 |
| nickName | string |  | 昵称 |
| stuId | string | false | 学号|
| major | string | false | 专业id |
| classNum | string | false | 班级 |  
| phone | long | false | 电话号码|
| gender | int | false | 性别 | 

## 学校/学院/专业 模块
前缀url：/admin

### 获取所有学校列表
url：GET /schools

返回JSON格式
```json
[
  {
    "id": 1,
    "name": "北京理工大学珠海学院",
    "colleges": [
      {
        "id": 1,
        "name": "计算机学院",
        "majors": [
          {
            "id": 6,
            "name": "网络工程"
          },
          {
            "id": 5,
            "name": "软件工程"
          }
        ]
      },
      {
        "id": 2,
        "name": "信息学院",
        "majors": []
      }
    ]
  },
  {
    "id": 2,
    "name": "北京师范大学珠海学院",
    "colleges": []
  }
]
```

### 添加一个学校信息
url：POST /schools
上传JSON格式
```json
{
  "name": "暨南大学"
}
```

### 通过Excel文件添加学校信息
url: POST /schools/excel

excel格式

编号 | 学校名称
------- | -------
1 | 北理珠

### 获取学校的学院列表
url：GET /schools/{school_id}/colleges

返回JSON格式
```json
[
  {
    "id": 1,
    "name": "计算机学院",
    "majors": [
      {
        "id": 5,
        "name": "软件工程"
      },
      {
        "id": 6,
        "name": "网络工程"
      }
    ]
  }
]
```
### 向学校添加学院信息
url：POST /schools/{school_id}/colleges

上传JSON格式
```json
{
  "name": "艺术学院"
}
```

### 获取某个学院的专业列表
url：GET /colleges/{college_id}/majors

返回JSON格式
```json
[
  {
    "id": 5,
    "name": "软件工程"
  },
  {
    "id": 6,
    "name": "网络工程"
  }
]
```

### 向某个学院添加专业
url：POST /colleges/{college_id}/majors

上传JSON格式
```json
{
  "name": "树莓"
}
```

## 社团模块

### 创建一个社团

- url : /society
- method: PUT
- ContentType: application/json;
- Json:
```json
{
	"name":"社团名字",
	"summary":"社团介绍",
	"principal":"helloworld.wen@gmail.com",//社团负责人UserId
	"createTime":2354353254352,// 创建时间，转换成long
	"college":1, // 学院Id
	"society_logo":"http://i.niupic.com/images/2017/05/10/qamWL3.jpg" // 社团logo，现在只能上传个URL
}
```

### 更新一个社团信息

- url : /society
- method: POST
- ContentType: application/json;
- Json:
```json
{
	"name":"社团名字",
	"summary":"社团介绍",
	"principal":"helloworld.wen@gmail.com",//社团负责人UserId
	"createTime":2354353254352,// 创建时间，转换成long
	"college":1, // 学院Id
	"society_logo":"http://i.niupic.com/images/2017/05/10/qamWL3.jpg" // 社团logo，现在只能上传个URL
}
```

### 获取学校的社团

- url : /society/school/{schoolId}
- method: GET
- 返回值：
```json
[
	{
	"id": 2,
	"name": "不甘社",
	"society_logo": "http://i.niupic.com/images/2017/05/10/qamWL3.jpg",
	"summary": "这个社团很懒，什么都没有说"
	},
	{
	"id": 3,
	"name": "更新名字",
	"summary": "这个社团很懒，什么都没有说"
	}
]
```

### 获取专业拥有的社团

- url : /society/college/{collegeId}
- method: GET
- 返回值：
```json
[
	{
	"id": 2,
	"name": "不甘社",
	"society_logo": "http://i.niupic.com/images/2017/05/10/qamWL3.jpg",
	"summary": "这个社团很懒，什么都没有说"
	},
	{
	"id": 3,
	"name": "更新名字",
	"summary": "这个社团很懒，什么都没有说"
	}
]
```

### 获取社团拥有的所有用户

- url : /society/{societyId}/users
- method: GET
- Json:
```json
[
	{
		"gender": 1,
		"phone": 123,
		"societyPositions": [],
		"userId": "abc"
	}
]
```

### 获取用户拥有的所有社团

- url : /society/user
- method: GET
- Json:
```json
[
	{
		"id": 1,
		"name": "科协",
		"society_logo": "http://i.niupic.com/images/2017/05/10/qamWL3.jpg"
	}
]
```

### 获取社团的详细信息
- url : /society/{societyId}
- method: GET
- 返回值：
```json
{
	"college": 1,
	"createTime": 1494453191312,
	"id": 2,
	"name": "不甘社",
	"society_logo": "http://i.niupic.com/images/2017/05/10/qamWL3.jpg"
}
```

### 申请加入一个社团

- URL ： /society/join
- Method：POST
- Content-Type : application/json

```json
{
	"societyId":1,
	"reason": "申请理由"
}
```
添加成功返回HTTP Code 200，不返回任何信息

### 查询社团所有的申请请求

- URL ： /society/join
- Method：GET

**需要登录**，请求参数：
| 字段      |     类型 |   备注   |
| :-------- | --------:| :------: |
| societyId   |   String |  社团Id  |

返回的Json数据：
```json
[
	{
		"applyId":1,
		"applierId": "helloworld.wen@gmail.com",
		"reason": "申请理由"
	}
]
```

### 处理加入社团的请求

URL ： /society/handle
Method：POST
Content-Type : application/x-www-form-urlencoded

请求体：

| 字段      |     类型 |   备注   |
| :--------: | :--------:| :------: |
|  appleId   |   String |  申请加入社团的申请Id  |
| isAllow    |   Boolean |  是否同意加入  |

### 获取社团的拥有的职位

URL ： /society/position

Method：GET

```json
[
  {
    "grade": 1,
    "name": "副主席",
    "id": 1
  },
  {
    "grade": 0,
    "name": "干事",
    "id": 2
  }
]
```

------

 

## 任务模块
 
### 创建任务
 
 url: /task/add

上传一个Json:
```json
{
	"title":"任务名字",
	"societyId":"1",
	"executors":[
		"userId","helloworld.wen@gmail.com"
	],
	"subTask":[
		"子任务1","子任务2"
	]
}
```

### 更新任务

Method: PUT
URL: /task/add
Content-Type: application/json

上传一个Json:
```json
{
	"id":"40280e815c62e6c1015c62e718ae0000",
	"taskType":"1",
	"executors":[
		"userId","helloworld.wen@gmail.com"
	],
	"subTask":[
		{
			"id":"15",
			"process":"1",
			"question":"又带上了id"
		},
		{
			"process":"1",
			"question":"添加"
		}
	]
}
```


### 获取任务

#### 1. 获取一条任务信息

**URL：/task/task/{taskId}**

| 字段      |     是否必须 |   含义   |
| :--------: | :--------:| :------: |
|  taskId |   true |  任务的Id  |




#### 2. 获取用户拥有的所有任务

**URL：/task/user/{userId}**

| 字段      |     是否必须 |   含义   |
| :--------: | :--------:| :------: |
| userId |   true |  用户的Id  |




#### 3. 获取发布者发布的所有任务

**URL：/task/publicsher/{id}**

| 字段      |     是否必须 |   含义   |
| :--------: | :--------:| :------: |
| publisherId |   true |  发布者的Id  |




#### 4. 获取属于社团的所有任务

**URL：/task/society/{societyId}**

| 字段      |     是否必须 |   含义   |
| :--------: | :--------:| :------: |
| societyId |   true |  社团的Id  |


## 公告模块

### 1.创建模块
**URL：POST /notices**

POST JSON 格式
```json
{
    "title": "标题6",
    "content": "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
    "society": 1, //社团ID
    "publisher": "zojian@qq.com",
    "executors": [ //哪些用户可见
      "admin@qq.com"
    ]
  }
```

### 2.分页获取当前用户发布的公告（不区分社团,按时间降序）
**URL：GET `/notices/publisher/?page=1&pageSize=10`**

返回 JSON 格式

```json
{
    "id": 1204,
    "status": 0,
    "visitor_status": 0,
    "title": "测试",
    "content": "内容内容",
    "society": "ACM",
    "society_id": 4,
    "publisher": "zojian@qq.com",
    "time": 1496760791825,
    "executors": [
      "zojian2@qq.com",
      "zojian3@qq.com"
    ]
  }
```

### 3.分页获取该用户可见的所有公告（按时间降序）

**URL: GET `/notices/user/?page=1&pageSize=10`**

返回的JSON格式
```json
同上
```

### 4.分页获取该用户在某个社团内可见的公告（按时间降序）
**URL: GET `/notices/user/society/{society_id}/?page=1&pageSize=10`**


返回的JSON格式
```json
同上
```

### 5.删除公告
**URL： DELETE /notices/{notice_id}**

### 6.更新公告
**URL： PUT /notices/{notice_id}**

PUT JSON格式

```json
[
  {
    "title": "标题666",
    "content": "123内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容",
    "society": 1,
    "publisher": "zojian@qq.com",
    "executors": [
      "admin@qq.com"
    ]
  }
]
```

### 7.获取公告浏览者列表

**URL： GET /notices/visitor/{notice_id}**

返回的JSON格式
```json
[
"admin@qq.com",
"zojian@qq.com"
]
```

## 消息推送模块

### 1.分类获取所有未读消息
**URL： GET /message/{type}**

例如： 获取所有未读的公告通知

GET /message/notice

JSON 格式
```json
[
  {
    "id": 39, // 公告ID
    "identifier": "b5de015f-98bd-4233-9c92-a983abfe34d0",// 标识符（用于删除已读消息）
    "info": "测试", // 通知粗略内容
    "time": 1496647542777,
    "title": "你有一条新的公告通知" // 通知标题
  }
]
```

### 2.分类获取未读消息

**URL： GET /message/count/{type}**

例如： 获取所有未读的公告通知的数量

GET /message/count/notice

返回格式
```json
2
```

### 3.获取详细的通知消息
通过返回未读通知的json中的id，访问相应的url，并带上identifier查询参数（作用：清除未读通知）

比如： 获取到上面的公告通知id为39，则当用户点击未读通知时，访问`/notice/39?identifier=b5de015f-98bd-4233-9c92-a983abfe34d0`
这样就返回了公告详细信息的json了

### 4.获取离线通知
当建立websocket连接时，服务器会自动发送该用户的所有未读通知

## 会议模块

