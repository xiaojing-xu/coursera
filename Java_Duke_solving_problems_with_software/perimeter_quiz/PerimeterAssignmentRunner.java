import com.sun.xml.internal.xsom.impl.scd.Iterators;
import edu.duke.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerimeterAssignmentRunner {
    public int getNumPoints (Shape s) {
        // Put code here
        int n = 0;
        Iterable<Point> points = s.getPoints();
        for (Point p: points ){
            n += 1;
        }
        return n;
    }

    // add code in the method testPerimeter to call getNumPoints and to print out the results
    public void testPerimeter(){
        FileResource  fileResource = new FileResource("datatest4.txt");
        Shape s = new Shape(fileResource);

        int n = getNumPoints(s);
        System.out.println("getNumPoints is " + n);

        double a = getAverageLength(s);
        System.out.println("average length is " + a);

        double b = getLargestSide(s);
        System.out.println("largest length is " + b);

        double c = getLargestX(s);
        System.out.println("largest X is " + c);

        double d = getPerimeter(s);
        System.out.println("Perimeter " + d);
    }

    public double getPerimeter(Shape s){
        Iterable<Point> points = s.getPoints();

        double total_length = 0;
        Point p_pre = s.getLastPoint();
        for (Point p:points){
            while(!p.equals(p_pre)){
                total_length += p.distance(p_pre);
                p_pre = p;
            }
        }
        return total_length;
    }


    public double getAverageLength(Shape s) {
        // Put code here
        Iterable<Point> points = s.getPoints();

        double total_length = 0;
        int side_number = 0;
        Point p_pre = s.getLastPoint();
        for (Point p:points){
            while(!p.equals(p_pre)){
                total_length += p.distance(p_pre);
                side_number +=1;
                p_pre = p;
            }
        }
        return total_length/side_number;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        Iterable<Point> points = s.getPoints();

        double side_length = 0;
        Point p_pre = s.getLastPoint();
        for (Point p:points) {
            while (!p.equals(p_pre)) {
                double temp = p.distance(p_pre);
                if (temp > side_length){
                    side_length = temp;
                }
                p_pre = p;
            }
        }

        return side_length;
    }

    public double getLargestX(Shape s) {
        // Put code here
        Iterable<Point> points = s.getPoints();

        double point_x = Double.MIN_VALUE;
        for (Point p:points) {
            double temp = p.getX();
            if (temp > point_x){
                point_x = temp;
            }
        }
        return point_x;
    }


    public double getLargestPerimeterMultipleFiles() {
        // Put code here
        List<String> list = Arrays.asList("datatest1.txt","datatest2.txt", "datatest3.txt", "datatest4.txt", "datatest5.txt", "datatest6.txt");
        double perimter = 0;
        for (String string: list){
            FileResource  fileResource = new FileResource(string);
            Shape s = new Shape(fileResource);
            double temp = getPerimeter(s);
            if (temp > perimter){
                perimter = temp;
            }

        }
        System.out.println(perimter);
        return perimter;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        //List<String> list = Arrays.asList("datatest1.txt","datatest2.txt", "datatest3.txt", "datatest4.txt", "datatest5.txt", "datatest6.txt");
        List<String> list = Arrays.asList("example1.txt", "example2.txt", "example3.txt", "example4.txt");
        double perimter = 0;
        String name = null;
        for (String string: list){
            FileResource  fileResource = new FileResource(string);
            Shape s = new Shape(fileResource);
            double temp = getPerimeter(s);
            if (temp > perimter){
                perimter = temp;
                name = string;
            }
        }
        System.out.println(name);
        return name;

         // replace this code
        // return temp.getName();
    }

    public void testPerimeterMultipleFiles() {
        // Put code here
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
        pr.getLargestPerimeterMultipleFiles();
        pr.getFileWithLargestPerimeter();
    }
}
