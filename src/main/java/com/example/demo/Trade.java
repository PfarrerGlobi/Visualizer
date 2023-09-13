package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;



public class Trade {
    private static int idCounter = 0;
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private LocalDateTime triggerDate;
    public LocalDateTime getTriggerDate() {
        return triggerDate;
    }


    public void setTriggerDate(LocalDateTime triggerDate) {
        this.triggerDate = triggerDate;
    }

    private LocalDateTime expirationDate;
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }


    public void setExpirationdate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    private LocalDateTime openDate;
    public LocalDateTime getOpenDate() {
        return openDate;
    }


    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    private LocalDateTime closeDate;
    public LocalDateTime getCloseDate() {
        return closeDate;
    }


    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    private String ticker;
    public String getTicker() {
        return ticker;
    }


    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    private String direction;
    public String getDirection() {
        return direction;
    }


    public void setDirection(String direction) {
        this.direction = direction;
    }

    private double triggerPrice;
    public double getTriggerPrice() {
        return triggerPrice;
    }


    public void setTriggerPrice(double triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    private double stopPrice;
    public double getStopPrice() {
        return stopPrice;
    }


    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    private double targetPrice;
    public double getTargetPrice() {
        return targetPrice;
    }


    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    private double percentage;
    public double getPercentage() {
        return percentage;
    }


    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    private boolean activated;
    private double equity;

    private double risk_percentage;

    private double entryEquity;

    public double getEntryEquity() {
        return entryEquity;
    }


    public void setEntryEquity(double entryEquity) {
        this.entryEquity = entryEquity;
    }


    public double getRisk_percentage() {
        return risk_percentage;
    }


    public void setRisk_percentage(double risk_percentage) {
        this.risk_percentage = risk_percentage;
    }

    public static double leverageRatio =1;
   
    public double getLeverageRatio() {
        return leverageRatio;
    }


    public void getLeverageRatio(double leverageRatio) {
        this.leverageRatio = leverageRatio;
    }


    public double getEquity() {
        return equity;
    }


    public void setEquity(double equity) {
        this.equity = equity;
    }


    public boolean isActivated() {
        return activated;
    }


    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    private double finalRR;
    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }

    // Static variables
    private static double currentCapital = 100.0; // initial value, change as per your requirement
    public static HashMap<Integer, Trade> tradeMap = new HashMap<>();
    private static ArrayList<Date> dateList = new ArrayList<>();
    private static ArrayList<Double> chart = new ArrayList<>();
    

    public static ArrayList<DataPoint> lastTradeOfDayList = new ArrayList<>();
    private static ArrayList<DataPoint> lastTradeOfMonthList = new ArrayList<>();
    public static ArrayList<DataPoint> allClosingPoints = new ArrayList<>();
    private static HashMap<String, ArrayList<Double>> capitalValuesMap = new HashMap<>();
    private static HashMap<String, ArrayList<DataPoint>>  dataPointMap = new HashMap<>();

    private static HashMap<Integer, ArrayList<Double>> ema_map = new HashMap<>();

    public static HashMap<Integer, ArrayList<Double>> getEma_map() {
        return ema_map;
    }


    public static void setEma_map(HashMap<Integer, ArrayList<Double>> ema_map) {
        Trade.ema_map = ema_map;
    }


    public static void resetData() {
        tradeMap.clear();
        dateList.clear();
        chart.clear();
        lastTradeOfDayList.clear();
        lastTradeOfMonthList.clear();
        allClosingPoints.clear();
        capitalValuesMap.clear();
        dataPointMap.clear();
    }

    
    public static HashMap<String, ArrayList<DataPoint>> getDataPointMap() {
        return dataPointMap;
    }

    public static void setDataPointMap(HashMap<String, ArrayList<DataPoint>> dataPointMap) {
        Trade.dataPointMap = dataPointMap;
    }

    public static HashMap<String, ArrayList<Double>> getCapitalValuesMap() {
        return capitalValuesMap;
    }

    public static void setCapitalValuesMap(HashMap<String, ArrayList<Double>> capitalValuesMap) {
        Trade.capitalValuesMap = capitalValuesMap;
    }

    public static ArrayList<DataPoint> getAllClosingPoints() {
        return allClosingPoints;
    }

    public static void setAllClosingPoints(ArrayList<DataPoint> allClosingPoints) {
        Trade.allClosingPoints = allClosingPoints;
    }

    public static ArrayList<DataPoint> getLastTradeOfDayList() {
        return lastTradeOfDayList;
    }

    public static void setLastTradeOfDayList(ArrayList<DataPoint> lastTradeOfDayList) {
        Trade.lastTradeOfDayList = lastTradeOfDayList;
    }

    public static ArrayList<DataPoint> getLastTradeOfMonthList() {
        return lastTradeOfMonthList;
    }

    public static void setLastTradeOfMonthList(ArrayList<DataPoint> lastTradeOfMonthList) {
        Trade.lastTradeOfMonthList = lastTradeOfMonthList;
    }

    public static double getMaxDrawdonw(){
        return drawdown(getChart());
    }


    public static ArrayList<Double> getChart() {
        return chart;
    }

    public static void setChart(ArrayList<Double> chart) {
        Trade.chart = chart;
    }

    public static LocalDate lowerBound = LocalDate.of(1970, Month.SEPTEMBER, 13);

    public static LocalDate upperBound = LocalDate.of(2300, Month.SEPTEMBER, 13);

    public Trade(LocalDateTime creationDate, LocalDateTime invalidationDate, String ticker, String direction, 
                 double entry, double stopLoss, double takeProfit, double rPercentage, 
                 LocalDateTime openDate, LocalDateTime closeDate, double rr, String result, double risk_percentage) {

        
        this.id = idCounter++;
        this.triggerDate = creationDate;
        this.expirationDate = invalidationDate;
        this.ticker = ticker;
        this.direction = direction;
        this.triggerPrice = entry;
        this.stopPrice = stopLoss;
        this.targetPrice = takeProfit;
        this.percentage = rPercentage;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.finalRR = rr;
        this.result = result;
        this.risk_percentage =risk_percentage;

        this.activated = true;

        tradeMap.put(this.id, this);
        addDatesToChronologicalList();
    }

    // Getters, setters and other methods go here...

    private void addDatesToChronologicalList() {
        Date creationDateObject = new Date(triggerDate, true, this.id, this.ticker);
        Date closeDateObject = new Date(closeDate, false, this.id, this.ticker);
        dateList.add(creationDateObject);
        dateList.add(closeDateObject);
        Collections.sort(dateList);
    }

    public static void loadData(String filename) {
        resetData();
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            // Skip the first line (header row)
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Parse each line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                try{
                    LocalDateTime sampleDate = LocalDateTime.parse(values[0], formatter);
                    } catch (Exception e) {
                    continue;
                }

                    
                LocalDateTime triggerDate = LocalDateTime.parse(values[0], formatter); 
                LocalDateTime expirationDate = LocalDateTime.parse(values[1], formatter);
                String ticker = values[2];
                String direction = values[3];
                double triggerPrice = Double.parseDouble(values[4]);
                double stopPrice = Double.parseDouble(values[5]);
                double targetPrice = Double.parseDouble(values[6]);
                double percentage = Double.parseDouble(values[7]);
                LocalDateTime activationDate = LocalDateTime.parse(values[8], formatter);
                LocalDateTime closeDate = LocalDateTime.parse(values[9], formatter);
                double finalRR = Double.parseDouble(values[10]);
                String result = values[11];
                double risk_percentage = Double.parseDouble(values[12]);//risk_percentage

                
                /*|| triggerDate.getDayOfWeek().name().equals("SATURDAY")|| triggerDate.getDayOfWeek().name().equals("SUNDAY") */

                

                // Create new Trade object
                new Trade(triggerDate, expirationDate, ticker, direction, 
                          triggerPrice, stopPrice, targetPrice, percentage, 
                          activationDate, closeDate, finalRR, result, risk_percentage);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    public static void calculateEma(int[] architecture){

       
       

    for (int i : architecture){
        ArrayList <Double> eq_vals = new ArrayList<>();
        int tradeCount =0;
        allClosingPoints.clear();
        double movingAverage =0;
        double previous_ema=0;
        ArrayList<Double> list = new ArrayList<>();
        
            
            for (int j =0; j<tradeMap.size(); j++) {
                
                Trade trade = tradeMap.get(j);
                if (!trade.isActivated()){
                    continue;
                }
                
                tradeCount+=1;
                eq_vals.add(trade.equity);

                movingAverage = eq_vals.stream().mapToDouble(val -> val).average().orElse(0.0);

                
                
                System.out.println(trade);
                System.out.println("moving average: "+movingAverage);
                System.out.println("trade equity: "+trade.equity);
                if((int)j<i){
                    previous_ema=movingAverage;
                   if (trade.equity < movingAverage){
                    trade.activated=false;
                    System.out.println("deactivated");
                   }
                } else{
                       
                    double ema_now = (2 / (i+1)) * ((trade.equity-previous_ema)+previous_ema);
                    if (trade.equity<ema_now){
                        trade.activated=false;
                    }
                    previous_ema=ema_now;

                
            }
           
            tradeMap.put(trade.id, trade);
            
             
        }            
        
        list = calculateCapital(leverageRatio, true);
        System.out.println("size: "+list.size());
        ema_map.put(i, list);
    }
    }
        


    
    


    public static ArrayList<Double> calculateCapital(double leverageRatio, boolean weekend) {
        
        Trade.leverageRatio = leverageRatio;
        currentCapital =100;
        int lastDay = -1;
        int lastMonth = -1;

        
        
        for (Date date : dateList) {
            //System.out.println(date);
            Trade trade = tradeMap.get(date.getTradeId());
            if (!trade.isActivated()){
               
                continue;
            }

            if (trade.triggerDate.toLocalDate().isBefore(lowerBound)|| trade.triggerDate.toLocalDate().isAfter(upperBound) ){
                continue;
            }




            /* || trade.triggerDate.getDayOfWeek().name().equals("SATURDAY")|| trade.triggerDate.getDayOfWeek().name().equals("SUNDAY") */

               if (trade.risk_percentage==0 ){
                    continue;
                }

                if (!weekend){
                    if (trade.triggerDate.getDayOfWeek().name().equals("SATURDAY")|| trade.triggerDate.getDayOfWeek().name().equals("SUNDAY")){
                        continue;
                    }


                }
            
            

            
            
            
            if (date.isCreation()) {
                trade.entryEquity = currentCapital;
                
            } else {

                if(trade.entryEquity<=0){

                    System.out.println(trade.entryEquity);
                    System.out.println("skipped");
                    continue;
                }
                /*C + Q * Direction (Long position = 1; Short Position = -1) * 
                (Target_Price - Trigger-price) - Q * Trigger_Price * Taker Fee (0.0004) - Q * 
                Target_Price * Maker Fee (0.0002) */
                
                double q = (trade.entryEquity  * trade.risk_percentage*leverageRatio/(trade.triggerPrice *trade.percentage));
                if ("winner".equals(trade.result) && !date.isCreation()) {
                    if (trade.direction.equals("short")){

                        currentCapital += q  *  (trade.triggerPrice - 
                    trade.targetPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.targetPrice * 0.0002;

                    }

                    if (trade.direction.equals("long")){

                        currentCapital += q  *  (trade.targetPrice - 
                    trade.triggerPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.targetPrice * 0.0002;

                    }
                    
                
                    /*if result = loser 
                    C + Q *  Direction (Long position = 1; Short Position = -1) * (Trigger_Price - Stop_Price) - 
                    Q * Trigger_Price * Taker Fee (0.0004) - Q * Stop_Price * Taker Fee (0.0004) */
                    
                    
                } else if (!date.isCreation()&&"loser".equals(trade.result)) {
                    
                    if (trade.direction.equals("short")){

                        currentCapital -= q  *  (trade.stopPrice - 
                    trade.triggerPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.stopPrice * 0.0004;

                    }

                    if (trade.direction.equals("long")){

                        currentCapital -= q  *  (trade.triggerPrice - 
                    trade.stopPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.stopPrice * 0.0004;

                    }
                    
                } else{
                    continue;
                }
                chart.add(currentCapital);
                trade.equity= currentCapital;
                System.out.println(currentCapital);
               
                
               
               
              

               
                
                if (date.getDateTime().getDayOfMonth() != lastDay) {
                    lastTradeOfDayList.add(new DataPoint(date.getDateTime(), currentCapital));
                    lastDay = date.getDateTime().getDayOfMonth();
                }
                

                // Check if the date is the last trade of the month
                if (date.getDateTime().getMonthValue() != lastMonth) {
                    lastTradeOfMonthList.add(new DataPoint(date.getDateTime(), currentCapital));
                    lastMonth = date.getDateTime().getMonthValue();
                }
                
                    allClosingPoints.add(new DataPoint(date.getDateTime(), currentCapital));
                    trade.setEquity(currentCapital);

                
                
                
            }

             dataPointMap.put("all", allClosingPoints);
             dataPointMap.put("mm", lastTradeOfMonthList);
             dataPointMap.put("dd", lastTradeOfDayList);
            
        }
        createMap();
        return capitalValuesMap.get("all");
      
     //   System.out.println(chart);
    }

    public static HashMap<String, ArrayList<AdvancedDataPoint>> equityPerTrade(){
            TreeSet<String> library = new TreeSet<>();
            
            HashMap<String, ArrayList<AdvancedDataPoint>> map = new HashMap<>();



            for (Integer i: tradeMap.keySet()){
                    Trade t = tradeMap.get(i);
                    library.add(t.ticker);
            }


            for (String tickername : library){

            ArrayList<AdvancedDataPoint> equityPerTrade = new ArrayList<>();



                currentCapital =100;
                



                for (Date date : dateList) {
            //System.out.println(date);
            Trade trade = tradeMap.get(date.getTradeId());

            if (!trade.ticker.equals(tickername)){
                continue;
            }
            
            
            

            
            
            
            if (date.isCreation()) {
                trade.entryEquity = currentCapital;
                
            } else {

                if(trade.entryEquity<=0){

                    System.out.println(trade.entryEquity);
                    System.out.println("skipped");
                    continue;
                }
                /*C + Q * Direction (Long position = 1; Short Position = -1) * 
                (Target_Price - Trigger-price) - Q * Trigger_Price * Taker Fee (0.0004) - Q * 
                Target_Price * Maker Fee (0.0002) */
                
                double q = (trade.entryEquity/(trade.triggerPrice *trade.percentage));
                if ("winner".equals(trade.result) && !date.isCreation()) {
                    if (trade.direction.equals("short")){

                        currentCapital += q  *  (trade.triggerPrice - 
                    trade.targetPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.targetPrice * 0.0002;

                    }

                    if (trade.direction.equals("long")){

                        currentCapital += q  *  (trade.targetPrice - 
                    trade.triggerPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.targetPrice * 0.0002;

                    }
                    
                
                    /*if result = loser 
                    C + Q *  Direction (Long position = 1; Short Position = -1) * (Trigger_Price - Stop_Price) - 
                    Q * Trigger_Price * Taker Fee (0.0004) - Q * Stop_Price * Taker Fee (0.0004) */
                    
                    
                } else if (!date.isCreation()&&"loser".equals(trade.result)) {
                    
                    if (trade.direction.equals("short")){

                        currentCapital -= q  *  (trade.stopPrice - 
                    trade.triggerPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.stopPrice * 0.0004;

                    }

                    if (trade.direction.equals("long")){

                        currentCapital -= q  *  (trade.triggerPrice - 
                    trade.stopPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.stopPrice * 0.0004;

                    }
                    
                } else{
                    continue;
                }
                chart.add(currentCapital);
                trade.equity= currentCapital;
                System.out.println(tickername+" "+currentCapital);
               
                
               
               
              

               
                
                
                    trade.setEquity(currentCapital);

                    AdvancedDataPoint ap = new AdvancedDataPoint(trade.getTriggerDate(), currentCapital,trade.entryEquity, trade.getTicker());

                    equityPerTrade.add(ap);

                
                
                
            }

             
            
        }
                map.put(tickername, equityPerTrade);
               




            }



            return map;
    }



    public static ArrayList<Double> calculateIdealCapital(double leverageRatio) {
        
        Trade.leverageRatio = leverageRatio;
        currentCapital =100;
        int lastDay = -1;
        int lastMonth = -1;

        
        
        for (Date date : dateList) {
            //System.out.println(date);
            Trade trade = tradeMap.get(date.getTradeId());
            trade.result="winner";
            if (!trade.isActivated()){
               
                continue;
            }

         
            
            

            
            
            
            if (date.isCreation()) {
                trade.entryEquity = currentCapital;
                
            } else {

                if(trade.entryEquity<=0){

                    System.out.println(trade.entryEquity);
                    System.out.println("skipped");
                    continue;
                }
                /*C + Q * Direction (Long position = 1; Short Position = -1) * 
                (Target_Price - Trigger-price) - Q * Trigger_Price * Taker Fee (0.0004) - Q * 
                Target_Price * Maker Fee (0.0002) */
                
                double q = (trade.entryEquity  * trade.risk_percentage*leverageRatio/(trade.triggerPrice *trade.percentage));
                if ("winner".equals(trade.result) && !date.isCreation()) {
                    if (trade.direction.equals("short")){

                        currentCapital += q  *  (trade.triggerPrice - 
                    trade.targetPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.targetPrice * 0.0002;

                    }

                    if (trade.direction.equals("long")){

                        currentCapital += q  *  (trade.targetPrice - 
                    trade.triggerPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.targetPrice * 0.0002;

                    }
                    
                
                    /*if result = loser 
                    C + Q *  Direction (Long position = 1; Short Position = -1) * (Trigger_Price - Stop_Price) - 
                    Q * Trigger_Price * Taker Fee (0.0004) - Q * Stop_Price * Taker Fee (0.0004) */
                    
                    
                } else if (!date.isCreation()&&"loser".equals(trade.result)) {
                    
                    if (trade.direction.equals("short")){

                        currentCapital -= q  *  (trade.stopPrice - 
                    trade.triggerPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.stopPrice * 0.0004;

                    }

                    if (trade.direction.equals("long")){

                        currentCapital -= q  *  (trade.triggerPrice - 
                    trade.stopPrice) - q  * 
                    trade.triggerPrice * 0.0004 - q * trade.stopPrice * 0.0004;

                    }
                    
                } else{
                    continue;
                }
                chart.add(currentCapital);
                trade.equity= currentCapital;
                System.out.println(currentCapital);
               
                
               
               
              

               
                
                if (date.getDateTime().getDayOfMonth() != lastDay) {
                    lastTradeOfDayList.add(new DataPoint(date.getDateTime(), currentCapital));
                    lastDay = date.getDateTime().getDayOfMonth();
                }
                

                // Check if the date is the last trade of the month
                if (date.getDateTime().getMonthValue() != lastMonth) {
                    lastTradeOfMonthList.add(new DataPoint(date.getDateTime(), currentCapital));
                    lastMonth = date.getDateTime().getMonthValue();
                }
                
                    allClosingPoints.add(new DataPoint(date.getDateTime(), currentCapital));
                    trade.setEquity(currentCapital);

                
                
                
            }

             dataPointMap.put("all", allClosingPoints);
             dataPointMap.put("mm", lastTradeOfMonthList);
             dataPointMap.put("dd", lastTradeOfDayList);
            
        }
        createMap();
        return capitalValuesMap.get("all");
      
     //   System.out.println(chart);
    }

    // Inner class Date
    public class Date implements Comparable<Date> {
        // Replace LocalDate with LocalDateTime
        private LocalDateTime dateTime;
        private boolean creation;
        private int tradeId;
        private String ticker;

        public Date(LocalDateTime dateTime, boolean creation, int tradeId, String ticker) {
            this.dateTime = dateTime;
            this.creation = creation;
            this.tradeId = tradeId;
            this.ticker = ticker;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public boolean isCreation() {
            return creation;
        }

        public int getTradeId() {
            return tradeId;
        }

        @Override
        public int compareTo(Date o) {
            int dateTimeComparison = this.dateTime.compareTo(o.getDateTime());

            if (dateTimeComparison != 0) {
                // If the dateTimes are not the same, use their comparison
                return dateTimeComparison;
            } else {
                // If the dateTimes are the same, return -1 if this date is not a creation date,
                // and 1 otherwise. This will ensure that non-creation dates are sorted earlier.
                return this.creation ? 1 : -1;
            }
        }

        @Override
        public String toString() {
            return "Date{" +
                    "dateTime=" + dateTime +
                    ", creation=" + creation +
                    ", tradeId=" + tradeId +
                    '}';
        }
    }

    public static double  drawdown(List<Double> list){

        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("Values cannot be null or empty.");
        }

        double maxDrawdown = 0.0;    // This will store the max drawdown
        double peak = list.get(0); // Initially, assume the first value is the peak

        for (double value : list) {
            // If we find a new peak, update the peak value
            if (value > peak) {
                peak = value;
            }

            // Calculate the current drawdown
            double drawdown =(peak - value)/ peak*100;

            // Update maxDrawdown if the current drawdown is greater
            if (drawdown > maxDrawdown) {
                maxDrawdown = drawdown;
                System.out.println(drawdown+": "+peak+" -> "+value);
                
            }
        }

        return maxDrawdown;
    }


    public static void main(String[] args){
        


        



    }

    public String toString() {
        return "Trade{" +
                "id=" + id +
                ", creationDate=" + triggerDate +
                ", invalidationDate=" + expirationDate +
                ", ticker='" + ticker + '\'' +
                ", direction='" + direction + '\'' +
                ", entry=" + triggerPrice +
                ", stopLoss=" + stopPrice +
                ", takeProfit=" + targetPrice +
                ", rPercentage=" + percentage +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", rr=" + finalRR +
                ", result='" + result + '\'' +
                '}';
    }

    public static ArrayList<DataPoint> getDays(){

        return lastTradeOfDayList;
    }

    public static void createMap() {
        ArrayList<Double> allCapital = new ArrayList<>();
        ArrayList<Double> monthEndCapital = new ArrayList<>();
        ArrayList<Double> dayEndCapital = new ArrayList<>();
    
        for (DataPoint dp : allClosingPoints) {
            allCapital.add(dp.getCapital());
        }
    
        for (DataPoint dp : lastTradeOfMonthList) {
            monthEndCapital.add(dp.getCapital());
        }
    
        for (DataPoint dp : lastTradeOfDayList) {
            dayEndCapital.add(dp.getCapital());
        }
    
        capitalValuesMap.put("all", allCapital);
        capitalValuesMap.put("mm", monthEndCapital);
        capitalValuesMap.put("dd", dayEndCapital);
    }
    

}
