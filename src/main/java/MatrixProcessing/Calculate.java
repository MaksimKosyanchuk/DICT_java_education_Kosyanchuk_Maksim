package MatrixProcessing;

import java.util.ArrayList;

public class Calculate {
    public static Matrix AddMatrices(Matrix firstMatrix, Matrix secondMatrix) throws Exceptions.MatrixSizesAreNotEqual {
        if(firstMatrix.GetCountOfRows() != firstMatrix.GetCountOfRows() || firstMatrix.GetCountOfColumns() !=
                firstMatrix.GetCountOfColumns()) { throw new Exceptions.MatrixSizesAreNotEqual(); }
        var newMatrix = new Matrix(firstMatrix.GetCountOfRows(), firstMatrix.GetCountOfColumns());
        for (int i = 0; i < firstMatrix.GetCountOfRows(); i++) {
            for (int j = 0; j < firstMatrix.GetCountOfColumns(); j++) {
                newMatrix.SetValue(i, j, firstMatrix.GetValue(i, j) + secondMatrix.GetValue(i, j));
            }
        }
        return newMatrix;
    }

    private static Matrix Turn(Matrix matrix) {
        var newMatrix = new Matrix(matrix.GetCountOfColumns(), matrix.GetCountOfRows());
        int rowsCount = newMatrix.GetCountOfRows();
        int columnsCount = newMatrix.GetCountOfColumns();
        for (int index1 = 0; index1 < rowsCount; index1++) {
            for (int index2 = 0; index2 < columnsCount; index2++) {
                newMatrix.SetValue(index1, index2, matrix.GetValue(index2, index1));
            }
        }
        return newMatrix;
    }

    public static Matrix MultiplyMatrices(Matrix firstMatrix, Matrix secondMatrix)
        throws Exceptions.MatrixSizeLimit, Exceptions.CountOfRowsOrColumnsIsNotEqual {
        CheckCountOfRowsOrColumnsIsEqual(firstMatrix, secondMatrix);
        Matrix transposedMatrix = Turn(firstMatrix);
        double[][] newMatrix = new double[transposedMatrix.GetCountOfColumns()][secondMatrix.GetCountOfColumns()];
        double finalCount = 0;
        for (int index3 = 0; index3 < transposedMatrix.GetCountOfColumns(); index3++) {
            for (int index2 = 0; index2 < secondMatrix.GetCountOfColumns(); index2++) {
                for (int index1 = 0; index1 < transposedMatrix.GetCountOfRows(); index1++) {
                    finalCount += (transposedMatrix.GetValue(index1, index3) * secondMatrix.GetValue(index1, index2));
                }
                newMatrix[index3][index2] = finalCount;
                finalCount = 0;
            }
        }
        return new Matrix(newMatrix);
    }

    private static void CheckCountOfRowsOrColumnsIsEqual(Matrix firstMatrix, Matrix secondMatrix)
            throws Exceptions.CountOfRowsOrColumnsIsNotEqual {
        if(firstMatrix.GetCountOfColumns() != secondMatrix.GetCountOfRows()) {
            throw new Exceptions.CountOfRowsOrColumnsIsNotEqual();
        }
    }

    public static Matrix MultiplyMatrixByConstant(Matrix matrix, double constant) {
        for(int i = 0; i < matrix.GetCountOfRows(); i++) {
            for(int j = 0; j < matrix.GetCountOfColumns(); j++) {
                matrix.SetValue(i, j, matrix.GetValue(i, j) * constant);
            }
        }
        return matrix;
    }

    public static Matrix TransposeMatrixByMainDiagonal(Matrix matrix)
        throws MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        double[][] newMatrix = new double[matrix.GetCountOfColumns()][matrix.GetCountOfRows()];

