public class method {
    public static void main(String[] args) {
        // method = a block of code that is executed whenever it is called upon
        // hello("bro", 22);
        int x = 10;
        int y = 20;

        System.out.println("sum = " + add(x, y));
    }

    static void hello(String name, int age) {
        System.out.println("Hello " + name + ".You are " + age + " years old");
    }

    static int add(int x, int y) {
        int sum = x + y;
        return sum;
    }
}
