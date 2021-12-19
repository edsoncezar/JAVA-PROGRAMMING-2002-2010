package com.wrox.pjsp2.struts.order;

import java.util.Random;

public class OrderNumberGenerator {
   private static Random random = new Random();
   private static OrderNumberGenerator numGen = new OrderNumberGenerator();
   
   private OrderNumberGenerator() {
   }

   public static synchronized OrderNumberGenerator getInstance() {
      return numGen;
   }
      
   public synchronized String getNextOrderNumber() {
      return String.valueOf(Math.abs(random.nextInt()));
   }
}