        for (int index1 = 0; index1 < matrix.GetCountOfColumns(); index1++) {
            for (int index2 = 0; index2 < matrix.GetCountOfRows(); index2++) {
                newMatrix[index1][index2] = matrix.GetValue(index2, index1);
            }
        }
        return new Matrix(newMatrix);
    }

    public static Matrix TransposeMatrixBySideDiagonal(Matrix matrix)
    throws MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        double[][] newMatrix = new double[matrix.GetCountOfColumns()][matrix.GetCountOfRows()];

        for (int index1 = 0; index1 < matrix.GetCountOfColumns(); index1++) {
            for (int index2 = 0; index2 < matrix.GetCountOfRows(); index2++) {
                newMatrix[index1][index2] = matrix.GetValue(matrix.GetCountOfRows() - 1 - index2,
                                                    matrix.GetCountOfColumns() - 1 - index1);
            }
        }
        return new Matrix(newMatrix);
    }

    public static Matrix TransposeMatrixByVertical(Matrix matrix)
        throws MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        int rows = matrix.GetCountOfRows();
        int columns = matrix.GetCountOfColumns();
        double[][] newMatrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMatrix[i][j] = matrix.GetValue(i, columns - 1 - j);
            }
        }
        return new Matrix(newMatrix);
    }

    public static Matrix TransposeMatrixByHorizontal(Matrix matrix)
        throws MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        int rows = matrix.GetCountOfRows();
        int columns = matrix.GetCountOfColumns();
        double[][] newMatrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMatrix[i][j] = matrix.GetValue(rows - 1 - i, j);
            }
        }
        return new Matrix(newMatrix);
    }

    private static Matrix GetMinor(Matrix matrix, int row, int column)
        throws MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        ArrayList<ArrayList<Double>> minor = new ArrayList<>();
        ArrayList<Double> minorCycled = new ArrayList<>();
        var sizeMatrix = matrix.GetCountOfRows();
        for (int index1 = 0; index1 < sizeMatrix; index1++) {
            if (index1 == row) {
                continue;
            }
            for (int index2 = 0; index2 < sizeMatrix; index2++) {
                if (index2 == column) {
                    continue;
                }
                minorCycled.add(matrix.GetValue(index1, index2));
            }
            minor.add(new ArrayList<>(minorCycled));
            minorCycled.clear();
        }
        return new Matrix(minor);
    }

    public static void Determinant(Matrix matrix)
    throws Exceptions.MatrixIsNotSquare, MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        System.out.println(CalculateDeterminant(matrix));
    }

    private static double CalculateDeterminant(Matrix matrix)
        throws Exceptions.MatrixIsNotSquare, MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        CheckIsSquareMatrix(matrix);
        double determinant = 0;
        var matrixSize = matrix.GetCountOfRows();
        if (matrixSize == 1) { return matrix.GetValue(0,0); }
        for (int i = 0; i < matrix.GetCountOfRows(); i++) {
            var minorSymbol = Math.pow(-1, i);
            var minor = GetMinor(matrix, i, 0);
            determinant += CalculateDeterminant(minor) * minorSymbol * matrix.GetValue(i, 0);
        }
        return determinant;
    }

    private static void CheckIsSquareMatrix(Matrix matrix) throws Exceptions.MatrixIsNotSquare {
        if(matrix.GetCountOfRows() != matrix.GetCountOfColumns()) { throw new Exceptions.MatrixIsNotSquare(); }
    }

    public static Matrix InverseMatrix(Matrix matrix)
        throws MatrixProcessing.Exceptions.MatrixIsNotSquare, MatrixProcessing.Exceptions.MatrixSizeLimit, MatrixProcessing.Exceptions.CountOfRowsOrColumnsIsNotEqual {
        if (matrix.GetCountOfRows() == 1 && matrix.GetCountOfColumns() == 1) {
            return matrix;
        }

        ArrayList<ArrayList<Double>> allianceMatrix = new ArrayList<>();
        for (int i = 0; i < matrix.GetCountOfRows(); i++) {
            ArrayList<Double> matrixHeight = new ArrayList<>();
            for (int j = 0; j < matrix.GetCountOfColumns(); j++) {
                int minorSymbol = (int) Math.pow(-1, i + j);
                Matrix minor = GetMinor(matrix, i, j);
                matrixHeight.add(CalculateDeterminant(minor) * minorSymbol);
            }
            allianceMatrix.add(matrixHeight);
        }

        var mainDeterminant = CalculateDeterminant(matrix);

        Matrix inversedMatrix = MultiplyMatrixByConstant(new Matrix(allianceMatrix), 1 / mainDeterminant);
        inversedMatrix = TransposeMatrixByMainDiagonal(inversedMatrix);

        return inversedMatrix;
    }
}
