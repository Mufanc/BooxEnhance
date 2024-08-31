package xyz.mufanc.boox.enhance.core

import android.os.Handler
import android.os.Looper
import android.os.LooperHidden
import android.os.Message

class LooperObserver(
    private val callback: () -> Unit
) : LooperHidden.Observer, Runnable {

    override fun run() {
        callback()
    }

    fun finalize() {
        Handler(Looper.getMainLooper()).postAtFrontOfQueue(this)
    }

    override fun messageDispatchStarting(): Any? = null
    override fun messageDispatched(token: Any?, msg: Message?) = Unit
    override fun dispatchingThrewException(token: Any?, msg: Message?, err: Exception?) = Unit

    companion object {
        fun observe(callback: () -> Unit) {
            LooperHidden.setObserver(LooperObserver(callback))
        }
    }
}