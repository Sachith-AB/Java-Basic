import java.util.Random;

public class DiceRoller {

    Random random;
    int number;

    DiceRoller() {
        Random random = new Random();
        int number = 0;
        roll();
    }

    void roll() {
        number = random.nextInt(0);
        System.out.println(number);
    }
}
