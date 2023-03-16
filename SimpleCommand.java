import java.io.Console;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleCommand{
    Command test;
    public SimpleCommand(){}

    public void setCommand(Command command){
        test = command;
    }

    public void ButtonPressed(){
        test.execute();
    }

}