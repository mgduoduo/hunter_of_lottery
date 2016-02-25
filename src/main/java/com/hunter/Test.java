package com.hunter;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created by gaoqiang on 2015/9/21.
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        HttpClient client = new HttpClient();
//        PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");
//        post.addRequestHeader("Content-Type",
//                                "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
//                NameValuePair[] data = { new NameValuePair("Uid", "sneil"), // 注册的用户名
//                                new NameValuePair("Key", "gm282417"), // 注册成功后,登录网站使用的密钥
//                                new NameValuePair("smsMob", "18761993277"), // 手机号码
//                                new NameValuePair("smsText", "【提示】以后给我老实点哈。。。。听话。。。") };//设置短信内容
//        post.setRequestBody(data);
//
//            client.executeMethod(post);
//            Header[] headers = post.getResponseHeaders();
//            int statusCode = post.getStatusCode();
//            System.out.println("statusCode:" + statusCode);
//            for (Header h : headers) {
//                    System.out.println(h.toString());
//                }
//            String result = new String(post.getResponseBodyAsString().getBytes(
//                            "gbk"));
//            System.out.println(result);
//            post.releaseConnection();

        sendMsg("18761993277","gm282417","18761993277","【提示】不会吧。。。。飞信真的可以推送短信。。。");
    }

    public static void sendMsg(String _phone,String _pwd,String _to,String _msg) throws HttpException, IOException{
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://3.ibtf.sinaapp.com/f.php");
//        ?username=13800000000&password=00000000000000&sendto=
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
        NameValuePair[] data ={
                new NameValuePair("phone", _phone),
                new NameValuePair("pwd", _pwd),
                new NameValuePair("to",_to),
                new NameValuePair("msg",_msg),
                new NameValuePair("type","0")
        };
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode》》》》》》:"+statusCode);
        for(Header h : headers){
            System.out.println(h.toString());
        }
        //String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
        //System.out.println(result);
        System.out.println("ok!");
        post.releaseConnection();
    }
}
