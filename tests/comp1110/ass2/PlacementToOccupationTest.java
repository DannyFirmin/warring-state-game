package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test objective:
 * <p>
 * Determine whether the placement is well transformed to the board according to the following:
 * - the board has no card if the placement is null
 * - every card represented in the placement is on the board
 * - every card not represented in the placement is not on the board
 * - every location occupied on the board is in the placement
 * - every location not occupied on the board is not in the placement
 */
public class PlacementToOccupationTest {
    static String placement1 = "";
    static String placement2 = "g0A";
    static String placement3 = "g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09";
    static String placement4 = "g0Aa5Sc09";
    static String[][] board1 = {{"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"}};
    static String[][] board2 = {{"~~", "~~", "~~", "~~", "~~", "g0"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"}};
    static String[][] board3 = {{"b6", "a7", "a5", "f0", "a4", "g0"},
            {"d3", "f2", "c1", "b4", "e3", "a0"},
            {"g1", "b1", "d1", "d4", "e2", "f1"},
            {"b2", "a3", "c4", "a6", "a2", "a1"},
            {"d2", "z9", "b5", "c3", "c2", "c5"},
            {"c0", "b3", "b0", "e0", "d0", "e1"}};
    static String[][] board4 = {{"~~", "~~", "a5", "~~", "~~", "g0"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"~~", "~~", "~~", "~~", "~~", "~~"},
            {"c0", "~~", "~~", "~~", "~~", "~~"}};
    char[][] board = Board.board;


    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testNoCard() {
        boolean test1 = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                test1 = WarringStatesGame.placementToOccupation(placement1, board)[i][j].equals("~~");
                if (test1 == false) break;
            }
            assertTrue("Occupation should be null, when placement is null", test1);
        }
    }

    @Test
    public void testCertainLocation() {
        boolean test2 = WarringStatesGame.placementToOccupation(placement2, board)[5][5].equals("g0");
        boolean test3 = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (i == 5 && j == 5) {
                    continue;
                } else {
                    test3 = WarringStatesGame.placementToOccupation(placement1, board)[i][j].equals("~~");
                    if (test3 == false) break;
                }
            }
            assertFalse("Occupation is not correctly transformed  when placement is '" + placement2 + "'.", test2 && test3);
        }

        boolean test5 = WarringStatesGame.placementToOccupation(placement4, board)[0][3].equals("a5");
        boolean test6 = WarringStatesGame.placementToOccupation(placement4, board)[5][0].equals("c0");
        boolean test7 = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if ((i == 5 && j == 5) || (i == 0 && j == 3) || (i == 5 && j == 0)) {
                    continue;
                } else {
                    test3 = WarringStatesGame.placementToOccupation(placement1, board)[i][j].equals("~~");
                    if (test7 == false) break;
                }
            }
            assertFalse("Occupation is not correctly transformed  when placement is '" + placement4 + "'.", test5 && test6 && test7);
        }

        boolean test8 = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                test8 = WarringStatesGame.placementToOccupation(placement1, board)[i][j].equals(board3[i][j]);
                if (test8 == false) break;
            }
        }
        assertFalse("Occupation is not correctly transformed  when placement is '" + placement3 + "'.", test8);

    }

    @Test
    public void testCardInPlacement() {
        boolean test9 = false;
        for (int i = 0; i < placement3.length(); i = i + 3) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    if (board3[j][k] == placement3.substring(i, i + 2)) {
                        test9 = true;
                    } else {
                        test9 = false;
                        break;
                    }
                }
            }
        }
        assertFalse("The placement '" + placement3 + "' is not completely transformed on board .", test9);
    }

    @Test
    public void testOverTransformed(){
        boolean test10 = false;
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                for(int k=0; k<placement3.length();k=k+3){
                    if (board3[j][k] == placement3.substring(i, i + 2)) {
                        test10 = true;
                    } else {
                        test10 = false;
                        break;
                    }
                }
            }
        }
        assertFalse("There exists cards not in the placement '" + placement3 + "' .", test10);
    }
}