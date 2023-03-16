import java.io.Console;

class Exit implements Command{
    Console cons = System.console();
    public void execute(){
        cons.printf("We hope our service met your standards. Please come again!\n");
        cons.printf("\n");
    }
}