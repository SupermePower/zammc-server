package com.smart.program.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * hello.activeEntity
 * Created by baihe
 * ON 2018/4/19.
 */
@Entity
@Table(name = "home_config")
public class HomeConfigEntity {
    private long id;
    private String areaName;
    private String areaTag;
    private int areaHight;
    private int areaSort;
    private Timestamp createTime;
    private Timestamp updateTime;
    private byte version;
    private byte dataStatus;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "area_name")
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Basic
    @Column(name = "area_tag")
    public String getAreaTag() {
        return areaTag;
    }

    public void setAreaTag(String areaTag) {
        this.areaTag = areaTag;
    }

    @Basic
    @Column(name = "area_hight")
    public int getAreaHight() {
        return areaHight;
    }

    public void setAreaHight(int areaHight) {
        this.areaHight = areaHight;
    }

    @Basic
    @Column(name = "area_sort")
    public int getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(int areaSort) {
        this.areaSort = areaSort;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "version")
    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Basic
    @Column(name = "data_status")
    public byte getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(byte dataStatus) {
        this.dataStatus = dataStatus;
    }

}
