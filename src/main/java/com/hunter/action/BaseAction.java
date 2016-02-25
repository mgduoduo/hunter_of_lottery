package com.hunter.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class BaseAction {

    protected Logger logger = LoggerFactory.getLogger(BaseAction.class);

    public static Map<String, Integer> serviceMap = new HashMap<String, Integer>(18);

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView exp(Exception ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);
        ModelAndView mav = new ModelAndView();
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            mav.addObject("errorMsg", sw.toString());
        } catch (Exception e) {
            mav.addObject("errorMsg", ex.getMessage());
        }
        mav.setViewName("common/500");
        return mav;
    }

    @ModelAttribute("orderStatus")
    public Map<String, String> getOrderStatus() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("commit", "已提交");
        map.put("audit", "已审核");
        map.put("enable", "已完成");
        map.put("cancel", "已取消");
        map.put("reject", "已驳回");
        return map;
    }

    @ModelAttribute("payStatus")
    public Map<String, String> getPayStatus() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Y", "已支付");
        map.put("N", "未支付");
        return map;
    }

    @ModelAttribute("paymentTypes")
    public Map<String, String> getPaymentTypes() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("online", "在线支付");
        map.put("transfer", "企业转账");
        map.put("none", "免支付");
        return map;
    }

    @ModelAttribute("paymentProviders")
    public Map<String, String> getpaymentProviders() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("yeepay", "易宝支付");
        map.put("unionpay", "银联在线");
        return map;
    }

    @ModelAttribute("newsTypes")
    public Map<String, String> getNewsTypes() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("public", "消息通知");
        map.put("news", "新闻中心");
        map.put("index", "最新动态");
        map.put("owasp", "安全实验室");
        return map;
    }
    
}
