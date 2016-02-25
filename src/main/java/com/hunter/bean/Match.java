package com.hunter.bean;

import java.util.List;

/**
 * Created by gaoqiang on 2015/9/15.
 */
public class Match extends BaseBO{

    private String id;
    private String matchOrder;
    private String matchNo;
    private String matchNoOkooo;
    private String matchType;//比赛类型：联赛、杯赛、友谊赛
    private String homeTeamNo;
    private String guestTeamNo;
    private String startTime;
    private String halfTimeScore;
    private String finalScore;
    private String homeGoals;
    private String guestGoals;

    private String place;
    private String weather;

    private Club homeTeam;
    private Club visitingTeam;
    private List<Odds> oddsList;

    public Match(){

    }

    public Match(String matchNo){
        this.matchNo = matchNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchOrder() {
        return matchOrder;
    }

    public void setMatchOrder(String matchOrder) {
        this.matchOrder = matchOrder;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getMatchNoOkooo() {
        return matchNoOkooo;
    }

    public void setMatchNoOkooo(String matchNoOkooo) {
        this.matchNoOkooo = matchNoOkooo;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getHomeTeamNo() {
        return homeTeamNo;
    }

    public void setHomeTeamNo(String homeTeamNo) {
        this.homeTeamNo = homeTeamNo;
    }

    public String getGuestTeamNo() {
        return guestTeamNo;
    }

    public void setGuestTeamNo(String guestTeamNo) {
        this.guestTeamNo = guestTeamNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Odds> getOddsList() {
        return oddsList;
    }

    public void setOddsList(List<Odds> oddsList) {
        this.oddsList = oddsList;
    }

    public String getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(String homeGoals) {
        this.homeGoals = homeGoals;
    }

    public String getGuestGoals() {
        return guestGoals;
    }

    public void setGuestGoals(String guestGoals) {
        this.guestGoals = guestGoals;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Club getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Club homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Club getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(Club visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public String getHalfTimeScore() {
        return halfTimeScore;
    }

    public void setHalfTimeScore(String halfTimeScore) {
        this.halfTimeScore = halfTimeScore;
    }
}
