class Student {
    int rollNumber;
    String name;
    float percentage;

    Student() {
        rollNumber = 0;
        name = "Unknown";
        percentage = 100.0f;
    }

    Student(int rollNumber, String name, float percentage) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.percentage = percentage;
    }

    void display() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Name: " + name);
        System.out.println("Percentage: " + percentage);
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student(1, "Hello", 99.99f);
        Student s2 = new Student();
        System.out.println("Details of Student 1:");
        s1.display();
        System.out.println("\nDetails of Student 2:");
        s2.display();
    }
}