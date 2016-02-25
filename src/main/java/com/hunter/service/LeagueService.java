package com.hunter.service;

import com.hunter.bean.Club;
import com.hunter.bean.League;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LeagueService extends BaseService{
    int saveClub(Club club);
    int deleteClub(Club club);
    int updateClub(Club club);
    Club findClubById(Integer id);
    Club findClubByNo(String clubNo);
    Club findClub(Club club);

    int saveLeague(League league);
    int deleteLeague(League league);
    int updateLeague(League league);
    League findLeagueById(Integer id);

    League findLeagueByNo(String leagueNo, String country);
    List<League> findLeaguesByCountry(String country);
    List<League> findAllLeagues();

    List<Club> findClubsByLeagueNo(String leagueNo);

    List<League> findAllNoClubLeagues();
}
