import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Observer{
    void subscribe(int day, String location){
        try{
            String fileName = "Logger-"+ day + location + ".txt";
            FileWriter file = new FileWriter(fileName);
            PrintStream fileStream = new PrintStream(fileName);
            System.setOut(fileStream);
        }
        catch(IOException e){
            System.out.println("We have a problem");
        }
    }
}