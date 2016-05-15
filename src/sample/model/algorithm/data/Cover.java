package sample.model.algorithm.data;

public class Cover {
    int i;
    int j;
    double deltaR;
    public Cover(){
        i=0;
        j=0;
        deltaR=0;
    }
    public void coverSet(int i,int j,double deltaPherMatrix){
        this.deltaR=deltaPherMatrix;
        this.i=i;
        this.j=j;
    }
    public void coverShow(){
        System.out.println("i:"+i+" j:"+j+" deltaR:"+deltaR);
    }
}
