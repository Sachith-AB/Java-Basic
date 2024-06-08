public class car extends vehicle {
    String make = "BMW company";
    String model = "BMW";
    int year = 2020;
    String color = "blue";
    double price = 20000.00;

    void drive() {
        System.out.println("car is being driving by me");
    }

    void brake() {
        System.out.println("car is being break by me");
    }

    public void go() {
        System.out.println("this is vehicle speed");
    }

    public String toString() {
        return make + "\n" + model + "\n" + color + "\n" + year;
    }

}