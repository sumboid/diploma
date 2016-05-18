package sample.controller.problem.internal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PathLength {
    private SimpleIntegerProperty a;
    private SimpleIntegerProperty b;
    private SimpleStringProperty length;

    public PathLength(int a, int b, String length) {
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

    public String getLength() {
        return length.get();
    }

    public void setLength(String length) {
        this.length = new SimpleStringProperty(length);
    }
}
