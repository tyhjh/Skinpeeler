package com.hikvision.skinpeeler;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.hikvision.skinlibrary.SkinManager;
import com.hikvision.skinpeeler.utils.FileUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final String SKIN_PATH = "/sdcard/skin.apk";

    Switch switchJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchJump = findViewById(R.id.switchSkin);
        if (lacksPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        switchJump.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                SkinManager.getInstance().loadSkin(SKIN_PATH);
                Toast.makeText(MainActivity.this, "换肤成功", Toast.LENGTH_SHORT).show();
            } else {
                SkinManager.getInstance().loadSkin(null);
                Toast.makeText(MainActivity.this, "恢复默认", Toast.LENGTH_SHORT).show();
            }
        });


        //下载皮肤包
        findViewById(R.id.tvSkinDownload).setOnClickListener((v) -> {
            FileUtil.copyFileFromAssets(MainActivity.this, "skin.apk", SKIN_PATH);
            Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * 判断是否缺少权限
     *
     * @param permission
     * @return
     */
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}
