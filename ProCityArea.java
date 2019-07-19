package com.yucong.excel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProCityArea {


    /**
     * http://www.mca.gov.cn/article/sj/xzqh/2019/
     * notepad，去除左右空格，tab转空格
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("d:\\省市区.txt"));
        String str = "";
        while ((str = br.readLine()) != null) {
            String[] split = str.split("  ");
            Map<String, Object> map = new HashMap<>();
            map.put("code", split[0]);
            map.put("name", split[1]);
            list.add(map);
        }
        br.close();
        System.out.println("数据总数：" + list.size());

        Set<String> set = list.stream().map(m -> m.get("code").toString().substring(0, 2)).distinct().collect(Collectors.toSet());

        System.out.println("省份数量：" + set.size());
    }

}
