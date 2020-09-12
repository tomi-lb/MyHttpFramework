package com.secure.myhttp.base

import com.secure.myhttp.`interface`.IHttpRequest

/**
 * Created by lb on 2020/9/8
 */
class HttpRequest(override var url: String, override var requestData: ByteArray) : IHttpRequest {

}