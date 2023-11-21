package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character,Integer> prices = new HashMap<>();
    private static final Map<Character,SpecialOffer> specialOffers = new HashMap<>();

    static {
        
    }


    public Integer checkout(String skus) {
        System.out.println(skus);
        List<String> skuList = Arrays.stream(skus.split(",")).toList();
        return skuList.stream().mapToInt(p -> getProductPrice(skus)).sum();
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



