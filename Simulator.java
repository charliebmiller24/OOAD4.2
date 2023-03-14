// Simulator to cycle for select number of days
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Console;

public class Simulator implements SysOut {
    final int numDays;
    Enums.DayOfWeek dayOfWeek;
    Simulator() {
        numDays = 30;  // magic number for days to run here
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);  // we'll start on a random day (for fun)
    }

    // cycling endlessly through enum values
    // https://stackoverflow.com/questions/34159413/java-get-next-enum-value-or-start-from-first
    public Enums.DayOfWeek getNextDay(Enums.DayOfWeek e)
    {
        int index = e.ordinal();
        int nextIndex = index + 1;
        Enums.DayOfWeek[] days = Enums.DayOfWeek.values();
        nextIndex %= days.length;
        return days[nextIndex];
    }

    void run() {
        FNCD fncdNorth = new FNCD();
        FNCD fncdSouth = new FNCD();
        for (int day = 1; day <= numDays; ++day) {
            try{
                String fileName = "Logger-"+ day + "North" + ".txt";
                FileWriter file = new FileWriter(fileName);
                PrintStream fileStream = new PrintStream(fileName);
                System.setOut(fileStream);
                out(">>> Start Simulation Day "+day+" "+dayOfWeek);
                //System.out.println("-------------" + dayOfWeek + "-------------------");
                if(dayOfWeek == Enums.DayOfWeek.Sun || dayOfWeek == Enums.DayOfWeek.Wed){
                    fncdNorth.raceday();
                }
                if (dayOfWeek == Enums.DayOfWeek.Sun) fncdNorth.closedDay(dayOfWeek);  // no work on Sunday
                else fncdNorth.normalDay(dayOfWeek);  // normal stuff on other days
                out(">>> End Simulation Day "+day+" "+dayOfWeek+"\n");
                
                dayOfWeek = getNextDay(dayOfWeek);  // increment to the next day
                file.close();

                Console cons = System.console();
                cons.printf("Tracker North: Day "+day+" \n");
                fncdNorth.calculateStaffSal();
                cons.printf("Total Money Earned By All Staff: "+Utility.asDollar(fncdNorth.getTotalStaffSalary())+" \n");
                fncdNorth.resetTotalStaffSalary();
                cons.printf("Total Money Earned By the FNCD: "+Utility.asDollar(fncdNorth.getBudget())+"\n");
                cons.printf("\n");
                cons.flush();
            }
            catch(IOException e){
                System.out.println("We have a problem");
                
            }
            try{
                String fileName = "Logger-"+ day + "South" + ".txt";
                FileWriter file = new FileWriter(fileName);
                PrintStream fileStream = new PrintStream(fileName);
                System.setOut(fileStream);
                out(">>> Start Simulation Day "+day+" "+dayOfWeek);
                //System.out.println("-------------" + dayOfWeek + "-------------------");
                if(dayOfWeek == Enums.DayOfWeek.Sun || dayOfWeek == Enums.DayOfWeek.Wed){
                    fncdSouth.raceday();
                }
                if (dayOfWeek == Enums.DayOfWeek.Sun) fncdSouth.closedDay(dayOfWeek);  // no work on Sunday
                else fncdSouth.normalDay(dayOfWeek);  // normal stuff on other days
                out(">>> End Simulation Day "+day+" "+dayOfWeek+"\n");
                
                dayOfWeek = getNextDay(dayOfWeek);  // increment to the next day
                file.close();

                Console cons = System.console();
                cons.printf("Tracker South: Day "+day+" \n");
                fncdSouth.calculateStaffSal();
                cons.printf("Total Money Earned By All Staff: "+Utility.asDollar(fncdSouth.getTotalStaffSalary())+" \n");
                fncdSouth.resetTotalStaffSalary();
                cons.printf("Total Money Earned By the FNCD: "+Utility.asDollar(fncdSouth.getBudget())+"\n");
                cons.printf("\n");
                cons.flush();
            }
            catch(IOException e){
                System.out.println("We have a problem");
                
            }


        }
    }
}
