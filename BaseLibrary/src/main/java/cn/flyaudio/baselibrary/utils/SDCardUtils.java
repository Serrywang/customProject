package cn.flyaudio.baselibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @className SDCardUtils
 * @createDate 2018/11/5 17:40
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc SD卡工具类
 *
 */
public final class SDCardUtils {
    /**
     * 防止实例化
     */
    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 返回外部存储是否可使用（已挂载）
     *
     * @return true : 是 <br>false : 否
     */
    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 返回外部存储的绝对路径，默认的外部储存路径，一般返回的是内置虚拟SD卡路径
     *
     * @return the path of sdcard by environment
     */
    public static String getDefaultSDCardPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    /**
     * 返回SD卡是否可以使用
     * @return true : 是 <br>false : 否
     */
    public static boolean isSDCardEnable() {
        return !getSDCardPaths().isEmpty();
    }

    /**
     * 返回SD卡目录列表
     * @param removable 是否可移除，可移除就是外置SD卡
     * @return SD卡目录列表
    */
    public static List<String> getSDCardPaths(final boolean removable) {
        List<String> paths = new ArrayList<>();
        StorageManager sm =
                (StorageManager) AppUtils.getApp().getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            //noinspection JavaReflectionMemberAccess
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList");
            //noinspection JavaReflectionMemberAccess
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(sm);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean res = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable == res) {
                    paths.add(path);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 获取物理可移除的SD卡绝对路径,如果有多个SD卡，则返回的是第一张的
     * @return 可移动的SD卡绝对路径,如果没有，则返回null
     */
    public static String getDefaultRemoveableSDCardPath(){
        List<String> paths = getSDCardPaths(true);
        if (paths.size()!=0){
            return paths.get(0);
        }
        return null;
    }

    /**
     * 获取所有物理可移除的SD卡路径列表
     * @return 所有物理可移除的SD卡路径列表
     */
    public static List<String> getRemoveableSDCardPaths(){
        return getSDCardPaths(true);
    }

    /**
     * 返回SD卡目录列表
     * @return SD卡目录列表
     */
    public static List<String> getSDCardPaths() {
        StorageManager storageManager = (StorageManager) AppUtils.getApp()
                .getSystemService(Context.STORAGE_SERVICE);
        List<String> paths = new ArrayList<>();
        try {
            //noinspection JavaReflectionMemberAccess
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            getVolumePathsMethod.setAccessible(true);
            Object invoke = getVolumePathsMethod.invoke(storageManager);
            paths = Arrays.asList((String[]) invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 获取默认SD卡总空间大小
     * @return byte字节数
     */
    public static  long getDefaultSDCardTotalSize() {
        if (isSDCardEnable()) {
            String path = getDefaultSDCardPath();
            return getSDCardTotalSize(path);
        } else {
            return 0;
        }
    }

    /**
     * 获取默认SD卡可用空间大小
     * @return byte字节数
     */
    public static long getDefaultSDCardAvailableSize(){
        if (isSDCardEnable()) {
            String path = getDefaultSDCardPath();
            return getSDCardAvailableSize(path);
        } else {
            return 0;
        }
    }

    /**
     * 获取物理可移除类型SD卡总空间大小,如果有多个SD卡，则返回的是第一张的
     * @return byte字节数
     */
    public static long getDefaultRemoveableSDCardTotalSize(){
        if (isSDCardEnable()) {
            String pathSt = getDefaultRemoveableSDCardPath();
            return getSDCardTotalSize(pathSt);
        } else {
            return 0;
        }
    }

    /**
     * 获取物理可移除类型SD卡可用空间大小,如果有多个SD卡，则返回的是第一张的
     * @return byte字节数
     */
    public static long getDefaultRemoveableSDCardAvailableSize(){
        if (isSDCardEnable()) {
            String pathSt = getDefaultRemoveableSDCardPath();
            return getSDCardAvailableSize(pathSt);
        } else {
            return 0;
        }
    }

    /**
     * 获取指定SD卡的总容量
     * @param SDcardPath SD卡路径
     * @return SD卡的总容量
     */
    public static long getSDCardTotalSize(@NonNull String SDcardPath){
        if (SDcardPath == null){
            return 0;
        }
        StatFs stat = new StatFs(SDcardPath);
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    /**
     * 获取指定SD卡的可用容量
     * @param SDcardPath  SD卡路径
     * @return
     */
    public static long getSDCardAvailableSize(@NonNull String SDcardPath){
        if (SDcardPath == null){
            return 0;
        }
        StatFs stat = new StatFs(SDcardPath);
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }
}
