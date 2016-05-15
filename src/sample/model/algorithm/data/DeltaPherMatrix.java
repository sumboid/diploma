package sample.model.algorithm.data;

import java.io.*;
import java.util.Scanner;

public class DeltaPherMatrix {
    public int n;
    public double[] dataDeltaPher;
    public DeltaPherMatrix(){
        String FILE_DMATRIX="DistanzMatrix.m";
        try{
            Scanner scannerData = new Scanner(new File(FILE_DMATRIX));
            n=scannerData.nextInt();

        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
        dataDeltaPher = new double [n*n];
        for (int i=0;i<n*n;i++){
            dataDeltaPher[i]=0;
        }
    }
    public void deltaPherMatrixNull(){
        for (int i=0;i<n*n;i++){
            dataDeltaPher[i]=0;
        }
    }
    public void deltaPherMatrixUpdate(Cover cover){
        if (cover!=null) {
            dataDeltaPher[cover.i * n + cover.j] += cover.deltaR;
            dataDeltaPher[cover.j * n + cover.i] += cover.deltaR;
        }

    }

    public void deltaPherMatrixShow(){
        System.out.println();
        for(int i=0;i<n*n;i++){
            if ((i%n==0)&&(i!=0)){
                System.out.println();
            }
            System.out.print(dataDeltaPher[i]+" ");
        }
        System.out.println();
    }
}
