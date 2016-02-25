package com.hunter.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mario on 2015/9/19.
 */
public class RegUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RegUtil.class);

    public static void main(String[] args) throws Exception {
        String[][] res = RegUtil.parse2DArr("<a href=\"/zuqiu-3466/\" target=\"_blank\">欧罗巴</a></td><td><a href=\"/zuqiu-3498/\" target=\"_blank\">欧超杯</a>", ".*?zuqiu-(.*?)/.*?>(.*?)</a>",60);
        System.out.print(res[0][0]+","+res[0][1]);

    }
    public static String parseText(String str, String reg){
        //System.out.println(str);
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        String result = null;
        while(m.find()) {
            result = m.group(1).trim();
        }
        return result;
    }

    public static List<String> parseTextAsList(String str, String reg){
        //System.out.println(str);
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        List<String> result = new ArrayList<String>();
        while(m.find()) {
            result.add(m.group(1).trim());
        }
        return result;
    }

    public static String[][] parse2DArr(String str, String reg, int maxSize){
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        String arr[][] = new String[maxSize][2];
        int i=0;
        while(m.find()) {
            arr[i][0]=m.group(1).trim();
            arr[i][1]=m.group(2).trim();
            i++;
            if(i>=maxSize){
                LOG.info(">>>> The length of resultSet is more than maxSize:"+maxSize);
                break;
            }
        }
        return arr;
    }
}
