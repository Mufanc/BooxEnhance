package xyz.mufanc.boox.enhance

import android.annotation.SuppressLint
import android.app.ActivityThread
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import org.joor.Reflect
import xyz.mufanc.boox.enhance.core.CommandReceiver
import xyz.mufanc.boox.enhance.core.LooperObserver


@SuppressLint("PrivateApi")
@Suppress("Unused")
class Main : IXposedHookLoadPackage {

    companion object {
        private const val TAG = "BooxEnhance"
    }

    private val mContext: Context by lazy {
        ActivityThread.currentActivityThread().systemContext
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        EzXHelper.initHandleLoadPackage(lpparam)
        EzXHelper.setLogTag(TAG)

        if (lpparam.packageName == "android") {
            onSystemServerLoaded()
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun onSystemServerLoaded() {
        Reflect.on(javaClass.classLoader).set("parent", ClassLoaderProvider.classLoader)

        LooperObserver.observe {
            Log.i("system ready, register receiver...")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mContext.registerReceiver(
                    CommandReceiver(),
                    IntentFilter().apply {
                        addAction(CommandReceiver.ACTION_INVERT_COLOR)
                    },
                    Context.RECEIVER_EXPORTED
                )
            } else {
                mContext.registerReceiver(
                    CommandReceiver(),
                    IntentFilter().apply {
                        addAction(CommandReceiver.ACTION_INVERT_COLOR)
                    }
                )
            }
        }
    }
}
