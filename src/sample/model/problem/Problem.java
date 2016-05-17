package sample.model.problem;

import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.OverParams;
import sample.model.algorithm.data.Parameters;
import sample.model.algorithm.data.PheromonMatrix;

public class Problem implements java.io.Serializable {
    private  PheromonMatrix phMatrix;
    private  DistanzMatrix dMatrix;
    private  Parameters params;
    private String problemName;
    private OverParams overParams;

    public Problem(PheromonMatrix phMatrix, DistanzMatrix dMatrix, Parameters params, String problemName
            ,OverParams overParams
    ) {
        this.phMatrix = phMatrix;
        this.params = params;
        this.dMatrix=dMatrix;
        this.problemName=problemName;
        this.overParams=overParams;
    }

    public  OverParams getOverParams(){return overParams;}

    public  void setOverParams(OverParams overParams){this.overParams=overParams;}

    public  DistanzMatrix getDMatrix(){return dMatrix;}

    public  void setDMatrix(DistanzMatrix dMatrix){this.dMatrix=dMatrix;}


    public  PheromonMatrix getPhMatrix() {
        return phMatrix;
    }

    public  void setPhMatrix(PheromonMatrix phMatrix) {
        this.phMatrix = phMatrix;
    }

    public  Parameters getParams() {
        return params;
    }

    public  void setParams(Parameters params) {
        this.params = params;
    }

    public  String getProblemName() {
        return problemName;
    }

    public  void setProblemName (String problemName) {
        this.problemName = problemName;
    }
}
