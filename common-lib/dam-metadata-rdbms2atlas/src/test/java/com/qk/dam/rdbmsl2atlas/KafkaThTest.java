package com.qk.dam.rdbmsl2atlas;

import com.qk.dam.rdbmsl2atlas.adapter.MysqlMetadata;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlColumnType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlDbType;
import com.qk.dam.rdbmsl2atlas.pojo.mysql.MysqlTableType;
import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasException;
import org.apache.atlas.kafka.KafkaNotification;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.notification.HookNotification;
import org.apache.atlas.notification.NotificationException;
import org.apache.atlas.notification.NotificationInterface;
import org.apache.atlas.v1.model.notification.HookNotificationV1;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.util.List;

public class KafkaThTest {
    private KafkaNotification     kafkaNotification     = null;
    private NotificationInterface notificationInterface = null;


    @Test
    public void setup() throws Exception {
        startNotificationServicesWithRetry();
        produceMessage(createEntity());
        cleanUpNotificationService();
    }
    HookNotification createEntity() throws Exception {

        MysqlDbType mysqlDbType =  MysqlDbType.builder()
                .name("dba")
                .displayName("dabaoi")
                .description("苗飒啊")
                .owner("zhudaoming")
                .qualifiedName("dba.accc@primary")
                .build();
        MysqlTableType mysqlTableType = MysqlTableType.builder()
                .name("table_a")
                .type("侧嗯啊啊")
                .displayName("assc")
                .createTime(System.currentTimeMillis())
                .description("assd")
                .qualifiedName("dba.table1@primary")
                .build();
        MysqlColumnType mysqlColumnType = MysqlColumnType.builder()
                .name("cl1")
                .displayName("ass")
                .position(0)
                .isNullable(false)
                .qualifiedName("dba.sss.column2@primary")
                .build();

        mysqlTableType.setMysqlColumnTypes(List.of(mysqlColumnType));
        mysqlDbType.setMysqlTableTypes(List.of(mysqlTableType));

        MysqlMetadata mysqlMetadata = new MysqlMetadata();
        AtlasEntity.AtlasEntitiesWithExtInfo entitiesWithExtInfo = mysqlMetadata.syncMysqlMetadata(List.of(mysqlDbType));


        return new HookNotification.EntityCreateRequestV2("dmz",entitiesWithExtInfo);
    }

    private void produceMessage(HookNotification message) throws NotificationException {
        kafkaNotification.send(NotificationInterface.NotificationType.HOOK, message);
    }

    // retry starting notification services every 2 mins for total of 30 mins
    // running parallel tests will keep the notification service ports occupied, hence retry
    void startNotificationServicesWithRetry() throws Exception {
        long totalTime = 0;
        long sleepTime = 2 * 60 * 1000; // 2 mins
        long maxTime   = 30 * 60 * 1000; // 30 mins

        while (true) {
            try {
                initNotificationService();
                break;
            } catch (Exception ex) {
                cleanUpNotificationService();

                if (totalTime >= maxTime) {
                    throw ex;
                }

                Thread.sleep(sleepTime);

                totalTime = totalTime + sleepTime;
            }
        }
    }

    void initNotificationService() throws AtlasException, InterruptedException {
        Configuration applicationProperties = ApplicationProperties.get();

        kafkaNotification     = new KafkaNotification(applicationProperties);
        notificationInterface = kafkaNotification;

        kafkaNotification.start();

        Thread.sleep(2000);
    }

    void cleanUpNotificationService() {
        if (kafkaNotification != null) {
            kafkaNotification.close();
            kafkaNotification.stop();
        }

    }
}
