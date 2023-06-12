package com.example.cloud.entity;

import java.math.BigInteger;

public class TracksInfo {
    private BigInteger t;
    private String uuid;
    private String x;
    private String y;
    private String treeid;
    private String tancid;

    public BigInteger getT() {
        return t;
    }

    public void setT(BigInteger t) {
        this.t = t;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getTreeid() {
        return treeid;
    }

    public void setTreeid(String treeid) {
        this.treeid = treeid;
    }

    public String getTancid() {
        return tancid;
    }

    public void setTancid(String tancid) {
        this.tancid = tancid;
    }

    @Override
    public String toString() {
        return "TracksInfo{" +
                "t=" + t.toString() +
                ", uuid='" + uuid + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", treeid='" + treeid + '\'' +
                ", tancid='" + tancid + '\'' +
                '}';
    }
}
