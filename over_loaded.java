public class over_loaded {
    public static void main(String[] args) {
        // overloaded method = method that share the same name but have different
        // parameter
        // method name + parameter = method signature

        int x = 10;
        int y = 20;
        int z = 30;

        System.out.println(add(x, y));
        System.out.println(add(x, y, z));
    }

    static int add(int a, int b) {
        return a + b;
    }

    static int add(int a, int b, int c) {
        return a + b + c;
    }
}
