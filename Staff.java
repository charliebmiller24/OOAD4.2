import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;

public abstract class Staff implements SysOut {
    String name;
    double salary;  // daily salary
    double salaryEarned;
    double bonusEarned;
    Enums.StaffType type;
    int daysWorked;
    Staff() {
        salaryEarned = 0;
        bonusEarned = 0;
        daysWorked = 0;
    }

    // utility for getting Staff by Type
    // You could do this with getClass instead of Type, but I use the enum
    // because it's clearer to me - less Java-y
    static ArrayList<Staff> getStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        ArrayList<Staff> subclassInstances = new ArrayList<>();
        for (Staff s : staffList) {
            if (s.type == t) subclassInstances.add(s);
        }
        return subclassInstances;
    }

    //Utility for finding out how many of a Staff type there are
    static int howManyStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        int n = 0;
        for (Staff s: staffList) {
            if (s.type == t) n++;
        }
        return n;
    }
}

class Intern extends Staff {
    static List<String> names = Arrays.asList("Fred","Ethel","Lucy","Desi");
    static Namer namer = new Namer(names);
    Intern() {
        super();
        type = Enums.StaffType.Intern;
        name = namer.getNext();  // every new intern gets a new name
        salary = 60; // daily salary
    }

    // How an intern washes cars
    // TODO: There's some duplication in this - it's a little clumsy - refactor me!
    void washVehicles(ArrayList<Vehicle> vList) {
        int washCount = 0;
        Enums.Cleanliness startAs;
        for (Vehicle v:vList) {
            // wash the first dirty car I see
            if (v.cleanliness == Enums.Cleanliness.Dirty) {
                double washtype = Utility.rnd();
                //out("washtype"+washtype);
                washCount += 1;
                startAs = Enums.Cleanliness.Dirty;

                if (washtype < .33){ // Chemical
                    double chance = Utility.rnd();
                    if (chance >= .9){
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Chemical Wash!");
                    }
                    if (chance <.8) {
                        v.cleanliness = Enums.Cleanliness.Clean;
                        bonusEarned += v.wash_bonus;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Chemical Wash!");
                    }

                }
                if (washtype >= .33 && washtype <= .66){ // elbow grease
                    double chance = Utility.rnd();
                    if (chance >= .95){
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+" for elbow grease wash!");
                    }
                    if (chance <.7) {
                        v.cleanliness = Enums.Cleanliness.Clean;
                        bonusEarned += v.wash_bonus;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+" for elbow grease wash!");
                    }
                    
                }
                if (washtype > .66){ //detail
                    double chance = Utility.rnd();
                    if (chance >= .8){
                        v.cleanliness = Enums.Cleanliness.Sparkling;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Detailed wash!");
                    }
                    if (chance <.6) {
                        v.cleanliness = Enums.Cleanliness.Clean;
                        bonusEarned += v.wash_bonus;
                        out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Detailed wash!");
                    }
                    
                }
                out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);
                if (washCount == 2) break;
            }
        }
        if (washCount<2) {
            for (Vehicle v:vList) {
                // wash the first clean car I see
                if (v.cleanliness == Enums.Cleanliness.Clean) {
                    washCount += 1;
                    double washtype = Utility.rnd();
                    //out("washtype"+washtype);
                    startAs = Enums.Cleanliness.Clean;
                    //-----
                    if (washtype < .33){ // Chemical
                        double chance = Utility.rnd();
                        if (chance >= .9){
                            v.cleanliness = Enums.Cleanliness.Sparkling;
                            out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Chemical Wash!");
                        }
                        if (chance <.2) {
                            v.cleanliness = Enums.Cleanliness.Dirty;
                        }
    
                    }
                    if (washtype >= .33 && washtype <= .66){ // elbow grease
                        double chance = Utility.rnd();
                        if (chance >= .85){
                            v.cleanliness = Enums.Cleanliness.Sparkling;
                            out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Elbow grease Wash!");

                        }
                        if (chance <.15) {
                            v.cleanliness = Enums.Cleanliness.Dirty;
                        }
                        
                    }
                    if (washtype > .66){ //detail
                        double chance = Utility.rnd();
                        if (chance >= .6){
                            v.cleanliness = Enums.Cleanliness.Sparkling;
                            out("Intern "+name+" got a bonus of "+Utility.asDollar(v.wash_bonus)+"for Detailed Wash!");

                        }
                        if (chance <.05) {
                            v.cleanliness = Enums.Cleanliness.Dirty;
                        }
                        
                    }
                    out("Intern "+name+" washed "+v.name+" "+startAs+" to "+v.cleanliness);
                    if (washCount == 2) break;
                }
            }
        }
    }
}

class Mechanic extends Staff {
    static List<String> names = Arrays.asList("James", "Scotty", "Spock", "Uhura");
    static Namer namer = new Namer(names);
    Mechanic() {
        super();
        type = Enums.StaffType.Mechanic;
        name = namer.getNext();  // every new mechanic gets a new name
        salary = 120; // daily salary
    }

