package com.secure.myhttp.`interface`

/**
 * Created by lb on 2020/9/8
 * 响应类: 成功，失败
 */
interface IHttpResponse {
    fun onSuccess(context: String?)
    fun onError()
}