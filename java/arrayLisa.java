import java.util.ArrayList;

public class arrayLisa {
    public static void main(String[] args) {
        ArrayList<String> food = new ArrayList<String>();

        food.add("Pizza");
        food.add("Kottu");
        food.add("Rice"); // add element

        food.set(0, "sushi"); // set element to special place
        food.add("Nasi guran");

        for (int i = 0; i < food.size(); i++) {
            System.out.println(food.get(i));
        }

        food.clear(); // remove all element

        for (int i = 0; i < food.size(); i++) {
            System.out.println(food.get(i));
        }
    }
}
