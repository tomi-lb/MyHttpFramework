package com.secure.myhttp.base

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by lb on 2020/9/8
 * 队列和线程池调度类,单例模式
 * 所有进来的任务加入到队列，由队列分配给线程池处理
 */
class Dispather {
    val linkedBlockingQueue = LinkedBlockingQueue<Runnable>()
    var threadPoolExecutor: ThreadPoolExecutor
    var runnable: Runnable

    //懒汉单例
    companion object {
        private var instance: Dispather? = null
            get() {
                if (field == null) {
                    field = Dispather()
                }
                return field
            }

        fun get(): Dispather {
            return instance!!
        }
    }

    constructor() {
        threadPoolExecutor = ThreadPoolExecutor(10, Int.MAX_VALUE, 60, TimeUnit.SECONDS,
            linkedBlockingQueue, object : RejectedExecutionHandler {
                override fun rejectedExecution(p0: Runnable?, p1: ThreadPoolExecutor?) {
                    linkedBlockingQueue.put(p0)
                }
            })

        // 创建一个死循环，用于从队列中取数据
        runnable = Runnable {
            while (true) {
                var run = linkedBlockingQueue.take()
                threadPoolExecutor.execute(run)
            }
        }

        threadPoolExecutor.execute(runnable)
    }

    /**
     * 新来的任务加入到队列中
     */
    open fun enqueue(run: Runnable) {
        linkedBlockingQueue.put(run)
    }
}

