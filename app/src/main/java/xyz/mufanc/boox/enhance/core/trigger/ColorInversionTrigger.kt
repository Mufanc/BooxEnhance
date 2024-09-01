package xyz.mufanc.boox.enhance.core.trigger

import android.app.Activity
import android.os.Bundle
import xyz.mufanc.boox.enhance.core.CommandChannel
import xyz.mufanc.boox.enhance.core.ability.ColorInversion

class ColorInversionTrigger : Activity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        CommandChannel.Emitter.emit(ColorInversion.action())
        finish()
    }
}
