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
  private static final Db use = Db.use("longgov");

  public static List<PiciTaskVO> longgovTaskAll() {
    try {
      List<Entity> query =
          use.query("SELECT pici,tablename,osspath FROM task_qk_rizhi ORDER BY pici,tablename");
      List<PiciTaskVO> piciTask =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getStr("osspath")))
              .collect(Collectors.toList());
      return piciTask;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskVO> longgovTaskrizhi(int pici) {
    try {
      List<Entity> query =
          use.query(
              "SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE pici=? ORDER BY pici,tablename",
              pici);
      List<PiciTaskVO> piciTask =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getStr("osspath")))
              .collect(Collectors.toList());
      return piciTask;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskVO> longgovTaskrizhi(String table, int pici) {
    try {
      List<Entity> query =
          use.query(
              "SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE tablename=? and pici=? ORDER BY pici,tablename",
              table,
              pici);
      List<PiciTaskVO> piciTask =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getStr("osspath")))
              .collect(Collectors.toList());
      System.out.println(piciTask);
      return piciTask;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskVO> longgovTaskrizhi(String table) {
    try {
      List<Entity> query =
          use.query(
              "SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE tablename=? ORDER BY pici,tablename",
              table);
      List<PiciTaskVO> piciTask =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getStr("osspath")))
              .collect(Collectors.toList());
      System.out.println(piciTask);
      return piciTask;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskVO> longgovFrontTaskrizhi(String frontTabNme, int pici) {
    try {
      List<Entity> query =
          use.query(
              "SELECT pici,tablename,osspath FROM task_qk_rizhi WHERE tablename like ? and pici=? ORDER BY pici,tablename",
              frontTabNme,
              pici);
      List<PiciTaskVO> piciTask =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getStr("osspath")))
              .collect(Collectors.toList());
      System.out.println(piciTask);
      return piciTask;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public static List<PiciTaskVO> longgovTaskAllPath() {
    try {
      List<Entity> query =
          use.query(
              " SELECT pici,tablename,substring_index(osspath,\"/\",-1) as 'osspath' FROM task_qk_rizhi ORDER BY pici,tablename ");
      List<PiciTaskVO> piciTask =
          query.stream()
              .map(
                  entity ->
                      new PiciTaskVO(
                          entity.getInt("pici"),
                          entity.getStr("tablename"),
                          entity.getStr("osspath")))
              .collect(Collectors.toList());
      return piciTask;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }
}
