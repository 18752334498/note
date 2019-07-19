package com.yucong.excel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class ProCityArea {

    public static void main(String[] args) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("d:\\省市区.txt"));

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
        System.out.println("数据总数：" + list.size());

        Set<String> set = list.stream().map(m -> m.get("code").toString().substring(0, 2)).distinct().collect(Collectors.toSet());

        System.out.println("省份数量：" + set.size());
    }

}
