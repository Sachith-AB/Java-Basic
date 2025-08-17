import java.util.ArrayList;

public class arrayList_twoD {
    public static void main(String args[]) {

        ArrayList<ArrayList<String>> groceryList = new ArrayList<>();

        ArrayList<String> bakeryList = new ArrayList<>();
        bakeryList.add("pasta");
        bakeryList.add("garlic bread");
        bakeryList.add("donuts");

        ArrayList<String> productList = new ArrayList<>();
        productList.add("tomatoes");
        productList.add("garlic");
        productList.add("peper");

        ArrayList<String> drinkList = new ArrayList<>();
        drinkList.add("faluda");
        drinkList.add("milkshake");
        drinkList.add("saruwath");

        groceryList.add(bakeryList);
        groceryList.add(productList);
        groceryList.add(drinkList);

        System.out.println(groceryList.get(0).get(2));
    }
}