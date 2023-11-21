package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.Arrays;
import java.util.List;

public class CheckoutSolution {
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


