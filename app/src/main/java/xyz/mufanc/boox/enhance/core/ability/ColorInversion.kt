package xyz.mufanc.boox.enhance.core.ability

import android.annotation.SuppressLint
import android.os.Bundle
import com.android.server.LocalServices
import com.android.server.display.color.ColorDisplayService
import com.android.server.display.color.DisplayTransformManager
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.FieldFinder.`-Static`.fieldFinder
import xyz.mufanc.boox.enhance.core.CommandChannel

@SuppressLint("PrivateApi")
object ColorInversion : CommandChannel.ICommandHandler {

    private var mInverted = false

    private val MATRIX_INVERT_COLOR: FloatArray by lazy {
        ColorDisplayService::class.java
            .fieldFinder()
            .filterByName("MATRIX_INVERT_COLOR")
            .first()
            .apply { isAccessible = true }
            .get(null) as FloatArray
    }

    private val LEVEL_COLOR_MATRIX_INVERT_COLOR: Int by lazy {
        DisplayTransformManager::class.java
            .fieldFinder()
            .filterByName("LEVEL_COLOR_MATRIX_INVERT_COLOR")
            .first()
            .get(null) as Int
    }

    override fun action(): String = "INVERT_COLOR"

    @Synchronized
    override fun onCommand(extras: Bundle?) {
        mInverted = !mInverted

        LocalServices.getService(DisplayTransformManager::class.java)
            .setColorMatrix(LEVEL_COLOR_MATRIX_INVERT_COLOR, if (mInverted) MATRIX_INVERT_COLOR else null)

        Log.i("toggle color inversion")
    }
}
