# Websocket Chatroom

有任何疑问可以加群，相互讨论

**QQ群**：119096066

![](F:\space\Websocket-ChatRoom-SpringBoot\document\qq.png)

## 项目演示地址

<http://121.43.182.154:7010/> 

**可以用来登录的用户名和密码：**

| 用户名 | 密码 |
| ------ | ---- |
| 100    | 123  |
| 101    | 123  |
| 102    | 123  |
| 103    | 123  |

## 项目介绍

### 基本简介

此项目是我毕业之后进入公司实习期间，公司布置的小训练任务。使用 `websocket` 实现聊天室程序，支持群里和单聊。最近想着把做的东西留存一下，就对项目小改一下上传到git上了。代码中有许多坏味道。后续有时间会继续进行修改。

#### 使用框架和技术

* websocket

* springboot

* mybatis

* bootstrap

* jquery

  
### 功能介绍

#### 登陆：

![登陆图片](F:\space\Websocket-ChatRoom-SpringBoot\document\login.jpg)



#### 群聊：

* 支持发送普通文本消息
* 支持发送图片
* 支持发生表情

![群聊](F:\space\Websocket-ChatRoom-SpringBoot\document\room-chat.jpg)


####   私聊：

* 点击群里的某个用户，就可以与该用户进行私聊。如果发送了私聊信息，头像会有一个点表示未读信息

![私聊1](F:\space\Websocket-ChatRoom-SpringBoot\document\siliao1.jpg)

![](F:\space\Websocket-ChatRoom-SpringBoot\document\siliao2.jpg)

#### 共享文件

![](F:\space\Websocket-ChatRoom-SpringBoot\document\sharefile.jpg)

#### 聊天记录查询

![](F:\space\Websocket-ChatRoom-SpringBoot\document\record.jpg)

#### 修改密码 和 修改密码（密码功能演示环境已屏蔽）

**注意： 上传的文件控制在512kb内。否则后台抛出错误**

![](F:\space\Websocket-ChatRoom-SpringBoot\document\head.jpg)

#### ![](F:\space\Websocket-ChatRoom-SpringBoot\document\password.jpg)

## 项目部署

### 开发环境部署

* 部署mysql，修改 `application-dev.properties` 文件中的 数据库连接信息，连接地址`spring.datasource.url` 用户名： `spring.datasource.username`  密码： `spring.datasource.password`
* 导入sql脚本 `sql/talk.sql` 和 `sql/staff.sql`
* 使用idea或者eclipse导入工程，配置好maven仓库，import 项目依赖
* 运行springboot的启动类 `per.xin.chatroom.ChatrromApplication`



### linux环境部署

* 执行mvn package 打一个jar包，拷贝到linux环境中
* 运行 java -jar [编译的jar包] 