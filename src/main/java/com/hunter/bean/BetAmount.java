package com.hunter.bean;

/**
 * Created by mario on 2015/9/23.
 */
public class BetAmount {
    public int id;
    public String matchNo;
    public Double homeAmountBetfair;
    public Double drawAmountBetfair;
    public Double awayAmountBetfair;
    public Double homeAmountSporttery;
    public Double drawAmountSporttery;
    public Double awayAmountSporttery;
    public String publishTime;

    public BetAmount(){

    }

    public BetAmount(String matchNo){
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

    public Double getHomeAmountBetfair() {
        return homeAmountBetfair;
    }

    public void setHomeAmountBetfair(Double homeAmountBetfair) {
        this.homeAmountBetfair = homeAmountBetfair;
    }

    public Double getDrawAmountBetfair() {
        return drawAmountBetfair;
    }

    public void setDrawAmountBetfair(Double drawAmountBetfair) {
        this.drawAmountBetfair = drawAmountBetfair;
    }

    public Double getAwayAmountBetfair() {
        return awayAmountBetfair;
    }

    public void setAwayAmountBetfair(Double awayAmountBetfair) {
        this.awayAmountBetfair = awayAmountBetfair;
    }

    public Double getHomeAmountSporttery() {
        return homeAmountSporttery;
    }

    public void setHomeAmountSporttery(Double homeAmountSporttery) {
        this.homeAmountSporttery = homeAmountSporttery;
    }

    public Double getDrawAmountSporttery() {
        return drawAmountSporttery;
    }

    public void setDrawAmountSporttery(Double drawAmountSporttery) {
        this.drawAmountSporttery = drawAmountSporttery;
    }

    public Double getAwayAmountSporttery() {
        return awayAmountSporttery;
    }

    public void setAwayAmountSporttery(Double awayAmountSporttery) {
        this.awayAmountSporttery = awayAmountSporttery;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
