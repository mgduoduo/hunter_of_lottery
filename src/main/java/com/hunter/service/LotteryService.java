package com.hunter.service;

import com.hunter.bean.BetAmount;
import com.hunter.bean.Match;
import com.hunter.bean.Odds;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface LotteryService extends BaseService{
    public boolean saveMatch(Match match);
    public int updateMatch(Match match);
    Match findMatchByNo(String matchNo);
    List<Match> findMatch(Match match);
    List<Match> findAllMatches();
    List<Match> findAllNoBetMatches();

    public boolean saveOdds(Odds odds);
    public int updateOdds(Odds odds);
    public List<Odds> findOddsByMatch(Match match);

    public boolean checkIfLatestOdds(Odds odds);
    Odds findLatestOddsByMatchNo(String MatchNo);

    Match findLatestMatchByMatchOrder(String morder);

    boolean saveBetAmout(BetAmount betAmount);

    boolean existsSameBetAmount(BetAmount betAmount);

}
