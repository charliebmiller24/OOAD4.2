import java.io.Console;

class SalesName implements Command{
    Salesperson salesperson;
    public void name(Salesperson salesperson){
        this.salesperson = salesperson;
    }

    public void execute(){
        Console cons = System.console();
        cons.printf("Hello I am your salesperson today. My name is "+ salesperson.name+ ". \n");
    }
}