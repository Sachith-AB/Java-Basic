public class array {
    public static void main(String[] args) {
        String[] cars = { "benze", "BMW" };

        cars[1] = "mustang";

        System.out.println(cars[0]);

        for (int i = 0; i < cars.length; i++) {
            System.out.print(cars[i] + " ");
        }
    }
}
