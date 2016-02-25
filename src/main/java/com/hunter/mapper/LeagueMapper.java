package com.hunter.mapper;

import com.hunter.bean.Club;
import com.hunter.bean.League;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoqiang on 2015/9/11.
 */
public interface LeagueMapper extends BaseMapper{
    int insertLeague(League league);
    int deleteLeague(League league);
    int updateLeague(League league);
    League selectLeagueById(Integer id);

    int insertClub(Club club);
    int deleteClub(Club club);
    int updateClub(Club club);
    Club selectClubById(Integer id);
    Club selectClubByNo(String clubNo);
    Club selectClub(Club club);

    List<League> selectLeague(Map<String, Object> params);
    List<Club> selectAllClubsByLeagueNo(String leagueNo);

    List<League> selectAllLeagues(String country);

    List<League> selectAllNoClubLeagues();
}
