//Decorator
public class vDecorator extends Vehicle{
    private Vehicle v;
    protected vDecorator(Vehicle v, double priceInc){
        this.price = (int)(v.price * priceInc);
        this.name = v.name;
        this.type = v.type;
        this.condition = v.condition;
        this.cleanliness = v.cleanliness;
        this.cost = v.cost;
        this.wins = v.wins;
        this.sale_bonus = v.sale_bonus;
    }
}

//addOns - Extended Warrenty
class ExtendedWarrenty extends vDecorator{
    private static double priceInc = 1.2;
    public static double chance = .25;
    public ExtendedWarrenty(Vehicle v){
        super(v, priceInc);
    }
}
//addOns - UnderCoating
class UnderCoating extends vDecorator{
    private static double priceInc = 1.05;
    public static double chance = .10;
    public UnderCoating(Vehicle v){
        super(v, priceInc);
    }
}
//addOns - Road Rescue Coverage
class RRC extends vDecorator{
    private static double priceInc = 1.02;
    public static double chance = .05;
    public RRC(Vehicle v){
        super(v, priceInc);
    }
}
//addOns SatelliteRadio
class SatelliteRadio extends vDecorator{
    private static double priceInc = 1.05;
    public static double chance = .4;
    public SatelliteRadio(Vehicle v){
        super(v, priceInc);
    }
}