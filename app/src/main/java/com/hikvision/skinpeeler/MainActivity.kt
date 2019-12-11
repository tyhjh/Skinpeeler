package com.hikvision.skinpeeler

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hikvision.skinlibrary.SkinManager
import com.hikvision.skinpeeler.utils.FileUtil
import com.hikvision.skinpeeler.utils.SharedPreferencesUtil
import com.hikvision.skinpeeler.utils.threadpool.AppExecutors
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @author  Tyhj
 * @date    2019-12-12
 *
 */

class MainActivity : AppCompatActivity() {

    /**
     * 换肤包保存位置
     */
    private val skinPath = "/sdcard/skin.apk"

    /**
     * 是否进行了换肤key
     */
    private val skinChangedKey = "skin_changed"

    /**
     * theme key
     */
    private val androidThemeKey = "android_theme"

    /**
     * 插件化换肤
     */
    private lateinit var switchJump: Switch
    /**
     * 切换Theme
     */
    private lateinit var swTheme: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themeStatus = SharedPreferencesUtil.getBoolean(androidThemeKey, false)
        if (themeStatus) {
            setTheme(R.style.AppTheme)
        } else {
            setTheme(R.style.AppTheme2)
        }
        setContentView(R.layout.activity_main)

        //复制文件
        val file = File(skinPath)
        if (!file.exists()) {
            FileUtil.copyFileFromAssets(this@MainActivity, "skin.apk", skinPath)
        }

        switchJump = findViewById(R.id.switchSkin);
        swTheme = findViewById(R.id.swTheme)

        swTheme.isChecked = themeStatus
        swTheme.setOnClickListener { v ->
            SharedPreferencesUtil.save(androidThemeKey, swTheme.isChecked)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        switchJump.isChecked = SharedPreferencesUtil.getBoolean(skinChangedKey, false)
        //获取存储权限
        if (lacksPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

        switchJump.setOnClickListener { v ->
            var isChecked = switchJump.isChecked
            if (isChecked) {
                if (SkinManager.getInstance().loadSkin(skinPath)) {
                    Toast.makeText(this@MainActivity, "换肤成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "换肤失败", Toast.LENGTH_SHORT).show()
                    AppExecutors.getInstance().scheduledExecutorService().schedule({ AppExecutors.getInstance().mainThread().execute { switchJump.isChecked = false } }, 1, TimeUnit.SECONDS)
                    isChecked = false
                }
            } else {
                SkinManager.getInstance().clearSkin()
                Toast.makeText(this@MainActivity, "恢复默认", Toast.LENGTH_SHORT).show()
            }
            SharedPreferencesUtil.save(skinChangedKey, isChecked)
        }

    }


    /**
     * 判断是否缺少权限
     *
     * @param permission
     * @return
     */
    private fun lacksPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
    }


}