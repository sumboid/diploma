package sample.model;


import javafx.application.Platform;
import javafx.concurrent.Task;
import sample.controller.runtime.RuntimeController;
import sample.model.algorithm.data.*;
import sample.model.problem.Problem;
import sample.model.report.Report;

import java.util.ArrayList;

public class Model {
    private RuntimeController controller;
    private Task task;

    public void setController(RuntimeController c) {
        controller = c;
    }

    public void start(Problem problem, Parameters params) {

        task = new Task<Integer>() {
            @Override
            public Integer call() {
                long time =0,dTime=0;
               // String FILE_DMATRIX=fileDmatrix;
               // String FILE_PHER_MATRIX=filePherMatrix;
                Cover cover=new Cover();//объект для обертки в методе Ants.move
              //  DistanzMatrix dmatrix =DistanzMatrix.buildDMatrixFromFile(FILE_DMATRIX);//создаем матрицу смехности
               // PheromonMatrix phMatrix = PheromonMatrix.buildDMatrixFromFile(FILE_PHER_MATRIX);//создаем матрицу феромонов
                DeltaPherMatrix deltaPherMatrix=new DeltaPherMatrix(problem.getDMatrix().n);//создаем матрицу измененения феромонов
               // dmatrix.distanzMatrixShow();
                ArrayList<Double> grafik=new ArrayList<Double>();//для сейва инфы
                ArrayList<Integer> bestRouteRun =new ArrayList<>();
                Double bestLength;                                   //конец сейва


                Ants[] ant=new Ants[params.ANTS_NUMBER];
                Double topLength[]=new Double[params.lifeСycle];
                for(int j = 0; j< params.lifeСycle; j++){
                    topLength[j]= -2.0;
                }
                for(int j = 0; j< params.ANTS_NUMBER; j++){
                    ant[j]= new Ants(problem.getDMatrix().n);
                }
                Double topLengthlocal=Double.POSITIVE_INFINITY;
                int topAnt=-2;
                ArrayList<Integer> bestRoute =new ArrayList<>();
                int lifetime=0;

                time=System.currentTimeMillis();//time
                while(lifetime<params.lifeСycle) {             //основное тело цикла
                    for(int i = 0; i< params.ANTS_NUMBER; i++){       //обнуление всех штук у всех муравьев
                        ant[i].antNull(problem.getDMatrix().n);
                    }
                    try {

                        for (int t = 0; t <= problem.getDMatrix().n; t++) {
                            for (int k = 0; k < params.ANTS_NUMBER; k++) {
                                cover = ant[k].move(problem.getDMatrix().n, problem.getDMatrix().data, problem.getPhMatrix().dataPher
                                        , params.endWhile, k, params);
                                deltaPherMatrix.deltaPherMatrixUpdate(cover);// откладывание изменения феромонов на пути

                            }

                            problem.getPhMatrix().pheromonMatrixSteam(params.p);
                            problem.getPhMatrix().pheromonMatrixUpdate(deltaPherMatrix.dataDeltaPher);
                            deltaPherMatrix.deltaPherMatrixNull();
                        }
                        //topLengthlocal=ant[0].leng;
                        for (int k = 0; k < params.ANTS_NUMBER; k++) {
                            if ((topLengthlocal > ant[k].leng) && (ant[k].currentPosition == ant[k].startingPosition)) {
                                topLengthlocal = ant[k].leng;
                                topAnt = k;
                                bestRoute = ant[k].route;
                            }
                        }
                        if (topAnt != -2) {
                            topLength[lifetime] = (double) topLengthlocal;
                            System.out.println(topLength[lifetime]);
                        }
                        dTime=System.currentTimeMillis()-time; //time
                        Thread.sleep(1);
                        final double yoba = lifetime;
                        Platform.runLater(() -> controller.setPoint(yoba,topLength[(int)yoba]));//risyem
                        grafik.add(topLength[(int)yoba]);   ///save

                        lifetime++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                time=System.currentTimeMillis()-time;//time
                System.out.println("time - "+time/1000+","+time%1000);
                bestRouteRun=bestRoute;
                bestLength=topLengthlocal;
                System.out.println("BEST ROUTE LENGTH: " + bestRoute.size());
                if (topAnt != -2) {
                    System.out.println();
                    for(int i=0;i<problem.getDMatrix().n;i++){
                        System.out.print(bestRoute.get(i) + " ");
                    }
                    System.out.println();
                    System.out.println("TOP LENGTH: " + topLengthlocal);
                } else {
                    System.out.println("Route not detected");
                }

                Report report = new Report(problem, params, bestRouteRun,bestLength,grafik);
                report.setTime(time);
                System.out.println("Interesting");
                controller.endExecution(report);
                return 10;
            }
        };

        new Thread(task).start();
    }
}



