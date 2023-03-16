// OOAD Spring 2023 Project 2
// Friendly Neighborhood Car Dealership (FNCD)
// Bruce Montgomery - 2/19/23
// I spent about six hours on this monster...

public class Main {
    public static void main(String[] args) {
        // bootstrap code only - no logic
        myTest t = new myTest();
        t.firstTest();
        Simulator sim = new Simulator();
        sim.run();  // Let's do this thing
    }
}