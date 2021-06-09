package com.qk.dam.sqlloader.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TargzUtils {

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
        byte data[] = new byte[1024];
        while ((count = tais.read(data, 0, 1024)) != -1) {
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

    public static Map<String,String> readTarbgzContent(InputStream inputStream) {
        Map<String,String> rt = new HashMap<>();
        try {
            BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(inputStream);
            extracted(rt, bZip2CompressorInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rt;
    }

    public static Map<String,String> readTarbgzContent(File gzFile) throws Exception {
        Map<String,String> rt = new HashMap<>();
        BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream((new FileInputStream(gzFile)));
        extracted(rt, bZip2CompressorInputStream);
        return rt;
    }

    private static void extracted(Map<String, String> rt, BZip2CompressorInputStream bZip2CompressorInputStream) throws IOException {
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(bZip2CompressorInputStream);
        while (true){
            var tarEmtry = tarArchiveInputStream.getNextTarEntry();
            if (tarEmtry==null){
                break;
            }
            String fileName = tarEmtry.getName();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int count;
            byte[] data = new byte[1024];
            while ((count = tarArchiveInputStream.read(data, 0, 1024)) != -1) {
                baos.write(data, 0, count);
            }
            rt.put(fileName, baos.toString(UTF_8));
            baos.close();
        }
        tarArchiveInputStream.close();
        bZip2CompressorInputStream.close();
    }


}