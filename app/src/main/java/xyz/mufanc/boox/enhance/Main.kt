package xyz.mufanc.boox.enhance

import android.annotation.SuppressLint
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import org.joor.Reflect
import xyz.mufanc.boox.enhance.core.CommandChannel
import xyz.mufanc.boox.enhance.core.LooperObserver
import xyz.mufanc.boox.enhance.core.ability.ColorInversion


@SuppressLint("PrivateApi")
@Suppress("Unused")
class Main : IXposedHookLoadPackage {

    companion object {
        private const val TAG = "BooxEnhance"
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        EzXHelper.initHandleLoadPackage(lpparam)
        EzXHelper.setLogTag(TAG)

        if (lpparam.packageName == "android") {
            onSystemServerLoaded()
        }
    }

    private fun onSystemServerLoaded() {
        Reflect.on(javaClass.classLoader).set("parent", ClassLoaderProvider.classLoader)

        LooperObserver.observe {
            Log.i("system ready, register receiver...")

            CommandChannel.register(ColorInversion)
            CommandChannel.commit()
        }
    }
}
