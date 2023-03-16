import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Buy implements Command{
    Vehicle v;
    Console cons = System.console();
    Scanner in = new Scanner(System.in);
    Vehicle buying(ArrayList<Vehicle> vList){
        cons.printf("Which car number would you like to buy? \n");
        int a = in.nextInt();
        cons.printf("Would you like to buy this car? 1 - Yes and 2 - No\n");
        a = in.nextInt();
        this.v = vList.get(a-1);
        if(a == 1){
            System.out.println("You just bought "+v.cleanliness+" "+v.condition+" "+v.name+" for "+Utility.asDollar(v.price));
        }
        addOns(v);
        return v;
    }

    public void addOns(Vehicle v){
        cons.printf("Would you like to buy add-ons? 1 - Yes and 2 - No \n");
        int b = in.nextInt();
        if(b == 1){

            while(b != 0){
                cons.printf("What add-ons would you like? \n"); //might need to do a for loop
                cons.printf("Input a number. Exit - 0, Satellite Radio - 1, Extended Warrenty - 2, Undercoating - 3, or Road Rescue Coverage - 4. \n");
                b = in.nextInt();
                if(b == 1){
                    v = new SatelliteRadio(v);
                    System.out.println("Added a satellite radio, increasing the price to "+Utility.asDollar(v.price));
                }
                if(b == 2){
                    v = new ExtendedWarrenty(v);
                    System.out.println("Added an extended warrenty, increasing the price to "+Utility.asDollar(v.price));
                }
                if(b == 3){
                    v = new UnderCoating(v);
                    System.out.println("Added an undercoating, increasing the price to "+Utility.asDollar(v.price));
                }
                if(b == 4){
                    v = new RRC(v);
                    System.out.println("Added a road rescue coverage, increasing the price to "+Utility.asDollar(v.price));
                }
            }
        }
        if(b == 2){
            System.out.println("No add-ons is alright.");
        }
    }

    public void execute(){
        cons.printf("Transaction Complete. \n");
    }
}