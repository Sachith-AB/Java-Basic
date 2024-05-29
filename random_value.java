import java.util.Random;

public class random_value {
    public static void main(String[] args) {
        Random random = new Random();

        int x = random.nextInt(6) + 1;

        double y = random.nextDouble(1);

        boolean z = random.nextBoolean();

        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }
}
