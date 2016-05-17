package sample.model.problem;

import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.OverParams;
import sample.model.algorithm.data.Parameters;
import sample.model.algorithm.data.PheromonMatrix;

public class Problem implements java.io.Serializable {
    private  PheromonMatrix phMatrix;
    private  DistanzMatrix dMatrix;
    private String problemName;

    public Problem(PheromonMatrix phMatrix, DistanzMatrix dMatrix, String problemName) {
        this.phMatrix = phMatrix;
        this.dMatrix=dMatrix;
        this.problemName=problemName;
    }

    public  DistanzMatrix getDMatrix(){return dMatrix;}

    public  void setDMatrix(DistanzMatrix dMatrix){this.dMatrix=dMatrix;}


    public  PheromonMatrix getPhMatrix() {
        return phMatrix;
    }

    public  void setPhMatrix(PheromonMatrix phMatrix) {
        this.phMatrix = phMatrix;
    }

    public  String getProblemName() {
        return problemName;
    }

    public  void setProblemName (String problemName) {
        this.problemName = problemName;
    }
}
