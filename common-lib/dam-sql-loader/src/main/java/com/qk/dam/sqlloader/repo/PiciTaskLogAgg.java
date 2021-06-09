package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PiciTaskLogAgg {
    public static Integer saveQkLogPici(PiciTaskLogVO pici) {
        Db use = Db.use("qk_etl");
        try {
            Entity uniqWhere = Entity.create("t_qk_datain_log")
                    .set("pici", pici.getPici())
                    .set("tablename", pici.getTableName());
            long count = use.count(uniqWhere);
            Entity entity = Entity.create("t_qk_datain_log").parseBean(pici);

            if (count==0){
                use.insert(entity);
            }else {
                use.update(entity,uniqWhere);
            }
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static List<PiciTaskLogVO> qkLogPici(int pici) {
        Db use = Db.use("qk_etl");
        try {
            List<Entity> query = use.query("SELECT * FROM t_qk_datain_log WHERE pici=? ORDER BY tablename,pici", pici);

            List<PiciTaskLogVO> piciTaskLogs = query.stream().map(entity ->
                    new PiciTaskLogVO(
                            entity.getInt("pici"),
                            entity.getStr("tablename"),
                            entity.getInt("is_down"),
                            entity.getDate("updated")
                    )).
                    collect(Collectors.toList());
            return piciTaskLogs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static List<PiciTaskLogVO> qkLogPici(int pici,String tablename) {
        Db use = Db.use("qk_etl");
        try {
            List<Entity> query = use.query("SELECT * FROM t_qk_datain_log WHERE pici=? and tablename=? ORDER BY tablename,pici", pici,tablename);

            List<PiciTaskLogVO> piciTaskLogs = query.stream().map(entity ->
                    new PiciTaskLogVO(
                            entity.getInt("pici"),
                            entity.getStr("tablename"),
                            entity.getInt("is_down"),
                            entity.getDate("updated")
                    )).
                    collect(Collectors.toList());
            return piciTaskLogs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static List<PiciTaskLogVO> qkLogPici(int pici,String tablename,int isDown) {
        Db use = Db.use("qk_etl");
        try {
            List<Entity> query = use.query("SELECT * FROM t_qk_datain_log WHERE pici=? and tablename=? and is_down=? ORDER BY tablename,pici", pici,tablename,isDown);

            List<PiciTaskLogVO> piciTaskLogs = query.stream().map(entity ->
                    new PiciTaskLogVO(
                            entity.getInt("pici"),
                            entity.getStr("tablename"),
                            entity.getInt("is_down"),
                            entity.getDate("updated")
                    )).
                    collect(Collectors.toList());
            return piciTaskLogs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
