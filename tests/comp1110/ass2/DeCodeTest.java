package comp1110.ass2;

import org.junit.Test;
import static comp1110.ass2.WarringStatesGame.decode;
import static junit.framework.TestCase.assertEquals;

public class DeCodeTest {
    @Test
    public void testLetters(){
        int A = 0;
        char a = decode(A);
        assertEquals(a,'A');

        int Z = 25;
        char z = decode(Z);
        assertEquals(z,'Z');

        int D = 3;
        char d = decode(D);
        assertEquals(d,'D');
    }

    @Test
    public void testNumbers(){
        int one = 27;
        char x = decode(one);
        assertEquals(x, '1');

        int nine = 35;
        char y = decode(nine);
        assertEquals(y,'9');

    }
}
