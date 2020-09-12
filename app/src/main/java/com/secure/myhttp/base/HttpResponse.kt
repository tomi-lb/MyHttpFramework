package com.secure.myhttp.base

import android.util.Log
import com.secure.myhttp.`interface`.IHttpResponse

/**
 * Created by lb on 2020/9/8
 */
open class HttpResponse : IHttpResponse {
    override fun onSuccess(context: String?) {
        Log.e("test", "success : \n" + context)
    }

    override fun onError() {
        Log.e("test", "onError")
    }
}