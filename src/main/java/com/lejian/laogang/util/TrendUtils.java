package com.lejian.laogang.util;

import java.time.LocalDate;

public class TrendUtils {

    static double Increase[] = {0.092049224,0.08501175,0.0783185,0.063099125,0.0674955,0.069063375,0.081037375,0.08991225,0.082673125,0.0966785,0.097114375,0.097555};
    public static double Oldman_Inreasement = 0.05;

    public static Long getTrendData(Long lastCount){
        double factor = Increase[LocalDate.now().getMonthValue()];
        Integer one = 1;
        double temp =lastCount * (factor * Oldman_Inreasement + Long.parseLong(one.toString()));
        temp = Math.ceil(temp);
        return (long)temp;
    }
}
