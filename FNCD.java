import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.Console;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

// This represents the FNCD business and things they would control
public class FNCD implements SysOut {
    ArrayList<Staff> staff;  // folks working
    ArrayList<Staff> departedStaff;   // folks that left
    ArrayList<Vehicle> inventory;   // vehicles at the FNCD
    ArrayList<Vehicle> soldVehicles;   // vehicles the buyers bought

    private double budget;   // big money pile
    FNCD() {
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        soldVehicles = new ArrayList<>();
        budget = 100000;  // I changed this just to see additions to the budget happen
    }
    double getBudget() {
        return budget;    // I'm keeping this private to be on the safe side
    }
    void moneyIn(double cash) {  // Nothing special about taking money in yet
        budget += cash;
    }
    void moneyOut(double cash) {   // I check for budget overruns on every payout
        budget -= cash;

        if (budget<0) {
            budget += 250000;
            out("***Budget overrun*** Added $250K, budget now: " + Utility.asDollar(budget));
        }
    }

    int totalStaffSalary;

    void calculateStaffSal(){
        for(Staff s: staff){
            totalStaffSalary += s.salaryEarned;
            totalStaffSalary += s.bonusEarned;
        }
    }

    void resetTotalStaffSalary() {
        totalStaffSalary = 0;
    }

    int getTotalStaffSalary(){
        return totalStaffSalary;
    }

    // Here's where I process daily activities
    // I debated about moving the individual activities out to an Activity class
    // It would make the normal day less of a monster maybe, eh...

    void closedDay(Enums.DayOfWeek day) {   // Nothing really special about closed days
        out("Sorry, FNCD is closed on "+day);
    }
    
