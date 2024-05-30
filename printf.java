public class printf {
    public static void main(String[] args) {
        int a = 10000000;
        int b = -1000000;

        System.out.printf("this value of a = %,d", a);

        boolean value = true;
        char character = 'a';
        String name = "sachith";
        double floating = 12.34;

        // System.out.printf("It is %b", value);
        // System.out.printf("My name is %s", name);
        // System.out.printf("My name is %10s", name); // right justify
        // System.out.printf("My name is %-3s", name); // left justify
        // System.out.printf("Character is %c", character);
        // System.out.printf("float is %.2f", floating);
        // System.out.printf("float is %+.2f", floating);
        // System.out.printf("float is %020d", b);
    }
}
