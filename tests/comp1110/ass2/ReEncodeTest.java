package comp1110.ass2;

import org.junit.Test;
import static comp1110.ass2.WarringStatesGame.reEncode;
import static org.testng.Assert.assertEquals;

public class ReEncodeTest {
    @Test
    public void testLetters(){
        char A = 'A';
        int a = reEncode(A);
        assertEquals(a,0);

        char Z = 'Z';
        int z = reEncode(Z);
        assertEquals(z,25);

        char D = 'D';
        int d = reEncode(D);
        assertEquals(d,3);
    }

    @Test
    public void testNumbers(){
        char one = '1';
        int x = reEncode(one);
        assertEquals(x,27);

        char nine = '9';
        int y = reEncode(nine);
        assertEquals(y,35);

    }
}
