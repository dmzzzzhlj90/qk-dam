package com.qk.dam.sqlloader.util;

import java.io.*;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class TargzUtils {
  /** 解压.tar文件 */
  public static void unTarFile(File srcFile, String destPath) throws Exception {

    try (TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(srcFile))) {
      dearchive(new File(destPath), tais);
    }
  }

  private static void dearchive(File destFile, TarArchiveInputStream tais) throws Exception {

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

  private static void dearchiveFile(File destFile, TarArchiveInputStream tais) throws Exception {

    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

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
    BZip2CompressorInputStream inputStream =
        new BZip2CompressorInputStream((new FileInputStream(gzFile)));
    TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(inputStream);
    dearchive(new File(descDir), tarArchiveInputStream);
  }

  public static void readTarbgzSqls(InputStream inputStream, Consumer<String> consumer)
      throws IOException {
    BZip2CompressorInputStream bZip2CompressorInputStream =
        new BZip2CompressorInputStream(inputStream);
    extracted(bZip2CompressorInputStream, consumer);
  }

  //    public static List<String> readTarbgzContent(File gzFile) {
  //        Map<String,String> rt = new HashMap<>();
  //        BZip2CompressorInputStream bZip2CompressorInputStream = null;
  //        try {
  //            bZip2CompressorInputStream = new BZip2CompressorInputStream((new
  // FileInputStream(gzFile)));
  //            return extracted( bZip2CompressorInputStream);
  //        } catch (IOException e) {
  //            e.printStackTrace();
  //        }
  //
  //        return new ArrayList<>();
  //    }

  private static void extracted(
      BZip2CompressorInputStream bZip2CompressorInputStream, Consumer<String> consumer)
      throws IOException {
    TarArchiveInputStream tarArchiveInputStream =
        new TarArchiveInputStream(bZip2CompressorInputStream);
    InputStreamReader inputStreamReader = new InputStreamReader(tarArchiveInputStream);
    BufferedReader input = new BufferedReader(inputStreamReader);

    while (true) {
      var tarEmtry = tarArchiveInputStream.getNextTarEntry();
      if (tarEmtry == null) {
        break;
      }
      String line;

      while ((line = input.readLine()) != null) {
        if (line.startsWith("UNLOCK")) {
          break;
        }
        if (!(line.startsWith("/*") || line.startsWith("LOCK") || line.startsWith("UNLOCK"))) {
          // 读取SQL
          if (!"".equals(line)) {
            consumer.accept(line.replace(";", ""));
          }
        }
      }
    }
    input.close();
    tarArchiveInputStream.close();
    bZip2CompressorInputStream.close();
  }

  public static void writeRtFile(InputStream inputStream, String destDir) throws Exception {
    BZip2CompressorInputStream bZip2CompressorInputStream =
        new BZip2CompressorInputStream(inputStream);

    TarArchiveInputStream tarArchiveInputStream =
        new TarArchiveInputStream(bZip2CompressorInputStream);
    // /home/rmtFile
    while (true) {
      var tarEmtry = tarArchiveInputStream.getNextTarEntry();
      if (tarEmtry == null) {
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
