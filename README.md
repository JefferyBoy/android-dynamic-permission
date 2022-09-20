# android动态权限申请

使用aspectj编译期class字节码插入实现动态权限申请

使用方式

1. 在项目根目录的build.gradle中加入

```gradle
buildscript {
    dependencies {
        classpath 'org.aspectj:aspectjweaver:1.9.9.1'
        classpath 'org.aspectj:aspectjtools:1.9.9.1'
    }
}
```

2. 在需要使用的模块中build.gradle中加入aspectj支持

```gradle
// aspectj.gradle放到项目根目录
apply from: '../aspectj.gradle'

dependencies {
    implementation project(':library')
}
```

3. 项目中使用

```java

public class MainActivity extends Activity {

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
    }

    /**
     * 使用@Permission注解需要申请动态权限后再执行的方法
     * */
    @Permission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestStoragePermission() {
        // 权限申请成功后才会执行到这里
        Log.d(TAG, "requestStoragePermission ok");
    }

    /**
     * 申请权限被拒绝后执行的方法
     * 参数可以为List\<PermisssionResult\> 或者 无参数 
     * */
    @PermissionDenied()
    void onPermissionDenied(List<PermissionResult> results) {
        // 权限申请被拒绝
        Log.d(TAG, "requestPermissionDenied");
        for (PermissionResult result : results) {
            System.out.println(result);
        }
    }
}

```