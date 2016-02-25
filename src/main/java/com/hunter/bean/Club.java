package com.hunter.bean;

import java.util.List;

/**
 * Created by gaoqiang on 2015/9/15.
 */
public class Club extends BaseBO{

    private int id;
    private String clubNo;
    private String clubName;
    private String clubShortName;

    private String leagueRanking;//TODO

    private String leagueNo;
    private League league;
    private List<Member> members;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClubNo() {
        return clubNo;
    }

    public void setClubNo(String clubNo) {
        this.clubNo = clubNo;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubShortName() {
        return clubShortName;
    }

    public void setClubShortName(String clubShortName) {
        this.clubShortName = clubShortName;
    }

    public String getLeagueRanking() {
        return leagueRanking;
    }

    public void setLeagueRanking(String leagueRanking) {
        this.leagueRanking = leagueRanking;
    }

    public String getLeagueNo() {
        return leagueNo;
    }

    public void setLeagueNo(String leagueNo) {
        this.leagueNo = leagueNo;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
