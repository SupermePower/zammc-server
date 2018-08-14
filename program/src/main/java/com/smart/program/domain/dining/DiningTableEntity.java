package com.smart.program.domain.dining;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description
 * @author: liying.fu
 * @Date: 2018/8/14 下午10:19
 */
@Entity
@Table(name = "dining_table")
public class DiningTableEntity {
    private long tableId;
    private String tableCode;
    private byte tableStatus;
    private byte dataStatus;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int version;

    @Id
    @Column(name = "table_id", nullable = false)
    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    @Basic
    @Column(name = "table_code", nullable = false, length = 100)
    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    @Basic
    @Column(name = "table_status", nullable = false)
    public byte getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(byte tableStatus) {
        this.tableStatus = tableStatus;
    }

    @Basic
    @Column(name = "data_status", nullable = false)
    public byte getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(byte dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = false)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
