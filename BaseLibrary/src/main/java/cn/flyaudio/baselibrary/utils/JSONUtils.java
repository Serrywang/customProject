package cn.flyaudio.baselibrary.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newtrekWang
 * @fileName JSONUtils
 * @createDate 2018/10/31 14:59
 * @email 408030208@qq.com
 * @desc JSON工具类，是对Gson的一层简单封装
 */
public final class JSONUtils {
    /**
     * 构造器私有
     */
    private JSONUtils(){throw new UnsupportedOperationException("u don't touch me!");}

    /**
     * gson对象
     */
    private static Gson gson = null;

    /**
     * 初始化
     */
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 获取gong单例
     * @return
     */
    public static Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }
        return gson;
    }
    /**
     * 将object对象转成json字符串
     *
     * @param object object对象。注意：如果object是Map类型，那么map中不能包含数组，否则解析不出来
     * @return json字符串
     */
    public static String object2JsonString(@NonNull Object object) {
        if (object == null){
            throw new NullPointerException("object is null");
        }
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 将json字符串转为对象
     *
     * @param jsonString json字符串
     * @param cls 要转的对象的class.注意：最好是自定义的数据类型，如果是Map的class,那么只支持map里标准类型的解析
     * @return 解析出的数据对象
     */
    public static <T> T jsonString2Object(@NonNull String jsonString, @NonNull Class<T> cls) {
        if (jsonString == null || cls == null){
            throw  new NullPointerException();
        }
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }
    /**
     * json字符串转成list对象
     * @param jsonString json字符串
     * @param cls 要转的Item对象的class
     * @return list对象
     */
    public static <T> List<T> jsonString2List(String jsonString, Class<T> cls) {
        if (jsonString == null || cls == null){
            throw  new NullPointerException();
        }
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }
}
