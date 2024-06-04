public class Human {

    String name;
    int age;
    double weight;

    Human(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    void eat() {
        System.out.println(this.name + ' ' + "is eateing ");
    }
}

// local variables = decalerd inside a method visible only to that method
// global variables = visible to all parts of a class
