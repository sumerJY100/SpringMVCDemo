package com.gaussic.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "alarm_history", schema = "wind", catalog = "")
public class AlarmHistoryEntity implements Serializable {
    private long id;
    private Long aDeviceId;
    private Integer aDeviceType;
    private String aDeviceName;
    private Timestamp aAlarmTime;
    private Integer aAlarmType;
    private Timestamp aReAlarmTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "a_device_id")
    public Long getaDeviceId() {
        return aDeviceId;
    }

    public void setaDeviceId(Long aDeviceId) {
        this.aDeviceId = aDeviceId;
    }

    @Basic
    @Column(name = "a_device_type")
    public Integer getaDeviceType() {
        return aDeviceType;
    }

    public void setaDeviceType(Integer aDeviceType) {
        this.aDeviceType = aDeviceType;
    }

    @Basic
    @Column(name = "a_device_name")
    public String getaDeviceName() {
        return aDeviceName;
    }

    public void setaDeviceName(String aDeviceName) {
        this.aDeviceName = aDeviceName;
    }

    @Basic
    @Column(name = "a_alarm_time")
    public Timestamp getaAlarmTime() {
        return aAlarmTime;
    }

    public void setaAlarmTime(Timestamp aAlarmTime) {
        this.aAlarmTime = aAlarmTime;
    }

    @Basic
    @Column(name = "a_alarm_type")
    public Integer getaAlarmType() {
        return aAlarmType;
    }

    public void setaAlarmType(Integer aAlarmType) {
        this.aAlarmType = aAlarmType;
    }

    @Basic
    @Column(name = "a_re_alarm_time")
    public Timestamp getaReAlarmTime() {
        return aReAlarmTime;
    }

    public void setaReAlarmTime(Timestamp aReAlarmTime) {
        this.aReAlarmTime = aReAlarmTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmHistoryEntity that = (AlarmHistoryEntity) o;
        return id == that.id &&
                Objects.equals(aDeviceId, that.aDeviceId) &&
                Objects.equals(aDeviceType, that.aDeviceType) &&
                Objects.equals(aDeviceName, that.aDeviceName) &&
                Objects.equals(aAlarmTime, that.aAlarmTime) &&
                Objects.equals(aAlarmType, that.aAlarmType) &&
                Objects.equals(aReAlarmTime, that.aReAlarmTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, aDeviceId, aDeviceType, aDeviceName, aAlarmTime, aAlarmType, aReAlarmTime);
    }
}
