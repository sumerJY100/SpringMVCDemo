package com.gaussic.model.history;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

public interface CoalPipingHistory {

    long getId();

    void setId(long id);

    Timestamp gethTime();

    void sethTime(Timestamp hTime);


    Float gethPipeAVelocity();

    void sethPipeAVelocity(Float hPipeAVelocity);


    Float gethPipeBVelocity();

    void sethPipeBVelocity(Float hPipeBVelocity);


    Float gethPipeCVelocity();

    void sethPipeCVelocity(Float hPipeCVelocity);

    Float gethPipeDVelocity();

    void sethPipeDVelocity(Float hPipeDVelocity);


    Float gethPipeADencity();

    void sethPipeADencity(Float hPipeADencity);


    Float gethPipeBDencity();

    void sethPipeBDencity(Float hPipeBDencity);


    Float gethPipeCDencity();

    void sethPipeCDencity(Float hPipeCDencity);


    Float gethPipeDDencity();

    void sethPipeDDencity(Float hPipeDDencity);


    Long gethCoalMillId();

    void sethCoalMillId(Long hCoalMillId);

}
