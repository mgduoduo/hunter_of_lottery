package com.hunter.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Component;

@Component
public interface CrawlerService extends BaseService{
    public JSONArray getDataFromOkooo();
    public JSONArray getTxnDataFromOkooo();

    public boolean getBetAmoutFromOkooo(String matchNoOkooo, String matchNo);

    public JSONArray getDataFromQiutan();

    public JSONArray getOddsDataFrom500w();
    public JSONArray getTxnDataFrom500w();
    public void getClubDataFrom500w();

    public JSONArray getClubDataByLeagueNoFrom500w(String leagueNo);
    public JSONArray getLeagueDataFrom500w();
    public void getMatchHistoryByDate(String dateStr);

    public void syncTxnHistory();
}
