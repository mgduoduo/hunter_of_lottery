package com.hunter.bean;

/**
 * Created by gaoqiang on 2015/9/15.
 */
public class Handicap extends BaseBO{
    private int id;
    private int matchId;
    private String offset;
    private double above;
    private double below;
    private String publishTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public double getAbove() {
        return above;
    }

    public void setAbove(double above) {
        this.above = above;
    }

    public double getBelow() {
        return below;
    }

    public void setBelow(double below) {
        this.below = below;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
