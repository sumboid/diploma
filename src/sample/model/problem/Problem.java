package sample.model.problem;

import sample.model.algorithm.data.DeltaPherMatrix;
import sample.model.algorithm.data.Parameters;

public class Problem implements java.io.Serializable {
    private static DeltaPherMatrix matrix;
    private static Parameters params;

    public Problem(DeltaPherMatrix matrix, Parameters params) {
        this.matrix = matrix;
        this.params = params;
    }

    public static DeltaPherMatrix getMatrix() {
        return matrix;
    }

    public static void setMatrix(DeltaPherMatrix matrix) {
        Problem.matrix = matrix;
    }

    public static Parameters getParams() {
        return params;
    }

    public static void setParams(Parameters params) {
        Problem.params = params;
    }
}
