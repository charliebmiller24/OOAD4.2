import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Details implements Command{
    Vehicle v;
    Console cons = System.console();
    public void details(ArrayList<Vehicle> vList){
        cons.printf("Select a number to know more details. \n");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        this.v = vList.get(a);
    }

    public void execute(){
        cons.printf("Details: "+ v.cleanliness+" "+v.condition+" "+v.name+" for "+Utility.asDollar(v.price)+"\n");
    }
}