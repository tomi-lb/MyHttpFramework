# MyHttpFramework
仿okhttp手写网络请求框架

手写网络框架一般要考虑以下步骤：
1.封装请求类Request，所有与请求相关的参数都由该类进行管理
2.封装响应类Response, 所有与响应有关的参数封装
3.异步调用Callback接口，返回响应类Response
4.线程池+队列的封装类Dispatcher
5.任务执行类RealCall，RealCall接受Request参数，调用线程池实现网络请求的具体逻辑，返回Response结构体。
6.统一管理类HttpClient，负责Dispatcher，RealCall等参数的初始化，负责把所有组件串联起来


整个架构分为以下几个类：

Request
封装请求参数 url,method,headers,body

Response
封装的响应结构体，在Callback中回调

Callback
响应回调

RealCall
接受处理Request请求，处理具体请求操作，Dispatcher同步异步，责任链调用

Dispatcher
队列 + 线程池

HttpClient
统一管理连接资源，如Dispatcher
