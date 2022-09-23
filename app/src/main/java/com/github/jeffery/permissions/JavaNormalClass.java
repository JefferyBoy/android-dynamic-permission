package com.github.jeffery.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.github.jeffery.permission.annotation.Permission;

/**
 * @author mxlei
 * @date 2022/9/23
 */
public class JavaNormalClass {
    @Permission(Manifest.permission.RECORD_AUDIO)
    public void normalClassTest(Context context) {
        if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            System.out.println("normalClassTest success");
        } else {
            throw new RuntimeException("normalClassTest fail");
        }
    }
}
