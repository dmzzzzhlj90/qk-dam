package com.qk.dam.sqlloader.util;

import cn.hutool.db.Db;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.GZIPInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TargzUtils {
    private final static Db use = Db.use("qk_es_updated");
    /**
     * 解压.tar文件
     */
    public static void unTarFile(File srcFile, String destPath)
            throws Exception {

        try (TarArchiveInputStream tais = new TarArchiveInputStream(
                new FileInputStream(srcFile))) {
            dearchive(new File(destPath), tais);
        }
    }


    private static void dearchive(File destFile, TarArchiveInputStream tais)
            throws Exception {

        TarArchiveEntry entry;
        while ((entry = tais.getNextTarEntry()) != null) {

            // 文件
            String dir = destFile.getPath() + File.separator + entry.getName();
            File dirFile = new File(dir);


            if (entry.isDirectory()) {
                dirFile.mkdirs();
            } else {
                dearchiveFile(dirFile, tais);
            }

        }
    }

    private static void dearchiveFile(File destFile, TarArchiveInputStream tais)
            throws Exception {

        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(destFile));

        int count;
        byte data[] = new byte[4096];
        while ((count = tais.read(data, 0, 4096)) != -1) {
            bos.write(data, 0, count);
        }

        bos.close();
    }

    public static void unTargzFile(File gzFile, String descDir) throws Exception {
        GZIPInputStream inputStream = new GZIPInputStream((new FileInputStream(gzFile)));
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(inputStream);
        dearchive(new File(descDir), tarArchiveInputStream);
    }

    public static void unTarbgz2File(File gzFile, String descDir) throws Exception {
        BZip2CompressorInputStream inputStream = new BZip2CompressorInputStream((new FileInputStream(gzFile)));
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(inputStream);
        dearchive(new File(descDir), tarArchiveInputStream);
    }

    public static List<String> readTarbgzContent(InputStream inputStream) {
        try {
            BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(inputStream);
            return extracted( bZip2CompressorInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<String> readTarbgzContent(File gzFile) {
        Map<String,String> rt = new HashMap<>();
        BZip2CompressorInputStream bZip2CompressorInputStream = null;
        try {
            bZip2CompressorInputStream = new BZip2CompressorInputStream((new FileInputStream(gzFile)));
            return extracted( bZip2CompressorInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static List<String> extracted(BZip2CompressorInputStream bZip2CompressorInputStream) throws IOException {
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(bZip2CompressorInputStream);
        InputStreamReader inputStreamReader = new InputStreamReader(tarArchiveInputStream);
        BufferedReader input = new BufferedReader(inputStreamReader);

        final List<String> sqls = new ArrayList<>(10000);

        while (true){
            var tarEmtry = tarArchiveInputStream.getNextTarEntry();
            if (tarEmtry==null){
                break;
            }
            String fileName = tarEmtry.getName();
            String line;
            StringBuilder appendstr = new StringBuilder();

            while ((line = input.readLine()) != null) {
                if (!(line.startsWith("/*")||line.startsWith("LOCK")||line.startsWith("UNLOCK"))){
                    // 读取SQL
                    if (!"".equals(line)){
                        sqls.add(line);
                    }
//                    if (sqls.size()==100000){
//                        try {
//                            use.executeBatch(sqls);
//                            sqls.clear();
//                        } catch (SQLException throwables) {
//                            sqls.clear();
//                            throwables.printStackTrace();
//                            break;
//                        }
//                    }
                }
//                appendstr.append(line).append("\n");
            }
//            byte[] data = new byte[2048];
//            while ((count = tarArchiveInputStream.read(data, 0, 2048)) != -1) {
//                ByteBuffer wrap = ByteBuffer.wrap(data, 0, count);
//                baos.write(data, 0, count);
//            }
//            rt.put(fileName, baos.toString(UTF_8));
//            baos.close();
        }
        input.close();
        tarArchiveInputStream.close();
        bZip2CompressorInputStream.close();
        return sqls;
    }

    public static void writeRtFile(InputStream inputStream, String destDir) throws Exception {
        BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(inputStream);

        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(bZip2CompressorInputStream);
        // /home/rmtFile
        while (true){
            var tarEmtry = tarArchiveInputStream.getNextTarEntry();
            if (tarEmtry==null){
                break;
            }

            BufferedOutputStream baos = new BufferedOutputStream(new FileOutputStream(destDir));
            int count;
            byte[] data = new byte[4096];
            while ((count = tarArchiveInputStream.read(data, 0, 4096)) != -1) {
                baos.write(data, 0, count);
            }
            baos.close();
        }

        tarArchiveInputStream.close();

    }

}