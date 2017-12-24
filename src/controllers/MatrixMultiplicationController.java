package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.la4j.Matrices;
import org.la4j.Matrix;
import org.la4j.inversion.GaussJordanInverter;
import org.la4j.inversion.NoPivotGaussInverter;
import org.la4j.matrix.dense.Basic1DMatrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.Double.parseDouble;
import static java.util.Arrays.asList;
import static utils.ObjectsGenerator.alphabet72;

public class MatrixMultiplicationController {
    @FXML
    TextArea MATRIX_ORIGINAL;

    @FXML
    TextArea MATRIX_CIPHER;

    @FXML
    Spinner<Integer> MATRIX_SIZE;

    @FXML
    Button MATRIX_GENERATE;

    @FXML
    Button MATRIX_ENCRYPT;

    @FXML
    Button MATRIX_DECIPHER;

    private double matrix[][];

    public void initialize() {
        MATRIX_SIZE.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1_000_000, 5));

        MATRIX_GENERATE.setOnAction(this::generateOnAction);

        MATRIX_ENCRYPT.setOnAction(this::encryptOnAction);

        MATRIX_DECIPHER.setOnAction(this::decipherOnAction);
    }

    private void generateOnAction(ActionEvent event) {
        int size = MATRIX_SIZE.getValue();
        matrix = new double[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
    }

    private void encryptOnAction(ActionEvent event) {
        if (MATRIX_ORIGINAL.getText().length() == 0) {
            return;
        }
        List<List<Double>> list = stringSplitAndReplace(MATRIX_ORIGINAL.getText());
        MATRIX_CIPHER.setText(encypt(list));
    }

    private List<List<Double>> stringSplitAndReplace(String string) {
        Integer matrixSize = matrix.length;
        List<List<Double>> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder(string);
        //дополняем звездочками
        while (builder.length() % matrixSize != 0) {
            builder.append('*');
        }
        string = builder.toString();
        List<Double> mas = new ArrayList<>();
        int iter = 0;
        for (int i = 0; i < string.length(); i++) {
            if (iter == matrixSize) {
                result.add(mas);
                mas = new ArrayList<>();
                iter = 0;
            }
            mas.add((double) alphabet72.indexOf(string.charAt(i)));
            iter++;
        }
        result.add(mas);
        return result;
    }

    private String encypt(List<List<Double>> lists) {
        int size = matrix.length;
        StringBuilder result = new StringBuilder();
        RealMatrix a = MatrixUtils.createRealMatrix(matrix);
        lists.forEach(item -> {
            RealMatrix b = MatrixUtils.createColumnRealMatrix(item.stream().mapToDouble(i -> i).toArray());
            RealMatrix c = a.multiply(b);
            double[] nextResult = c.getColumn(0);
            for (double d : nextResult) {
                result.append((int) d).append(" ");
            }
        });
        return result.toString();
    }

    private void decipherOnAction(ActionEvent event) {
        if (MATRIX_CIPHER.getText().length() == 0) {
            return;
        }
        Matrix a = new Basic2DMatrix(matrix);
        NoPivotGaussInverter g = new NoPivotGaussInverter(a);
        Date startDate = new Date();
        Matrix transponseMatrix = g.inverse();
        Date endDate = new Date();
        System.out.println((double) (endDate.getTime() - startDate.getTime()) / 1000d);

        List<double[]> encryptedArrays = stringToEncryptedArrays(MATRIX_CIPHER.getText());
        StringBuffer result = new StringBuffer();
        encryptedArrays.forEach(item -> {
            Matrix b = new Basic1DMatrix(matrix.length, 1, item);
            Matrix c = transponseMatrix.multiply(b);
            for (double d : c.getColumn(0)) {
                result.append(alphabet72.charAt((int)Math.round(d)));
            }
        });

        MATRIX_ORIGINAL.setText(result.toString());
    }

    private List<double[]> stringToEncryptedArrays(String string) {
        Integer matrixSize = matrix.length;
        List<String> list = new ArrayList<>(asList(string.split(" ")));
        while (list.size() % matrixSize != 0) {
            list.add("0");
        }
        List<double[]> result = new ArrayList<>();
        int iter = 0;
        double[] mas = new double[matrixSize];
        for (String d : list) {
            if (iter == matrixSize) {
                result.add(mas);
                mas = new double[matrixSize];
                iter = 0;
            }
            mas[iter] = parseDouble(d);
            iter++;
        }
        result.add(mas);
        return result;
    }
}
