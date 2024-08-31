package xyz.mufanc.boox.enhance.core.trigger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import xyz.mufanc.boox.enhance.core.CommandReceiver

class ColorInversionTrigger : Activity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        sendBroadcast(Intent(CommandReceiver.ACTION_INVERT_COLOR))
        finish()
    }
}
