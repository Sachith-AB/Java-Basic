public class expression {
    public static void main(String[] args) {
        int a = 10;

        a = a + 1;
        a = a + 1;
        a = a + 1;
        a = a + 1;
        System.out.println(a);
        int x = a++;
        int y = a--;

        System.out.println(x);
        System.out.println(y);
    }
}
