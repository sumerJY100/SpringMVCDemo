package com.gaussic.model.dcs_history;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "h_023", schema = "wind", catalog = "")
public class H023Pojo {
    private int id;
    private Timestamp vTime;
    private Float v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "v_time")
    public Timestamp getvTime() {
        return vTime;
    }

    public void setvTime(Timestamp vTime) {
        this.vTime = vTime;
    }

    @Basic
    @Column(name = "v")
    public Float getV() {
        return v;
    }

    public void setV(Float v) {
        this.v = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        H023Pojo h023Pojo = (H023Pojo) o;
        return id == h023Pojo.id &&
                Objects.equals(vTime, h023Pojo.vTime) &&
                Objects.equals(v, h023Pojo.v);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, vTime, v);
    }
}
