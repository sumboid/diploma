package sample.model.report;

import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.Parameters;
import sample.model.algorithm.data.PheromonMatrix;
import sample.model.problem.Problem;

import java.util.ArrayList;

public class Report {
    Problem problem;
    int lifeCycle;              //время жизни(количество итераций)
    ArrayList<Double> grafik=new ArrayList<>();// лист с точками графика которые должны отрисовываться
    ArrayList<Integer> bestRouteRun =new ArrayList<>();// лучший маршрут
    int bestLength;

    public Report(Problem problem,int lifeCycle,ArrayList<Integer> bestRouteRun,int bestLength) {
        this.problem = problem;
        this.lifeCycle = lifeCycle;
        this.bestRouteRun=bestRouteRun;
        this.bestLength=bestLength;
    }
    public void setpoint(Double point){
        this.grafik.add(point);

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

    public int getBestLength() {
        return bestLength;
    }

    public void setBestLength(int bestLength) {
        this.bestLength = bestLength;
    }




}
