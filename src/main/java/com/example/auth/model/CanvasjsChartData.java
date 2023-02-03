package com.example.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CanvasjsChartData {
    static Map<Object,Object> map = null;
    static List<List<Map<Object,Object>>> list = new ArrayList<List<Map<Object,Object>>>();
    static List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();

    static {
        map = new HashMap<Object,Object>(); map.put("name", "Chicago"); map.put("y", 21);dataPoints1.add(map);
        map = new HashMap<Object,Object>(); map.put("name", "Dallas"); map.put("y", 14);dataPoints1.add(map);
        map = new HashMap<Object,Object>(); map.put("name", "Los Angeles"); map.put("y", 14);dataPoints1.add(map);
        map = new HashMap<Object,Object>(); map.put("name", "New York"); map.put("y", 29);dataPoints1.add(map);
        map = new HashMap<Object,Object>(); map.put("name", "Seattle"); map.put("y", 22);dataPoints1.add(map);

        list.add(dataPoints1);
    }
    public static List<List<Map<Object, Object>>> getCanvasjsDataList() {
        return list;
    }
}
