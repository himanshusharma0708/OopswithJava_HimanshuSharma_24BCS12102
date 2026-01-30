class Area {
    void area(int side) {
        int result = side * side;
        System.out.println("Area of Square: " + result);
    }
    void area(int length, int breadth) {
        int result = length * breadth;
        System.out.println("Area of Rectangle: " + result);
    }
    void area(double radius) {
        double result = 3.14 * radius * radius;
        System.out.println("Area of Circle: " + result);
    }
}

public class Main {
    public static void main(String[] args) {
        Area obj = new Area();
        obj.area(10);
        obj.area(10, 5);
        obj.area(5.5);
    }
}