package cn.flyaudio.baselibrary.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @className FileIOUtils
 * @createDate 2018/11/7 15:12
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 文件IO工具
 *
 */
public final class FileIOUtils {
    /**
     * 缓冲字节数
     */
    private static int sBufferSize = 8192;

    /**
     * 防止实例化
     */
    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 从输入流写入到文件
     *
     * @param filePath 要写入的文件路径
     * @param is       输入流
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return writeFileFromIS(getFileByPath(filePath), is, false);
    }

    /**
     *从输入流写入到文件
     *
     * @param filePath 要写入的文件路径
     * @param is       输入流
     * @param append   True 为追加写入 false 为覆盖写入
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * 从输入流写入到文件
     *
     * @param file 要写入的文件对象
     * @param is   输入流
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromIS(final File file, final InputStream is) {
        return writeFileFromIS(file, is, false);
    }

    /**
     * 从输入流写入到文件
     *
     * @param file   要写入的文件对象
     * @param is     输入流
     * @param append True 为追加写入 false 为覆盖写入
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append) {
        if (!createOrExistsFile(file) || is == null) {return false;}
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] data = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
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

    /**
     * 通过IO流将byte字节写入到文件中
     *
     * @param filePath 要写入到的文件路径
     * @param bytes    写入内容的byte字节数组
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    /**
     * 通过IO流将byte字节写入到文件中
     *
     * @param filePath 要写入到的文件路径
     * @param bytes    写入内容的byte字节数组
     * @param append   True 为追加写入 false 为覆盖写入
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByStream(final String filePath,
                                                     final byte[] bytes,
                                                     final boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    /**
     * 通过IO流将byte字节写入到文件中
     *
     * @param file  要写入到的文件对象
     * @param bytes 写入内容的byte字节数组
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    /**
     * 通过IO流将byte字节写入到文件中
     *
     * @param file   要写入到的文件对象
     * @param bytes  写入内容的byte字节数组
     * @param append True 为追加写入 false 为覆盖写入
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByStream(final File file,
                                                     final byte[] bytes,
                                                     final boolean append) {
        if (bytes == null || !createOrExistsFile(file)) {return false;}
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过通道将byte字节写入到文件中
     *
     * @param filePath 要写入到的文件路径
     * @param bytes    写入内容的byte字节数组
     * @param isForce  是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 通过通道将byte字节写入到文件中
     *
     * @param filePath 要写入到的文件路径
     * @param bytes    写入内容的byte字节数组
     * @param append   True 为追加写入 false 为覆盖写入
     * @param isForce  是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 通过通道将byte字节写入到文件中
     *
     * @param file    要写入到的文件对象
     * @param bytes   写入内容的byte字节数组
     * @param isForce 是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * 通过通道将byte字节写入到文件中
     *
     * @param file    要写入到的文件对象
     * @param bytes   写入内容的byte字节数组
     * @param append  True 为追加写入 false 为覆盖写入
     * @param isForce 是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        if (bytes == null) {return false;}
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) {fc.force(true);}
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过Map将byte字节写入到文件中
     *
     * @param filePath 要写入到的文件路径
     * @param bytes    写入内容的byte字节数组
     * @param isForce  是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    /**
     * 通过Map将byte字节写入到文件中
     *
     * @param filePath 要写入到的文件对象
     * @param bytes    写入内容的byte字节数组
     * @param append   True 为追加写入 false 为覆盖写入
     * @param isForce  是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 通过Map将byte字节写入到文件中
     *
     * @param file    要写入到的文件对象
     * @param bytes   写入内容的byte字节数组
     * @param isForce 是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * 通过Map将byte字节写入到文件中
     *
     * @param file    要写入到的文件对象
     * @param bytes   写入内容的byte字节数组
     * @param append  True 为追加写入 false 为覆盖写入
     * @param isForce 是否强制写入文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        if (bytes == null || !createOrExistsFile(file)) {return false;}
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) {mbb.force();}
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字符传写入到指定文件
     *
     * @param filePath 要写入到的文件路径
     * @param content  写入的字符串内容
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    /**
     * 将字符传写入到指定文件
     *
     * @param filePath 要写入到的文件路径
     * @param content  写入的字符串内容
     * @param append   True 为追加写入 false 为覆盖写入
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromString(final String filePath,
                                              final String content,
                                              final boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 将字符传写入到指定文件
     *
     * @param file   要写入到的文件对象
     * @param content 写入的字符串内容
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    /**
     * 将字符传写入到指定文件
     *
     * @param file    要写入到的文件对象
     * @param content 写入的字符串内容
     * @param append  True 为追加写入 false 为覆盖写入
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean writeFileFromString(final File file,
                                              final String content,
                                              final boolean append) {
        if (file == null || content == null) {return false;}
        if (!createOrExistsFile(file)) {return false;}
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 读写分界线
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 读取文件中每行的内容
     *
     * @param filePath 文件路径
     * @return 文件中每行的内容
     */
    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }

    /**
     * 读取文件中每行的内容
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式
     * @return 文件中每行的内容
     */
    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * 读取文件中每行的内容
     *
     * @param file file对象
     * @return 文件中每行的内容
     */
    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    /**
     * 读取文件中每行的内容
     *
     * @param file        file对象
     * @param charsetName 编码名
     * @return 文件中每行的内容
     */
    public static List<String> readFile2List(final File file, final String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 读取文件中指定范围行的内容
     *
     * @param filePath 文件路径
     * @param st       起始行index
     * @param end      结束行index
     * @return 文件中指定范围行的内容,不包含end行的内容
     */
    public static List<String> readFile2List(final String filePath, final int st, final int end) {
        return readFile2List(getFileByPath(filePath), st, end, null);
    }

    /**
     * 读取文件中指定范围行的内容
     *
     * @param filePath    文件路径
     * @param st          起始行index
     * @param end         结束行index
     * @param charsetName 编码名
     * @return 文件中指定范围行的内容，不包含end行的内容
     */
    public static List<String> readFile2List(final String filePath,
                                             final int st,
                                             final int end,
                                             final String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * 读取文件中指定范围行的内容
     *
     * @param file 文件对象
     * @param st   起始行index
     * @param end  结束行index
     * @return 文件中指定范围行的内容,不包含end行的内容
     */
    public static List<String> readFile2List(final File file, final int st, final int end) {
        return readFile2List(file, st, end, null);
    }

    /**
     * 读取文件中指定范围行的内容
     *
     * @param file        文件对象
     * @param st          起始行index
     * @param end         结束行index
     * @param charsetName 编码格式名
     * @return 文件中指定范围行的内容,不包含end行的内容
     */
    public static List<String> readFile2List(final File file,
                                             final int st,
                                             final int end,
                                             final String charsetName) {
        if (!isFileExists(file)) {return null;}
        if (st > end) {return null;}
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file), charsetName)
                );
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) {break;}
                if (st <= curLine && curLine <= end) {list.add(line);}
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从指定文件读取字符串
     *
     * @param filePath 文件路径
     * @return 文件中的字符串
     */
    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }

    /**
     * 从指定文件读取字符串.
     *
     * @param filePath    文件路径
     * @param charsetName 编码格式名
     * @return 文件中的字符串内容
     */
    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * 从指定文件读取字符串.
     *
     * @param file 文件对象
     * @return 文件中的字符串内容
     */
    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

    /**
     * 从指定文件读取字符串
     *
     * @param file        文件对象
     * @param charsetName 编码格式名
     * @return 文件中的字符串内容
     */
    public static String readFile2String(final File file, final String charsetName) {
        byte[] bytes = readFile2BytesByStream(file);
        if (bytes == null) {return null;}
        if (isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * 通过输入流从指定文件读取byte数组
     *
     * @param filePath 文件路径
     * @return 文件中的byte数组内容
     */
    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    /**
     * 通过输入流从指定文件读取byte数组
     *
     * @param file 文件对象
     * @return 文件中的byte数组内容
     */
    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file)) {return null;}
        try {
            return is2Bytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过通道从文件读取byte数组
     *
     * @param filePath 文件路径
     * @return 文件中的byte数组内容
     */
    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    /**
     * 通过通道从文件读取byte数组
     *
     * @param file 文件对象
     * @return 文件中的byte数组内容
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExists(file)) {return null;}
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (((fc.read(byteBuffer)) <= 0)){ break;}
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过map读取指定文件中的字节数组
     *
     * @param filePath 文件路径
     * @return 文件中的byte数组内容
     */
    public static byte[] readFile2BytesByMap(final String filePath) {
        return readFile2BytesByMap(getFileByPath(filePath));
    }

    /**
     * 通过map读取指定文件中的字节数组
     *
     * @param file 文件对象
     * @return t文件中的byte数组内容
     */
    public static byte[] readFile2BytesByMap(final File file) {
        if (!isFileExists(file)) {return null;}
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int size = (int) fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] result = new byte[size];
            mbb.get(result, 0, size);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置缓冲字节数组大小
     * <p>默认为8192</p>
     *
     * @param bufferSize 缓冲字节数组大小
     */
    public static void setBufferSize(final int bufferSize) {
        sBufferSize = bufferSize;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 其它方法
    ///////////////////////////////////////////////////////////////////////////

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    private static boolean createOrExistsFile(final File file) {
        if (file == null) {return false;}
        if (file.exists()) {return file.isFile();}
        if (!createOrExistsDir(file.getParentFile())) { return false;}
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
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

    private static byte[] is2Bytes(final InputStream is) {
        if (is == null) {return null;}
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] b = new byte[sBufferSize];
            int len;
            while ((len = is.read(b, 0, sBufferSize)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
