package CoffeeMachine;

public class Coffee {
    public static abstract class BasicCoffee {
        public int Water;
        public int Milk;
        public int Beans;
        public int Money;
        BasicCoffee(int water, int milk, int beans, int money){
            Water = water;
            Milk = milk;
            Beans = beans;
            Money = money;
        }
    }

    public static class Espresso extends BasicCoffee{
        Espresso(){
            super(250, 0, 16, 4);
        }
    }

    public static class Cappuccino extends BasicCoffee{
        Cappuccino(){
            super(200, 100, 12, 6);
        }
    }
    public static class Latte extends BasicCoffee{
        Latte(){
            super(350, 75, 20, 7);
        }
    }
}
