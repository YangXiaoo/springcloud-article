# springcloud-article publish system
## 使用技术
- eureka, 服务注册
- hystrix, 服务容错
- fegin, 声明式调用
- config-bus, 分布式配置
- zuul, 服务网关
- hystrix-dashboard
- sleuth-zipkin, 链路跟踪
- redis, rabbitMQ
- 单点登录

## 环境
- IDEA
- springcloud(Greenwich.RELEASE)
- springboot(2.1.6.RELEASE)
- redis, rabbitMQ, zipkin

## 结构
- 应用层: app-front(处理业务逻辑)
- 服务层：article-server(文章服务), comment-server(评论服务), user-server(用户服务)
- 数据层: redis
- 公共模块: common-module(为整个应用提供公共模块，使用Maven共享)
- 各服务端口
![](/resource/capture/server-port.png)
## 使用
- [数据库](/resource/sql/bbs.sql)导入，并配置数据库
- 启动base-server/eureka-server注册服务
- 启动base-server/config-server配置中心服务
- 启动article-server
- 启动comment-server
- 启动user-server
- 启动app-front
- 启动base-server/zuul-server服务网关
- Windows CMD启动 ```redis-server```,不启动的话登录会出现调用服务失败

## 运行截图
- 访问[http://localhost:8761/](http://localhost:8761/)查看注册服务
![](/resource/capture/ureka.png)
- 访问[http://localhost:8763/config-dev/default/](http://localhost:8763/config-dev/default/) 查看配置消息
![](/resource/capture/config-server.png)
- 访问[http://localhost:8080/demo/config-client](http://localhost:8080/demo/config-client) 查看配置是否成功
![](/resource/capture/config-client.png)
- 访问[http://localhost:8080/article/list](http://localhost:8080/article/list) 进入应用
![](/resource/capture/article-list.png)
![](/resource/capture/article-comment.png)
- 通过服务网关zuul进入应用, [http://localhost:8762/article/list](http://localhost:8762/article/list)
![](/resource/capture/article-list.png)
- 查看hystrix-dashboard需启动base-server/hystrix-dashboard，然后访问[http://localhost:8765/hystrix](http://localhost:8765/hystrix)
![](/resource/capture/hystrix-dashboard.png)
- 在面板中输入参数，点击monitor stream
![](/resource/capture/hystrix-input.png)
- 新页面没有记录, 进入文章列表随便点击一个文章链接，这样服务之间就相互调用，此时刷新hystrix面板会有新的内容
![](/resource/capture/hystrix-detail.png)

- 查看链路。首先下载[zipkin.jar](/resource/zipkin/zipkin-server-2.10.1-exec.jar), 使用命令```java -jar zipkin-server-2.10.1-exec.jar localhost:9411```打开服务器。然后再浏览器中访问[http://localhost:9411/zipkin/](http://localhost:9411/zipkin/),发现什么也没有，因为没有此时没有记录服务之间的调用，进入文章列表点击文章链接、登录使应用之间相互调用后再点击Find Traces
![](/resource/capture/zipkin-cmd.png)
![](/resource/capture/zipkin-board.png)

## 已经实现的功能
- 用户注册、登录
- 文章发布与查看
- 多级评论

## 待完成
- 文章点赞、浏览统计
- 文章管理中心
- 用户管理中心
- 使用富文本编辑
- 页面
- so on...

## 资料
- [方志明-springcloud教程-github](https://github.com/forezp/SpringCloudLearning)