    void raceday()
    {
        ArrayList<Vehicle> tempRaceArray = new ArrayList<Vehicle>(); 
        ArrayList<Staff> tempDriverArray = new ArrayList<Staff>();  

        out("The FNCD drivers are racing...");
        ArrayList<Staff> drivers = Staff.getStaffByType(staff, Enums.StaffType.Driver);
        ArrayList<String> carType = new ArrayList<String>(Arrays.asList("PerfCar", "Pickup", "Motercycle", "Monstertruck"));

        //picks a random number 0 through 5
        int rand = Utility.rndFromRange(0, 3);
        //sends 3 drivers to race 3 vehicles of the same type

        ArrayList<Integer> rankings = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)); //possible rankings
        rankings.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
        int threeRacers = 0;
        for (Staff s:drivers) {
            drivers = Staff.getStaffByType(staff, Enums.StaffType.Driver);
            int randomDriver = Utility.rndFromRange(0, drivers.size()-1);
            Driver d = (Driver) drivers.get(randomDriver);
            Vehicle vehic = d.raceVehicles(inventory, carType.get(rand)); //sends in our inventory along with the car type for that race, and it returns vehicle. Picks same vehicle for all drivers
            if(vehic == null || drivers.size() == 0){
                break;
            }
            tempRaceArray.add(vehic);
            tempDriverArray.add(d);
            staff.remove(d);
            inventory.remove(vehic);
            int randNum = (int) Utility.rndFromRange(0, rankings.size()-1);
            int placement = rankings.get(randNum); //gets a random number out of 20 always picks 1,2,3
            rankings.remove(randNum);

            out("Driver "+d.name+" finished in "+placement+" place with "+vehic.name+" "+vehic.type);

            //if placed in the top three else if placed last 5
            if(placement <= 3){
                vehic.wins++;
                vehic.price = vehic.price * 1.10; //vehicle increases by 10%
                d.wins++;
                d.bonusEarned += vehic.race_bonus;
                out("Driver "+d.name+" got a bonus of "+Utility.asDollar(vehic.race_bonus)+"!");
            }
            else if(placement >=15){
                vehic.condition = Enums.Condition.Broken;
                //30% driver gets injured
                if(Math.random() <= .3){
                    out("Driver "+d.name+" got injured in todays race.");
                    //this is so we don't add them back into the array since they got hurt.
                    tempDriverArray.remove(d);
                    departedStaff.add(d);
                }
            }
            threeRacers++;
            if(threeRacers == 3){
                break;
            }

        }
        for(int i = 0; i < tempRaceArray.size()-1; i++){
            inventory.add(tempRaceArray.get(i));
            //drivers.add(tempDriverArray.get(i));
        }
        for(int i = 0; i < tempDriverArray.size()-1; i++){
            //inventory.add(tempRaceArray.get(i));
            staff.add(tempDriverArray.get(i));
        }

    }

    void normalDay(Enums.DayOfWeek day, int numDays) {  // On a normal day, we do all the activities

        // opening
        out("The FNCD is opening...");
        hireNewStaff();    // hire up to 3 of each staff type
        updateInventory();  // buy up to 4 of each type

        // washing - tell the interns to do the washing up
        out("The FNCD interns are washing...");
        ArrayList<Staff> interns = Staff.getStaffByType(staff, Enums.StaffType.Intern);
        for (Staff s:interns) {
            Intern i = (Intern) s;
            i.washVehicles(inventory);
        }

        // repairing - tell the mechanics to do their repairing
        out("The FNCD mechanics are repairing...");
        ArrayList<Staff> mechanics = Staff.getStaffByType(staff, Enums.StaffType.Mechanic);
        for (Staff s:mechanics) {
            Mechanic m = (Mechanic) s;
            m.repairVehicles(inventory);
        }

        // selling
        out("The FNCD salespeople are selling...");
        ArrayList<Buyer> buyers = getBuyers(day);
        ArrayList<Staff> salespeople = Staff.getStaffByType(staff, Enums.StaffType.Salesperson);
        ArrayList<Vehicle> vehicle = inventory;
        Console cons = System.console();

        //cons.printf("What location are you at? \n");
        //cons.printf("Input a number. North - 1 and South - 2. \n");

        if(numDays == 31){
            String s;
            Scanner in = new Scanner(System.in);

            int randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
            Salesperson seller = (Salesperson) salespeople.get(randomSeller);

            int randomBuyer = Utility.rndFromRange(0,buyers.size()-1);
            Buyer b = (Buyer) buyers.get(randomBuyer);



            int nu = 0;
            while(nu != 8){
                cons.printf("\n");
                cons.printf("MENU \n");
                cons.printf("1) FNCD North or South? \n");
                cons.printf("2) Ask the Salesperson their name \n");
                cons.printf("3) Ask what time it is \n");
                cons.printf("4) Ask for a different Salesperson \n");
                cons.printf("5) Ask for the current inventory \n");
                cons.printf("6) Ask the Salesperson for all details on car \n");
                cons.printf("7) Buy a car! \n");
                cons.printf("8) Quit \n");
                cons.printf("\n");

                nu = in.nextInt();

                switch(nu){
                    case 1:
                        FNCDNorS loc = new FNCDNorS();
                        loc.NorS();
                        loc.execute();
                        //Doesn't do anything as of now
                        break;
                    case 2:
                        SalesName name = new SalesName();
                        name.name(seller);
                        name.execute(); //send salesperson and get name back
                        break;
                    case 3:
                        Time time = new Time();
                        time.execute();
                        break;
                    case 4:
                        //fix
                        DifferentSalesperson diffSale = new DifferentSalesperson();
                        seller = diffSale.newPerson(salespeople);
                        diffSale.execute();
                        break;
                    case 5:
                        Inventory inv = new Inventory();
                        inv.inventory(vehicle);
                        inv.execute();
                        break;
                    case 6:
                        Details detail = new Details();
                        detail.details(vehicle);
                        detail.execute();
                        break;
                    case 7:
                        Buy buy = new Buy();
                        Vehicle vSold = buy.buying(vehicle);
                        buy.execute();
                        if (vSold != null) {
                            soldVehicles.add(vSold);
                            moneyIn(vSold.price);
                            inventory.removeIf ( n -> n.name == vSold.name);
                        }
                        break;
                    case 8:
                        Exit exit = new Exit();
                        exit.execute();
                        break;
                }
            }






            /*
            cons.printf("What is your name? \n");
            cons.printf("Hello I am your salesperson today. My name is "+ seller.name+ " \n");
            //s = in.nextLine();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            cons.printf("What time is it? \n");
            cons.printf("The date and time is "+ dtf.format(now) +" \n");
            //s = in.nextLine();

            int nu = 1;
            while(nu == 1){
                cons.printf("Would you like a different sales person? \n");
                cons.printf("Input a number. Yes - 1 and No - 2 \n");
                nu = in.nextInt();
                randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
                seller = (Salesperson) salespeople.get(randomSeller);
                cons.printf("New salesperson:"+seller.name+" \n");
            }

            cons.printf("What is your inventory? \n");
            //cons.printf(inventory + "\n");
            int it = 1;
            for(Vehicle v: inventory){
                cons.printf("Number "+ it + ": "+v.name + "\n");
                it++;
            }
            //s = in.nextLine();

            cons.printf("Select a number to know more details. \n");
            int a = in.nextInt();
            Vehicle vSold = seller.sellVehicleInput(b, inventory, a);
            */
        }

        //cons.printf("Thank you for visiting FNCD \n"); //maybe add north or south



        // tell a random salesperson to sell each buyer a car - may get bonus
        for(Buyer b: buyers) {
            out("Buyer "+b.name+" wants a "+b.preference+" ("+b.type+")");
            int randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
            Salesperson seller = (Salesperson) salespeople.get(randomSeller);
            Vehicle vSold = seller.sellVehicle(b, inventory);
            // What the FNCD needs to do if a car is sold - change budget and inventory
            if (vSold != null) {
                soldVehicles.add(vSold);
                moneyIn(vSold.price);
                inventory.removeIf ( n -> n.name == vSold.name);
            }
        }


        //Race drivers all use the same car and always some in 1st 2nd 3rd. 
        //racing

        // ending
        // pay all the staff their salaries
        payStaff();
        // anyone quitting? replace with an intern (if not an intern)
        checkForQuitters();
        // daily report
        reportOut();
    }

    // generate buyers
    ArrayList<Buyer> getBuyers(Enums.DayOfWeek day) {
        // 0 to 5 buyers arrive (2-8 on Fri/Sat)
        int buyerMin = 0;  //normal Mon-Thur
        int buyerMax = 5;
        if (day == Enums.DayOfWeek.Fri || day == Enums.DayOfWeek.Sat) {
            buyerMin = 2;
            buyerMax = 8;
        }
        ArrayList<Buyer> buyers = new ArrayList<Buyer>();
        int buyerCount = Utility.rndFromRange(buyerMin,buyerMax);
        for (int i=1; i<=buyerCount; ++i) buyers.add(new Buyer());
        out("The FNCD has "+buyerCount+" buyers today...");
        return buyers;
    }

    // see if we need any new hires
    void hireNewStaff() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    // adding staff
    // smells like we need a factory or something...
    void addStaff(Enums.StaffType t) {
        Staff newStaff = null;
        if (t == Enums.StaffType.Intern) newStaff = new Intern();
        if (t == Enums.StaffType.Mechanic) newStaff = new Mechanic();
        if (t == Enums.StaffType.Salesperson) newStaff = new Salesperson();
        if (t == Enums.StaffType.Driver) newStaff = new Driver();
        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }

    // see if we need any vehicles
    void updateInventory() {
        final int numberInInventory = 6;
        for (Enums.VehicleType t : Enums.VehicleType.values()) {
            int typeInList = Vehicle.howManyVehiclesByType(inventory, t);
            int need = numberInInventory - typeInList;
            for (int i = 1; i<=need; ++i) addVehicle(t);
        }

    }

    // add a vehicle of a type to the inventory
    void addVehicle(Enums.VehicleType t) {
        Vehicle v = null;
        if (t == Enums.VehicleType.Car) v = new Car();
        if (t == Enums.VehicleType.PerfCar) v = new PerfCar();
        if (t == Enums.VehicleType.Pickup) v = new Pickup();
        if (t == Enums.VehicleType.Electric) v = new Electric();
        if (t == Enums.VehicleType.Motercycle) v = new Motorcycle();
        if (t == Enums.VehicleType.Monstertruck) v = new Monstertruck();
        if (t == Enums.VehicleType.Bus) v = new Bus();
        if (t == Enums.VehicleType.Boat) v = new Boat();
        if (t == Enums.VehicleType.Semi) v = new Semi();
        moneyOut(v.cost);  // pay for the vehicle
        out ("Bought "+v.name+", a "+v.cleanliness+" "+v.condition+" "+v.type+" for "+Utility.asDollar(v.cost));
        inventory.add(v);
    }

    // pay salary to staff and update days worked
    void payStaff() {
        for (Staff s: staff) {
            moneyOut(s.salary);  // money comes from the FNCD
            s.salaryEarned += s.salary;  // they get paid
            s.daysWorked += 1; // they worked another day
        }
    }

    // Huh - no one wants to quit my FNCD!
    // I left this as an exercise to the reader...
    void checkForQuitters() {
        boolean someoneQuit = false;

        Random rand = new Random();

        int internQuit = rand.nextInt(100);
        int salesQuit = rand.nextInt(100);
        int mechanicQuit = rand.nextInt(100);

        if(internQuit <= .1){
            ArrayList<Staff> interns = Staff.getStaffByType(staff, Enums.StaffType.Intern);
            int randomIntern = Utility.rndFromRange(0, interns.size()-1);
            Intern i = (Intern) interns.get(randomIntern);
            departedStaff.add(i);
            staff.remove(i);
            someoneQuit = true;
            out(i.name + "(Intern) quit.");
        }

        if(salesQuit <= .1){
            ArrayList<Staff> sales = Staff.getStaffByType(staff, Enums.StaffType.Salesperson);
            int randomSales = Utility.rndFromRange(0, sales.size()-1);
            Salesperson s = (Salesperson) sales.get(randomSales);
            departedStaff.add(s);
            staff.remove(s);
            out(s.name + "(Salesperson) quit.");

            //Promoting an intern
            for(Staff staf: staff){
                  if(staf.type == Enums.StaffType.Intern){
                      Salesperson sal = new Salesperson();
                      sal.name = staf.name;
                      sal.salaryEarned = staf.salaryEarned;
                      sal.bonusEarned = staf.bonusEarned;
                      sal.daysWorked = staf.daysWorked;
                      //staf.type = Enums.StaffType.Mechanic;
                      out(sal.name+" has got promoted to "+sal.type+"!");
                      break;
                  }
              }

            someoneQuit = true;
        }

        if(mechanicQuit <= .1){
            ArrayList<Staff> mech = Staff.getStaffByType(staff, Enums.StaffType.Mechanic);
            int randomMech = Utility.rndFromRange(0, mech.size()-1);
            Mechanic m = (Mechanic) mech.get(randomMech);
            departedStaff.add(m);
            staff.remove(m);
            out(m.name + "(Mechanic) quit.");

            //Promoting an intern
            for(Staff staf: staff){
                if(staf.type == Enums.StaffType.Intern){
                    Mechanic mec = new Mechanic();
                    mec.name = staf.name;
                    mec.salaryEarned = staf.salaryEarned;
                    mec.bonusEarned = staf.bonusEarned;
                    mec.daysWorked = staf.daysWorked;
                    //staf.type = Enums.StaffType.Mechanic;
                    out(mec.name+" has got promoted to "+mec.type+"!");
                    break;
                }
            }
            someoneQuit = true;
        }

        if(someoneQuit == false){
            out("No-one on the staff is leaving!");
        }
        // I would check the percentages here
        // Move quitters to the departedStaff list
        // If an intern quits, you're good
        // If a mechanic or a salesperson quits
        // Remove an intern from the staff and use their properties to
        // create the new mechanic or salesperson
    }

    void reportOut() {
        // We're all good here, how are you?
        // Quick little summary of happenings, you could do better

        out("Vehicles in inventory "+inventory.size());
        out("Vehicles sold count "+soldVehicles.size());
        out("Money in the budget "+ Utility.asDollar(getBudget()));
        out("That's it for the day.");
    }
}
