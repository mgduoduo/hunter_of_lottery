package com.hunter.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hunter.bean.Club;
import com.hunter.bean.League;
import com.hunter.bean.Odds;
import com.hunter.common.util.DateUtil;
import com.hunter.service.CrawlerService;
import com.hunter.service.LeagueService;
import com.hunter.service.LotteryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoqiang on 2015/9/15.
 */
@Controller
@RequestMapping("/hunter")
public class HunterAction extends BaseAction{

    private static final Logger LOG = LoggerFactory.getLogger(HunterAction.class);

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LeagueService leagueService;

    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public String getInfo(Model model) {
//        crawlerService.getLeagueDataFrom500w();
//        crawlerService.getClubDataFrom500w();
        crawlerService.getOddsDataFrom500w();
        crawlerService.getTxnDataFromOkooo();
//        crawlerService.syncTxnHistory();
//        Date d = DateUtil.parseDate("2015-08-01",DateUtil.FORMAT_DATE_YYYY_MM_DD);
//        Date end = DateUtil.parseDate("2015-09-01",DateUtil.FORMAT_DATE_YYYY_MM_DD);
//        while(d.before(end)){
//            crawlerService.getMatchHistoryByDate(DateUtil.formatDate(d, DateUtil.FORMAT_DATE_YYYY_MM_DD));
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(d);
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            d = calendar.getTime();
//        }

        crawlerService.getBetAmoutFromOkooo("830779","547086");
        crawlerService.getBetAmoutFromOkooo("830751","547062");
//        crawlerService.getBetAmoutFromOkooo("816334","546823");
//        crawlerService.getBetAmoutFromOkooo("816333","547633");
//        crawlerService.getBetAmoutFromOkooo("814975","539806");
//        crawlerService.getBetAmoutFromOkooo("777640","505638");
//        crawlerService.getBetAmoutFromOkooo("817518","540141");
//        crawlerService.getBetAmoutFromOkooo("793289","519665");
//        crawlerService.getBetAmoutFromOkooo("829814","546782");

//        crawlerService.getOddsDataFrom500w();
//        crawlerService.getTxnDataFromOkooo();
//        Odds odds = new Odds();
//        odds.setMatchNo("550152");
//        odds.setHome(2.33);
//        odds.setHome2(1.1);
//        boolean f = lotteryService.checkIfLatestOdds(odds);
//        System.out.print(f);
        return "test";
    }

    @RequestMapping(value = "/listClub_{leagueNo}")
    public String listClub(@PathVariable("leagueNo") String leagueNo,Model model) {
        List<League> list = leagueService.findAllLeagues();
        model.addAttribute("list",list);
        if(StringUtils.isNotBlank(leagueNo)){
            model.addAttribute("leagueNo",leagueNo);
            List<Club> clubList = leagueService.findClubsByLeagueNo(leagueNo);
            model.addAttribute("clubList",clubList);
        }
        return "club_list";
    }

    @RequestMapping(value = "/listClubByLeagueNo",method = RequestMethod.POST)
    public String listClubByLeagueNo(@RequestParam(value="leagueNo") String leagueNo, Model model) {
        LOG.info("listClubByLeagueNo  leagueNo="+leagueNo);
        List<League> leagueList = leagueService.findAllLeagues();
        model.addAttribute("leagueList",leagueList);
        model.addAttribute("leagueNo",leagueNo);
        if(StringUtils.isNotBlank(leagueNo)){
            List<Club> clubList = leagueService.findClubsByLeagueNo(leagueNo);
            model.addAttribute("clubList",clubList);

        }
        return "club_list";
    }

    @RequestMapping(value = "/listMatch",method = RequestMethod.POST)
    @ResponseBody
    public String listMatch(@RequestParam(value="startTime") String startTime,@RequestParam(value="endTime") String endTime, Model model) {

        return "match_list";
    }

    @RequestMapping(value = "/betDetail",method = RequestMethod.POST)
    @ResponseBody
    public String betDetail(@RequestParam(value="matchNo") String matchNo, Model model) {

        return "bet_detail";
    }

}
