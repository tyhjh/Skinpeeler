package com.hikvision.skinpeeler.app

import android.app.Application
import com.hikvision.skinlibrary.SkinManager
import com.hikvision.skinpeeler.utils.SharedPreferencesUtil
import com.hikvision.skinpeeler.utils.threadpool.AppExecutors

/**
 *
 * @author  Tyhj
 * @date    2019-12-12
 *
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
        SharedPreferencesUtil.init(this)
        AppExecutors.getInstance()
    }
}