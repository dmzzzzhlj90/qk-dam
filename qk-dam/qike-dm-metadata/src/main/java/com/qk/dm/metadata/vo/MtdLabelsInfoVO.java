package com.qk.dm.metadata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author spj
 * @date 2021/7/30 5:33 下午
 * @since 1.0.0
 */
public class MtdLabelsInfoVO extends MtdLabelsVO{

    public MtdLabelsInfoVO(Long id, Date gmtCreate) {
        this.id = id;
        this.gmtCreate = gmtCreate;
    }

    private Long id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
