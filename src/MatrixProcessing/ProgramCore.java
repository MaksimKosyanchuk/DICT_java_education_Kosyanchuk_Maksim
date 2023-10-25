package MatrixProcessing;

public class ProgramCore {
    public static void Start() {
        while(true) {
            try {
                switch(GetAction()) {
                    case 0 -> { return; }
                    case 1 -> Calculate.AddMatrices(new Matrix(), new Matrix()).Print();
                    case 2 -> Calculate.MultiplyMatrixByConstant(new Matrix(), CreateConstant()).Print();
                    case 3 -> Calculate.MultiplyMatrices(new Matrix(), new Matrix()).Print();
                    case 4 -> Transpose().Print();
                    case 5 -> Calculate.Determinant(new Matrix());
                    case 6 -> Calculate.InverseMatrix(new Matrix()).Print();
                    default -> { }
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int GetAction() {
        while(true) {
            System.out.println("""
0. Exit
1. Add matrices
2. Multiply matrix by a constant
3. Multiply matrices
4. Transpose matrix
5. Calculate a determinant
6. Inverse matrix""");
            try { return Integer.parseInt(Program.In.nextLine()); }
            catch(NumberFormatException ignored) { }
        }
    }

    private static int CreateConstant() {
        while(true) {
            System.out.println("Enter the constant");
            try { return Integer.parseInt(Program.In.nextLine()); }
            catch(NumberFormatException ignored) { }
        }
    }

    private static Matrix Transpose() throws Exceptions.CountOfRowsOrColumnsIsNotEqual, Exceptions.MatrixSizeLimit {
        System.out.println("""
1. Main diagonal
2. Side diagonal
3. Vertical line
4. Horizontal line
Your choice:""");
        String userChoice = Program.In.nextLine();
        return switch(userChoice) {
            case "1" -> Calculate.TransposeMatrixByMainDiagonal(new Matrix());
            case "2" -> Calculate.TransposeMatrixBySideDiagonal(new Matrix());
            case "3" -> Calculate.TransposeMatrixByVertical(new Matrix());
            case "4" -> Calculate.TransposeMatrixByHorizontal(new Matrix());
            default -> Transpose();
        };
    }
}
