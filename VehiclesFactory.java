public class VehiclesFactory {
    public Vehicle createCar(Enums.VehicleType t)
    {
        Vehicle v = null;
        if (t == null){
            return null;
        }
        if (t == Enums.VehicleType.Car) v = new Car();
        if (t == Enums.VehicleType.PerfCar) v = new PerfCar();
        if (t == Enums.VehicleType.Pickup) v = new Pickup();
        if (t == Enums.VehicleType.Electric) v = new Electric();
        if (t == Enums.VehicleType.Motercycle) v = new Motorcycle();
        if (t == Enums.VehicleType.Monstertruck) v = new Monstertruck();
        if (t == Enums.VehicleType.Bus) v = new Bus();
        if (t == Enums.VehicleType.Boat) v = new Boat();
        if (t == Enums.VehicleType.Semi) v = new Semi();
        return v;
            // FNCD.moneyOut(v.cost);  // pay for the vehicle
            // out ("Bought "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost));
            // inventory.add(v);
    }
}