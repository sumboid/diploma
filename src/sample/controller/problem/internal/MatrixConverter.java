package sample.controller.problem.internal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.algorithm.data.DistanzMatrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixConverter {
    public static ObservableList<PathLength> matrixToPath(DistanzMatrix matrix) {
        List<PathLength> list = new ArrayList();
        final int N = matrix.n;
        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                if (i != j) {
                    list.add(new PathLength(i, j, Double.toString(matrix.data[i * N + j])));
                }
            }
        }
        return FXCollections.observableArrayList(list);
    }

    public static DistanzMatrix pathToMatrix(ObservableList<PathLength> list) {
        int N = 0;
        for(; N * N - N != list.size(); N++);
        double[] data = new double[N * N];

        for (int i = 0; i < N;  ++i) {
            data[i * N + i] = 0;
        }
        for(PathLength path : list) {
            data[path.getA() * N + path.getB()] = Double.parseDouble(path.getLength());
        }

        return new DistanzMatrix(N, N, data);
    }
}
