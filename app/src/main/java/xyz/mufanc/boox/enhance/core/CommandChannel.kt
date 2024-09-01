package xyz.mufanc.boox.enhance.core

import android.annotation.SuppressLint
import android.app.ActivityThread
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.ArrayMap
import com.github.kyuubiran.ezxhelper.Log
import xyz.mufanc.boox.enhance.App
import xyz.mufanc.boox.enhance.BuildConfig

object CommandChannel : BroadcastReceiver() {

    private const val ACTION_PREFIX = BuildConfig.APPLICATION_ID

    private val mSystemContext: Context by lazy {
        ActivityThread.currentActivityThread().systemContext
    }

    private val mCommandHandlers by lazy { ArrayMap<String, ICommandHandler>() }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return
        val extras = intent.extras ?: return

        @Suppress("DEPRECATION")
        val token = extras.getParcelable("token") as? PendingIntent

        if (token?.creatorPackage != BuildConfig.APPLICATION_ID) {
            Log.w("onReceive, but authentication failed, ignore")
            return
        }

        Log.i("onReceive, action = $action")

        try {
            mCommandHandlers[intent.action]?.onCommand(extras.getBundle("extras"))
        } catch (err: Exception) {
            Log.e(err)
        }
    }

    private fun wrapAction(action: String): String {
        return "$ACTION_PREFIX.$action"
    }

    fun register(handler: ICommandHandler) {
        mCommandHandlers[wrapAction(handler.action())] = handler
    }

    private fun createIntentFilter(): IntentFilter {
        return IntentFilter().apply {
            mCommandHandlers.keys.forEach { action ->
                addAction(action)
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun commit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mSystemContext.registerReceiver(
                CommandChannel,
                createIntentFilter(),
                Context.RECEIVER_EXPORTED
            )
        } else {
            mSystemContext.registerReceiver(
                CommandChannel,
                createIntentFilter()
            )
        }
    }

    interface ICommandHandler {
        fun action(): String
        fun onCommand(extras: Bundle?)
    }

    object Emitter {
        fun emit(action: String, extras: Bundle? = null) {
            App.instance.sendBroadcast(
                Intent(wrapAction(action)).apply {
                    setPackage("android")
                    putExtra("extras", extras)
                    putExtra("token", PendingIntent.getBroadcast(App.instance, 0, Intent(), PendingIntent.FLAG_IMMUTABLE))
                }
            )
        }
    }
}
