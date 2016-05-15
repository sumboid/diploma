package sample.controller.problem.internal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PathLength {
    private SimpleIntegerProperty a;
    private SimpleIntegerProperty b;
    private SimpleDoubleProperty length;

    public PathLength(int a, int b, double length) {
        this.setA(a);
        this.setB(b);
        this.setLength(length);
    }

    public int getA() {
        return a.get();
    }

    public void setA(int a) {
        this.a = new SimpleIntegerProperty(a);
    }

    public int getB() {
        return b.get();
    }

    public void setB(int b) {
        this.b = new SimpleIntegerProperty(b);
    }

    public double getLength() {
        return length.get();
    }

    public void setLength(double length) {
        this.length = new SimpleDoubleProperty(length);
    }
}
