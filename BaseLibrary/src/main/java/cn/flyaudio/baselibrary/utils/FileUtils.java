package cn.flyaudio.baselibrary.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @className FileUtils
 * @createDate 2018/11/7 10:22
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 文件操作工具
 *
 */
public final class FileUtils {
    /**
     * 文件系统的分隔符
     */
    public static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * 防止实例化
     */
    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 通过路径返回File实例
     *
     * @param filePath 文件路径
     * @return file实例，如果路径为空则返回null
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 文件或文件夹否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 是 <br>{@code false}: 否
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * file是否存在
     *
     * @param file file对象
     * @return {@code true}: 是 <br>{@code false}: 否
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路径
     * @param newName  新文件名
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 重命名文件
     *
     * @param file    file对象
     * @param newName 新文件名
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean rename(final File file, final String newName) {
        // file is null then return false
        if (file == null) {return false;}
        // file doesn't exist then return false
        if (!file.exists()) {return false;}
        // the new name is space then return false
        if (isSpace(newName)) {return false;}
        // the new name equals old name then return true
        if (newName.equals(file.getName())) {return true;}
        File newFile = new File(file.getParent() + File.separator + newName);
        // the new name of file exists then return false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * 是否为文件夹
     *
     * @param dirPath 路径
     * @return {@code true}: 是 <br>{@code false}: 否
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 是否为文件夹
     *
     * @param file file对象
     * @return {@code true}: 是 <br>{@code false}: 否
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 是否为文件
     *
     * @param filePath 路径
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 是否为文件
     *
     * @param file file对象
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     *  如果不存在则创建一个文件夹
     *
     * @param dirPath 文件夹路径
     * @return {@code true}: 存在或者成功创建 <br>{@code false}: 失败
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 如果不存在则创建一个文件夹
     *
     * @param file file对象
     * @return {@code true}: 存在或者成功创建<br>{@code false}: 失败
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 如果不存在则创建一个文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或者成功创建<br>{@code false}: 失败
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 如果不存在则创建一个文件
     *
     * @param file file对象
     * @return {@code true}: 存在或者成功创建 <br>{@code false}: 失败
     */
    public static boolean createOrExistsFile(final File file) {
        if (file == null) {return false;}
        if (file.exists()) {return file.isFile();}
        if (!createOrExistsDir(file.getParentFile())) {return false;}
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除旧的文件，新建文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 删除旧的文件，新建文件
     *
     * @param file file对象
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) {return false;}
        // file exists and unsuccessfully delete then return false
        if (file.exists() && !file.delete()) {return false;}
        if (!createOrExistsDir(file.getParentFile())) {return false;}
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制文件夹
     *
     * @param srcDirPath  要复制的文件夹路径
     * @param destDirPath 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyDir(final String srcDirPath,
                                  final String destDirPath) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 复制文件夹
     *
     * @param srcDirPath  要复制的文件夹路径
     * @param destDirPath 目的路径
     * @param listener    监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyDir(final String srcDirPath,
                                  final String destDirPath,
                                  final OnReplaceListener listener) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * 复制文件夹
     *
     * @param srcDir  要复制的文件夹路径
     * @param destDir 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyDir(final File srcDir,
                                  final File destDir) {
        return copyOrMoveDir(srcDir, destDir, false);
    }

    /**
     * 复制文件夹
     *
     * @param srcDir   要复制的文件夹路径
     * @param destDir  目的路径
     * @param listener 监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyDir(final File srcDir,
                                  final File destDir,
                                  final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    /**
     * 复制文件
     *
     * @param srcFilePath  要复制的文件路径
     * @param destFilePath  目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyFile(final String srcFilePath,
                                   final String destFilePath) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 复制文件
     *
     * @param srcFilePath  要复制的文件路径
     * @param destFilePath 目的路径
     * @param listener     监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyFile(final String srcFilePath,
                                   final String destFilePath,
                                   final OnReplaceListener listener) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * 复制文件
     *
     * @param srcFile  要复制的文件路径
     * @param destFile 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyFile(final File srcFile,
                                   final File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 复制文件
     *
     * @param srcFile  要复制的文件路径
     * @param destFile 目的路径
     * @param listener 监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean copyFile(final File srcFile,
                                   final File destFile,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    /**
     * 移动文件夹
     *
     * @param srcDirPath  要移动的文件夹路径
     * @param destDirPath 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveDir(final String srcDirPath,
                                  final String destDirPath) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 移动文件夹
     *
     * @param srcDirPath  要移动的文件夹路径
     * @param destDirPath 目的路径
     * @param listener    监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveDir(final String srcDirPath,
                                  final String destDirPath,
                                  final OnReplaceListener listener) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    /**
     * 移动文件夹
     *
     * @param srcDir  要移动的文件夹路径
     * @param destDir 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveDir(final File srcDir,
                                  final File destDir) {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * 移动文件夹
     *
     * @param srcDir   要移动的文件夹路径
     * @param destDir  目的路径
     * @param listener 监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveDir(final File srcDir,
                                  final File destDir,
                                  final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    /**
     * 移动文件
     *
     * @param srcFilePath  要移动的文件路径
     * @param destFilePath 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveFile(final String srcFilePath,
                                   final String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 移动文件
     *
     * @param srcFilePath  要移动的文件路径
     * @param destFilePath 目的路径
     * @param listener     监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveFile(final String srcFilePath,
                                   final String destFilePath,
                                   final OnReplaceListener listener) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    /**
     * 移动文件
     *
     * @param srcFile  要移动的文件路径
     * @param destFile 目的路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveFile(final File srcFile,
                                   final File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    /**
     * 移动文件
     *
     * @param srcFile  要移动的文件路径
     * @param destFile 目的路径
     * @param listener 监听器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean moveFile(final File srcFile,
                                   final File destFile,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final boolean isMove) {
        return copyOrMoveDir(srcDir, destDir, new OnReplaceListener() {
            @Override
            public boolean onReplace() {
                return true;
            }
        }, isMove);
    }

    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final OnReplaceListener listener,
                                         final boolean isMove) {
        if (srcDir == null || destDir == null) {return false;}
        // destDir's path locate in srcDir's path then return false
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) {return false;}
        if (!srcDir.exists() || !srcDir.isDirectory()) {return false;}
        if (destDir.exists()) {
            if (listener == null || listener.onReplace()) {
                // require delete the old directory
                if (!deleteAllInDir(destDir)) {
                    // unsuccessfully delete then return false
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destDir)) {return false;}
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) {return false;}
            } else if (file.isDirectory()) {
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) {return false;}
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final boolean isMove) {
        return copyOrMoveFile(srcFile, destFile, new OnReplaceListener() {
            @Override
            public boolean onReplace() {
                return true;
            }
        }, isMove);
    }

    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final OnReplaceListener listener,
                                          final boolean isMove) {
        if (srcFile == null || destFile == null) {return false;}
        // srcFile equals destFile then return false
        if (srcFile.equals(destFile)) {return false;}
        // srcFile doesn't exist or isn't a file then return false
        if (!srcFile.exists() || !srcFile.isFile()) {return false;}
        if (destFile.exists()) {
            if (listener == null || listener.onReplace()) {
                // require delete the old file
                if (!destFile.delete()) {
                    // unsuccessfully delete then return false
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destFile.getParentFile())) {return false;}
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile))
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param filePath 文件路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean delete(final String filePath) {
        return delete(getFileByPath(filePath));
    }

    /**
     * 删除文件或文件夹
     *
     * @param file file对象
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean delete(final File file) {
        if (file == null) {return false;}
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteFile(file);
    }

    /**
     * 删除文件夹
     *
     * @param dirPath 文件夹路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除文件夹
     *
     * @param dir dirFile对象
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) {return false;}
        // dir doesn't exist then return true
        if (!dir.exists()) {return true;}
        // dir isn't a directory then return false
        if (!dir.isDirectory()) {return false;}
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {return false;}
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {return false;}
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file file对象
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除文件夹内所有的东西
     *
     * @param dirPath 文件夹路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * 删除文件夹内所有的东西
     *
     * @param dir dirFile对象
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * 删除文件夹内所有的文件（保留文件夹）
     *
     * @param dirPath 文件夹路径
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 删除文件夹内所有的文件（保留文件夹）
     *
     * @param dir dirFile对象
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    /**
     * 删除文件夹内满足过滤条件的文件
     *
     * @param dirPath 文件夹路径
     * @param filter  过滤器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteFilesInDirWithFilter(final String dirPath,
                                                     final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 删除文件夹内满足过滤条件的文件
     *
     * @param dir    文件夹路径
     * @param filter 过滤器
     * @return {@code true}: 成功 <br>{@code false}: 失败
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) {return false;}
        // dir doesn't exist then return true
        if (!dir.exists()) {return true;}
        // dir isn't a directory then return false
        if (!dir.isDirectory()) {return false;}
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) {return false;}
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) {return false;}
                    }
                }
            }
        }
        return true;
    }

    /**
     * 获取路径下所有的文件及文件夹（不遍历子目录）
     * @param dirPath 路径
     * @return 路径下所有的文件及文件夹
     */
    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * 获取路径下所有的文件及文件夹（不遍历子目录）
     * @param dir 路径
     * @return 路径下所有的文件及文件夹
     */
    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * 获取路径下所有的文件及文件夹
     *
     * @param dirPath     路径
     * @param isRecursive 是否遍历子目录
     * @return 路径下所有的文件及文件夹
     */
    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取路径下所有的文件及文件夹
     *
     * @param dir         dirFile对象
     * @param isRecursive 是否遍历子目录
     * @return 路径下所有的文件及文件夹
     */
    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    /**
     * 遍历路径下满足条件的所有文件（不遍历子目录）
     *
     * @param dirPath 路径
     * @param filter  过滤器
     * @return 路径下满足条件的所有文件
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    /**
     * 遍历路径下满足条件的所有文件（不遍历子目录）
     *
     * @param dir    dirFile对象
     * @param filter 过滤器
     * @return 路径下满足条件的所有文件
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    /**
     * 遍历路径下满足过滤条件的所有文件
     *
     * @param dirPath     路径
     * @param filter      过滤器
     * @param isRecursive 是否遍历子目录
     * @return 路径下满足过滤条件的所有文件
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 遍历路径下满足过滤条件的所有文件
     *
     * @param dir         dirFile对象
     * @param filter      过滤器
     * @param isRecursive 是否遍历子目录
     * @return 路径下满足过滤条件的所有文件
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        if (!isDir(dir)) {return null;}
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    //noinspection ConstantConditions
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**
     * 返回文件上一次更改的时间戳
     *
     * @param filePath 文件路径
     * @return 文件上一次更改的时间戳
     */

    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * 返回文件上一次更改的时间戳
     *
     * @param file file对象
     * @return 文件上一次更改的时间戳
     */
    public static long getFileLastModified(final File file) {
        if (file == null) {return -1;}
        return file.lastModified();
    }

    /**
     * 返回文件的编码格式
     *
     * @param filePath 文件路径
     * @return 编码格式
     */
    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 返回文件的编码格式
     *
     * @param file file对象
     * @return 编码格式
     */
    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 返回文件内容的行数（一般用于文本文件）
     *
     * @param filePath 文件路径
     * @return 文件内容的行数
     */
    public static int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * 返回文件内容的行数（一般用于文本文件）
     *
     * @param file file对象
     * @return 文件内容的行数
     */
    public static int getFileLines(final File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int readChars;
            if (LINE_SEP.endsWith("\n")) {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\n') {++count;}
                    }
                }
            } else {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\r') {++count;}
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 返回文件夹的空间占用大小
     *
     * @param dirPath 路径
     * @return 文件夹的空间占用大小
     */
    public static String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * 返回文件夹的空间占用大小
     *
     * @param dir dirFile对象
     * @return 文件夹的空间占用大小
     */
    public static String getDirSize(final File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 返回文件的空间占用大小
     *
     * @param filePath 文件路径
     * @return 文件的空间占用大小
     */
    public static String getFileSize(final String filePath) {
        long len = getFileLength(filePath);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 返回文件的空间占用大小
     *
     * @param file file对象
     * @return 文件的空间占用大小
     */
    public static String getFileSize(final File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 返回文件夹空间占用大小
     *
     * @param dirPath 路径
     * @return 文件夹空间占用大小 单位为byte
     */
    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * 返回文件夹空间占用大小
     *
     * @param dir dirFile对象
     * @return 文件夹空间占用大小 单位为byte
     */
    public static long getDirLength(final File dir) {
        if (!isDir(dir)) {return -1;}
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 返回文件空间占用大小
     *
     * @param filePath 文件路径
     * @return 文件长度 单位为byte
     */
    public static long getFileLength(final String filePath) {
        boolean isURL = filePath.matches("[a-zA-z]+://[^\\s]*");
        if (isURL) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(filePath).openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    return conn.getContentLength();
                }
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * 返回文件占用空间大小
     *
     * @param file  file对象
     * @return 文件长度
     */
    public static long getFileLength(final File file) {
        if (!isFile(file)) {return -1;}
        return file.length();
    }

    /**
     * 返回文件的MD5的字符串
     *
     * @param filePath 文件路径
     * @return 文件的md5
     */
    public static String getFileMD5ToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * 返回文件的MD5的字符串
     *
     * @param file file对象
     * @return 文件的md5
     */
    public static String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }

    /**
     * 返回文件的MD5字节数组
     *
     * @param filePath 文件的路径
     * @return 文件的MD5字节数组
     */
    public static byte[] getFileMD5(final String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    /**
     * 返回文件的MD5字节数组
     *
     * @param file file对象
     * @return 文件的MD5字节数组
     */
    public static byte[] getFileMD5(final File file) {
        if (file == null) {return null;}
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if ((dis.read(buffer) <= 0)) {
                    break;
                }
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 返回文件所在的文件夹路径
     *
     * @param file file对象
     * @return 文件所在的文件夹路径
     */
    public static String getDirName(final File file) {
        if (file == null) {return "";}
        return getDirName(file.getAbsolutePath());
    }

    /**
     * 返回文件所在的文件夹路径
     *
     * @param filePath 路径
     * @return 文件所在的文件夹路径
     */
    public static String getDirName(final String filePath) {
        if (isSpace(filePath)) {return "";}
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * 返回文件名
     *
     * @param file file对象
     * @return 文件名
     */
    public static String getFileName(final File file) {
        if (file == null) {return "";}
        return getFileName(file.getAbsolutePath());
    }

    /**
     * 返回文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(final String filePath) {
        if (isSpace(filePath)) {return "";}
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 返回不带后缀的文件名
     *
     * @param file file对象
     * @return 不带后缀的文件名
     */
    public static String getFileNameNoExtension(final File file) {
        if (file == null) {return "";}
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * 返回不带后缀的文件名
     *
     * @param filePath 文件路径
     * @return 不带后缀的文件名
     */
    public static String getFileNameNoExtension(final String filePath) {
        if (isSpace(filePath)) {return "";}
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * 返回文件后缀
     *
     * @param file file对象
     * @return 文件后缀 比如txt
     */
    public static String getFileExtension(final File file) {
        if (file == null) {return "";}
        return getFileExtension(file.getPath());
    }

    /**
     * 返回文件后缀
     *
     * @param filePath 文件路径
     * @return 文件后缀
     */
    public static String getFileExtension(final String filePath) {
        if (isSpace(filePath)) {return "";}
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) {return "";}
        return filePath.substring(lastPoi + 1);
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnReplaceListener {
        /**
         * 替换回调，是否需要回调
         * @return
         */
        boolean onReplace();
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {return "";}
        int len = bytes.length;
        if (len <= 0) {return "";}
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / 1073741824);
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) {return true;}
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean writeFileFromIS(final File file,
                                           final InputStream is) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
