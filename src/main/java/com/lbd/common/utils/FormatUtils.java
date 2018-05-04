package com.lbd.common.utils;

import java.math.BigDecimal;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 19:41
 * @Description 格式转换工具
 */
public class FormatUtils {

    public static String PriceFormat(BigDecimal price) {

        String priceStr = price.toString();
        String priceInteger = priceStr.split("\\.")[0];
        String priceDecimal = priceStr.split("\\.")[1];
        String trimDecimal = "";

        for (int i=priceDecimal.length(); i>0; i--) {
            if (priceDecimal.charAt(i-1) == '0') {
                continue;
            } else {
                trimDecimal = priceDecimal.substring(0, i);

                return priceInteger + "." + trimDecimal;
            }
        }

        return priceInteger;

    }

}
