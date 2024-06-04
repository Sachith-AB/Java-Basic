public class pizza {
    String bread;
    String souce;
    String cheese;
    String topping;
    String chiken;

    pizza(String bread, String souce, String cheese, String topping) {
        this.bread = bread;
        this.souce = souce;
        this.cheese = cheese;
        this.topping = topping;
    }

    pizza(String bread, String chiken, String topping) {
        this.bread = bread;
        this.chiken = chiken;
        this.topping = topping;
    }
}
