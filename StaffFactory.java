public class StaffFactory {
    public Staff createStaff(Enums.StaffType t)
    {
        Staff v = null;
        if (t == null){
            return null;
        }
        if (t == Enums.StaffType.Intern) v = new Intern();
        if (t == Enums.StaffType.Mechanic) v = new Mechanic();
        if (t == Enums.StaffType.Salesperson) v = new Salesperson();
        if (t == Enums.StaffType.Driver) v = new Driver();
        return v;
            // FNCD.moneyOut(v.cost);  // pay for the vehicle
            // out ("Bought "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost));
            // inventory.add(v);
    }
}