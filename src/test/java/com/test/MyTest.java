package com.test;

import com.hunter.bean.League;
import com.hunter.service.CrawlerService;
import com.hunter.service.LeagueService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by gaoqiang on 2015/9/18.
 */
public class MyTest {

    ApplicationContext dsContext = null;
    LeagueService leagueService = null;
    CrawlerService crawlerService = null;
    @Before
    public void init(){
        dsContext = new ClassPathXmlApplicationContext(new String[] {
                "applicationContext.xml", "applicationContext-dao.xml",
                "applicationContext-service.xml" });

        leagueService = (LeagueService) dsContext.getBean("leagueService");
    }

    @Test
    public void testClub(){
        League league = new League();
        league.setCountry("1123");
        league.setLeagueNo("111");
        league.setLeagueName("123123");
        league.setLeagueShortName("11");
        leagueService.saveLeague(league);
    }

}
