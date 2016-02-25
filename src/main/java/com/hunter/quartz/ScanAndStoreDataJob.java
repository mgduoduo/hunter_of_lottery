package com.hunter.quartz;

import com.hunter.common.util.DateUtil;
import com.hunter.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mario on 2015/9/19.
 */
@Component
public class ScanAndStoreDataJob {

    @Autowired
    private CrawlerService crawlerService;

    public void invoke(){
        crawlerService.getOddsDataFrom500w();
        crawlerService.getTxnDataFromOkooo();
    }
    public void syncMatchHistory(){
        //sync the match result from yesterday
        String yesterday = DateUtil.formatDate(DateUtil.getDate(new Date(), Calendar.DAY_OF_MONTH, -1),DateUtil.FORMAT_DATE_YYYY_MM_DD);
        crawlerService.getMatchHistoryByDate(yesterday);
    }

}
