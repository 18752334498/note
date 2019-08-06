package com.yucong.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class ProCityArea {
	
	/**
     * 字符流的底层是字节流。 两种的用法也不一样，字符流主要是读取文本文件内容的， 可以一个字符一个字符的读取，也可以一行一行的读取文本内容，
     * 大多用于处理字符串。而字节流读取的单位为byte，byte是计算机存储最基本单位， 可以用字节流来读取很多其他的格式文件：比如图片、视频等等
     * 
     * BufferedReader 包装 FileReader, BufferedWrite 包装 FileWriter
     * 
     */

    /**
     * <li>http://www.mca.gov.cn/article/sj/xzqh/2019/</li>
     * <li>notepad++，去除左右空格，tab改为空格</li>
     */
    public static void main(String[] args) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("d:\\aaaa.txt"));

        String str = "";
        while ((str = br.readLine()) != null) {
            Iterable<String> iterable = Splitter.on(" ").trimResults().omitEmptyStrings().split(str);
            ArrayList<String> list2 = Lists.newArrayList(iterable);

            Map<String, Object> map = new HashMap<>();
            map.put("code", list2.get(0));
            map.put("name", list2.get(1));
            list.add(map);
        }
        br.close();

        // 省
//        List<String> list2 = list.stream().map(m -> m.get("code").toString().substring(0, 2)).distinct().collect(Collectors.toList());
//        List<Map<String, Object>> list4 = list.stream().filter(m -> m.get("code").toString().endsWith("0000")).collect(Collectors.toList());
//
//        System.out.println(list2.size());
//        System.out.println(list4.size());
//
//        // INSERT into vwt_dictionary VALUES('151', 'code', '行政区划', 'name', 'pid', 'soldier_info');
//
//        for (Map<String, Object> map : list4) {
//            System.out.println("INSERT into vwt_dictionary VALUES('151', '" + map.get("code").toString().substring(0, 2) + "', '行政区划', '" + map.get("name")
//                    + "', '-1', 'soldier_info';");
//        }
        
        

        // 市, 110000 北京市, 120000 天津市, 310000 上海市, 500000 重庆市
//        List<Map<String, Object>> list5 = list.stream().filter(m -> !m.get("code").toString().endsWith("0000")).collect(Collectors.toList());
//        List<Map<String, Object>> list6 = list5.stream().filter(m -> m.get("code").toString().startsWith("11") || m.get("code").toString().startsWith("12")
//                || m.get("code").toString().startsWith("31") || m.get("code").toString().startsWith("50")).collect(Collectors.toList());
//        System.out.println(list6.size());
//
//        List<Map<String, Object>> list7 = list5.stream().filter(m -> m.get("code").toString().endsWith("00")).collect(Collectors.toList());
//        System.out.println(list7.size());
//
//        for (Map<String, Object> map : list6) {
//            String substring = map.get("code").toString().substring(0, 2);
//            System.out.println("INSERT into vwt_dictionary VALUES('151', '" + map.get("code") + "', '行政区划', '" + map.get("name") + "', '" + substring
//                    + "', 'soldier_info';");
//        }
//        for (Map<String, Object> map : list7) {
//            String substring = map.get("code").toString().substring(0, 2);
//            System.out.println("INSERT into vwt_dictionary VALUES('151', '" + map.get("code") + "', '行政区划', '" + map.get("name") + "', '" + substring
//                    + "', 'soldier_info';");
//        }


        // 区县
        List<Map<String, Object>> list8 = list.stream().filter(m -> !m.get("code").toString().endsWith("00")).collect(Collectors.toList());
        List<Map<String, Object>> list9 = list8.stream().filter(m -> !m.get("code").toString().startsWith("11") && !m.get("code").toString().startsWith("12")
                && !m.get("code").toString().startsWith("31") && !m.get("code").toString().startsWith("50")).collect(Collectors.toList());

        int count = 0;
        for (Map<String, Object> map : list9) {
            count++;
            if (count % 500 == 0) {
                Thread.sleep(20000);
            }
            String substring = map.get("code").toString().substring(0, 4);
            System.out.println("INSERT into vwt_dictionary VALUES('151', '" + map.get("code") + "', '行政区划', '" + map.get("name") + "', '" + substring
                    + "', 'soldier_info';");
        }

    }

}
