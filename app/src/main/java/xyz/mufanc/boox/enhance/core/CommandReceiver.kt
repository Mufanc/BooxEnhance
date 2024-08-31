package xyz.mufanc.boox.enhance.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.kyuubiran.ezxhelper.Log
import xyz.mufanc.boox.enhance.core.ability.ColorInversion

class CommandReceiver : BroadcastReceiver() {

    companion object {
        private const val ACTION_PREFIX = "xyz.mufanc.boox.enhance"

        const val ACTION_INVERT_COLOR = "$ACTION_PREFIX.INVERT_COLOR"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return

        Log.i("onReceive, action = $action")

        try {
            when (action) {
                ACTION_INVERT_COLOR -> ColorInversion.toggle()
            }
        } catch (err: Exception) {
            Log.e(err)
        }
    }
}