    // how Mechanics repair Vehicles - not as complicated as the Wash thing above
    void repairVehicles(ArrayList<Vehicle> vList) {
        int fixCount = 0;
        Enums.Condition startAs;
        // I'm just grabbing the first Vehicle I find - would be easy to randomly pick one
        for (Vehicle v: vList) {
            if (v.condition != Enums.Condition.LikeNew) {
                startAs = v.condition;
                if (v.cleanliness == Enums.Cleanliness.Clean) v.cleanliness = Enums.Cleanliness.Dirty;
                if (v.cleanliness == Enums.Cleanliness.Sparkling) v.cleanliness = Enums.Cleanliness.Clean;
                double chance = Utility.rnd();
                if (chance < .8) {
                    fixCount += 1;
                    if (v.condition == Enums.Condition.Used) {
                        v.condition = Enums.Condition.LikeNew;
                        v.price = v.price * 1.25;  // 25% increase for Used to Like New
                    }
                    if (v.condition == Enums.Condition.Broken) {
                        v.condition = Enums.Condition.Used;
                        v.price = v.price * 1.5;  // 50% increase for Broken to Used
                    }
                    bonusEarned += v.repair_bonus;
                    out("Mechanic "+name+" got a bonus of "+Utility.asDollar(v.repair_bonus)+"!");
                    out("Mechanic "+name+" fixed "+v.name+" "+startAs+" to "+v.condition);
                }
                else {
                    fixCount += 1;   // I'm saying a failed repair still took up a fix attempt
                    out("Mechanic "+name+" did not fix the "+v.condition+" "+v.name);
                }
            }
            if (fixCount==2) break;
        }
    }
}
class Salesperson extends Staff {
    static List<String> names = Arrays.asList("Rachel","Monica","Phoebe","Chandler","Ross","Joey");
    static Namer namer = new Namer(names);
    Salesperson() {
        super();
        type = Enums.StaffType.Salesperson;
        name = namer.getNext();  // every new salesperson gets a new name
        salary = 90; // daily salary
    }

    // Someone is asking this Salesperson to sell to this Buyer
    // We'll return any car we sell for the FNCD to keep track of (null if no sale)
    Vehicle sellVehicle(Buyer b, ArrayList<Vehicle> vList) {
        // buyer type determines initial purchase chance
        double saleChance = .7; // needs one
        if (b.type == Enums.BuyerType.WantsOne) saleChance = .4;
        if (b.type == Enums.BuyerType.JustLooking) saleChance = .1;
        // find the most expensive vehicle of the type the buyer wants that isn't broken
        // sales chance +10% if Like New, + 10% if Sparkling
        // if no vehicles of type, find remaining most expensive vehicle and sell at -20%
        ArrayList<Vehicle> desiredList = Vehicle.getVehiclesByType(vList, b.preference);
        Vehicle v;
        v = getMostExpensiveNotBroken(desiredList);  // could be null
        if (v == null) {
            // no unbroken cars of preferred type
            saleChance -= .2;
            v = getMostExpensiveNotBroken(vList);  // could still be null
        }
        if (v == null) {
            out("Salesperson "+name+" has no car for buyer "+b.name);
            return null;
        }
        else { //sell this car!
            if (v.condition == Enums.Condition.LikeNew) saleChance += .1;
            if (v.cleanliness == Enums.Cleanliness.Sparkling) saleChance += .1;
            double chance = Utility.rnd();
            if (chance<=saleChance) {  // sold!
                bonusEarned += v.sale_bonus;
                out("Buyer "+b.name+" is buying! Salesperson "+name+" gets a bonus of "+Utility.asDollar(v.sale_bonus)+"!");
                out("Buyer "+b.name+" bought "+v.cleanliness+" "+v.condition+" "+v.name+" for "+Utility.asDollar(v.price));
                return v;
            }
            else {  // no sale!
                out("Buyer "+b.name+" decided not to buy.");
                return null;
            }
        }
    }

    // Little helper for finding most expensive and not broken in a list of vehicles
    // Used twice by salespeople
    Vehicle getMostExpensiveNotBroken(ArrayList<Vehicle> vList) {
        double highPrice = 0;
        int selected = -1;
        for (int index=0;index<vList.size();++index) {
            Vehicle v = vList.get(index);
            if (v.price>highPrice) {
                if (v.condition != Enums.Condition.Broken) {
                    selected = index;
                    highPrice = v.price;
                }
            }
        }
        if (selected == -1) return null;
        else return vList.get(selected);
    }
}

class Driver extends Staff {
    static List<String> names = Arrays.asList("Fredrick", "PJ", "Momo", "Carl");
    static Namer namer = new Namer(names);
    int wins;
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        name = namer.getNext();  // every new driver gets a new name
        salary = 150; // daily salary
        wins = 0; //every driver starts out with no wins
    }

    // how Drivers race Vehicles
    Vehicle raceVehicles(ArrayList<Vehicle> vList, String vType) {
         
        //int rand = (int) Math.random(vList.size());
        for(Vehicle v: vList)
        {
            int randomvehicle = Utility.rndFromRange(0,vList.size()-1);

            Vehicle c = (Vehicle) vList.get(randomvehicle);
            if (c.type == Enums.VehicleType.valueOf(vType) && c.condition != Enums.Condition.Broken) {
                return(c);
            }
        }
        out("No more "+vType+" vehicles");
        return null;
    }
}
