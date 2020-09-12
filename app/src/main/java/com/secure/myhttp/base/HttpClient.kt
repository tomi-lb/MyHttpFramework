package com.secure.myhttp.base

import com.secure.myhttp.`interface`.IHttpRequest

/**
 * Created by lb on 2020/9/8
 * 关联类：关联dispather与RealCall
 */
class HttpClient {
    val dispather = Dispather.get()

    fun newCall(request: IHttpRequest): RealCall {
        return RealCall.newRealCall(this, request)
    }
}