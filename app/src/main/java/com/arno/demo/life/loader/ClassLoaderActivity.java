package com.arno.demo.life.loader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.arno.demo.life.MainActivity;
import com.arno.demo.life.R;
import com.blankj.utilcode.util.PathUtils;

import java.io.File;

import dalvik.system.DexClassLoader;

public class ClassLoaderActivity extends AppCompatActivity {
    private static final String TAG = "ClassLoaderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_loader);
        printClassLoader();

        loadGson();
    }

    private void loadGson() {
        final File dexFile = new File(PathUtils.getExternalDownloadsPath() + File.separator + "gson_dex.jar");
        Log.d(TAG, "loadGson: can read " + dexFile.canRead());

        if (!dexFile.exists()) {
            Log.e(TAG, "loadGson: not exists");
            return;
        }

        DexClassLoader dexClassLoader =
                new DexClassLoader(dexFile.getAbsolutePath(), PathUtils.getExternalDownloadsPath(),
                        null, getClassLoader());

        try {
            Class clazz = dexClassLoader.loadClass("com.google.gson.JsonSerializer");
            if (clazz != null) {
                Log.d(TAG, "loadGson: " + clazz.getCanonicalName());
            } else {
                Log.e(TAG, "loadGson: class failed");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void printClassLoader() {
        ClassLoader loader = ClassLoaderActivity.class.getClassLoader();
        while (loader != null) {
            Log.d(TAG, "printClassLoader: -> " + loader.toString());
            loader = loader.getParent();
        }
    }
}