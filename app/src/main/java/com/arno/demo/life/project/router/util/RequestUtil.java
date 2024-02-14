package com.arno.demo.life.project.router.util;

import android.os.Parcelable;
import android.util.ArrayMap;

import com.arno.demo.life.project.router.base.Constant;
import com.arno.demo.life.project.router.base.RouterRequest;

public class RequestUtil {
    public static <T extends Parcelable> RouterRequest mainRouterBuilder(String action, ArrayMap<String, String> map, T obj) {
        return new RouterRequest()
                .withFrom(Constant.DOMAIN.MAIN)
                .withAction(action)
                .withData(map)
                .withObj(obj);
    }
}
