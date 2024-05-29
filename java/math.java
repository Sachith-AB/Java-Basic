import java.util.Scanner;

public class math {

    static public void main(String[] args) {

        double x = 3.14;
        double y = -10;

        double a = Math.max(x, y); // find max value
        double b = Math.sqrt(x); // find square value
        double c = Math.round(x); // give intger value clotest
        double d = Math.floor(x); // rounded down
        double e = Math.floor(x); // rounded up

        double p, q, r;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter side x: ");
        p = scanner.nextDouble();
        System.out.print("Enter side y: ");
        q = scanner.nextDouble();

        r = Math.sqrt((p * p) + (q * q));

        System.out.println("The hypotenuse is: " + r);
        scanner.close(); // good practice
    }
}
