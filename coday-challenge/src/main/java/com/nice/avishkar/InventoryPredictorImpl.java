
// written by Krishnaraj T and Gyanendra T
// class csf 2023 panel a

package com.nice.avishkar;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class InventoryPredictorImpl implements InventoryPredictor {
    @Override
    public PredictedWarehouseInfo predictWarehouseCapacityWithProductDetails(ResourceInfo resourceInfo)
            throws IOException {

        Path productInfoFilePath = Paths.get("src/main/resources/ProductInfo.csv");
        Path purchaseHistory1FilePath = Paths.get("src/main/resources/Day_1_PurchaseHistory.csv");
        Path purchaseHistory2FilePath = Paths.get("src/main/resources/Day_2_PurchaseHistory.csv");
        Path purchaseHistory3FilePath = Paths.get("src/main/resources/Day_3_PurchaseHistory.csv");
        Path purchaseHistory4FilePath = Paths.get("src/main/resources/Day_4_PurchaseHistory.csv");
        resourceInfo = new ResourceInfo(productInfoFilePath, purchaseHistory1FilePath,
                purchaseHistory2FilePath, purchaseHistory3FilePath, purchaseHistory4FilePath);

        /// read the product list from the csv file
        List<Map<String, Object>> productList = new ArrayList<>();

        try (Reader reader = new FileReader(String.valueOf(resourceInfo.productInfoPath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                Map<String, Object> product = new HashMap<>();
                // product id, int
                product.put("ProductId", Integer.parseInt(record.get("ProductId")));
                // product name string
                product.put("ProductName", record.get("ProductName"));
                // product buy price which int
                product.put("BuyPrice", Integer.parseInt(record.get("BuyPrice")));
                // product sell price int
                product.put("SellPrice", Integer.parseInt(record.get("SellPrice")));
                // product expiration date string
                product.put("AvailableQuantity", Integer.parseInt(record.get("AvailableQuantity")));
                // add margin after calculating it


                double sellPrice = Double.parseDouble(record.get("SellPrice"));
                double buyPrice = Double.parseDouble(record.get("BuyPrice"));
                double margin = (sellPrice - buyPrice) / buyPrice * 100;

                // add total investment
                double totalInvestment = buyPrice * Integer.parseInt(record.get("AvailableQuantity"));
                product.put("TotalInvestment", totalInvestment);

                String formattedMargin = String.format("%.2f%%", margin);
                double formattedMarginDouble = Double
                        .parseDouble(formattedMargin.substring(0, formattedMargin.length() - 1));

                product.put("Margin", formattedMarginDouble);
                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read the data for the rest of the files.
        List<Map<String, Object>> dayOnePurchaseHistory = new ArrayList<>();
        List<Map<String, Object>> dayTwoPurchaseHistory = new ArrayList<>();
        List<Map<String, Object>> dayThreePurchaseHistory = new ArrayList<>();
        List<Map<String, Object>> dayFourPurchaseHistory = new ArrayList<>();

        // read day one purchase history
        try (Reader reader = new FileReader(String.valueOf(purchaseHistory1FilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                Map<String, Object> purchase = new HashMap<>();
                purchase.put("PurchaseHistoryId", Integer.parseInt(record.get("PurchaseHistoryId")));
                purchase.put("ProductId", Integer.parseInt(record.get("ProductId")));
                purchase.put("Quantity", Integer.parseInt(record.get("Quantity")));
                purchase.put("PricePerQuantity", Integer.parseInt(record.get("PricePerQuantity")));

                String timeOfTheDayStr = record.get("TimeOfTheDay");
                if (!timeOfTheDayStr.isEmpty()) {
                    String[] timeComponents = timeOfTheDayStr.split(":");
                    int hour = Integer.parseInt(timeComponents[0]);
                    int minute = Integer.parseInt(timeComponents[1]);
                    int second = Integer.parseInt(timeComponents[2]);
                    LocalTime timeOfTheDay = LocalTime.of(hour, minute, second);
                    purchase.put("TimeOfTheDay", timeOfTheDay);
                    dayOnePurchaseHistory.add(purchase);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read day two purchase history
        try (Reader reader = new FileReader(String.valueOf(purchaseHistory2FilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                Map<String, Object> purchase = new HashMap<>();
                purchase.put("PurchaseHistoryId", Integer.parseInt(record.get("PurchaseHistoryId")));
                purchase.put("ProductId", Integer.parseInt(record.get("ProductId")));
                purchase.put("Quantity", Integer.parseInt(record.get("Quantity")));
                purchase.put("PricePerQuantity", Integer.parseInt(record.get("PricePerQuantity")));
                String timeOfTheDayStr = record.get("TimeOfTheDay");
                if (!timeOfTheDayStr.isEmpty()) {
                    String[] timeComponents = timeOfTheDayStr.split(":");
                    int hour = Integer.parseInt(timeComponents[0]);
                    int minute = Integer.parseInt(timeComponents[1]);
                    int second = Integer.parseInt(timeComponents[2]);
                    LocalTime timeOfTheDay = LocalTime.of(hour, minute, second);
                    purchase.put("TimeOfTheDay", timeOfTheDay);
                    dayTwoPurchaseHistory.add(purchase);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read day three purchase history
        try (Reader reader = new FileReader(String.valueOf(purchaseHistory3FilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                Map<String, Object> purchase = new HashMap<>();
                purchase.put("PurchaseHistoryId", Integer.parseInt(record.get("PurchaseHistoryId")));
                purchase.put("ProductId", Integer.parseInt(record.get("ProductId")));
                purchase.put("Quantity", Integer.parseInt(record.get("Quantity")));
                purchase.put("PricePerQuantity", Integer.parseInt(record.get("PricePerQuantity")));
                String timeOfTheDayStr = record.get("TimeOfTheDay");
                if (!timeOfTheDayStr.isEmpty()) {
                    String[] timeComponents = timeOfTheDayStr.split(":");
                    int hour = Integer.parseInt(timeComponents[0]);
                    int minute = Integer.parseInt(timeComponents[1]);
                    int second = Integer.parseInt(timeComponents[2]);
                    LocalTime timeOfTheDay = LocalTime.of(hour, minute, second);
                    purchase.put("TimeOfTheDay", timeOfTheDay);
                    dayThreePurchaseHistory.add(purchase);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read day four purchase history
        try (Reader reader = new FileReader(String.valueOf(purchaseHistory4FilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                Map<String, Object> purchase = new HashMap<>();
                purchase.put("PurchaseHistoryId", Integer.parseInt(record.get("PurchaseHistoryId")));
                purchase.put("ProductId", Integer.parseInt(record.get("ProductId")));
                purchase.put("Quantity", Integer.parseInt(record.get("Quantity")));
                purchase.put("PricePerQuantity", Integer.parseInt(record.get("PricePerQuantity")));
                // if the time is null set a local variable to null
                String timeOfTheDayStr = record.get("TimeOfTheDay");
                if (!timeOfTheDayStr.isEmpty()) {
                    String[] timeComponents = timeOfTheDayStr.split(":");
                    int hour = Integer.parseInt(timeComponents[0]);
                    int minute = Integer.parseInt(timeComponents[1]);
                    int second = Integer.parseInt(timeComponents[2]);
                    LocalTime timeOfTheDay = LocalTime.of(hour, minute, second);
                    purchase.put("TimeOfTheDay", timeOfTheDay);
                    dayFourPurchaseHistory.add(purchase);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // calculate roi for all products.

        List<Map<String, Object>> allPurchaseHistory = new ArrayList<>();
        for (Map<String, Object> purchase : dayOnePurchaseHistory) {
            purchase.put("Day", 1);
            allPurchaseHistory.add(purchase);
        }
        for (Map<String, Object> purchase : dayTwoPurchaseHistory) {
            purchase.put("Day", 2);
            allPurchaseHistory.add(purchase);
        }
        for (Map<String, Object> purchase : dayThreePurchaseHistory) {
            purchase.put("Day", 3);
            allPurchaseHistory.add(purchase);
        }
        for (Map<String, Object> purchase : dayFourPurchaseHistory) {
            purchase.put("Day", 4);
            allPurchaseHistory.add(purchase);
        }

        // calculate total investment on the product by ietrating through all the
        // purchase history

        for (Map<String, Object> product : productList) {
            int productId = (int) product.get("ProductId");
            double totalInvestment = (double) product.get("TotalInvestment");
            double totalProfit = 0;
            double roi = 0;
            int availableQuantity = (int) product.get("AvailableQuantity");
            int Day_1_quantity = 0;
            int Day_2_quantity = 0;
            int Day_3_quantity = 0;
            int Day_4_quantity = 0;

            for (Map<String, Object> purchase : allPurchaseHistory) {
                int purchaseProductId = (int) purchase.get("ProductId");
                if (productId == purchaseProductId) {
                    int quantity = (int) purchase.get("Quantity");
                    double sellPrice = (int) product.get("SellPrice");
                    double buyPrice = (int) product.get("BuyPrice");
                    double margin = (sellPrice - buyPrice) / buyPrice * 100;
                    double profitPerQuantity = margin / 100 * buyPrice;
                    totalProfit += quantity * profitPerQuantity;
                    roi = totalProfit / totalInvestment * 100;

                    int day = (int) purchase.get("Day");
                    switch (day) {
                        case 1:
                            Day_1_quantity += quantity;
                            break;
                        case 2:
                            Day_2_quantity += quantity;
                            break;
                        case 3:
                            Day_3_quantity += quantity;
                            break;
                        case 4:
                            Day_4_quantity += quantity;
                            break;
                        default:
                            break;
                    }
                }
            }
            // now claculate day sale rates
            double Day_1_SaleRate = Math.ceil(Day_1_quantity / (double) availableQuantity * 100);
            double Day_2_SaleRate = Math.ceil(Day_2_quantity / (double) availableQuantity * 100);
            double Day_3_SaleRate = Math.ceil(Day_3_quantity / (double) availableQuantity * 100);
            double Day_4_SaleRate = Math.ceil(Day_4_quantity / (double) availableQuantity * 100);


            product.put("TotalInvestment", totalInvestment);
            product.put("TotalProfit", totalProfit);
            product.put("ROI", roi);
            product.put("Day_1_SaleRate", Day_1_SaleRate);
            product.put("Day_2_SaleRate", Day_2_SaleRate);
            product.put("Day_3_SaleRate", Day_3_SaleRate);
            product.put("Day_4_SaleRate", Day_4_SaleRate);
        }

        // find average sale rate for each product using product list only

        for (Map<String, Object> product : productList) {
            double asr = 0;
            double day_1_SaleRate = Math.ceil((double) product.get("Day_1_SaleRate") * 10 / 100);
            double day_2_SaleRate = Math.ceil((double) product.get("Day_2_SaleRate") * 20 / 100);
            double day_3_SaleRate = Math.ceil((double) product.get("Day_3_SaleRate") * 30 / 100);
            double day_4_SaleRate = Math.ceil((double) product.get("Day_4_SaleRate") * 40 / 100);

            asr = day_1_SaleRate + day_2_SaleRate + day_3_SaleRate + day_4_SaleRate;
            System.out.println( product.get("ProductId"));

            double averageSaleRate = Math.ceil(asr) / 4;
            product.put("AverageSaleRate", averageSaleRate);

            double roi = Math.ceil((Double) product.get("ROI"));
            int availableQuantity = (int) product.get("AvailableQuantity");

            // calculate the quantity to be purchased on day 5
            int day_5_QuantityPrediction = (int) Math.ceil(((roi / 10) * availableQuantity) / 100)
                    + availableQuantity;

            int day_5_QuantityPredictionASR = (int) Math
                    .ceil((Math.ceil(averageSaleRate) * availableQuantity) / 100) + availableQuantity;

            product.put("Day_5_QuantityPrediction", day_5_QuantityPrediction);
            product.put("Day_5_QuantityPredictionASR", day_5_QuantityPredictionASR);

            double final_average = Math.ceil((double) (day_5_QuantityPrediction + day_5_QuantityPredictionASR) / 2);
            product.put("Final_Average", final_average);
        }


        // sort the productList based on fnral average and product name ascending if
        // final avrrage is the same
        productList.sort((o1, o2) -> {
            double finalAverage1 = (double) o1.get("Final_Average");
            double finalAverage2 = (double) o2.get("Final_Average");
            int finalAverageCompare = Double.compare(finalAverage2,finalAverage1);
            if (finalAverageCompare != 0) {
                return finalAverageCompare;
            } else {
                String productName1 = (String) o1.get("ProductName");
                String productName2 = (String) o2.get("ProductName");
                return productName1.compareTo(productName2);
            }
        });

        ///debug to save into csv

//         write the product liat to a CSV file
//        try {
//            FileWriter writer = new FileWriter("product_list.csv");
//            writer.write(
//                    "ProductId,ProductName,SellPrice,BuyPrice,AvailableQuantity,Margin,TotalInvestment,TotalProfit,ROI,Day_1_SaleRate,Day_2_SaleRate,Day_3_SaleRate,Day_4_SaleRate, Avgsalerate, Day_5_QuantityPrediction, Day_5_QuantityPredictionASR, Final_Average \n");
//
//            for (Map<String, Object> product : productList) {
//                int productId = (int) product.get("ProductId");
//                String productName = (String) product.get("ProductName");
//                double sellPrice = (int) product.get("SellPrice");
//                double buyPrice = (int) product.get("BuyPrice");
//                int availableQuantity = (int) product.get("AvailableQuantity");
//                double margin = (double) product.get("Margin");
//                double totalInvestment = (double) product.get("TotalInvestment");
//                double totalProfit = (double) product.get("TotalProfit");
//                double roi = (double) product.get("ROI");
//                double Day_1_SaleRate = (double) product.get("Day_1_SaleRate");
//                double Day_2_SaleRate = (double) product.get("Day_2_SaleRate");
//                double Day_3_SaleRate = (double) product.get("Day_3_SaleRate");
//                double Day_4_SaleRate = (double) product.get("Day_4_SaleRate");
//                double AverageSaleRate = (double) product.get("AverageSaleRate");
//                int Day_5_QuantityPrediction = (int) product.get("Day_5_QuantityPrediction");
//                int Day_5_QuantityPredictionASR = (int) product.get("Day_5_QuantityPredictionASR");
//                double Final_Average = (double) product.get("Final_Average");
//
//                writer.write(productId + "," + productName + "," + sellPrice + "," + buyPrice + ","
//                        + availableQuantity + "," + margin + "," + totalInvestment + "," + totalProfit + "," + roi
//                        + "," + Day_1_SaleRate + "," + Day_2_SaleRate + "," + Day_3_SaleRate + "," + Day_4_SaleRate
//                        + "," + AverageSaleRate + "," +
//                        Day_5_QuantityPrediction + "," + Day_5_QuantityPredictionASR + "," + Final_Average + "\n");
//            }
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//         debug save to csv file to check results
//        try (PrintWriter writer = new PrintWriter(new File("purchase_history.csv"))) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("PurchaseHistoryId,ProductId,Quantity,PricePerQuantity,TimeOfTheDay\n");
//
//            for (Map<String, Object> purchase : allPurchaseHistory) {
//                sb.append(purchase.get("PurchaseHistoryId")).append(",");
//                sb.append(purchase.get("ProductId")).append(",");
//                sb.append(purchase.get("Quantity")).append(",");
//                sb.append(purchase.get("PricePerQuantity")).append(",");
//                sb.append(purchase.get("TimeOfTheDay")).append("\n");
//            }
//
//            writer.write(sb.toString());
//            System.out.println("Purchase history written to purchase_history.csv");
//        } catch (FileNotFoundException e) {
//            System.err.println("Failed to write purchase history to file: " + e.getMessage());
//        }

        // create a PredictedWarehouseInfo object
        PredictedWarehouseInfo predictedWarehouseInfo = new PredictedWarehouseInfo();

        // create a list of ProductInfo objects
        List<PredictedProductInfo> productInfoList = new ArrayList<>();

        double totalWareHouseCapacity = 0;


        // fill this list using productslist
        for (Map<String, Object> product : productList) {
            PredictedProductInfo predictedProductInfo = new PredictedProductInfo();
            predictedProductInfo.setProductId((int) product.get("ProductId"));
            predictedProductInfo.setProductName((String) product.get("ProductName"));
            long current_capacity = (long) Math.ceil((Double) product.get("Final_Average"));
            totalWareHouseCapacity += current_capacity;
            predictedProductInfo.setPredictedQuantity(current_capacity);
            productInfoList.add(predictedProductInfo);
        }
        predictedWarehouseInfo.setProductList(productInfoList);

        // set the warehouse capacity to the PredictedWarehouseInfo object
        predictedWarehouseInfo.setWarehouseCapacity((long) totalWareHouseCapacity);

        // return the PredictedWarehouseInfo object
        return predictedWarehouseInfo;
    }
}
