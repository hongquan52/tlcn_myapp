package com.example.myapp.utils;

import com.example.myapp.entites.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Utils {
    public static final String  DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_NUMBER = "1";

    /*
    public static double calculateAvgRate(List<Feedback> feedbackList){
        double total = 0;
        if (feedbackList.isEmpty()) return 0;
        for (Feedback feedback : feedbackList){
            total += feedback.getVote();
        }
        return total/feedbackList.size();
    }
    */

    public static BigDecimal getTotalPrice(Product product, BigDecimal totalPrice, int quantity) {
        if (product.getPromotion() > 0) {
            totalPrice = totalPrice
                    .add(product.getPrice().multiply(
                            BigDecimal.valueOf((1 - (product.getPromotion() / 100)) * (double) quantity)))
                    .setScale(2, RoundingMode.UP);
        } else {
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity))).setScale(2, RoundingMode.UP);
        }

        return totalPrice;
    }
}
