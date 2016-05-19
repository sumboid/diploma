package sample.model.report;

import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.Parameters;
import sample.model.algorithm.data.PheromonMatrix;
import sample.model.problem.Problem;

import java.io.Serializable;
import java.util.ArrayList;

public class Report implements Serializable {
    Problem problem;
    int lifeCycle;              //время жизни(количество итераций)
    ArrayList<Double> grafik=new ArrayList<>();// лист с точками графика которые должны отрисовываться
    ArrayList<Integer> bestRouteRun =new ArrayList<>();// лучший маршрут
    double bestLength;
    Parameters parameters;

    long time = 0;

    public Report(Problem problem, Parameters params, ArrayList<Integer> bestRouteRun, double bestLength, ArrayList<Double> grafik) {
        this.problem = problem;
        this.grafik= grafik;
        this.bestRouteRun=bestRouteRun;
        this.bestLength=bestLength;
        this.parameters = params;
    }
    public void setpoint(Double point){
        this.grafik.add(point);

    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public Problem getProblem(){return problem;}

    public  void setProblem(Problem problem){this.problem=problem;}

    public int getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(int lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public ArrayList<Double> getGrafik() {
        return grafik;
    }

    public void setGrafik(ArrayList<Double> grafik) {
        this.grafik = grafik;
    }

    public ArrayList<Integer> getBestRouteRun() {
        return bestRouteRun;
    }

    public void setBestRouteRun(ArrayList<Integer> bestRouteRun) {
        this.bestRouteRun = bestRouteRun;
    }

    public double getBestLength() {
        return bestLength;
    }

    public void setBestLength(int bestLength) {
        this.bestLength = bestLength;
    }

    public void setBestLength(double bestLength) {
        this.bestLength = bestLength;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
