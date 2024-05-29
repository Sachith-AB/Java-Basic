public class two_array {
    public static void main(String[] args) {
        String[][] cars = new String[3][3];

        String[][] cars_2 = { { "camro", "bense", "audi" },
                { "Audi", "ranger", "ferari" },
                { "ducati", "mitsubishi", "nissan" }
        };

        cars[0][0] = "camro";
        cars[0][1] = "BMW";
        cars[0][2] = "bense";
        cars[1][0] = "Audi";
        cars[1][1] = "ranger";
        cars[1][2] = "ferari";
        cars[2][0] = "ducati";
        cars[2][1] = "mitsubishi";
        cars[2][2] = "nissan";

        System.out.println(cars[1][1]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(cars[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(cars_2[i][j] + " ");
            }
            System.out.println();
        }
    }
}
