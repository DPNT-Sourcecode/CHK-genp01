package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CheckoutSolution {

    private static final Map<Character,Integer> prices = new HashMap<>();
    private static final Map<Character,List<SpecialOffer>> specialOffers = new HashMap<>();

    static {
        loadPriceTableAndOffersFromFile("src/main/java/test/Items.txt");
    }

    private static void loadPriceTableAndOffersFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Item") || line.contains("+") ) {
                    continue; // Skip header and separator lines
                }
                String[] columns = line.split("\\|");
                char item = columns[1].trim().charAt(0);
                int price = Integer.parseInt(columns[2].trim());
                prices.put(item, price);

                String[] offers = columns[3].trim().split(",");

                List<SpecialOffer> offerList = new ArrayList<>();
                for (String offer : offers) {
                    String[] offerParts = offer.trim().split(" ");
                    if (offerParts.length == 3) {
                        int offerQuantity = Integer.parseInt(String.valueOf(offerParts[0].toCharArray()[0]));
                        int offerPrice = Integer.parseInt(offerParts[2]);
                        char freeItem = ' ';
                        offerList.add(new SpecialOffer(offerQuantity, offerPrice, freeItem));
                    }
                    if (offerParts.length == 5) {
                        int offerQuantity = Integer.parseInt(String.valueOf(offerParts[0].charAt(0)));
                        int offerPrice = 0;
                        char freeItem =  offerParts[3].charAt(0);
                        if(freeItem == item){
                            offerPrice = offerQuantity * prices.get(item);
                            offerQuantity++;
                        }
                        offerList.add(new SpecialOffer(offerQuantity, offerPrice, freeItem));
                    }
                }
                specialOffers.put(item, offerList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Integer checkout(String skus) {
        if (skus==null || !isInputValid(skus)){
            return -1;
        }

        Map<Character,Integer> skuCount = new HashMap<>();
        for(char sku : skus.toCharArray()){
            skuCount.put(sku ,skuCount.getOrDefault(sku,0) + 1);
        }

        applyEFreeItem(skuCount);

        int total = 0;
        for(Map.Entry<Character,Integer> entry : skuCount.entrySet()){
            char item = entry.getKey();
            int quantity = entry.getValue();
            int price = prices.get(item);

            List<SpecialOffer> specialOfferList = specialOffers.get(item);
            if (specialOfferList!=null) {
                for(SpecialOffer offer : specialOfferList){
                    Result countTotal = calculateSpecialOfferTotal(quantity,item,offer);
                    total += countTotal.total();
                    quantity=countTotal.quantity();
                }
                total += quantity * prices.get(item);
            }
            else {
                total+= quantity*price;
            }
        }

        return total;
    }

    private Boolean isInputValid(String skus){
        for(char sku : skus.toCharArray()) {
            if(!prices.containsKey(sku)) {
                return false;
            }
        }
        return true;
    }

    private Result calculateSpecialOfferTotal(int count,char sku, SpecialOffer specialOffer){
        if(sku == 'E'){
            return new Result(0,count*prices.get('E'));
        }

        if(count>=specialOffer.quantity()){
            int specialOfferQty = specialOffer.quantity();
            int specialOfferPrice = specialOffer.price();
            int specialOfferCount = count / specialOfferQty;
            int remaingItems = count % specialOfferQty;

            return new Result(remaingItems,specialOfferCount*specialOfferPrice);
        }
        return new Result(count,0);
    }


    private void applyEFreeItem(Map<Character,Integer> skuCount){
        int Ecount = skuCount.getOrDefault('E',0);
        int freeB = Ecount / 2;
        if(skuCount.containsKey('B')){
            skuCount.put('B',skuCount.get('B')-freeB);
        }
    }

}
