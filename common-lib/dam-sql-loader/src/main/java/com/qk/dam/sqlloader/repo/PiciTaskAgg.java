package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 批次任务聚合
 *
 * @author daomingzhu
 */
public class PiciTaskAgg {

    public static List<PiciTaskVO> longgovTaskAll() {
        Db use = Db.use("longgov");

        try {
            List<Entity> query = use.query("SELECT pici,tablename,osspath FROM task_qk_rizhi ORDER BY tablename,pici");
            List<PiciTaskVO> piciTask = query.stream().map(entity -> new PiciTaskVO(entity.getInt("pici"),
                    entity.getStr("tablename"),
                    entity.getStr("osspath"))).
                    collect(Collectors.toList());
            return piciTask;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public static List<PiciTaskVO> longgovTaskrizhi(int pici) {
        Db use = Db.use("longgov");

        try {
            List<Entity> query = use.query("SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE pici=? ORDER BY tablename,pici", pici);
            List<PiciTaskVO> piciTask = query.stream().map(entity -> new PiciTaskVO(entity.getInt("pici"),
                    entity.getStr("tablename"),
                    entity.getStr("osspath"))).
                    collect(Collectors.toList());
            return piciTask;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<PiciTaskVO> longgovTaskrizhi(String table, int pici) {
        Db use = Db.use("longgov");

        try {
            List<Entity> query = use.query("SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE tablename=? and pici=? ORDER BY tablename,pici", table, pici);
            List<PiciTaskVO> piciTask = query.stream().map(entity -> new PiciTaskVO(entity.getInt("pici"),
                    entity.getStr("tablename"),
                    entity.getStr("osspath"))).
                    collect(Collectors.toList());
            System.out.println(piciTask);
            return piciTask;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<PiciTaskVO> longgovTaskrizhi(String table) {
        Db use = Db.use("longgov");

        try {
            List<Entity> query = use.query("SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE tablename=? ORDER BY tablename,pici", table);
            List<PiciTaskVO> piciTask = query.stream().map(entity -> new PiciTaskVO(entity.getInt("pici"),
                    entity.getStr("tablename"),
                    entity.getStr("osspath"))).
                    collect(Collectors.toList());
            System.out.println(piciTask);
            return piciTask;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<PiciTaskVO> longgovFrontTaskrizhi(String frontTabNme, int pici) {
        Db use = Db.use("longgov");

        try {
            List<Entity> query = use.query("SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE tablename like ? and pici=? ORDER BY tablename,pici", frontTabNme, pici);
            List<PiciTaskVO> piciTask = query.stream().map(entity -> new PiciTaskVO(entity.getInt("pici"),
                    entity.getStr("tablename"),
                    entity.getStr("osspath"))).
                    collect(Collectors.toList());
            System.out.println(piciTask);
            return piciTask;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
