package com.company;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: jack
 */
public class IpUtil {

    public static void getIp(String ip) {

//     //接口连接地址
        String urlStr="http://whois.pconline.com.cn/ipJson.jsp?ip="+ip+"&json=true";
        //调用getResult获取接口返回值
        String result =  getResult(urlStr, "gbk");
        //解析json获取地址
        JSONObject jsonObject=JSONObject.parseObject(result);
        System.out.println(jsonObject.getString("addr"));

    }


    private static String getResult(String urlStr, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection(); // 新建连接实例
            connection.setConnectTimeout(20000); // 设置连接超时时间，单位毫秒
            connection.setReadTimeout(20000); // 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true); // 是否打开输出流 true|false
            connection.setDoInput(true); // 是否打开输入流true|false
            connection.setRequestMethod("GET"); // 提交方法POST|GET
            connection.setUseCaches(false); // 是否缓存true|false
            connection.connect(); // 打开连接端口
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据// ,以BufferedReader流来读取

            String text="";
            String line = "";
            while ((line = reader.readLine()) != null) {
                text+=line+"\n";
            }
            reader.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect(); // 关闭连接
            }
        }
        return null;
    }

}