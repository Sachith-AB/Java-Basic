import java.util.ArrayList;

public class for_each {
    public static void main(String args[]) {
        // String[] animals = { "cat", "dog", "shark", "person" };
        ArrayList<String> animals = new ArrayList<>();

        animals.add("cat");
        animals.add("dog");
        animals.add("rat");
        animals.add("shark");

        for (String i : animals) {
            System.out.println(i);
        }
    }
}
