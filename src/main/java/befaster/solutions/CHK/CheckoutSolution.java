package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.*;

public class CheckoutSolution {

    private static final Map<Character,Integer> prices = new HashMap<>();
    private static final Map<Character,List<SpecialOffer>> specialOffers = new HashMap<>();

    static {
        //initiate prices
        prices.put('A',50);
        prices.put('B',30);
        prices.put('C',20);
        prices.put('D',15);
        prices.put('E',40);

        //put specialoffers
        // Initialize special offers
        List<SpecialOffer> offersA = new ArrayList<>();
        offersA.add(new SpecialOffer(3, 130,' '));
        offersA.add(new SpecialOffer(5, 200,' '));
        specialOffers.put('A', offersA);

        List<SpecialOffer> offersB = new ArrayList<>();
        offersB.add(new SpecialOffer(2, 45,' '));
        specialOffers.put('B', offersB);

        List<SpecialOffer> offersE = new ArrayList<>();
        offersE.add(new SpecialOffer(2, 0, 'B'));
        specialOffers.put('E', offersE);
    }


    public Integer checkout(String skus) {
        if (skus==null || !isInputValid(skus)){
            return -1;
        }

        Map<Character,Integer> skuCount = new HashMap<>();
        for(char sku : skus.toCharArray()){
            skuCount.put(sku ,skuCount.getOrDefault(sku,0) + 1);
        }

        applyFreeItem(skuCount);


        int total = 0;
        for(Map.Entry<Character,Integer> entry : skuCount.entrySet()){
            char item = entry.getKey();
            int quantity = entry.getValue();
            int price = prices.get(item);

            List<SpecialOffer> specialOfferList = specialOffers.get(item);
            if (specialOfferList!=null) {
                SpecialOffer bestDeal = specialOfferList.get(0);
                for(SpecialOffer offer : specialOfferList){
                    if( offer.quantity() <= quantity && offer.quantity() > bestDeal.quantity()){
                        bestDeal = offer;
                    }
                }
                total += calculateSpecialOfferTotal(quantity,item,bestDeal);
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

    private Integer calculateSpecialOfferTotal(int count,char sku, SpecialOffer specialOffer){
        if(specialOffer.freeItem() != ' '){
            return  count * prices.get(sku);
        }

        int specialOfferQty = specialOffer.quantity();
        int specialOfferPrice = specialOffer.price();
        int specialOfferCount = count / specialOfferQty;
        int remaingItems = count % specialOfferQty;

        return (specialOfferCount * specialOfferPrice) + (remaingItems * prices.get(sku));
    }

    private void applyFreeItem(Map<Character,Integer> skuCount){
        int Ecount = skuCount.getOrDefault('E',0);
        int freeB = Ecount / 2;
        if(skuCount.containsKey('B')){
            skuCount.put('B',skuCount.get('B')-freeB);
        }
    }

}




