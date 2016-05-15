package sample.model.algorithm.data;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

public class DistanzMatrix implements java.io.Serializable {
    public int n;
    public int k;
    public double[]data;


    public static void main(String[] args){
        /*String FILE_DMATRIX="DistanzMatrix.m";
        String FILE_DMATRIX_PHER="PhMatrix.m";
        Cover cover=new Cover();//объект для обертки в методе Ants.move
        DistanzMatrix dmatrix =DistanzMatrix.buildDMatrixFromFile(FILE_DMATRIX);//создаем матрицу смехности
        DeltaPherMatrix deltaPherMatrix=new DeltaPherMatrix();//создаем матрицу измененения феромонов


        dmatrix.distanzMatrixShow();
        deltaPherMatrix.deltaPherMatrixShow();

        int ANTS_NUMBER=100;
        Ants[] ant=new Ants[ANTS_NUMBER];
        for(int j=0;j<ANTS_NUMBER;j++){
            ant[j]= new Ants(dmatrix.n);
        }
        int endWhile=11;
        int topLength=1000,topAnt=-2;

        for(int t=0;t<100;t++){        //основное тело цикла
            for(int k=0;k<ANTS_NUMBER;k++) {
                cover=ant[k].move(dmatrix.n,dmatrix.data,dmatrix.dataPher,endWhile);
                deltaPherMatrix.deltaPherMatrixUpdate(cover);

                if ((topLength > ant[k].leng) && (ant[k].currentPosition == endWhile)) {
                    topLength = ant[k].leng;
                    topAnt = k;
                }
            }
        }
        deltaPherMatrix.deltaPherMatrixShow();
        if (topAnt!=-2) {
            ant[topAnt].showRoute();
        }
*/

    }
    public void distanzMatrixShow(){
        int i=0;
        System.out.println();
        System.out.println(n + " " + k);
        while(i<n*n){
            i++;
            System.out.print(data[i-1]+" ");
            if (i%n==0){
                System.out.println();
            }
        }
    }
    public DistanzMatrix(int n,int k,double[] data){
        this.n=n;
        this.k=k;
        this.data=data;


    }
    public static DistanzMatrix buildDMatrixFromFile(String fileNameData) {
        int n=0,k=0,i=0;
        double [] data=null;

        try{

            Scanner scannerData = new Scanner(new File(fileNameData));
            n=scannerData.nextInt();
            k=scannerData.nextInt();
            data = new double [n*n];
            while(scannerData.hasNextDouble()){
                data[i++] = scannerData.nextDouble();
            }


        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
        return new DistanzMatrix(n,k,data);
    }
    public static DistanzMatrix generate(int nv,int minRust,int maxRust){
        double [] data=new double[nv*nv];
        double [][]dataTemp=new double[nv][nv];
        Random random=new Random();

        for(int i=0;i<nv;i++){
            for(int j=i;j<nv;j++){
                if (i==j) {
                    dataTemp[i][j] = 0;
                } else{
                    dataTemp[i][j]=(double)Math.round((minRust+(maxRust-minRust)*random.nextDouble())*100)/100;
                    dataTemp[j][i]=dataTemp[i][j];
                }
            }
        }
        for(int i=0;i<nv;i++){
            for(int j=0;j<nv;j++){
                data[i*nv+j]=dataTemp[i][j];
            }
        }




        for(int i=0;i<nv*nv;i++){
            if (i%nv==0){System.out.println();}
            System.out.print(data[i]+" ");

        }
        System.out.println();
        return new DistanzMatrix(nv,nv,data);
    }
}
