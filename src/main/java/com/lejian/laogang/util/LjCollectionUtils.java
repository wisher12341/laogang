package com.lejian.laogang.util;

import java.util.List;

public class LjCollectionUtils {
    public static boolean existDiff(List<String> list1, List<String> list2) {
        if (list1.size() != list2.size()){
            return true;
        }
        for (String str : list1){
            if (!list2.contains(str)){
                return true;
            }
        }
        return false;
    }
}
