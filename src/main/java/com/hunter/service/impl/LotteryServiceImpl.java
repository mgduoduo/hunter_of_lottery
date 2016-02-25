package com.hunter.service.impl;

import com.hunter.bean.BetAmount;
import com.hunter.bean.Match;
import com.hunter.bean.Odds;
import com.hunter.mapper.LotteryMapper;
import com.hunter.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2015/9/15.
 */
@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private LotteryMapper lotteryMapper;

    @Override
    public boolean saveMatch(Match match) {
        int n = lotteryMapper.insertMatch(match);
        return n>0;
    }

    @Override
    public int updateMatch(Match match) {
        return lotteryMapper.updateMatch(match);
    }

    @Override
    public Match findMatchByNo(String matchNo) {
        List list = this.findMatch(new Match(matchNo));
        return (list!=null&&!list.isEmpty()) ? (Match)list.get(0) : null;
    }

    @Override
    public List<Match> findMatch(Match match) {
        return lotteryMapper.selectMatch(match);
    }

    @Override
    public List<Match> findAllMatches() {
        return this.findMatch(new Match());
    }

    @Override
    public List<Match> findAllNoBetMatches() {
        return lotteryMapper.selectAllNoBetMatches();
    }

    @Override
    public boolean saveOdds(Odds odds) {
        int n = lotteryMapper.insertOdds(odds);
        return n>0;
    }

    @Override
    public int updateOdds(Odds odds) {
        return lotteryMapper.updateOdds(odds);
    }

    @Override
    public List<Odds> findOddsByMatch(Match match) {
        return lotteryMapper.selectOddsByMatch(match);
    }

    @Override
    public boolean checkIfLatestOdds(Odds odds) {
        Odds latestOdds = lotteryMapper.checkIfLatestOdds(odds);
        return latestOdds!=null;
    }

    @Override
    public Odds findLatestOddsByMatchNo(String MatchNo) {
        return lotteryMapper.findLatestOddsByMatchNo(MatchNo);
    }

    @Override
    public Match findLatestMatchByMatchOrder(String morder) {
        return lotteryMapper.findLatestMatchByMatchOrder(morder);
    }

    @Override
    public boolean saveBetAmout(BetAmount betAmount) {
        int n = lotteryMapper.insertBetAmount(betAmount);
        return n > 0;
    }

    @Override
    public boolean existsSameBetAmount(BetAmount betAmount) {
        List<BetAmount> list = lotteryMapper.selectBetAmount(betAmount);
        return list!=null&&!list.isEmpty();
    }
}
