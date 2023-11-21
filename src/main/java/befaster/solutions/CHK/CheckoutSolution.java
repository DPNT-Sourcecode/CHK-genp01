package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.*;

public class CheckoutSolution {

    private static final Map<Character,Integer> prices = new HashMap<>();
    private static final Map<Character,SpecialOffer> specialOffers = new HashMap<>();

    static {
        //initiate prices
        prices.put('A',50);
        prices.put('B',30);
        prices.put('C',20);
        prices.put('D',15);

        //put specialoffers
        specialOffers.put('A',new SpecialOffer(3,130));
        specialOffers.put('B',new SpecialOffer(2,45));
    }


    public Integer checkout(String skus) {
        if (skus==null || !isInputValid(skus)){
            return -1;
        }

        Map<Character,Integer> skuCount = new HashMap<>();
        for(char sku : skus.toCharArray()){
            skuCount.put(sku ,skuCount.getOrDefault(sku,0) + 1);
        }

        int total = 0;
        for(Map.Entry<Character,Integer> entry : skuCount.entrySet()){
            char item = entry.getKey();
            int quantity = entry.getValue();
            int price = prices.get(item);

            SpecialOffer specialOffer = specialOffers.get(item);
            if (specialOffer!=null) {
                total += 0;
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

    private Integer calculateSpecialOfferTotal(int count, SpecialOffer specialOffer){
        int total = 0;
        int specialOfferQty = specialOffer.quantity();
        int specialOfferPrice = specialOffer.price();
        int specialOfferCount = count / specialOfferQty;
        int remaingItems = 

        return total;
    }


}



