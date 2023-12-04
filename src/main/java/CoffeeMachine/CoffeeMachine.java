package CoffeeMachine;

public class CoffeeMachine{
    Coffee.Espresso Espresso;
    Coffee.Latte Latte;
    Coffee.Cappuccino Cappuccino;
    public int Cups = 9;
    public int Water = 400;
    public int Milk = 540;
    public int Beans = 120;
    public int Money = 550;

    public CoffeeMachine(Coffee.Espresso espresso, Coffee.Latte latte, Coffee.Cappuccino cappuccino){
        Espresso = espresso;
        Latte = latte;
        Cappuccino = cappuccino;
    }

    public void Buy(){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back – to\n" +
                "main menu:");
        var coffee = switch(Program.In.nextLine().toLowerCase()){
            case "back" -> null;
            case "1" -> Espresso;
            case "2" -> Latte;
            case "3" -> Cappuccino;
            default -> {Buy();
                yield null;
            }
        };
        if (coffee == null) return;
        try{
            String enoughResource;
            if(Cups <= 0) throw new Exceptions.EnoughResources("Cups");
            if(Water <= coffee.Water) throw new Exceptions.EnoughResources("Water");
            if(Milk <= coffee.Milk) throw new Exceptions.EnoughResources("Milk");
            if(Beans <= coffee.Beans) throw new Exceptions.EnoughResources("Beans");
        }
        catch (Exceptions.EnoughResources e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        Cups -= 1;
        Beans -= coffee.Beans;
        Milk -= coffee.Milk;
        Water -= coffee.Water;
        Money += coffee.Money;
    }
    public void Fill(){
        while(true){
            System.out.println("Write how many ml of water do you want to add");
            String water = Program.In.nextLine();
            System.out.println("Write how many ml of milk do you want to add");
            String milk = Program.In.nextLine();
            System.out.println("Write how many grams of coffee beans do you want to add");
            String beans = Program.In.nextLine();
            System.out.println("Write how many disposable cups of coffee do you want to add:");
            String cups = Program.In.nextLine();
            int _water;
            int _milk;
            int _beans;
            int _cups;
            try{
                _water = Integer.parseInt(water);
                _milk = Integer.parseInt(milk);
                _beans = Integer.parseInt(beans);
                _cups = Integer.parseInt(cups);
            }
            catch(NumberFormatException e){
                System.out.println("Incorrect input!");
                continue;
            }
            Water += _water;
            Milk += _milk;
            Beans += _beans;
            Cups += _cups;
            break;
        }
    }
    public void Take(){
        System.out.println("I gave you " + Money);
        Money = 0;
    }
    public void Remaining(){
        System.out.println("The coffee machine has:");
        System.out.println(Water + " of water");
        System.out.println(Milk + " of milk");
        System.out.println(Beans + " of beans");
        System.out.println(Cups + " of cups");
        System.out.println(Money + " of money");
    }
    public boolean Exit() { return true; }
}