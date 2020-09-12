package com.secure.myhttp.`interface`

/**
 * Created by lb on 2020/9/8
 * 请求接口，包含请求URL和请求数据
 */
interface IHttpRequest {
    var url : String
    var requestData : ByteArray
}