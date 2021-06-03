package com.qk.sankuai.service;

import com.qk.sankuai.config.LeafConf;
import com.qk.sankuai.exception.InitException;
import com.qk.sankuai.inf.IDGen;
import com.qk.sankuai.inf.leaf.common.Result;
import com.qk.sankuai.inf.leaf.common.ZeroIDGen;
import com.qk.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.qk.sankuai.inf.leaf.segment.dao.IDAllocDao;
import com.qk.sankuai.inf.leaf.segment.dao.impl.IDAllocDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Slf4j
@Service
public class SegmentService {
    final DataSource leafDataSource;
    final LeafConf leafConf;
    private final IDGen idGen;

    public SegmentService(DataSource leafDataSource, LeafConf leafConf) throws InitException {
        this.leafDataSource = leafDataSource;
        this.leafConf = leafConf;
        if (leafConf.isLeafSegmentEnable()) {
            // Config dataSource
            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(this.leafDataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setDao(dao);
            if (idGen.init()) {
                log.info("Segment Service Init Successfully");
            } else {
                throw new InitException("Segment Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }
    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}