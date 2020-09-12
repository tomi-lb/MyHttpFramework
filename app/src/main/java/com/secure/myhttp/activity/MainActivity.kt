package com.secure.myhttp.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.secure.myhttp.R
import com.secure.myhttp.base.HttpClient
import com.secure.myhttp.base.HttpRequest
import com.secure.myhttp.base.HttpResponse

/**
 * 手写网络请求库
 */
class MainActivity : AppCompatActivity() {
    val client = HttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun doClick(view: View) {
        var request: HttpRequest
        var i = 100
        var j = 1

        while (i > 0) {
            request = HttpRequest("https://www.baidu.com/" + i--, ByteArray(1))
            Log.e("test", "request " + request.url)
            client.newCall(request).enqueue(object : HttpResponse() {
                override fun onSuccess(context: String?) {
                    Log.e("test", "success : " + j++)
                }

                override fun onError() {
                    super.onError()
                }
            })
        }
    }
}
