
public class vDecorator extends Vehicle{
    private Vehicle v;
    protected vDecorator(Vehicle v, double priceInc){
        this.price = (int)(v.price * priceInc);
    }
}

class ExtendedWarrenty extends vDecorator{
    private static double priceInc = 1.2;
    public static double chance = .25;
    public ExtendedWarrenty(Vehicle v){
        super(v, priceInc);
    }
}

class UnderCoating extends vDecorator{
    private static double priceInc = 1.05;
    public static double chance = .10;
    public UnderCoating(Vehicle v){
        super(v, priceInc);
    }
}

class RRC extends vDecorator{
    private static double priceInc = 1.02;
    public static double chance = .05;
    public RRC(Vehicle v){
        super(v, priceInc);
    }
}

class SatelliteRadio extends vDecorator{
    private static double priceInc = 1.05;
    public static double chance = .4;
    public SatelliteRadio(Vehicle v){
        super(v, priceInc);
    }
}