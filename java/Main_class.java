public class Main_class {
    public static void main(String[] args) {
        car Car = new car();
        bicycle bicycle = new bicycle();

        vehicle[] racers = { Car, bicycle };
        for (vehicle x : racers) {
            x.go();
        }

    }
}
