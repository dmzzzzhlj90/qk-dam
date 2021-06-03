package com.qk.sankuai.service;

import com.qk.sankuai.config.LeafConf;
import com.qk.sankuai.exception.InitException;
import com.qk.sankuai.inf.Constants;
import com.qk.sankuai.inf.IDGen;
import com.qk.sankuai.inf.leaf.common.PropertyFactory;
import com.qk.sankuai.inf.leaf.common.Result;
import com.qk.sankuai.inf.leaf.common.ZeroIDGen;
import com.qk.sankuai.inf.leaf.snowflake.SnowflakeIDGenImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class SnowflakeService {
    private final IDGen idGen;
    final LeafConf leafConf;

    public SnowflakeService(LeafConf leafConf) throws InitException {
        this.leafConf = leafConf;
        if (leafConf.isLeafSnowflakeEnable()) {
            idGen = new SnowflakeIDGenImpl(leafConf.getLeafSnowflakeZkAddress(), leafConf.getLeafSnowflakePort());
            if(idGen.init()) {
                log.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}