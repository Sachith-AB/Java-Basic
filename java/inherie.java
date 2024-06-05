public class inherie {
    public static void main(String[] args) {
        vehicle car = new vehicle();

        System.out.println(car.speed);

        bicycle Bicycle = new bicycle();

        System.out.println(Bicycle.speed);
        car.go();
        car.stop();
        Bicycle.stop();
    }
}
