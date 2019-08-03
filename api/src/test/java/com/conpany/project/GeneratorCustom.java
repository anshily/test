package com.conpany.project;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class
GeneratorCustom {
    private List<String> eachProperties(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field[] field = model.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组
        List<String> proList = new LinkedList<>();

        for(int j=0 ; j<field.length ; j++){ //遍历所有属性
            String name = field[j].getName(); //获取属性的名字

            System.out.println("attribute name:"+name);
            name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString(); //获取属性的类型
            if(type.equals("class java.lang.String")){ //如果type是类类型，则前面包含"class "，后面跟类名
            }
            if(type.equals("class java.lang.Integer")){
            }
            if(type.equals("class java.lang.Short")){

            }
            if(type.equals("class java.lang.Double")){
            }
            if(type.equals("class java.lang.Boolean")){
            }
            if(type.equals("class java.util.Date")){
            }
        }

        return proList;
    }
}
