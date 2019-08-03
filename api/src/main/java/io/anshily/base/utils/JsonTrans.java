package io.anshily.base.utils;

import net.sf.json.JsonConfig;

import java.util.List;


public class JsonTrans {
    public static List jsonToObjArr(String objArrStr, Object obj) {
        try {
            net.sf.json.JSONArray arr = net.sf.json.JSONArray.fromObject(objArrStr);
            return net.sf.json.JSONArray.toList(arr,obj,new JsonConfig());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
