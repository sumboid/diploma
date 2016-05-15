package sample.model.algorithm.data;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PheromonMatrix {
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
}
