import java.util.Scanner;

public class logical_operator {
    public static void main(String[] args) {
        int temp = 25;

        if (temp > 30) {
            System.out.println("Hot day");
        } else if (temp >= 20 && temp <= 30) { // and operator
            System.out.println("Normal day");
        } else {
            System.out.println("cold day");
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("You are plaing a game! press q or Q to quit");
        String response = scanner.nextLine();

        if (response.equals("q") || response.equals("Q")) {
            System.out.println("You are quit");
        } else {
            System.out.println("Still play");
        }
    }
}
