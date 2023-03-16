import java.io.Console;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class Time implements Command{

    public void execute(){
        Console cons = System.console();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        cons.printf("What time is it? \n");
        cons.printf("The date and time is "+ dtf.format(now) +" \n");

    }
}