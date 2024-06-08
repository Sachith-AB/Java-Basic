public class animal {
    String name;
    int age;
    String model;

    animal(String name, int age, String model) {
        this.setName(name);
        this.setAge(age);
        this.setModel(model);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName(int age) {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getModel() {
        return model;
    }
}
