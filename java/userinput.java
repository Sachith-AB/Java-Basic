import java.util.Scanner;

public class userinput {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("what is your name:");
            String name = scanner.nextLine();
            System.out.print("How old are your:");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("hello " + name + " " + age);
        }

    }
}
