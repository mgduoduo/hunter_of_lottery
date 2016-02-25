package com.hunter.bean;

/**
 * Created by gaoqiang on 2015/9/15.
 */
public class Member extends BaseBO{
    private int id;
    private String name;
    private String position;
    private String clubNo;
    private int number;//号码
    private int gender;
    private String country;
    private String healthStatus;

    private Club club;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClubNo() {
        return clubNo;
    }

    public void setClubNo(String clubNo) {
        this.clubNo = clubNo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
