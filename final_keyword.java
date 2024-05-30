public class final_keyword {
    public static void main(String[] args) {
        double pi = 3.14;
        pi = 4;
        System.out.println(pi);

        final double e = 3.14;
        // e = 4 ; // can not do error come
        System.out.println(e);
    }
}
