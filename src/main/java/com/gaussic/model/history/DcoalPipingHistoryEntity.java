package com.gaussic.model.history;

import com.gaussic.model.CoalMillEntity;
import com.gaussic.model.CoalPipingEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "dcoal_piping_history", schema = "wind")
public class DcoalPipingHistoryEntity extends   CoalPipingHistory{
    private Long id;
    private Timestamp hTime;
    private Float hPipeAVelocity;
    private Float hPipeBVelocity;
    private Float hPipeCVelocity;
    private Float hPipeDVelocity;
    private Float hPipeADencity;
    private Float hPipeBDencity;
    private Float hPipeCDencity;
    private Float hPipeDDencity;
    private Long hCoalMillId;



    public DcoalPipingHistoryEntity(CoalMillEntity coalMillEntity, Date now) {
        this.hTime = new Timestamp(now.getTime());
        this.hCoalMillId = coalMillEntity.getId();
    }

    public DcoalPipingHistoryEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "h_time")
    public Timestamp gethTime() {
        return hTime;
    }

    public void sethTime(Timestamp hTime) {
        this.hTime = hTime;
    }

    @Basic
    @Column(name = "h_pipeA_Velocity")
    public Float gethPipeAVelocity() {
        return hPipeAVelocity;
    }

    public void sethPipeAVelocity(Float hPipeAVelocity) {
        this.hPipeAVelocity = hPipeAVelocity;
    }

    @Basic
    @Column(name = "h_pipeB_Velocity")
    public Float gethPipeBVelocity() {
        return hPipeBVelocity;
    }

    public void sethPipeBVelocity(Float hPipeBVelocity) {
        this.hPipeBVelocity = hPipeBVelocity;
    }

    @Basic
    @Column(name = "h_pipeC_velocity")
    public Float gethPipeCVelocity() {
        return hPipeCVelocity;
    }

    public void sethPipeCVelocity(Float hPipeCVelocity) {
        this.hPipeCVelocity = hPipeCVelocity;
    }

    @Basic
    @Column(name = "h_pipeD_velocity")
    public Float gethPipeDVelocity() {
        return hPipeDVelocity;
    }

    public void sethPipeDVelocity(Float hPipeDVelocity) {
        this.hPipeDVelocity = hPipeDVelocity;
    }

    @Basic
    @Column(name = "h_pipeA_dencity")
    public Float gethPipeADencity() {
        return hPipeADencity;
    }

    public void sethPipeADencity(Float hPipeADencity) {
        this.hPipeADencity = hPipeADencity;
    }

    @Basic
    @Column(name = "h_pipeB_dencity")
    public Float gethPipeBDencity() {
        return hPipeBDencity;
    }

    public void sethPipeBDencity(Float hPipeBDencity) {
        this.hPipeBDencity = hPipeBDencity;
    }

    @Basic
    @Column(name = "h_pipeC_dencity")
    public Float gethPipeCDencity() {
        return hPipeCDencity;
    }

    public void sethPipeCDencity(Float hPipeCDencity) {
        this.hPipeCDencity = hPipeCDencity;
    }

    @Basic
    @Column(name = "h_pipeD_dencity")
    public Float gethPipeDDencity() {
        return hPipeDDencity;
    }

    public void sethPipeDDencity(Float hPipeDDencity) {
        this.hPipeDDencity = hPipeDDencity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DcoalPipingHistoryEntity that = (DcoalPipingHistoryEntity) o;
        return id.equals( that.id)  &&
                Objects.equals(hTime, that.hTime) &&
                Objects.equals(hPipeAVelocity, that.hPipeAVelocity) &&
                Objects.equals(hPipeBVelocity, that.hPipeBVelocity) &&
                Objects.equals(hPipeCVelocity, that.hPipeCVelocity) &&
                Objects.equals(hPipeDVelocity, that.hPipeDVelocity) &&
                Objects.equals(hPipeADencity, that.hPipeADencity) &&
                Objects.equals(hPipeBDencity, that.hPipeBDencity) &&
                Objects.equals(hPipeCDencity, that.hPipeCDencity) &&
                Objects.equals(hPipeDDencity, that.hPipeDDencity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, hTime, hPipeAVelocity, hPipeBVelocity, hPipeCVelocity, hPipeDVelocity, hPipeADencity, hPipeBDencity, hPipeCDencity, hPipeDDencity);
    }

    @Basic
    @Column(name = "h_coal_mill_id")
    public Long gethCoalMillId() {
        return hCoalMillId;
    }

    public void sethCoalMillId(Long hCoalMillId) {
        this.hCoalMillId = hCoalMillId;
    }



    private Float hPipeAX;
    private Float hPipeAY;
    private Float hPipeAV;
    private Float hPipeBX;
    private Float hPipeBY;
    private Float hPipeBV;
    private Float hPipeCX;
    private Float hPipeCY;
    private Float hPipeCV;
    private Float hPipeDX;
    private Float hPipeDY;
    private Float hPipeDV;
    @Basic
    @Column(name = "h_pipeA_x")
    public Float gethPipeAX() {
        return hPipeAX;
    }

    public void sethPipeAX(Float hPipeAX) {
        this.hPipeAX = hPipeAX;
    }
    @Basic
    @Column(name = "h_pipeA_y")
    public Float gethPipeAY() {
        return hPipeAY;
    }

    public void sethPipeAY(Float hPipeAY) {
        this.hPipeAY = hPipeAY;
    }
    @Basic
    @Column(name = "h_pipeA_v")
    public Float gethPipeAV() {
        return hPipeAV;
    }

    public void sethPipeAV(Float hPipeAV) {
        this.hPipeAV = hPipeAV;
    }
    @Basic
    @Column(name = "h_pipeB_x")
    public Float gethPipeBX() {
        return hPipeBX;
    }

    public void sethPipeBX(Float hPipeBX) {
        this.hPipeBX = hPipeBX;
    }
    @Basic
    @Column(name = "h_pipeB_Y")
    public Float gethPipeBY() {
        return hPipeBY;
    }

    public void sethPipeBY(Float hPipeBY) {
        this.hPipeBY = hPipeBY;
    }
    @Basic
    @Column(name = "h_pipeB_v")
    public Float gethPipeBV() {
        return hPipeBV;
    }

    public void sethPipeBV(Float hPipeBV) {
        this.hPipeBV = hPipeBV;
    }
    @Basic
    @Column(name = "h_pipeC_x")
    public Float gethPipeCX() {
        return hPipeCX;
    }

    public void sethPipeCX(Float hPipeCX) {
        this.hPipeCX = hPipeCX;
    }
    @Basic
    @Column(name = "h_pipeC_Y")
    public Float gethPipeCY() {
        return hPipeCY;
    }

    public void sethPipeCY(Float hPipeCY) {
        this.hPipeCY = hPipeCY;
    }
    @Basic
    @Column(name = "h_pipeC_v")
    public Float gethPipeCV() {
        return hPipeCV;
    }

    public void sethPipeCV(Float hPipeCV) {
        this.hPipeCV = hPipeCV;
    }
    @Basic
    @Column(name = "h_pipeD_x")
    public Float gethPipeDX() {
        return hPipeDX;
    }

    public void sethPipeDX(Float hPipeDX) {
        this.hPipeDX = hPipeDX;
    }
    @Basic
    @Column(name = "h_pipeD_y")
    public Float gethPipeDY() {
        return hPipeDY;
    }

    public void sethPipeDY(Float hPipeDY) {
        this.hPipeDY = hPipeDY;
    }
    @Basic
    @Column(name = "h_pipeD_v")
    public Float gethPipeDV() {
        return hPipeDV;
    }

    public void sethPipeDV(Float hPipeDV) {
        this.hPipeDV = hPipeDV;
    }
}
