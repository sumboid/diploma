package sample.controller.report.internal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.model.algorithm.data.Parameters;
import sample.model.problem.Problem;
import sample.model.report.Report;

public class ReportView {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty size = new SimpleIntegerProperty();
    private SimpleIntegerProperty antsNumber = new SimpleIntegerProperty();
    private SimpleDoubleProperty length = new SimpleDoubleProperty();
    private SimpleIntegerProperty startNode = new SimpleIntegerProperty();
    private SimpleDoubleProperty al = new SimpleDoubleProperty();
    private SimpleDoubleProperty b = new SimpleDoubleProperty();
    private SimpleDoubleProperty q = new SimpleDoubleProperty();
    private SimpleDoubleProperty p = new SimpleDoubleProperty();
    private SimpleIntegerProperty iterationsNumber = new SimpleIntegerProperty();
    private SimpleIntegerProperty eliteAntsNumber = new SimpleIntegerProperty();

    public ReportView(Report report) {
        Problem problem = report.getProblem();
        Parameters params = report.getParameters();

        System.out.println(problem.getProblemName());
        setName(problem.getProblemName());
        setAntsNumber(report.getParameters().ANTS_NUMBER);
        setSize(problem.getDMatrix().n);
        setLength(report.getBestLength());
        setStartNode(params.endWhile);
        setAl(params.al);
        setB(params.b);
        setQ(params.q);
        setP(params.p);
        setIterationsNumber(params.lifeСycle);
        setEliteAntsNumber(params.eliteNumberAnt);
    }

    public int getEliteAntsNumber() {
        return eliteAntsNumber.get();
    }

    public SimpleIntegerProperty eliteAntsNumberProperty() {
        return eliteAntsNumber;
    }

    public void setEliteAntsNumber(int eliteAntsNumber) {
        this.eliteAntsNumber.set(eliteAntsNumber);
    }

    public int getAntsNumber() {
        return antsNumber.get();
    }

    public SimpleIntegerProperty antsNumberProperty() {
        return antsNumber;
    }

    public void setAntsNumber(int antsNumber) {
        this.antsNumber.set(antsNumber);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getSize() {
        return size.get();
    }

    public SimpleIntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public double getLength() {
        return length.get();
    }

    public SimpleDoubleProperty lengthProperty() {
        return length;
    }

    public void setLength(double length) {
        this.length.set(length);
    }

    public int getStartNode() {
        return startNode.get();
    }

    public SimpleIntegerProperty startNodeProperty() {
        return startNode;
    }

    public void setStartNode(int startNode) {
        this.startNode.set(startNode);
    }

    public double getAl() {
        return al.get();
    }

    public SimpleDoubleProperty alProperty() {
        return al;
    }

    public void setAl(double al) {
        this.al.set(al);
    }

    public double getB() {
        return b.get();
    }

    public SimpleDoubleProperty bProperty() {
        return b;
    }

    public void setB(double b) {
        this.b.set(b);
    }

    public double getQ() {
        return q.get();
    }

    public SimpleDoubleProperty qProperty() {
        return q;
    }

    public void setQ(double q) {
        this.q.set(q);
    }

    public double getP() {
        return p.get();
    }

    public SimpleDoubleProperty pProperty() {
        return p;
    }

    public void setP(double p) {
        this.p.set(p);
    }

    public int getIterationsNumber() {
        return iterationsNumber.get();
    }

    public SimpleIntegerProperty iterationsNumberProperty() {
        return iterationsNumber;
    }

    public void setIterationsNumber(int iterationsNumber) {
        this.iterationsNumber.set(iterationsNumber);
    }
}
