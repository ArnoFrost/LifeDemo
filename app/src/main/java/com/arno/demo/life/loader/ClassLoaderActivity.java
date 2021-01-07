package com.arno.demo.life.loader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.arno.demo.life.MainActivity;
import com.arno.demo.life.R;
import com.blankj.utilcode.util.PathUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

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
//            Class clazz = dexClassLoader.loadClass("com.google.gson.JsonSerializer");
//            Class clazz = dexClassLoader.loadClass("com.google.gson.Gson");
            Class clazz = dexClassLoader.loadClass("com.google.gson.internal.GsonBuildConfig");
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


    public static void loadDex(Context context) {
        List<File> mLoadedDex = new ArrayList<>();
        if (context == null) {
            return;
        }
        //1. 在缓存目录下得到文件列表
        File filesDir = context.getCodeCacheDir();
        File[] listFiles = filesDir.listFiles();
        if (listFiles == null) {
            return;
        }

        //2. 过滤非dex文件
        for (File file : listFiles) {
            if (file.getName().startsWith("classes") || file.getName().endsWith(".dex")) {
                Log.d(TAG, "dexName:" + file.getName());
                mLoadedDex.add(file);
            }
        }

        //3. 遍历文件加入
        for (File dex : mLoadedDex) {
            try {
                //4. 获取PathClassLoader加载的系统类等
                PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
                Class baseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");
                Field pathListFiled = baseDexClassLoader.getDeclaredField("pathList");
                pathListFiled.setAccessible(true);
                Object pathListObject = pathListFiled.get(pathClassLoader);

                Class systemDexPathListClass = pathListObject.getClass();
                Field systemElementsField = systemDexPathListClass.getDeclaredField("dexElements");
                systemElementsField.setAccessible(true);
                Object systemElements = systemElementsField.get(pathListObject);

                //5. 自定义DexClassLoader定义要载入的补丁dex，此处其实可以将多个dex用「:」隔开，则无需遍历
                DexClassLoader dexClassLoader = new DexClassLoader(dex.getAbsolutePath(), null, null, context.getClassLoader());
                Class customDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");
                Field customPathListFiled = customDexClassLoader.getDeclaredField("pathList");
                customPathListFiled.setAccessible(true);
                Object customDexPathListObject = customPathListFiled.get(dexClassLoader);

                Class customPathClass = customDexPathListObject.getClass();
                Field customElementsField = customPathClass.getDeclaredField("dexElements");
                customElementsField.setAccessible(true);
                Object customElements = customElementsField.get(customDexPathListObject);

                //6. 合并数组
                Class<?> elementClass = systemElements.getClass().getComponentType();
                int systemLength = Array.getLength(systemElements);
                int customLength = Array.getLength(customElements);
                int newSystemLength = systemLength + customLength;

                //7. 生成一个新的数组，类型为Element类型
                Object newElementsArray = Array.newInstance(elementClass, newSystemLength);
                for (int i = 0; i < newSystemLength; i++) {
                    if (i < customLength) {
                        Array.set(newElementsArray, i, Array.get(customElements, i));
                    } else {
                        Array.set(newElementsArray, i, Array.get(systemElements, i - customLength));
                    }
                }

                //8. 覆盖新数组
                Field elementsField = pathListObject.getClass().getDeclaredField("dexElements");
                elementsField.setAccessible(true);
                elementsField.set(pathListObject, newElementsArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}