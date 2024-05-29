public class switch_statement {
    public static void main(String[] args) {
        String day = "Sunday";

        switch (day) {
            case "Sunday":
                System.out.println("Sunday");
                break;
            case "Monday":
                System.out.println("monday");
                break;

            case "Wendeseday":
                System.out.println("Wendesday");
                break;

            default:
                System.out.println("Friday");
                break;
        }
    }
}
