package com.arno.demo.life.router.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class RouterRequest<T extends Parcelable> implements Parcelable {
    private static final String TAG = Constant.PRE.PRE_FIX + "RouterRequest";
    //来自
    private String from;
    //大分类
    private String domain;
    //命令
    private String action;
    //参数map
    private ArrayMap<String, String> data;
    //保留扩展自定义
    T obj;

    public RouterRequest withFrom(String from) {
        this.from = from;
        return this;
    }

    public RouterRequest withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public RouterRequest withAction(String action) {
        this.action = action;
        return this;
    }

    public RouterRequest withData(ArrayMap<String, String> data) {
        this.data = data;
        return this;
    }

    public RouterRequest withObj(T obj) {
        this.obj = obj;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public String getDomain() {
        return domain;
    }

    public String getAction() {
        return action;
    }

    public ArrayMap<String, String> getData() {
        return data;
    }

    public T getObj() {
        return obj;
    }

    public RouterRequest() {
        this.from = "";
        this.domain = "";
        this.action = "";
        this.data = new ArrayMap<>();
    }

    /**
     * 序列化过程
     *
     * @param in
     */
    protected RouterRequest(Parcel in) {
        from = in.readString();
        domain = in.readString();
        action = in.readString();
        obj = (T) in.readParcelable(this.getClass().getClassLoader());
        int mapSize = Math.max(in.readInt(), 0);
        data = new ArrayMap<>();
        for (int i = 0; i < mapSize; i++) {
            String key = in.readString();
            String value = in.readString();
            data.put(key, value);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(domain);
        dest.writeString(action);
        dest.writeParcelable(obj, flags);
        if (data != null && !data.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
            //先写入大小
            dest.writeInt(data.size());
            //循环写入数组
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                if (next != null) {
                    dest.writeString(next.getKey());
                    dest.writeString(next.getValue());
                }

            }

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouterRequest> CREATOR = new Creator<RouterRequest>() {
        @Override
        public RouterRequest createFromParcel(Parcel in) {
            return new RouterRequest(in);
        }

        @Override
        public RouterRequest[] newArray(int size) {
            return new RouterRequest[size];
        }
    };


    @NonNull
    @Override
    public String toString() {
        return toJsonString();
    }

//    //TODO 反序列化待处理
//    public RouterRequest fromJson(String json) {
//
//
//    }

    public String toJsonString() {
        //Here remove Gson to save about 10ms.
        //String result = new Gson().toJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from", from);
            jsonObject.put("domain", domain);
            jsonObject.put("action", action);

            try {
                if (data != null) {
                    JSONObject jsonData = new JSONObject();
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        jsonData.put(entry.getKey(), entry.getValue());
                    }
                    jsonObject.put("data", jsonData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                jsonObject.put("data", "{}");
            }
            //TODO
//            jsonObject.put("obj", obj.toJsonString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
