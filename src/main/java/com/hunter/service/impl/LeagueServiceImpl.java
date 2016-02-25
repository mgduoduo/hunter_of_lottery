package com.hunter.service.impl;

import com.hunter.bean.Club;
import com.hunter.bean.League;
import com.hunter.mapper.LeagueMapper;
import com.hunter.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2015/9/15.
 */
@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private LeagueMapper leagueMapper;

    @Override
    public int saveClub(Club club) {
        return leagueMapper.insertClub(club);
    }

    @Override
    public int deleteClub(Club club) {
        return leagueMapper.deleteClub(club);
    }

    @Override
    public int updateClub(Club club) {
        return leagueMapper.updateClub(club);
    }

    @Override
    public Club findClubById(Integer id) {
        return leagueMapper.selectClubById(id);
    }

    @Override
    public Club findClubByNo(String clubNo) {
        return leagueMapper.selectClubByNo(clubNo);
    }

    @Override
    public Club findClub(Club club) {
        return leagueMapper.selectClub(club);
    }

    @Override
    public int saveLeague(League league) {
        return leagueMapper.insertLeague(league);
    }

    @Override
    public int deleteLeague(League league) {
        return leagueMapper.deleteLeague(league);
    }

    @Override
    public int updateLeague(League league) {
        return leagueMapper.updateLeague(league);
    }

    @Override
    public League findLeagueById(Integer id) {
        return leagueMapper.selectLeagueById(id);
    }

    @Override
    public League findLeagueByNo(String leagueNo, String country) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("leagueNo",leagueNo);
        params.put("country",country);
        List<League> list = leagueMapper.selectLeague(params);
        League league= null;
        if(list!=null && !list.isEmpty()){
            league = list.get(0);
        }
        return league;
    }

    @Override
    public List<League> findLeaguesByCountry(String country) {
        return  leagueMapper.selectAllLeagues(country);
    }

    @Override
    public List<League> findAllLeagues() {
        return findLeaguesByCountry(null);
    }

    @Override
    public List<Club> findClubsByLeagueNo(String leagueNo) {
        return leagueMapper.selectAllClubsByLeagueNo(leagueNo);
    }

    @Override
    public List<League> findAllNoClubLeagues() {
        return leagueMapper.selectAllNoClubLeagues();
    }
}
