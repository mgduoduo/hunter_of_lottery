package com.hunter.bean;

/**
 * Created by gaoqiang on 2015/9/15.
 */
public class Odds extends BaseBO{

    private int id;
    private String matchNo;
    private Double home;
    private Double draw;
    private Double away;
    private String concedePoint;
    private Double home2;
    private Double draw2;
    private Double away2;
    private String publishTime;
    private Double homeBF;
    private Double drawBF;
    private Double awayBF;

    public Odds(){

    }

    public Odds(String matchNo){
        this.matchNo = matchNo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public Double getHome() {
        return home;
    }

    public void setHome(Double home) {
        this.home = home;
    }

    public Double getDraw() {
        return draw;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    public Double getAway() {
        return away;
    }

    public void setAway(Double away) {
        this.away = away;
    }

    public String getConcedePoint() {
        return concedePoint;
    }

    public void setConcedePoint(String concedePoint) {
        this.concedePoint = concedePoint;
    }

    public Double getHome2() {
        return home2;
    }

    public void setHome2(Double home2) {
        this.home2 = home2;
    }

    public Double getDraw2() {
        return draw2;
    }

    public void setDraw2(Double draw2) {
        this.draw2 = draw2;
    }

    public Double getAway2() {
        return away2;
    }

    public void setAway2(Double away2) {
        this.away2 = away2;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Double getHomeBF() {
        return homeBF;
    }

    public void setHomeBF(Double homeBF) {
        this.homeBF = homeBF;
    }

    public Double getDrawBF() {
        return drawBF;
    }

    public void setDrawBF(Double drawBF) {
        this.drawBF = drawBF;
    }

    public Double getAwayBF() {
        return awayBF;
    }

    public void setAwayBF(Double awayBF) {
        this.awayBF = awayBF;
    }
}
