package com.secure.myhttp.base

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import com.secure.myhttp.`interface`.IHttpRequest
import com.secure.myhttp.`interface`.IHttpResponse
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by lb on 2020/9/8
 * 任务执行类
 */
class RealCall : Runnable {
    var client: HttpClient
    var req: IHttpRequest
    lateinit var callback: IHttpResponse

    var handler = Handler(Looper.getMainLooper())

    constructor(client: HttpClient, req: IHttpRequest) {
        this.client = client
        this.req = req
    }

    companion object {
        fun newRealCall(client: HttpClient, originalRequest: IHttpRequest): RealCall {
            return RealCall(client, originalRequest)
        }
    }

    //异步方法
    fun enqueue(callback: IHttpResponse) {
        this.callback = callback
        client.dispather.enqueue(this)
    }

    //同步方法
    fun excute() {
        run()
    }

    override fun run() {
        //加大网络超时
        SystemClock.sleep(1000)
        connect()
    }

    fun connect() {
        var url: URL? = null
        try {
            url = URL(req.url)
            //打开http连接
            var urlConnection = url.openConnection() as HttpURLConnection
            //设置连接的超时时间
            urlConnection.setConnectTimeout(6000)
            //不使用缓存
            urlConnection.setUseCaches(false)
            urlConnection.setInstanceFollowRedirects(true)
            //响应的超时时间
            urlConnection.setReadTimeout(3000)
            //设置这个连接是否可以写入数据
            urlConnection.setDoInput(true)
            //设置这个连接是否可以输出数据
            urlConnection.setDoOutput(true)
            //设置请求的方式
            urlConnection.setRequestMethod("POST")
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8")
            urlConnection.setRequestProperty("Connection", "close")
            urlConnection.connect()
            //使用字节流发送数据
            val out: OutputStream = urlConnection.getOutputStream()
            val bos = BufferedOutputStream(out)
            if (req.requestData != null) {
                //把字节数组的数据写入缓冲区
                bos.write(req.requestData)
            }
            //刷新缓冲区，发送数据
            bos.flush()
            out.close()
            bos.close()
            //获得服务器响应
            if (urlConnection.getResponseCode() === HttpURLConnection.HTTP_OK) {
                val input: InputStream = urlConnection.getInputStream()
                var data = inputStream2String(input)
                //回调监听器的listener方法
                handler.post {
                    callback.onSuccess(data)
                }
            } else {
                handler.post {
                    callback.onError()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 流转化为字符串
     */
    fun inputStream2String(inputStream: InputStream): String? {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = null
        try {
            while (reader.readLine().also({ line = it }) != null) {
                sb.append(line + "\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }
}