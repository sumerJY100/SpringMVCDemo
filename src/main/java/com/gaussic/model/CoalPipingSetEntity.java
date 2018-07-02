package com.gaussic.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coal_piping_set", schema = "wind", catalog = "")
public class CoalPipingSetEntity {
    private long id;
    private Float sParam1;
    private Float sParam2;
    private Long coalPipingId;
    private Float sParam3;
    private Float sParam4;
    private Float sParam5;
    private String sUrl;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "s_param_1")
    public Float getsParam1() {
        return sParam1;
    }

    public void setsParam1(Float sParam1) {
        this.sParam1 = sParam1;
    }

    @Basic
    @Column(name = "s_param_2")
    public Float getsParam2() {
        return sParam2;
    }

    public void setsParam2(Float sParam2) {
        this.sParam2 = sParam2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoalPipingSetEntity that = (CoalPipingSetEntity) o;
        return id == that.id &&
                Objects.equals(sParam1, that.sParam1) &&
                Objects.equals(sParam2, that.sParam2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sParam1, sParam2);
    }

    @Basic
    @Column(name = "coal_piping_id")
    public Long getCoalPipingId() {
        return coalPipingId;
    }

    public void setCoalPipingId(Long coalPipingId) {
        this.coalPipingId = coalPipingId;
    }

    @Basic
    @Column(name = "s_param_3")
    public Float getsParam3() {
        return sParam3;
    }

    public void setsParam3(Float sParam3) {
        this.sParam3 = sParam3;
    }

    @Basic
    @Column(name = "s_param_4")
    public Float getsParam4() {
        return sParam4;
    }

    public void setsParam4(Float sParam4) {
        this.sParam4 = sParam4;
    }

    @Basic
    @Column(name = "s_param_5")
    public Float getsParam5() {
        return sParam5;
    }

    public void setsParam5(Float sParam5) {
        this.sParam5 = sParam5;
    }

    @Basic
    @Column(name = "s_url")
    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }
}
