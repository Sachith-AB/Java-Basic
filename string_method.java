public class string_method {
    public static void main(String[] args) {
        String name = "bro";

        boolean result1 = name.equals("Bro");
        int result2 = name.length();
        char result3 = name.charAt(0);
        int result4 = name.indexOf("r");
        boolean result5 = name.isEmpty();
        String result6 = name.toUpperCase();
        String result7 = name.trim();
        String result8 = name.replace('o', 'O');

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);
        System.out.println(result6);
        System.out.println(result7);
        System.out.println(result8);

    }
}
