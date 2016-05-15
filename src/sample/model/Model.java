package sample.model;


import javafx.application.Platform;
import javafx.concurrent.Task;
import sample.*;
import sample.model.algorithm.data.*;

import java.util.ArrayList;

public class Model {
    private Controller controller;
    private Task task;

    public void setController(Controller c) {
        controller = c;
    }

    public void start(int lifeCycle,Parametry parametry,String fileDmatrix,String filePherMatrix) {
        task = new Task<Integer>() {
            @Override
            public Integer call() {
                String FILE_DMATRIX=fileDmatrix;
                String FILE_PHER_MATRIX=filePherMatrix;
                Cover cover=new Cover();//объект для обертки в методе Ants.move
                DistanzMatrix dmatrix =DistanzMatrix.buildDMatrixFromFile(FILE_DMATRIX);//создаем матрицу смехности
                PheromonMatrix phMatrix = PheromonMatrix.buildDMatrixFromFile(FILE_PHER_MATRIX);//создаем матрицу феромонов
                DeltaPherMatrix deltaPherMatrix=new DeltaPherMatrix();//создаем матрицу измененения феромонов
               // dmatrix.distanzMatrixShow();

                Ants[] ant=new Ants[parametry.ANTS_NUMBER];
                Double topLength[]=new Double[parametry.lifeСycle];
                for(int j=0;j<parametry.lifeСycle;j++){
                    topLength[j]= 0.0;
                }
                for(int j=0;j<parametry.ANTS_NUMBER;j++){
                    ant[j]= new Ants(dmatrix.n);
                }
                int topLengthlocal=1000,topAnt=-2;
                ArrayList<Integer> bestRoute =new ArrayList<>();
                int lifetime=0;


                while(lifetime<lifeCycle) {             //основное тело цикла
                    for(int i=0;i<parametry.ANTS_NUMBER;i++){       //обнуление всех штук у всех муравьев
                        ant[i].antNull(dmatrix.n);
                    }
                    try {

                        for (int t = 0; t <= dmatrix.n; t++) {
                            for (int k = 0; k < parametry.ANTS_NUMBER; k++) {
                                cover = ant[k].move(dmatrix.n, dmatrix.data, phMatrix.dataPher, parametry.endWhile, k,parametry);
                                deltaPherMatrix.deltaPherMatrixUpdate(cover);// откладывание изменения феромонов на пути

                            }
                            phMatrix.pheromonMatrixSteam(parametry.p);
                            phMatrix.pheromonMatrixUpdate(deltaPherMatrix.dataDeltaPher);
                            deltaPherMatrix.deltaPherMatrixNull();
                        }
                        for (int k = 0; k < parametry.ANTS_NUMBER; k++) {
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
                        Thread.sleep(1);
                        final double yoba = lifetime;
                        Platform.runLater(() -> controller.setPoint(yoba,topLength[(int)yoba]));
                        lifetime++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (topAnt != -2) {
                    System.out.println();
                    for(int i=0;i<dmatrix.n;i++){
                        System.out.print(bestRoute.get(i) + " ");
                    }
                    System.out.println();
                    System.out.print(topLengthlocal);
                } else {
                    System.out.println("Route not detected");
                }


                return 10;
            }
        };

        new Thread(task).start();
    }
}



