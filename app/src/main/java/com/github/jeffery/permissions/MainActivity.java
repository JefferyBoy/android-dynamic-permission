package com.github.jeffery.permissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.jeffery.permission.PermissionResult;
import com.github.jeffery.permission.annotation.Permission;
import com.github.jeffery.permission.annotation.PermissionDenied;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        });
        findViewById(R.id.btn_write).setOnClickListener(this);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.btn_location).setOnClickListener(this);
        findViewById(R.id.btn_voice).setOnClickListener(this);
        findViewById(R.id.btn_normal_test).setOnClickListener(this);
        findViewById(R.id.btn_anonymous).setOnClickListener(this);
    }

    @Permission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestStoragePermission() {
        Log.d(TAG, "requestStoragePermission ok");
    }

    @Permission(value = {Manifest.permission.ACCESS_FINE_LOCATION})
    void requestLocationPermission() {
        Log.d(TAG, "requestLocationPermission ok");
    }

    @Permission(value = {Manifest.permission.RECORD_AUDIO})
    void requestVoicePermission() {
        Log.d(TAG, "requestVoicePermission ok");
    }

    @Permission(value = {Manifest.permission.CAMERA})
    void requestCameraPermission() {
        Log.d(TAG, "requestCameraPermission ok");
    }

    @PermissionDenied()
    void onPermissionDenied(List<PermissionResult> results) {
        Log.d(TAG, "requestPermissionDenied");
        for (PermissionResult result : results) {
            System.out.println(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                requestCameraPermission();
                break;
            case R.id.btn_write:
                requestStoragePermission();
                break;
            case R.id.btn_location:
                requestLocationPermission();
                break;
            case R.id.btn_voice:
                requestVoicePermission();
                break;
            case R.id.btn_normal_test:
                new JavaNormalClass().normalClassTest(v.getContext());
                break;
            case R.id.btn_anonymous:
                new JavaNormalClass().normalClassTestAnonymousClass(new Consumer<String>() {
                    @Override
                    @Permission(Manifest.permission.CALL_PHONE)
                    public void accept(String s) {
                        int grant = ContextCompat.checkSelfPermission(
                            MainActivity.this,
                            Manifest.permission.CALL_PHONE
                        );
                        if (grant == PackageManager.PERMISSION_GRANTED) {
                            System.out.println("anonymous class success");
                        } else {
                            throw new RuntimeException("normalClassTest fail");
                        }
                    }
                });
                break;
            default:
        }
    }
}