package com.gaussic.model.dcsTemp;

import java.sql.Timestamp;

public class TT {
    private int id;
    private Timestamp vTime;
    private Float v;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getvTime() {
        return vTime;
    }

    public void setvTime(Timestamp vTime) {
        this.vTime = vTime;
    }

    public Float getV() {
        return v;
    }

    public void setV(Float v) {
        this.v = v;
    }
}
