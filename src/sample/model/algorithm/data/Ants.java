package sample.model.algorithm.data;

import java.util.ArrayList;
import java.util.Random;

public class Ants extends Parametry {
    public ArrayList<Integer> route =new ArrayList<>();; //маршрут пройденный муравьем
    public int leng =0; //длинна маршрута
    public boolean used[];//массив для хранения инфы о пройденных вершинах
    //true - не пройдена, false -пройдена
    public int startingPosition =0;
    public int currentPosition=startingPosition;
    boolean permissionToGo=true;

    public Ants(int n){
        used=new boolean[n+1];
        for (int i=0;i<n+1;++i){
            used[i]=true;
        }
        route.add(currentPosition);

    }
    public void antNull(int n){
        for (int i=0;i<n+1;++i){
            used[i]=true;
        }
        route.clear();
        leng=0;
        currentPosition=startingPosition;
        route.add(currentPosition);
        permissionToGo=true;
    }
    public Cover move(int n, double []data, double []dataPher, int endWhile, int antNumber,Parametry parametry){ //движение муравья
        double pSumDom = 0;
        double[] pSumNom=new double[n];
        double deltaPherMatrix=0;
        Cover cover=new Cover();
       // if (endWhile==currentPosition){
     //       permissionToGo=false;
      //  }
     //   if (!permissionToGo){
      //      return null;

      //  }

        for (int j=0;j<n;++j){
            if ((data[(currentPosition)*n+j]>0)&&(used[j])){
                pSumDom+=Math.pow((1/(data[(currentPosition)*n+j])),parametry.b)
                        *Math.pow(dataPher[(currentPosition)*n+j],parametry.al);
            }
        }

        for(int i=0;i<n;++i){
            pSumNom[i]=0;
        }
        for(int i=0;i<n;++i){
            if ((data[(currentPosition)*n+i]>0)&&(used[i])){
                pSumNom[i]=(Math.pow(dataPher[(currentPosition)*n+i],parametry.al)*
                        Math.pow((1/data[(currentPosition)*n+i]),parametry.b))/pSumDom;

            }
        }

        ///список всех доступных маршрутов////////////////
        System.out.println();
        System.out.println(antNumber+" cp -"+currentPosition);
        for(int i=0;i<n;i++){
            if (((pSumNom[i]!=0)||(!used[i]))&&data[(currentPosition)*n+i]>0) {
                System.out.println(i+": "+used[i]);

            }
        }
        //////////////////////////////////////////////////////

        Random random=new Random();
        double ran = random.nextDouble();
        double kf = 0;
        int nextV = -1;
        System.out.println(ran + " random");

        for(int i=0;i<n;++i){
            if (pSumNom[i]!=0) {
                System.out.println(currentPosition + "-" + i + "- " + pSumNom[i]);
            }
            if ((pSumNom[i]!=0)&&((pSumNom[i]+kf)>=ran)&&(ran>kf)){
                nextV=i;
                break;

            }

            kf+=pSumNom[i];
        }

        System.out.println("nextV - "+nextV);
        System.out.println("info");
        System.out.println("leng:"+leng+"; currentPosition:"+currentPosition+"; used:"+used[currentPosition]);
        int oldPos = currentPosition;
        if (nextV>=0) {
            leng += data[(currentPosition) * n + nextV];
            used[currentPosition] = false;
            currentPosition = nextV;
            route.add(currentPosition);
            deltaPherMatrix=(double)q/leng;

        }
        if(nextV==-1){
            permissionToGo=false;
            return null;
        }

        System.out.println(antNumber+" info");
        System.out.println("leng:"+leng+"; currentPosition:"+currentPosition+"; used:"+used[oldPos]);
        for (int i=0;i< route.size();i++){
           // System.out.print(route.get(i)+"-");
        }
       System.out.println();
        System.out.println("CLOSE_INFO");
        System.out.println();
        if (route.size()==(n-1)){
            used[startingPosition]=true;
        }
        cover.coverSet(oldPos, nextV, deltaPherMatrix);
        return cover;
    }

    public void showRoute(){
        for (int i=0;i< route.size();i++){
            System.out.print(route.get(i)+"-");
        }
        System.out.println();
        System.out.println(route.get(0)+" "+currentPosition);
        System.out.println(leng);


    }


}
