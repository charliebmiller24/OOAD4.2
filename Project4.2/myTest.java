import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class myTest {

    @Test
    public void firstTest() {
        Intern intern = new Intern();
        assertEquals(60, intern.salary);
    }
}
