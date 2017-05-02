
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
| realName | string | false | 真实姓名 |
| nickName | string | true | 昵称 |
| stuId | string | true | 学号|
| major | string | true | 专业id |
| classNum | string | false | 班级 |  
| phone | long | true | 电话号码|
| gender | int | true | 性别 | 

 

## 任务模块
 
### 创建任务
 
 url: /task/add

上传一个Json:
```json
{
    "executors":[  // 执行者id
        "admin",
        "wen"
    ],
    "publisher":"wen", // 发布者id
    "society":1,  // 所属社团id
    "subTask":[  // 子任务
        {
            "progress":0.1, // 任务进度
            "question":"问题1" // 子任务的问题
        }
    ],
    "sumProgress":0, // 总进度
    "taskNum":0, // 任务数量
    "taskType":1, // 任务类型 ：1:活动，2:已经归档，3:删除
    "time":1493479530484 // 创建时间
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


