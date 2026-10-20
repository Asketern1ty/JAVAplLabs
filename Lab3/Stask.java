import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

// --- Model ---

interface Drawable {
    void draw();
}

abstract class Shape implements Drawable {
    protected String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public abstract double calcArea();

    public String getShapeColor() {
        return shapeColor;
    }

    @Override
    public String toString() {
        return "Shape color: " + shapeColor;
    }
}

class Rectangle extends Shape {
    private double width, height;

    public Rectangle(String shapeColor, double width, double height) {
        super(shapeColor);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }

    @Override
    public String toString() {
        return String.format("Rectangle [Color=%s, Width=%.2f, Height=%.2f, Area=%.2f]", 
                shapeColor, width, height, calcArea());
    }
}

class Triangle extends Shape {
    private double base, height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Triangle");
    }

    @Override
    public String toString() {
        return String.format("Triangle [Color=%s, Base=%.2f, Height=%.2f, Area=%.2f]", 
                shapeColor, base, height, calcArea());
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(String shapeColor, double radius) {
        super(shapeColor);
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }

    @Override
    public String toString() {
        return String.format("Circle [Color=%s, Radius=%.2f, Area=%.2f]", 
                shapeColor, radius, calcArea());
    }
}

// --- MVC: VIEW ---

class ShapeView {
    public void displayShapes(Shape[] shapes) {
        System.out.println("--- List of figures ---");
        for (Shape shape : shapes) {
            System.out.println(shape.toString());
        }
    }

    public void displayTotalArea(double totalArea) {
        System.out.printf("Total area of all shapes: %.2f\n", totalArea);
    }

    public void displayTotalAreaByType(String type, double totalArea) {
        System.out.printf("Total area of figures of the type %s: %.2f\n", type, totalArea);
    }
    
    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }
}

// --- MVC: CONTROLLER ---

class ShapeController {
    private Shape[] shapes;
    private ShapeView view;

    public ShapeController(ShapeView view, int size) {
        this.view = view;
        this.shapes = generateShapes(size);
    }

    private Shape[] generateShapes(int size) {
        Shape[] generatedShapes = new Shape[size];
        String[] colors = {"Red", "Green", "Blue", "Yellow", "Black"};
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            String color = colors[random.nextInt(colors.length)];
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    generatedShapes[i] = new Rectangle(color, random.nextDouble() * 10 + 1, random.nextDouble() * 10 + 1);
                    break;
                case 1:
                    generatedShapes[i] = new Triangle(color, random.nextDouble() * 10 + 1, random.nextDouble() * 10 + 1);
                    break;
                case 2:
                    generatedShapes[i] = new Circle(color, random.nextDouble() * 10 + 1);
                    break;
            }
        }
        return generatedShapes;
    }

    public void showAllShapes() {
        view.displayShapes(shapes);
    }

    public void calculateTotalArea() {
        double sum = 0;
        for (Shape shape : shapes) {
            sum += shape.calcArea();
        }
        view.displayTotalArea(sum);
    }

    public void calculateTotalAreaByType(Class<?> shapeClass) {
        double sum = 0;
        for (Shape shape : shapes) {
            if (shapeClass.isInstance(shape)) {
                sum += shape.calcArea();
            }
        }
        view.displayTotalAreaByType(shapeClass.getSimpleName(), sum);
    }

    public void sortByArea() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return Double.compare(s1.calcArea(), s2.calcArea());
            }
        });
        view.displayMessage("--- The figures are sorted by area ---");
        showAllShapes();
    }

    public void sortByColor() {
        Arrays.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape s1, Shape s2) {
                return s1.getShapeColor().compareToIgnoreCase(s2.getShapeColor());
            }
        });
        view.displayMessage("--- The figures are sorted by color---");
        showAllShapes();
    }
}

// --- MAIN ---

public class Stask {
    public static void main(String[] args) {
        ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController(view, 10); // Массив из 10 элементов

        controller.showAllShapes();
        System.out.println();
        
        controller.calculateTotalArea();
        controller.calculateTotalAreaByType(Circle.class);
        
        controller.sortByArea();
        controller.sortByColor();
    }
}