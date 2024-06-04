public class overloaded {
    public static void main(String[] aStrings) {
        pizza pizza1 = new pizza("this cruse", "tomato", "peparoni", "topping");
        pizza pizza2 = new pizza("bread", "Roast", "topping");
        System.out.println("Here are this ibgredients of your pizza");
        System.out.println(pizza1.bread);
        System.out.println(pizza1.souce);
        System.out.println(pizza1.cheese);
        System.out.println(pizza2.topping);
        System.out.println(pizza2.chiken);
    }
}
