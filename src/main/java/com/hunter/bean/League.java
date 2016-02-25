package com.hunter.bean;

import java.util.List;

/**
 * Created by gaoqiang on 2015/9/18.
 */
public class League extends BaseBO{
    private int id;
    private String leagueNo;
    private String leagueName;
    private String leagueShortName;
    private String country;

    private List<Club> clubList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeagueNo() {
        return leagueNo;
    }

    public void setLeagueNo(String leagueNo) {
        this.leagueNo = leagueNo;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getLeagueShortName() {
        return leagueShortName;
    }

    public void setLeagueShortName(String leagueShortName) {
        this.leagueShortName = leagueShortName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }
}
