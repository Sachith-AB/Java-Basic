import java.util.Scanner;

public class nested_loop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows;
        int columns;
        String sysmbols;

        System.out.println("Enter nubert of rows");
        rows = scanner.nextInt();
        System.out.println("Enter nubert of columns");
        columns = scanner.nextInt();
        System.out.println("Enter symbol you use");
        sysmbols = scanner.next();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(sysmbols);
            }
            System.out.println();
        }
    }
}
