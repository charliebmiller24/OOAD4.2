import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class FNCDNorS implements Command{
    String location;
    Console cons = System.console();
    public void NorS(){
        cons.printf("North or South location? \n");
        cons.printf("1 - North and 2 - South \n");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        if(a == 1){
            location = "North";
        }
        if(a == 2){
            location = "South";
        }
    }

    public void execute(){
        cons.printf("Welcome to FNCD "+ location+"\n");
    }
}