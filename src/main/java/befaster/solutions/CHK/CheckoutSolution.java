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
        for(Map.Entry(Character,Integer) entry : )
        skuCount.forEach((k,v) -> {
            char item = k;
            int quantity = v;
            int price = prices.get(item);

            SpecialOffer specialOffer = specialOffers.get(item);
            if (specialOffer!=null) {
                total += 0;
            }

        });

        List<String> skuList = Arrays.stream(skus.split(",")).toList();
        return skuList.stream().mapToInt(p -> getProductPrice(skus)).sum();
    }

    private Boolean isInputValid(String skus){
        for(char sku : skus.toCharArray()) {
            if(!prices.containsKey(sku)) {
                return false;
            }
        }
        return true;
    }

    private Integer getProductPrice(String sku){
        switch (sku){
            case "A":
                return 50;
            case "B":
                return 30;
            case "C":
                return 20;
            case "D":
                return 15;
            case "3A":
                return 130;
            case "2B":
                return 45;
            default:
                return -1;
        }
    }


}


