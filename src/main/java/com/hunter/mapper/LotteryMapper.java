package com.hunter.mapper;

import com.hunter.bean.BetAmount;
import com.hunter.bean.Match;
import com.hunter.bean.Odds;

import java.util.List;

/**
 * Created by gaoqiang on 2015/9/11.
 */
public interface LotteryMapper extends BaseMapper{
    int insertMatch(Match match);
    int updateMatch(Match match);
    List<Match> selectMatch(Match match);
    List<Match> selectAllNoBetMatches();

    int insertOdds(Odds odds);
    int updateOdds(Odds odds);
    List<Odds> selectOddsByMatch(Match match);

    Odds checkIfLatestOdds(Odds odds);

    Odds findLatestOddsByMatchNo(String matchNo);

    Match findLatestMatchByMatchOrder(String morder);

    int insertBetAmount(BetAmount betAmount);

    List<BetAmount> selectBetAmount(BetAmount betAmount);

}
