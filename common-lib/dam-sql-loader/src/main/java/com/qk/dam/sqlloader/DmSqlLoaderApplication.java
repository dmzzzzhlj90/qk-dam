package com.qk.dam.sqlloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Console;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import com.qk.dam.sqlloader.util.TargzUtils;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.List;

public class DmSqlLoaderApplication {
    public static void main(String[] args) throws Exception {
        List<Entity> user = Db.use().findAll("qk_dsd_basicinfo");
        System.out.println(user);
        Db.use().closeConnection(Db.use().getConnection());


        TargzUtils.unTarFile(new File("/Users/daomingzhu/WeDrive/company_history_name-210606181858-ke9ymjts.tar.gz"),"/Users/daomingzhu/WeDrive/");
    }
}
