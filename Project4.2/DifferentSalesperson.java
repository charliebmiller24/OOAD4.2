import java.io.Console;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DifferentSalesperson implements Command{
    Salesperson seller;
    Console cons = System.console();

    Salesperson newPerson(ArrayList<Staff> salespeople){

        int randomSeller = Utility.rndFromRange(0,salespeople.size()-1);
        seller = (Salesperson) salespeople.get(randomSeller);
        this.seller = seller;
        return seller;

    }

    public void execute(){
        cons.printf("New Salesperson: "+seller.name+"\n");
    }
}