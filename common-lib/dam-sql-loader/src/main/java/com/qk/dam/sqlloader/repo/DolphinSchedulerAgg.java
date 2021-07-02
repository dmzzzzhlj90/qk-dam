package com.qk.dam.sqlloader.repo;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.qk.dam.sqlloader.vo.DolphinProcessInstanceVO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 批次任务聚合
 *
 * @author daomingzhu
 */
public class DolphinSchedulerAgg {
    private static final Db use = Db.use("dolphinscheduler");

    public static List<DolphinProcessInstanceVO> schedulerProcessInstanceInfo(String dataDay) {
        try {
            List<Entity> query =
                    use.query(" SELECT a.process_definition_code,a.`name`,a.state,IFNULL(c.user_name,'null') as user_name ,IFNULL(a.update_time,0) as update_time\n" +
                            "FROM t_ds_process_instance a\n" +
                            "\tLEFT JOIN t_ds_process_definition b ON a.process_definition_code = b.`CODE`\n" +
                            "\tLEFT JOIN t_ds_user c ON b.user_id = c.id \n" +
                            "WHERE date( a.update_time ) = ? ", dataDay);
            List<DolphinProcessInstanceVO> dolphinProcessInstanceList =
                    query.stream()
                            .map(
                                    entity ->
                                            new DolphinProcessInstanceVO(
                                                    entity.getLong("process_definition_code"),
                                                    entity.getStr("name"),
                                                    entity.getInt("state"),
                                                    entity.getStr("user_name"),
                                                    entity.getStr("update_time")))
                            .collect(Collectors.toList());
            return dolphinProcessInstanceList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
