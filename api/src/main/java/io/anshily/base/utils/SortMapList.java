package io.anshily.base.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortMapList {
    /**
     * 按照List中的某个String类型的属性进行排序
     * @param list
     */
    @SuppressWarnings("unchecked")
    public static void sortStringMethod(List list, String orderStr){
        list.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map m1 = (Map) o1;
                Map m2 = (Map) o2;
                if (Long.valueOf((String) m1.get(orderStr)) > Long.valueOf((String) m2.get(orderStr))) {
                    return 1;
                } else if (m1.get(orderStr) == m2.get(orderStr)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
//        System.out.println("/////////////排序之后///////////////");
//        for(int i=0;i<list.size();i++){
//            Map st=(Map) list.get(i);
//            System.out.println("st.age="+st.get("add_time")+",st.name="+st.get("add_time"));
//        }
    }
}
