package MatrixProcessing;


import java.util.ArrayList;

public class Matrix {
    private final ArrayList<ArrayList<Double>> MatrixCore;
    private final int Rows;
    private final int Columns;

    public Matrix(ArrayList<ArrayList<Double>> values) throws Exceptions.MatrixSizeLimit, Exceptions.CountOfRowsOrColumnsIsNotEqual {
        CheckMatrixIsCorrect(values);
        Rows = values.size();
        Columns = values.get(0).size();
        CheckMatrixSize(Rows, Columns);
        MatrixCore = values;
    }

    public Matrix(double[][] list) throws Exceptions.CountOfRowsOrColumnsIsNotEqual, Exceptions.MatrixSizeLimit {
        var values = ConvertArrayToList(list);
        CheckMatrixIsCorrect(values);
        Rows = values.size();
        Columns = values.get(0).size();
        CheckMatrixSize(Rows, Columns);
        MatrixCore = values;
    }

    public Matrix(int row, int column) {
        MatrixCore = ConvertArrayToList(new double[row][column]);
        Rows = row;
        Columns = column;
    }

    public Matrix() throws Exceptions.CountOfRowsOrColumnsIsNotEqual, Exceptions.MatrixSizeLimit {
        var values = CreateMatrix();
        Rows = values.size();
        Columns = values.get(0).size();
        CheckMatrixSize(Rows, Columns);
        MatrixCore = values;
    }

    public void SetValue(int row, int column, double value) {
        ArrayList<Double> innerList = MatrixCore.get(row);
        innerList.set(column, value);
    }

    public double GetValue(int row, int column) { return MatrixCore.get(row).get(column); }

    private static ArrayList<ArrayList<Double>> ConvertArrayToList(double[][] values) {
        var array = new ArrayList<ArrayList<Double>>();
        for(var row:values) {
            var cycleArray = new ArrayList<Double>();
            for(var obj:row) {
                cycleArray.add(obj);
            }
            array.add(cycleArray);
        }
        return array;
    }

    private static void CheckMatrixIsCorrect(ArrayList<ArrayList<Double>> values) throws Exceptions.CountOfRowsOrColumnsIsNotEqual {
        var sizes = new ArrayList<Integer>();
        for(var row:values) {
            sizes.add(row.size());
        }
        if (sizes.stream().distinct().count() > 1) { throw new Exceptions.CountOfRowsOrColumnsIsNotEqual(); }
    }

    private void CheckMatrixSize(int rows, int columns) throws Exceptions.MatrixSizeLimit {
        if(rows > 10 || rows < 1) { throw new Exceptions.MatrixSizeLimit(); }
        if(columns > 10 || columns < 1) { throw new Exceptions.MatrixSizeLimit(); }
    }

    public int GetCountOfRows() { return Rows; }

    public int GetCountOfColumns() { return Columns; }

    private ArrayList<Integer> GetStringsSizes() {
        var sizes = new ArrayList<Integer>();
        for(int i = 0; i < MatrixCore.get(0).size(); i++) {
            var maxValue =  Double.toString(MatrixCore.get(0).get(i)).length();
            for (var doubles : MatrixCore) {
                var size = Double.toString(doubles.get(i)).length();
                if (size > maxValue) {
                    maxValue = size;
                }
            }
            sizes.add(maxValue);
        }
        return sizes;
    }

    public void Print(){
        var sizes = GetStringsSizes();
        var matrix = new StringBuilder();
        for (var doubles : MatrixCore) {
            for (int j = 0; j < MatrixCore.get(0).size(); j++) {
                String symbol = GetSymbol(doubles.get(j));
                matrix.append(symbol);
                matrix.append(" ".repeat(sizes.get(j) - symbol.length()));
                matrix.append("   ");
            }
            matrix.append("\n");
        }
        System.out.println(matrix);
    }

    private String GetSymbol(double value) {
        if(value == 0) { return "0"; }
        return String.valueOf(value).replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static ArrayList<ArrayList<Double>> CreateMatrix() throws Exceptions.CountOfRowsOrColumnsIsNotEqual {
        String sizeOfUser;
        while(true) {
            System.out.println("Enter size of matrix>");
            sizeOfUser = Program.In.nextLine();
            int[] size;
            try { size  = SizeParse(sizeOfUser); }
            catch(Exceptions.IncorrectMatrixSizeFormat e) { continue; }
            return GetMatrixFromUser(size);
        }
    }

    private static ArrayList<ArrayList<Double>> GetMatrixFromUser(int[] size) throws Exceptions.CountOfRowsOrColumnsIsNotEqual {
        System.out.println("Enter matrix>");
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        for(var index1 = 0; index1 < size[0]; index1++) {
            ArrayList<Double> cycleMatrix = new ArrayList<>();
            String row = Program.In.nextLine();
            for(var obj:row.split(" ")) {
                try { cycleMatrix.add(Double.parseDouble(obj)); }
                catch(NumberFormatException e) { return GetMatrixFromUser(size); }
            }
            matrix.add(cycleMatrix);
        }
        try { CheckMatrixIsCorrect(matrix); }
        catch(Exceptions.CountOfRowsOrColumnsIsNotEqual e) { return GetMatrixFromUser(size); }
        return matrix;
    }

    private static int[] SizeParse(String sizeOfUser) throws Exceptions.IncorrectMatrixSizeFormat {
        var size = sizeOfUser.split(" ");
        if(size.length != 2) { throw new Exceptions.IncorrectMatrixSizeFormat(); }
        int rows;
        int columns;
        try {
            rows = Integer.parseInt(size[0]);
            columns = Integer.parseInt(size[1]);
        }
        catch(NumberFormatException e) { throw new Exceptions.IncorrectMatrixSizeFormat(); }
        return new int[] { rows, columns };
    }
}
