package CreditCalculator;
import java.util.HashMap;

public class Program {
    public static void main(String[] args) {
        try {
            var data = ParseData(args);
            var credit = GetCredit(data);
            credit.Calculate();
        }
        catch(Exceptions.IncorrectFormat e) {
            System.out.println(e.getMessage());
            System.out.println("Use --help to get help!");
        }
        catch(Exceptions.GetHelp e) {
            GetHelp();
        }
        catch(Exception e) { System.out.println(e.getMessage()); }
    }

    private static HashMap<String,Object> ParseData(String[] args) throws Exceptions.GetHelp, Exceptions.IncorrectFormat {
        HashMap<String, Object> data = new HashMap<>();
        for(var arg:args) {
            if(arg.equals("--help")) { System.out.println("test"); throw new Exceptions.GetHelp(); }
            var arguments = arg.split("=");
            if(arguments.length != 2) { throw new Exceptions.IncorrectFormat(); }
            data.computeIfAbsent(arguments[0], k -> arguments[1]);
        }
        return data;
    }

    private static String GetCreditType(HashMap<String, Object> data) throws Exceptions.RequiredArgumentIsNotFound {
        try {
            return data.get("type").toString().toLowerCase();
        }
        catch(NullPointerException e) { throw new Exceptions.RequiredArgumentIsNotFound("type"); }
    }

    private static HashMap<String, Double> GetValues(HashMap<String, Object> data) {
        HashMap<String, Double> values = new HashMap<String, Double>();
        for(var value : data.keySet()) {
            if(!value.equals("type")) {
                try {
                    values.put(value, Double.parseDouble((String)data.get(value)));
                } catch (NumberFormatException ignored) { }
            };
        }
        return values;
    }

    private static Credits.BaseCredit GetCredit(HashMap<String, Object> data)
        throws Exceptions.UnknownCreditType, Exceptions.RequiredArgumentIsNotFound, java.lang.IllegalAccessException {
        var type = GetCreditType(data);
        var values = GetValues(data);
        return switch (type) {
            case "annuity" -> new Credits.Annuity(values);
            case "diff" -> new Credits.Differential(values);
            default -> throw new Exceptions.UnknownCreditType(type);
        };
    }

    private static void GetHelp() {
        var sb = new StringBuilder();
        sb.append("Required arguments:\n");
        for(var field : Credits.BaseCredit.class.getFields()) {
            sb.append("(").append(field.getType().getSimpleName()).append(")");
            sb.append(field.getName().toLowerCase()).append("\n");
        }
        sb.append("type(annuity/diff)");
        System.out.println(sb);
    }
}
