package com.gaussic.model.history;

import org.hibernate.cfg.annotations.HCANNHelper;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

public abstract class CoalPipingHistory {
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
    public abstract  Long getId();

    public void setId(Long id){this.id = id;}


    public abstract  Timestamp gethTime();

    public void sethTime(Timestamp hTime){
        this.hTime = hTime;
    }


    public abstract  Float gethPipeAVelocity();

    public void sethPipeAVelocity(Float hPipeAVelocity){this.hPipeAVelocity = hPipeAVelocity;}


    public abstract   Float gethPipeBVelocity();

    public void sethPipeBVelocity(Float hPipeBVelocity){this.hPipeBVelocity = hPipeBVelocity;}


    public abstract  Float gethPipeCVelocity();

    public void sethPipeCVelocity(Float hPipeCVelocity){this.hPipeCVelocity = hPipeCVelocity;}

    public abstract  Float gethPipeDVelocity();

    public void sethPipeDVelocity(Float hPipeDVelocity){this.hPipeDVelocity = hPipeDVelocity;}


    public abstract   Float gethPipeADencity();

    public void sethPipeADencity(Float hPipeADencity){this.hPipeADencity = hPipeADencity;}


    public abstract   Float gethPipeBDencity();

    public void sethPipeBDencity(Float hPipeBDencity){this.hPipeBDencity = hPipeBDencity;}


    public abstract   Float gethPipeCDencity();

    public void sethPipeCDencity(Float hPipeCDencity){this.hPipeCDencity = hPipeCDencity;}


    public abstract  Float gethPipeDDencity();

    public void sethPipeDDencity(Float hPipeDDencity){
        this.hPipeDDencity = hPipeDDencity;
    }


    public abstract   Long gethCoalMillId();

    public void sethCoalMillId(Long hCoalMillId){this.hCoalMillId = hCoalMillId;}


}
