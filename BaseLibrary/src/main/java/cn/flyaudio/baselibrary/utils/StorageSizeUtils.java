package cn.flyaudio.baselibrary.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import cn.flyaudio.baselibrary.common.MemoryConstants;


/**
 * @author newtrekWang
 * @fileName StorageSizeUtils
 * @createDate 2018/11/6 11:53
 * @email 408030208@qq.com
 * @desc 存储空间大小工具类
 */
public final class StorageSizeUtils {

    private static final String TAG = "StorageSizeUtils";
    /**
     * 防止实例化
     */
    private StorageSizeUtils(){
        throw new UnsupportedOperationException("u can't touch me!");
    }

    /**
     * 返回文件或文件夹的容量大小,注：如果文件数量多，该方法会比较耗时
     * @param filePath 文件或文件夹绝对路径
     * @param sizeType 容量大小单位
     * @return
     */
    public static double getFileOrFilesSize(String filePath, @MemoryConstants.Unit final int sizeType){
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()){
                blockSize = getFileSizes(file);
            }else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            Log.e(TAG, "getFileOrFilesSize: >>>获取文件大小失败！" );
            e.printStackTrace();
        }
        return formatFileSize(blockSize,sizeType);
    }

    /**
     * 返回文件或文件夹的容量大小字符串,注：如果文件数量多，该方法会比较耗时
     * @param filePath 文件或文件夹绝对路径
     * @param sizeType 容量大小单位
     * @return
     */
    public static String getFileOrFilesSizeString(String filePath, @MemoryConstants.Unit final int sizeType){
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()){
                blockSize = getFileSizes(file);
            }else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            Log.e(TAG, "getFileOrFilesSize: >>>获取文件大小失败！" );
            e.printStackTrace();
        }
        return formatFileSize2String(blockSize,sizeType);
    }

    /**
     * 返回文件或文件夹的容量大小字符串，自动选则合适的单位,注：如果文件数量多，该方法会比较耗时
     * @param filePath
     * @return
     */
    public static String getFileOrFilesSizeAutoString(String filePath){
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()){
                blockSize = getFileSizes(file);
            }else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            Log.e(TAG, "getFileOrFilesSize: >>>获取文件大小失败！" );
            e.printStackTrace();
        }
        return formatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     * @param file 文件对象
     * @return 文件大小
     * @throws Exception 异常
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()){
            FileInputStream fis= null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        }
        return size;
    }

    /**
     * 获取指定文件夹的容量,注：如果文件数量多，该方法会比较耗时
     * @param dirFile 文件夹对象
     * @return 容量
     * @throws Exception 异常
     */
    private static long getFileSizes(File dirFile) throws Exception {
        long size = 0;
        File[] fileList = dirFile.listFiles();
        for (int i=0;i<fileList.length;i++){
            if (fileList[i].isDirectory()){
                size +=getFileSizes(fileList[i]);
            }else {
                size +=getFileSize(fileList[i]);
            }
        }
        return size;
    }

    /**
     * 转大小为合适的大小字符串描述
     * @param byteSize 字节大小
     * @return 合适的大小字符串描述
     */
    public static String formatFileSize(final long byteSize){
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (byteSize == 0){
            return wrongSize;
        }
        if (byteSize < 1024){

            fileSizeString = df.format((double) byteSize)+"B";

        }else if (byteSize < 1048576){

            fileSizeString = df.format((double)byteSize/1024)+"K";

        }else if (byteSize < 1073741824){

            fileSizeString = df.format((double)byteSize/1048576)+"M";

        }else{

            fileSizeString = df.format((double)byteSize/1073741824)+"G";

        }
        return fileSizeString;
    }

    /**
     * 转换为指定单位类型的大小
     * @param byteSize 字节大小
     * @param sizeType 容量单位类型
     * @return 容量大小
     */
    public static double formatFileSize(final long byteSize, @MemoryConstants.Unit final int sizeType){
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType){
            case MemoryConstants.BYTE:
                fileSizeLong = Double.valueOf(df.format((double)byteSize));
                break;
            case MemoryConstants.KB:
                fileSizeLong = Double.valueOf(df.format((double)byteSize/1024));
                break;
            case MemoryConstants.MB:
                fileSizeLong = Double.valueOf(df.format((double)byteSize/1048576));
                break;
            case MemoryConstants.GB:
                fileSizeLong = Double.valueOf(df.format((double)byteSize/1073741824));
                break;
        }
        return fileSizeLong;
    }

    /**
     * 转换为指定单位类型的大小字符串描述
     * @param byteSize 字节大小
     * @param sizeType 容量单位类型
     * @return 容量大小字符串
     */
    public static String formatFileSize2String(final long byteSize, @MemoryConstants.Unit final int sizeType){
        DecimalFormat df = new DecimalFormat("#0.00");
        String sizeString = "0B";
        if (byteSize == 0){
            return sizeString;
        }
        switch (sizeType){
            case MemoryConstants.BYTE:
                sizeString = df.format((double)byteSize)+"B";
                break;
            case MemoryConstants.KB:
                sizeString = df.format((double)byteSize/1024)+"K";
                break;
            case MemoryConstants.MB:
                sizeString = df.format((double)byteSize/1048576)+"M";
                break;
            case MemoryConstants.GB:
                sizeString = df.format((double)byteSize/1073741824)+"G";
                break;
        }
        return sizeString;
    }

}
