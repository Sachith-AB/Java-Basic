public class object_passing {
    public static void main(String[] args) {
        garage Garage = new garage("garage");

        employee Employee = new employee(Garage, "sachith");
    }
}
