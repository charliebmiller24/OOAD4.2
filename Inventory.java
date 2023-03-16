import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Inventory implements Command{
    ArrayList<Vehicle> vehicle;
    public void inventory(ArrayList<Vehicle> vehicle){
        this.vehicle = vehicle;
    }

    public void execute(){
        Console cons = System.console();
        int it = 0;
        for(Vehicle v: vehicle){
            cons.printf("Number "+ it + ": "+v.name + "\n");
            it++;
        }
    }
}