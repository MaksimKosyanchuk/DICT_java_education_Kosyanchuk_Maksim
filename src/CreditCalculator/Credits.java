package CreditCalculator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Credits {
    public static abstract class BaseCredit {
        public Integer Periods;
        public Double Payment;
        public Double Principal;
        public Double Interest;

        protected static void SetValues(BaseCredit credit, HashMap<String, Double> data)
            throws java.lang.IllegalAccessException {
            for (Field field : credit.getClass().getFields()) {
                String name = field.getName();
                if (field.getType() == Integer.class) {
                    int value = data.get(name.toLowerCase()).intValue();
                    field.set(credit, Integer.valueOf(value));
                }
                else{
                    field.set(credit, data.get(name.toLowerCase()));
                }
            }
        }

        public abstract void Calculate() throws Exceptions.IncorrectFormat, java.lang.NoSuchMethodException,
            java.lang.reflect.InvocationTargetException, java.lang.IllegalAccessException, Exceptions.FullDataGiven,
            Exceptions.RequiredArgumentIsNotFound;
    }

    public static class Annuity extends BaseCredit {
        public Annuity(HashMap<String, Double> data) throws java.lang.IllegalAccessException {
            SetValues(this, data);
        }

        public void Calculate() throws Exceptions.IncorrectFormat, Exceptions.RequiredArgumentIsNotFound,
            java.lang.NoSuchMethodException, java.lang.IllegalAccessException,
            java.lang.reflect.InvocationTargetException {
            System.out.println(GetMethod().invoke(this));
        }

        private java.lang.reflect.Method GetMethod()
            throws java.lang.IllegalAccessException, Exceptions.IncorrectFormat,
            Exceptions.RequiredArgumentIsNotFound, java.lang.NoSuchMethodException {
            for (Field field : this.getClass().getFields()) {
                var correct = CheckCorrectCountArguments(field);
                if(!correct) { continue; }
                return this.getClass().getMethod(field.getName());
            }
            throw new Exceptions.IncorrectFormat();
        }

        private boolean CheckCorrectCountArguments(Field field)
            throws Exceptions.RequiredArgumentIsNotFound, java.lang.IllegalAccessException {
            int count = 0;
            for(var argument : this.getClass().getFields()) {
                if(argument.getName() == "Interest" && argument.get(this) == null) {
                    throw new Exceptions.RequiredArgumentIsNotFound("interest");
                }
                if(!argument.equals(field) && argument.get(this) == null) {
                    count ++;
                }
            }
            return count == 0;
        }
        public String Periods() {
            int periods;
            if(Interest == 0) { periods = (int)Math.ceil(Principal/Payment); }
            else {
                var interest = Interest / 1200;
                var answer = Payment / (Payment - interest * Principal);
                periods = (int)Math.ceil(Math.log(answer) / Math.log(1 + interest));
            }
            return GetPeriodsStr(periods);
        }

        private String GetPeriodsStr(int periods) {
            int periods_years = periods / 12;
            int periods_months = periods % 12;
            String text = "It will take ";
            if (periods_years != 0) {
                text += periods_years + " years ";
            } else if (periods_months != 0) {
                text +=  "and ";
            }
            if (periods_months != 0) {
                text += periods_months + " months ";
            }
            text += "to repay this loan!";
            return text;
        }

        public String Payment() {
            double payment;
            if(Interest == 0) { payment = Principal/Payment; }
            else {
                var interest = Interest/1200;
                payment = Principal * ((interest * Math.pow(1 + interest, Periods)) / (Math.pow(1 + interest, Periods) - 1));
            }
            return "Your monthly payments = " + ((Double)Math.ceil(payment)).intValue() + "!";
        }

        public String Principal() {
            var interest = Interest/1200;
            var principal = Payment / ((interest * Math.pow(1 + interest, Periods)) / (Math.pow(1 + interest, Periods) - 1));
            principal = Math.floor(principal);
            return "Your loan principal = " + ((Double)principal).intValue() + "!";
        }
    }


    public static class Differential extends BaseCredit {
        public Differential(HashMap<String, Double> data) throws java.lang.IllegalAccessException {
            SetValues(this, data);
        }
        public void Calculate() throws Exceptions.IncorrectFormat, java.lang.IllegalAccessException,
            Exceptions.FullDataGiven {
            CheckCorrectArgumentsCount();
            System.out.println(CalculateMonthlyPayments());
        }

        private String CalculateMonthlyPayments() {
            var interest = Interest/1200;
            var monthlyPayments = new ArrayList<Integer>();
            for (int m = 1; m <= Periods; m++) {
                double d = (Principal / Periods) + (interest * (Principal - ((Principal * (m - 1)) / Periods)));
                monthlyPayments.add(((Double)(Math.ceil(d))).intValue());
            }
            return GetMonthlyPaymentsStr(monthlyPayments);
        }

        private String GetMonthlyPaymentsStr(ArrayList<Integer> data) {
            var sb = new StringBuilder();
            for(int index = 0; index < data.size(); index++) {
                sb.append("Month ").append(index + 1).append(": payment is ").append(data.get(index)).append("\n");
            }
            return GetOVerPayment(data, sb);
        }

        private void CheckCorrectArgumentsCount() throws Exceptions.IncorrectFormat, Exceptions.FullDataGiven,
            java.lang.IllegalAccessException {
            ArrayList<Field> fields = new ArrayList<>();
            for(var field : this.getClass().getFields()) {
                if(field.get(this) == null) {fields.add(field); }
            }
            if(fields.isEmpty()) { throw new Exceptions.FullDataGiven(); }
            if(fields.size() == 1 && fields.get(0).getName().equals("Payment")) { return; }
            throw new Exceptions.IncorrectFormat();
        }

        private String GetOVerPayment(ArrayList<Integer> data, StringBuilder str) {
            long overPayment = 0;
            for(var payment : data) {
                overPayment += payment;
            }
            overPayment = (long)(overPayment - Principal);
            return str.append("\n").append("Overpayment = ").append(overPayment).toString();
        }
    }
}
