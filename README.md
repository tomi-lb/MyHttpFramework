# MyHttpFramework
仿okhttp手写网络请求框架

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

OkHttpClient
统一管理连接资源，如Dispatcher
