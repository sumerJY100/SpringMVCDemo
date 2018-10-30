package com.gaussic.model.dcs_history;

import java.sql.Timestamp;

public class H000Pojo_Base {
    private int id;
    private float v;
    private Timestamp vTime;

    public H000Pojo_Base(int id, float v, Timestamp vTime) {
        this.id = id;
        this.v = v;
        this.vTime = vTime;
    }

    public H000Pojo_Base(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public Timestamp getvTime() {
        return vTime;
    }

    public void setvTime(Timestamp vTime) {
        this.vTime = vTime;
    }
}
