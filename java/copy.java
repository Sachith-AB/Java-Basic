public class copy {
    public static void main(String[] args) {
        Dog dog1 = new Dog();
        dog1.name = "ishan";

        Dog dog2 = new Dog();
        dog2.name = "lakshitha";

        System.out.println(dog1.name + " " + dog2.name);

        dog2 = dog1;

        System.out.println(dog1.name + " " + dog2.name);

    }
}
