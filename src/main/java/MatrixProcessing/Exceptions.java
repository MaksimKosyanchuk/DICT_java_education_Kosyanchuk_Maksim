package MatrixProcessing;


public class Exceptions {

    public static class MatrixSizeLimit extends Exception {
        MatrixSizeLimit() {
            super("The size of the matrix can be no more than 10 and and not less then 1!");
        }
    }

    public static class MatrixSizesAreNotEqual extends Exception {
        public MatrixSizesAreNotEqual() {
            super("Matrix sizes are not equal!");
        }
    }

    public static class CountOfRowsOrColumnsIsNotEqual extends Exception {
        public CountOfRowsOrColumnsIsNotEqual() {
            super("Count of rows or columns is not equal!");
        }
    }

    public static class MatrixIsNotSquare extends Exception {
        public MatrixIsNotSquare() {
            super("Matrix is not square!");
        }
    }

    public static class IncorrectMatrixSizeFormat extends Exception {
        public IncorrectMatrixSizeFormat() {
            super("Incorrect format of size matrix!");
        }
    }
}
