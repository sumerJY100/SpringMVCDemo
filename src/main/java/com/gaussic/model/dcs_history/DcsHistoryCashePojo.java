package com.gaussic.model.dcs_history;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "dcs_history_cashe", schema = "wind")
public class DcsHistoryCashePojo {
    private int id;
    private Timestamp vTime;
    private Integer vOffset;
    private String values;

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
    @Column(name = "v_offset")
    public Integer getvOffset() {
        return vOffset;
    }

    public void setvOffset(Integer vOffset) {
        this.vOffset = vOffset;
    }

    @Basic
    @Column(name = "v_values")
    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DcsHistoryCashePojo that = (DcsHistoryCashePojo) o;
        return id == that.id &&
                Objects.equals(vTime, that.vTime) &&
                Objects.equals(vOffset, that.vOffset) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, vTime, vOffset, values);
    }
}
