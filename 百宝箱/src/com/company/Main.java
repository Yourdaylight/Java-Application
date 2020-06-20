package com.company;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    //菜单
    public static String menu(){
        return "\t===========生活百宝箱===========\n" +
                "1、ip地址来源查询\n"+
                "2、天气预报查询\n"+
                "3、中文简繁体双向转换\n"+
                "4、身份证归属查询\n"+
                "5、手机号码归属查询\n" +
                "6、退出";
    }


    public static void main(String[] args) {
	// write your code here
        Scanner sc=new Scanner(System.in);
        int choice=-1;
        while (true){
            System.out.println(menu());
            System.out.print("请选择操作:");

            //用户输入数字选择操作，如果输入操作不符合规范，提示错误信息后让用户继续选择
            try {
                choice = sc.nextInt();
                //如果输入为6则退出
                if (choice==6)
                    break;
                else if(choice==1) {
                    System.out.print("请输入ip地址");
                    String ip=sc.next();
                    IpUtil.getIp(ip);
                    continue;
                }
                else if(choice==2){
                    System.out.print("请输入城市名称:");
                    String city=sc.next();
                    System.out.println(WeatherUtil.getWeather(city));
                    continue;
                }
                else if(choice==3){
                    jianfanConvert.jianfan();
                    continue;
                }
                else if(choice==4){
                    System.out.print("请输入身份证号:");
                    String cardNum=sc.next();
                    IdCardUtil.idCard(cardNum);
                    continue;
                }
                else if(choice==5){
                    System.out.print("请输入手机号:");
                    String phone=sc.next();
                    System.out.println(MobileLocationUtil.getMobileNoTrack(phone));
                }
            }
            catch (Exception e){
                continue;
            }
        }
    }
}
