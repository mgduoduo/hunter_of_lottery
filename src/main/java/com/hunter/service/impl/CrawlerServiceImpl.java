package com.hunter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hunter.bean.*;
import com.hunter.common.util.DateUtil;
import com.hunter.common.util.RegUtil;
import com.hunter.service.CrawlerService;
import com.hunter.service.LeagueService;
import com.hunter.service.LotteryService;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by gaoqiang on 2015/9/15.
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LeagueService leagueService;

    @Override
    public JSONArray getDataFromOkooo() {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.okooo.com/jingcai/").get();//http://trade.500.com/jczq/
            String title = doc.title();
            //LOG.info(title);

            //1
            //cont endday
            //cont
            Element cont = doc.select(".cont").first();

            //2
            //touzhu
            Element touzhu = cont.select(".touzhu").first();

            //3
            //liansai
            Element liansai = cont.select(".liansai").first();
            String liansai_name = liansai.select("a").html().trim();
            LOG.info(liansai_name);
            //shenpf
            //rangqiuspf

            Element shenpf = cont.select(".shenpf").first();
            Element zhu = shenpf.select(".zhu").first();
            String zhuPeiLv = zhu.select("div.peilv").html().trim();
            LOG.info(zhuPeiLv);
            Element ping = shenpf.select(".ping").first();
            String pingPeiLv = ping.select("div.peilv").html().trim();
            LOG.info(pingPeiLv);
            Element fu = shenpf.select(".fu").first();
            String fuPeiLv = fu.select("div.peilv").html().trim();
            LOG.info(fuPeiLv);

            Element rangqiuspf = cont.select(".rangqiuspf").first();
            Element rangqiuzhu = rangqiuspf.select(".zhu").first();
            String rangqiuzhuPeiLv = rangqiuzhu.select("div.peilv").html().trim();
            LOG.info(rangqiuzhuPeiLv);
            Element rangqiuping = rangqiuspf.select(".ping").first();
            String rangqiupingPeiLv = rangqiuping.select("div.peilv").html().trim();
            LOG.info(rangqiupingPeiLv);
            Element rangqiufu = rangqiuspf.select(".fu").first();
            String rangqiufuPeiLv = rangqiufu.select("div.peilv").html().trim();
            LOG.info(rangqiufuPeiLv);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public JSONArray getTxnDataFromOkooo() {
        //1 http://www.okooo.com/livecenter/jingcai/ : get matchno
        // .livescore_table each_match
        // tr #id match_detail_777678
        // http://www.okooo.com/soccer/match/777678/history/
        // .ctrl_homename .ctrl_awayname


        //2 http://www.okooo.com/soccer/match/792377/exchanges/ : get txn info

        Document doc = null;
        try {
            Date start = new Date();
            LOG.info("getTxnDataFromOkooo >>>>>>> begin at:"+ DateUtil.formatDate(start, DateUtil.FORMAT_DATE_TIMESTAMP));
            doc = Jsoup.connect("http://www.okooo.com/jingcai/").get();

            //whole table
            Elements bet_tables = doc.select("div.touzhu_1");
            LOG.info("begin to scan");
            int count = 0;
            for (Element el : bet_tables) {
                String mid = el.attr("data-mid").trim();
                String morder = el.attr("data-morder").trim();

                Match m = lotteryService.findLatestMatchByMatchOrder(morder);//TODO
                if(m == null ){
                    LOG.info("No record found with match order="+morder);
                    continue;
                }
                if(StringUtils.isNotBlank(m.getStartTime())){
                    Date matchTime = DateUtil.parseDate(m.getStartTime(), DateUtil.FORMAT_DATE_DATETIME);
                    Date matchEndTime = DateUtil.getDate(matchTime, Calendar.HOUR_OF_DAY,3);
                    //Date tmr = DateUtil.getDate(new Date(), Calendar.DAY_OF_MONTH, 1);
                    if(matchEndTime.before(new Date())){
                        LOG.info("It's no need to process the one which match no = "+m.getMatchNo()+" since the match has been started at: "+m.getStartTime());
                        continue;
                    }
                }
                //save the related match no from okooo.
                if(StringUtils.isBlank(m.getMatchNoOkooo())){
                    m.setMatchNoOkooo(mid);
                    lotteryService.updateMatch(m);
                }
                String matchNo = m.getMatchNo();
                boolean result = getBetAmoutFromOkooo(mid,matchNo);
                if(result){
                    count++;
                }
            }

            Date end = new Date();
            LOG.info("getTxnDataFromOkooo <<< success >>> end at:"+ DateUtil.formatDate(end, DateUtil.FORMAT_DATE_TIMESTAMP));
            LOG.info("total count proceed :"+count+", cost :"+(end.getTime()-start.getTime()) +" ms");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public void getMatchHistoryByDate(String dateStr){
        String urlOkooo = "http://www.okooo.com/livecenter/jingcai/?date="+dateStr;//2015-09-10
        String url500 = "http://live.500.com/?e="+dateStr;//2015-09-10

        LOG.info("begin to sync the data from okooo from the date="+dateStr);
        Document doc = null;
        try {
            //1, get match info.
            doc = Jsoup.connect(url500).get();
            Elements tableMatch500 = doc.select("#table_match");
            if(tableMatch500==null || tableMatch500.size() ==0){
                return;
            }

            Elements trs = tableMatch500.get(0).select("tr");
            if (trs != null && trs.size()>9) {
                //LOG.info("debar size="+trs.size());
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    if(tds.size()<2){
                        continue;
                    }
                    String matchStatus = tds.get(4).text().trim();
                    if(matchStatus.indexOf("完")<0){
                        //比赛中断、未正常结束、改期的场次均跳过
                        continue;
                    }
                    String matchNo = trs.get(i).attr("fid");

                    String startTime = tds.get(3).text().trim();
                    if(StringUtils.isNotBlank(startTime)){
                        startTime = dateStr.substring(0,5)+startTime;
                    }
                    Element finalScoreTd = tds.get(6);
                    String finalHomeGoals = finalScoreTd.select("a.clt1").get(0).html().trim();
                    String finalGuestGoals = finalScoreTd.select("a.clt3").get(0).html().trim();

                    String halfTimeScore = tds.get(8).text().replaceAll(" ","");

                    String leagueNo = trs.get(i).attr("sid");
                    String matchOrder = trs.get(i).attr("order");

                    Match match = lotteryService.findMatchByNo(matchNo);
                    if(match == null){
                        match = new Match(matchNo);
                        Element homeTeamTd = tds.get(5);
                        String homeTeamNo = RegUtil.parseText(homeTeamTd.toString(), ".*?/team/(.*?)/.*?");
                        Element guestTeamTd = tds.get(7);
                        String guestTeamNo = RegUtil.parseText(guestTeamTd.toString(), ".*?/team/(.*?)/.*?");

                        match.setMatchType(leagueNo);
                        match.setMatchOrder(matchOrder);
                        match.setHomeTeamNo(homeTeamNo);
                        match.setGuestTeamNo(guestTeamNo);

                        match.setHomeGoals(finalHomeGoals);
                        match.setGuestGoals(finalGuestGoals);
                        match.setHalfTimeScore(halfTimeScore);
                        match.setStartTime(startTime);

                        lotteryService.saveMatch(match);
                        LOG.info("New match record is saved:matchNo=" + matchNo);
                    }else{
                        match.setMatchType(leagueNo);
                        match.setMatchOrder(matchOrder);
                        match.setHomeGoals(finalHomeGoals);
                        match.setGuestGoals(finalGuestGoals);
                        match.setHalfTimeScore(halfTimeScore);
                        lotteryService.updateMatch(match);
                        LOG.info("Match with matchNo="+matchNo+" is updated");
                    }

                }
            }

            //1, get related okooo match no.
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.parseDate(dateStr, DateUtil.FORMAT_DATE_YYYY_MM_DD));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
            if(dayOfWeek==0) dayOfWeek=7;
            doc = Jsoup.connect(urlOkooo).get();

            Elements tableMatchOkooo = doc.select(".football_list");//tableborder football_list
            if(tableMatchOkooo==null || tableMatchOkooo.size() ==0){
                return;
            }

            Elements trsOkooo = tableMatchOkooo.select("tr");
            for(int i=2; i<trsOkooo.size(); i++){
                Element tr = trsOkooo.get(i);
                Elements tds = tr.select("td");
                if(tds.size()<2){
                    continue;
                }
                String matchid = tr.attr("matchid");
                String order = tds.get(0).select("span").get(0).html();
                if(order!=null){
                    order = dayOfWeek +order;
                }
                //String matchType = RegUtil.parseText(tds.get(1).toString(), ".*?/league/(.*?)/.*?");
                String matchTime = dateStr.substring(0,5)+tds.get(2).text().trim();

                Match match = new Match();
                match.setMatchOrder(order);
                match.setStartTime(matchTime);
                List<Match> list = lotteryService.findMatch(match);
                if(list == null){
                    LOG.info("There is no match with okooo No. = " + matchid);
                }else if(list.size() > 1){
                    LOG.info("Multiple records are found which okooo No. = " + matchid);
                }else if(list!=null && list.size() ==1) {
                    Match m = list.get(0);
//                    if (StringUtils.equals(m.getMatchNoOkooo(), matchid)) {
//                        LOG.info("The match with okooo No. = " + matchid + " is existence");
//                        continue;
//                    }
                    m.setMatchNoOkooo(matchid);
                    lotteryService.updateMatch(m);
                    LOG.info("The match with match No. = " + m.getMatchNo() + " and okooo No. = " + matchid + " is updated");

                    //3, get odds & bet amount data
                    getBetAmoutFromOkooo(matchid,m.getMatchNo());
                }

            }



            LOG.info("The match history of  "+dateStr+" has been copied completely");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void syncTxnHistory(){
        LOG.info("============= sync start =============");
        List<Match> list = lotteryService.findAllNoBetMatches();
        Date start = new Date();
        int count=0;
        for(int i=0; i<list.size();i++){
            Match m = list.get(i);
            if(StringUtils.isNotBlank(m.getMatchNoOkooo()) && StringUtils.isNotBlank(m.getMatchNo())){
                LOG.info("MatchNoOkooo="+m.getMatchNoOkooo()+",matchNo="+m.getMatchNo());
                this.getBetAmoutFromOkooo(m.getMatchNoOkooo(), m.getMatchNo());
                count++;

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(count%50==0){
                    int n = 15;
                    try {
                        LOG.info("Sleep "+n+"s for each 50 times.");
                        Thread.sleep(n*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Date end = new Date();
        LOG.info("total count : "+count+",cost :"+(end.getTime()-start.getTime()) +" ms");
        LOG.info("============= sync end =============");
    }

    public boolean getBetAmoutFromOkooo(String matchNoOkooo, String matchNo){
        boolean result = false;
        int TR_NO_HOME_INFO = 2;
        int TR_NO_DRAW_INFO = 3;
        int TR_NO_AWAY_INFO = 4;
        int COL_NO_ODDS_BF = 5;
        int COL_NO_BET_AMOUT_BF = 6;
        int COL_NO_ODDS_ST = 8;
        int COL_NO_BET_AMOUT_ST = 9;
        try{
            String requestUrl = "http://www.okooo.com/soccer/match/"+matchNoOkooo+"/exchanges/";
            Document betAmountDoc  = Jsoup.connect(requestUrl).get();
            Element bfTable = betAmountDoc.select("table.noBberBottom").get(0);
            Elements trs = bfTable.select("tr");
            int saveOrUpdateOdds = 0;//0 update,1 save
            Odds odds = lotteryService.findLatestOddsByMatchNo(matchNo);
            if(odds == null){
                LOG.info("NO odds info found by match no = "+matchNo +", and okooo match no="+matchNoOkooo);
                odds = new Odds(matchNo);
                saveOrUpdateOdds = 1;
            }


            if(trs!=null && trs.size()>TR_NO_AWAY_INFO){
                BetAmount betAmount = new BetAmount(matchNo);
                //home
                Elements home_tds = trs.get(TR_NO_HOME_INFO).select("td");
                if(home_tds!=null && home_tds.size()>COL_NO_BET_AMOUT_ST){
                    String homeOddsBetFair = home_tds.get(COL_NO_ODDS_BF).html().trim();
                    if(StringUtils.isNotBlank(homeOddsBetFair)){
                        odds.setHomeBF(Double.valueOf(homeOddsBetFair));
                        //needUpdateOdds = true;
                    }

                    String homeAmoutBetFair = home_tds.get(COL_NO_BET_AMOUT_BF).html().trim();
                    if(StringUtils.isNotBlank(homeAmoutBetFair)){
                        betAmount.setHomeAmountBetfair(Double.valueOf(homeAmoutBetFair));
                    }else{
                        betAmount.setHomeAmountBetfair(0.00);
                    }

                    String homeOddsST = home_tds.get(COL_NO_ODDS_ST).html().trim();
                    if(StringUtils.isNotBlank(homeOddsST)){
                        odds.setHome(Double.valueOf(homeOddsST));
                    }

                    String homeAmoutSporttery = home_tds.get(COL_NO_BET_AMOUT_ST).html().trim();
                    if(StringUtils.isNotBlank(homeAmoutSporttery)){
                        betAmount.setHomeAmountSporttery(Double.valueOf(homeAmoutSporttery));
                    }else{
                        betAmount.setHomeAmountSporttery(0.00);
                    }
                }

                //draw
                Elements draw_tds = trs.get(TR_NO_DRAW_INFO).select("td");
                if(draw_tds!=null && draw_tds.size()>COL_NO_BET_AMOUT_ST){
                    String drawOddsBetFair = draw_tds.get(COL_NO_ODDS_BF).html().trim();
                    if(StringUtils.isNotBlank(drawOddsBetFair)){
                        odds.setDrawBF(Double.valueOf(drawOddsBetFair));
                        //needUpdateOdds = true;
                    }

                    String drawAmoutBetFair = draw_tds.get(COL_NO_BET_AMOUT_BF).html().trim();
                    if(StringUtils.isNotBlank(drawAmoutBetFair)){
                        betAmount.setDrawAmountBetfair(Double.valueOf(drawAmoutBetFair));
                    }else{
                        betAmount.setDrawAmountBetfair(0.00);
                    }

                    String drawOddsST = draw_tds.get(COL_NO_ODDS_ST).html().trim();
                    if(StringUtils.isNotBlank(drawOddsST)){
                        odds.setDraw(Double.valueOf(drawOddsST));
                    }

                    String drawAmoutSporttery = draw_tds.get(COL_NO_BET_AMOUT_ST).html().trim();
                    if(StringUtils.isNotBlank(drawAmoutSporttery)){
                        betAmount.setDrawAmountSporttery(Double.valueOf(drawAmoutSporttery));
                    }else{
                        betAmount.setDrawAmountSporttery(0.00);
                    }
                }

                //away
                Elements away_tds = trs.get(TR_NO_AWAY_INFO).select("td");
                if(away_tds!=null && away_tds.size()>COL_NO_BET_AMOUT_ST){
                    String awayOddsBetFair = away_tds.get(COL_NO_ODDS_BF).html().trim();
                    if(StringUtils.isNotBlank(awayOddsBetFair)){
                        odds.setAwayBF(Double.valueOf(awayOddsBetFair));
                        //needUpdateOdds = true;
                    }

                    String awayAmoutBetFair = away_tds.get(COL_NO_BET_AMOUT_BF).html().trim();
                    if(StringUtils.isNotBlank(awayAmoutBetFair)){
                        betAmount.setAwayAmountBetfair(Double.valueOf(awayAmoutBetFair));
                    }else{
                        betAmount.setAwayAmountBetfair(0.00);
                    }

                    String awayOddsST = away_tds.get(COL_NO_ODDS_ST).html().trim();
                    if(StringUtils.isNotBlank(awayOddsST)){
                        odds.setAway(Double.valueOf(awayOddsST));
                    }

                    String awayAmoutSporttery = away_tds.get(COL_NO_BET_AMOUT_ST).html().trim();
                    if(StringUtils.isNotBlank(awayAmoutSporttery)){
                        betAmount.setAwayAmountSporttery(Double.valueOf(awayAmoutSporttery));
                    }else{
                        betAmount.setAwayAmountSporttery(0.00);
                    }
                }
                if(betAmount.getAwayAmountSporttery() + betAmount.getDrawAmountSporttery() + betAmount.getHomeAmountSporttery() < 2000000
                        && betAmount.getAwayAmountBetfair() + betAmount.getDrawAmountBetfair() + betAmount.getHomeAmountBetfair() < 1000000){
                    //LOG.info("The total ST(BF) amount of match(no="+matchNo+") is less than 2000000(100000).");
                    return false;
                }
                boolean existBet = lotteryService.existsSameBetAmount(betAmount);
                if(existBet){
                    LOG.info("So far the bet amount with match no="+matchNo +" has no any change!");
                    return false;
                }

                betAmount.setPublishTime(DateUtil.getCurrentDateTime());
                lotteryService.saveBetAmout(betAmount);
                LOG.info("The bet info is saved");
                odds.setPublishTime(DateUtil.getCurrentDateTime());
                if(saveOrUpdateOdds==0){
                    lotteryService.updateOdds(odds);
                    LOG.info("The odds info is updated");
                }else{
                    lotteryService.saveOdds(odds);
                    LOG.info("The odds info is saved");
                }
                result = true;
                LOG.info("bet amount :okooo mid="+matchNoOkooo+", matchNo=" + betAmount.getMatchNo() + "| homeAmountBF-" + betAmount.getHomeAmountBetfair() + ",drawBF-" + betAmount.getDrawAmountBetfair() + ",awayBF-" + betAmount.getAwayAmountBetfair() + "| homeAmountST-"+betAmount.getHomeAmountSporttery()+",drawST-"+betAmount.getDrawAmountSporttery()+",awayST-"+betAmount.getAwayAmountSporttery());
            }
        }catch(Exception e){
            LOG.error(e.getMessage(), e);
        }

        return result;
    }

    public static void main(String[] args) throws IOException {

//        Document doc = Jsoup.connect("http://liansai.500.com/zuqiu-" + 3444 + "/").get();

//        LOG.info("doc.title"+doc.title());
        CrawlerServiceImpl impl = new CrawlerServiceImpl();
        //impl.getDataFromOkooo();
        impl.getOddsDataFrom500w();
        //impl.getClubDataFrom500w("3468");
//        impl.getClubDataByLeagueNo("3444");

//        String clubNo = "http://trade.500.com/jczq/123123/";
//        Pattern p = Pattern.compile(".*?\\/jczq\\/(.*?)\\/");
//        Matcher m = p.matcher(clubNo);
//        while(m.find()) {
//            LOG.info(m.group(1));//m.group(1)不包括这两个字符
//        }
//        String res = RegUtil.parseText("http://baidu.com/jczq/123123/", ".*?/jczq/(.*?)/");
//        LOG.info(res);
//        String str = "开赛时间：2015-09-19 00:00";
//        LOG.info(str.substring(5));
//
//        }

    }



    @Override
    public JSONArray getDataFromQiutan() {
        return null;
    }

    @Override
    public JSONArray getOddsDataFrom500w() {
        Document doc = null;
        try {
            Date start = new Date();
            LOG.info("getOddsData >>>>>>> begin at:"+ DateUtil.formatDate(start, DateUtil.FORMAT_DATE_TIMESTAMP));
            doc = Jsoup.connect("http://trade.500.com/jczq/").get();
            String title = doc.title();
            //LOG.info(title);

            //1 .bet_content
            //2 .bet_table
            //2.1 .match_time
            //2.1 .left_team
            //2.1 .right_team
            //table
            Elements bet_tables = doc.select(".bet_table");
            LOG.info("begin to scan bet_table");
            for (Element el : bet_tables) {
                String date = el.attr("id").trim();

                Elements trs = el.select("tr");
                LOG.info("----- -----------------------------------" + date);
                for (Element tr : trs) {
                    String matchOrder = tr.attr("pname").trim();

                    String start_time = tr.select("td span.match_time").first().attr("title").substring(5);
                    if(StringUtils.isNotBlank(start_time)){
                        Date matchTime = DateUtil.parseDate(start_time, DateUtil.FORMAT_DATE_DATETIME);
                        Date matchEndTime = DateUtil.getDate(matchTime, Calendar.HOUR_OF_DAY,3);
                        if(matchEndTime.before(new Date())){
                            LOG.info("The match has been started at "+start_time+" with matchOrder="+matchOrder);
                            continue;
                        }
                    }

                    Element league_el = tr.select("td span.league").first();
                    String leagueNo = RegUtil.parseText(league_el.toString(),".*?zuqiu-(.*?)/.*?");

                    Element left_team_el = tr.select("td.left_team a").first();
                    String left_team = left_team_el.attr("title").trim();
                    String hostTeamNo = RegUtil.parseText(left_team_el.attr("href"), ".*?/team/(.*?)/");

                    Element right_team_el = tr.select("td.right_team a").first();
                    String right_team = right_team_el.attr("title").trim();
                    String guestTeamNo = RegUtil.parseText(right_team_el.attr("href"), ".*?/team/(.*?)/");

                    Elements concedePoint_els = tr.select("td.border_left p.concede");
                    String concedePoint = "0";
                    if(concedePoint_els!=null && !concedePoint_els.isEmpty()){
                        concedePoint = concedePoint_els.get(1).select("span").html().trim();
                    }

                    String home = null;
                    String draw = null;
                    String away = null;
                    String home2 = null;
                    String draw2 = null;
                    String away2 = null;
                    Element bet = tr.select("td div.bet_odds").first();
                    Element bet2 = tr.select("td div.bet_odds_2").first();

                    Elements peilv = bet.select("span.odds_item");
                    if(peilv!=null && !peilv.isEmpty()){//没有胜平负，只有让球
                        home = peilv.eq(0).html().trim();
                        draw = peilv.eq(1).html().trim();
                        away = peilv.eq(2).html().trim();
                    }

                    Elements peilv2 = bet2.select("span.odds_item");
                    if(peilv2!=null && !peilv2.isEmpty()){
                        home2 = peilv2.eq(0).html().trim();
                        draw2 = peilv2.eq(1).html().trim();
                        away2 = peilv2.eq(2).html().trim();
                    }

                    String matchNo = RegUtil.parseText(tr.toString(),".*?shuju-(.*?).shtml");

                    Match match = lotteryService.findMatchByNo(matchNo);
                    if(match==null){
                        match = new Match(matchNo);
                        match.setMatchOrder(matchOrder);
                        match.setMatchType(leagueNo);
                        match.setStartTime(start_time);
                        match.setHomeTeamNo(hostTeamNo);
                        match.setGuestTeamNo(guestTeamNo);
                        lotteryService.saveMatch(match);
                    }

                    Odds odds = new Odds(matchNo);
                    if(StringUtils.isNotBlank(home)) odds.setHome(Double.parseDouble(home));
                    if(StringUtils.isNotBlank(draw)) odds.setDraw(Double.parseDouble(draw));
                    if(StringUtils.isNotBlank(away)) odds.setAway(Double.parseDouble(away));
                    if(StringUtils.isNotBlank(concedePoint)) odds.setConcedePoint(concedePoint);
                    if(StringUtils.isNotBlank(home2)) odds.setHome2(Double.parseDouble(home2));
                    if(StringUtils.isNotBlank(draw2)) odds.setDraw2(Double.parseDouble(draw2));
                    if(StringUtils.isNotBlank(away2)) odds.setAway2(Double.parseDouble(away2));
                    boolean result = lotteryService.checkIfLatestOdds(odds);
                    if(!result){
                        odds.setPublishTime(DateUtil.getCurrentDateTime());
                        lotteryService.saveOdds(odds);
                        LOG.info(matchNo+"|"+start_time+"|"+left_team+"-"+right_team+"|"+"{"+home+","+draw+","+away+"},{"+concedePoint+"},{"+home2+","+draw2+","+away2+"}");
                    }
                }

            }

            Date end = new Date();
            LOG.info("getOddsData <<< success >>> end at:"+ DateUtil.formatDate(end, DateUtil.FORMAT_DATE_TIMESTAMP));
            LOG.info("cost :"+(end.getTime()-start.getTime()) +" ms");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public JSONArray getTxnDataFrom500w() {
        //http://zx.500.com/jczq/bf_data.shtml
        LOG.info("getTxnData >>>>>>> begin at:"+ DateUtil.getCurrentDateTime());

        LOG.info("getTxnData <<<< sucess >>> end at:"+ DateUtil.getCurrentDateTime());
        return null;
    }


    public void getClubDataFrom500w() {
        //List<League> list = leagueService.findAllLeagues();
        List<League> list = leagueService.findAllNoClubLeagues();

        if(list!=null && !list.isEmpty()){
            for(final League obj : list){
                //List<Club> clubList =leagueService.findClubsByLeagueNo(obj.getLeagueNo());
                if(StringUtils.isNotBlank(obj.getCountry())){
                    getClubDataByLeagueNoFrom500w(obj.getLeagueNo());
                }
//                if(clubList==null || clubList.isEmpty()){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getClubDataByLeagueNoFrom500w(obj.getLeagueNo());
//                        }
//                    }).start();
//                    //break;
//                }
            }
        }

        getClubDataByLeagueNoFrom500w("3102");//欧洲杯 http://liansai.500.com/zuqiu-3102/teams/
        getClubDataByLeagueNoFrom500w("3651");//世外南美
        getClubDataByLeagueNoFrom500w("3442");//世外北美
        getClubDataByLeagueNoFrom500w("3398");//世外亚洲 http://liansai.500.com/zuqiu-3398/shuju-8576/
        getClubDataByLeagueNoFrom500w("3655");//世外非洲


        getClubDataByLeagueNoFrom500w("2974");//女亚洲杯
        getClubDataByLeagueNoFrom500w("2817");//女欧洲杯
    }

    public JSONArray getClubDataByLeagueNoFrom500w(String leagueNo) {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://liansai.500.com/zuqiu-" + leagueNo + "/").get();
            Elements debars = doc.select(".lsidebar .lbox_bd");
            if(debars==null || debars.size()==0){
                return null;
            }

            Element debar = debars.get(0);
            Elements trs = debar.select("tr");
            if (trs != null && !trs.isEmpty()) {
                //LOG.info("debar size="+trs.size());
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    String ranking = tds.get(0).text().trim();//排名
                    Element clubEle = tds.get(1).select("a").get(0);
                    String clubName = clubEle.attr("title");
                    String clubShortName = clubEle.text().trim();
                    String totalMatches = tds.get(2).text().trim();//总场次
                    String win = tds.get(3).text().trim();//胜
                    String draw = tds.get(4).text().trim();//平
                    String lose = tds.get(5).text().trim();//负
                    String score = tds.get(6).text().trim();//积分
                    //example:/team/1072/
                    String clubNo = RegUtil.parseText(clubEle.attr("href"),".*?/team/(.*?)/");//TODO

                    Club club = leagueService.findClubByNo(clubNo);
                    if( club == null){
                        LOG.info(ranking + "|" + clubNo + "|" + clubName);
                        club = new Club();
                        club.setClubNo(clubNo);
                        club.setClubName(clubName);
                        club.setClubShortName(clubShortName);
                        club.setLeagueRanking(ranking);
                        club.setLeagueNo(leagueNo);
                        leagueService.saveClub(club);
                    }
                }
            }


        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
        return null;
    }

    public JSONArray getLeagueDataFrom500w() {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://liansai.500.com/zuqiu-5468/").get();
            Elements allraces = doc.select("#allraceMainWrap div.lallrace_main");
            Elements allcups = doc.select("#match-cup-div table.lrace_bei");
            //1 国际赛事 跳过
            //2 各大洲赛事
            for (int i = 1; i < allraces.size(); i++) {
                Elements lis = allraces.get(i).select("ul li");
                for (Element li : lis) {
                    //国家：.lallrace_main ul li span
                    //编号、名称、简称：.lallrace_main ul li .lallrace_pop_in a
                    String country = li.select("span").get(0).text().trim();
                    Elements races = li.select(".lallrace_pop_in a");
                    for (Element race : races) {
                        //example:/zuqiu-3448/
                        String leagueNo = RegUtil.parseText(race.attr("href"),".*?zuqiu-(.*?)/");//TODO
                        String leagueName = race.attr("title").trim();
                        String leagueShortName = race.html().trim();

                        League league = leagueService.findLeagueByNo(leagueNo, country);
                        if (league != null) {
                            LOG.info("The league "+leagueName+" with No."+leagueNo+" is existence.");
                            continue;
                        }
                        LOG.info(country+"|"+leagueNo+"|"+leagueShortName+"|"+leagueName);
                        league = new League();
                        league.setCountry(country);
                        league.setLeagueNo(leagueNo);
                        league.setLeagueName(leagueName);
                        league.setLeagueShortName(leagueShortName);
                        leagueService.saveLeague(league);
                    }
                }
            }

            for(Element el : allcups){
                String[][] arrs = RegUtil.parse2DArr(el.toString(), ".*?zuqiu-(.*?)/.*?>(.*?)</a>.*?", 60);
                if(arrs!=null && arrs.length>0){
                    for(int i=0; i<arrs.length; i++){
                        String leagueNo = arrs[i][0];
                        String leagueShortName = arrs[i][1];

                        League league = leagueService.findLeagueByNo(leagueNo, "");
                        if (league != null) {
                            LOG.info("The league "+leagueShortName+" with No."+leagueNo+" is existence.");
                            continue;
                        }
                        LOG.info("杯赛："+"|"+leagueNo+"|"+leagueShortName);
                        league = new League();
                        league.setCountry("");
                        league.setLeagueNo(leagueNo);
                        league.setLeagueName(leagueShortName);
                        league.setLeagueShortName(leagueShortName);
                        leagueService.saveLeague(league);
                    }

                }
            }

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }


}
