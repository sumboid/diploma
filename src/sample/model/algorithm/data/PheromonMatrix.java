package sample.model.algorithm.data;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class PheromonMatrix implements java.io.Serializable {
    public int n;
    public int k;
    public double []dataPher;

    public static PheromonMatrix buildDMatrixFromFile(String fileNameData) {
        int n=0,k=0,i=0;
        double [] dataPher=null;
        try{

            Scanner scanner = new Scanner(new File(fileNameData));
            n=scanner.nextInt();
            dataPher = new double [n*n];
            while(scanner.hasNextDouble()){
                dataPher[i++] = scanner.nextDouble();
            }


        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
        return new PheromonMatrix(n,dataPher);
    }
    public PheromonMatrix(int n,double[] data) {
        this.n = n;
        this.dataPher = data;
    }
    public void pheromonMatrixShow(){
        int i=0;
        System.out.println();
        System.out.println(n);
        while(i<n*n){
            i++;
            System.out.print(dataPher[i-1]+" ");
            if (i%n==0){
                System.out.println();
            }
        }
    }
    public void pheromonMatrixUpdate(double [] deltaPherMatrix){
        for (int i=0;i<n*n;i++){
            dataPher[i]+=deltaPherMatrix[i];
        }
    }
    public void pheromonMatrixSteam(double p){
        for (int i=0;i<n*n;i++){
            dataPher[i]=dataPher[i]*(1-p);
        }
    }
    public static PheromonMatrix generate(int nv){
        double [] data=new double[nv*nv];
        double [][]dataTemp=new double[nv][nv];
        Random random=new Random();

        for(int i=0;i<nv;i++){
            for(int j=i;j<nv;j++){
                if (i==j) {
                    dataTemp[i][j] = 0;
                } else{
                    dataTemp[i][j]=1;
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
        return new PheromonMatrix(nv,data);
    }
}
