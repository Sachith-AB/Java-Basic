public class array_object {
    public static void main(String[] args) {

        food[] Food = new food[3];

        food food1 = new food("pizza");
        food food2 = new food("kottu");

        Food[0] = food1;
        Food[1] = food2;
        Food[2] = food1;

        System.out.println(Food[0].name);

        for (int i = 0; i < Food.length; i++) {
            System.out.println(Food[i].name);
        }
    }
}
