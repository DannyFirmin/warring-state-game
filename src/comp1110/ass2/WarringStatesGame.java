package comp1110.ass2;


import java.util.ArrayList;

/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {

    /**
     * Determine whether a card placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range a..g (kingdom) or is z (Zhang Yi)
     * - the second character is numeric, and is a valid character number for that kingdom (9 for Zhang Yi)
     * - the third character is in the range A .. Z or 0..9 (location)
     *
     * @param cardPlacement A string describing a card placement
     * @return true if the card placement is well-formed
     */
    static boolean isCardPlacementWellFormed(String cardPlacement) {
        // FIXME Task 2: determine whether a card placement is well-formed
        boolean d = false;
        if (cardPlacement.length() != 3) {
            d = false;
        } else {
            char a = cardPlacement.charAt(0);
            char b = cardPlacement.charAt(1);
            char c = cardPlacement.charAt(2);

            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                switch (a) {
                    case 'a':
                        if (b >= '0' && b <= '7') {
                            d = true;
                        }
                    case 'b':
                        if (b >= '0' && b <= '6') {
                            d = true;
                        }
                    case 'c':
                        if (b >= '0' && b <= '5') {
                            d = true;
                        }
                    case 'd':
                        if (b >= '0' && b <= '4') {
                            d = true;
                        }
                    case 'e':
                        if (b >= '0' && b <= '3') {
                            d = true;
                        }
                    case 'f':
                        if (b >= '0' && b <= '2') {
                            d = true;
                        }
                    case 'g':
                        if (b >= '0' && b <= '1') {
                            d = true;
                        }
                    case 'z':
                        if (b == '9') {
                            d = true;
                        }
                }
            } else {
                d = false;
            }
        }
        return d;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N three-character card placements (where N = 1 .. 36);
     * - each card placement is well-formed
     * - no card appears more than once in the placement
     * - no location contains more than one card
     *
     * @param placement A string describing a placement of one or more cards
     * @return true if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        boolean b = true;
        if (placement != null && placement.length() >= 3 && placement.length() <= 108 && placement.length() % 3 == 0) {
            for (int i = 0; i < placement.length() / 3 & b == true; i++) {
                String sub_i = placement.substring(3 * i, 3 * i + 3);
                String sub_i_1 = sub_i.substring(0, 2);
                String sub_i_2 = sub_i.substring(2);
                if (isCardPlacementWellFormed(sub_i) == true) {
                    if (placement.length() == 3) {
                        b = true;
                    } else {
                        for (int j = i + 1; j < placement.length() / 3; j++) {
                            String sub_j = placement.substring(3 * j, 3 * j + 3);
                            String sub_j_1 = sub_j.substring(0, 2);
                            String sub_j_2 = sub_j.substring(2);
                            if ((sub_j_1.equals(sub_i_1)) || (sub_j_2.equals(sub_i_2))) {
                                b = false;
                                break;
                            } else {
                                b = true;
                            }
                        }
                    }
                } else {
                    b = false;
                    break;
                }
            }
        } else {
            b = false;
        }
        return b;
    }

    /**
     * Change the board encode all to numbers from 0-35
     *
     * @param origin the origin board encode
     * @return newcode the new number encode
     */
    public static int reEncode(char origin) {
        int newcode = 0;
        if (origin >= 'A' && origin <= 'Z') {
            newcode = origin - 65;
        } else if (origin >= '0' && origin <= '9') {
            newcode = origin - 22;
        }
        return newcode;
    }

    public static char decode(int encode) {
        if (encode >= 0 && encode <= 25) {
            encode = encode + 65;
            if (encode >= 26 && encode <= 35) {
                encode = encode + 22;
            }
        }
        char c = (char) encode;
        return c;

    }


    /**
     * Determine whether a given move is legal given a provided valid placement:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the location is in the same row or column of the grid as Zhang Yi's current position; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * that are further away from Zhang Yi.
     *
     * @param placement    the current placement string
     * @param locationChar a location for Zhang Yi to move to
     * @return true if Zhang Yi may move to that location
     */
    public static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal
        boolean result = false;//return
        char b;
        char c = '0'; //Zhangyi's 3rd card
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
        int lrow = 0;//destination's row on board
        int lcol = 0;//destination's column on board
        char kingdom = 'z';//kingdom card of destination
        boolean hasCard = false;//whether destination has card
        boolean noFurther = true;//whether further card of same kingdom

        char[][] board = Board.board;

        String[][] occupation = placementToOccupation(placement, board);

        //find Zhangyi
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i) == 'z') {
                b = placement.charAt(i + 1);
                c = placement.charAt(i + 2);
                //break;
            }
        }

        //find Zhangyi on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == c) {
                    zrow = i;
                    zcol = j;
                    //break;
                }
            }
        }

        //whether there's card on location
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i + 2) == locationChar) {
                hasCard = true;
                //break;
            }
        }

        //find the kingdom card on the destination
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == locationChar) {
                    kingdom = occupation[i][j].charAt(0);
                }
            }
        }

        //find destination on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == locationChar) {
                    lrow = i;
                    lcol = j;
                }
            }
        }

        //whether furthest kingdom
        if (zrow == lrow) {
            if (zcol > lcol) {
                for (int i = lcol - 1; i >= 0; i--) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        noFurther = false;
                        //break;
                    }
                }
            } else {
                for (int i = lcol + 1; i < 6; i++) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        noFurther = false;
                        //break;
                    }
                }
            }
        }
        if (zcol == lcol) {
            if (zrow > lrow) {
                for (int i = lrow - 1; i >= 0; i--) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        noFurther = false;
                        //break;
                    }
                }
            } else {
                for (int i = lrow + 1; i < 6; i++) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        noFurther = false;
                        //break;
                    }
                }
            }
        }


        if ((locationChar <= '9' && locationChar >= '0') || (locationChar >= 'A' && locationChar <= 'Z')) {//in the range
            if (hasCard) {
                if (zrow == lrow || zcol == lcol) {//same row or column
                    if (noFurther) {
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    static public String[][] placementToOccupation(String placement, char[][] board) {
        String[][] occupation = new String[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                occupation[i][j] = "~~";
            }
        }
        for (int i = 2; i < placement.length(); i = i + 3) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    if (board[j][k] == placement.charAt(i)) {
                        occupation[j][k] = placement.substring(i - 2, i);
                    }
                }
            }
        }
        return occupation;
    }


    /**
     * Determine whether a move sequence is valid.
     * To be valid, the move sequence must be comprised of 1..N location characters
     * showing the location to move for Zhang Yi, and each move must be valid
     * given the placement that would have resulted from the preceding sequence
     * of moves.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @return True if the placement sequence is valid
     */
    static boolean isMoveSequenceValid(String setup, String moveSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        int zi = 0; //zhangyi's index
        int li = 0; //location's index
        char l; //destination's board location char
        char z = '0';//zhangyi's board location char
//        int zrow = '0';
//        int zcol = '0';
//        int lrow = '0';
//        int lcol = '0';
        boolean result = true;


        for (int i = 0; i < moveSequence.length(); i++) {


            //legal move
            if (isMoveLegal(setup, moveSequence.charAt(i))) {
                l = moveSequence.charAt(i);//where to go now, take the location char

                //find Zhangyi
                for (int j = 0; j < setup.length() - 3; j = j + 3) {
                    if (setup.charAt(j) == 'z') {
                        zi = j;//zhangyi's current index
                        z = setup.charAt(j + 2);//zhangyi's current board location char
                    }
                }

                //find location char in setup string
                for (int j = 2; j < setup.length(); j = j + 3) {
                    if (setup.charAt(j) == l) {
                        li = j;//location's current index
                    }
                }

                if (li < zi) { //if zhangyi goes left in setup string
                    StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3).insert(li - 2, setup.substring(zi, zi + 2)).delete(li, li + 2);
                    setup = sb.toString();
                }

                if (li < zi) { //if zhangyi goes right in setup string
                    StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3).insert(li - 5, setup.substring(zi, zi + 2)).delete(li - 3, li - 1);
                    setup = sb.toString();
                }

            } else {
                result = false;
            }
        }
        return result;
    }
//        //find Zhangyi on board
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 6; j++) {
//                if (board[i][j] == c) {
//                    zrow = i;
//                    zcol = j;
//                }
//            }
//        }
//
//        //find destination on board
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 6; j++) {
//                if (board[i][j] == moveSequence.charAt(0)) {
//                    lrow = i;
//                    lcol = j;
//                }
//            }
//        }
//
//
//        if(isMoveLegal(setup,moveSequence.charAt(0))){
//            zrow = lrow;
//            zcol = lcol;
//            //setupchar.remove(0);
//
//        }
//    return result;
//    }
////
////        char a;
////        char b;
////        char c;
////        int row = 0;
////        int col = 0;
////        int rowX = 0;
////        int colX = 0;
//
//        boolean sameRNC = false;
//        boolean hasCard = false;
//        boolean result = false;
//        ArrayList <Integer> setupInt = new ArrayList<>();
//        ArrayList <Integer> moveSeqInt = new ArrayList<>();
//        ArrayList<Boolean> sameRNCal = new ArrayList<>();
//
////
////
////
////        //find first position
////        for (int i = 0; i < setup.length()-3; i=i+3) {
////            if (setup.charAt(i+3) == moveSequence.charAt(i))
////            c = setup.charAt(i+2);
////
////                for (int j = 0; j<6;j++) {
////                    for(int k = 0; k<6;k++) {
////                        if (board[j][k] == setup.charAt(i)){
////                            row = j;
////                            col = k;
////                        }
////                    }
////                }
////
////            for (int m = 0; m<6;m++) {
////                for(int n = 0; n<6;n++) {
////                    if (board[m][n] == setup.charAt(i+3)){
////                        rowX = m;
////                        colX = n;
////                    }
////                }
////            }
////                if (row == rowX || col == colX){
////                    sameRNC = true;
////                    setup.charAt(i)==moveSequence.charAt(i-3)
////                }
////
////        }
//
//        for (int i = 0; i < setup.length() / 3; i++) {
//            if (setup.charAt(3 * i + 2) >= 'A' && setup.charAt(3 * i + 2) <= 'Z') {
//                setupInt.add((int) setup.charAt(3 * i + 2) - 65);
//            }
//            if (setup.charAt(3 * i + 2) >= '0' && setup.charAt(3 * i + 2) <= '9') {
//                setupInt.add((int) setup.charAt(3 * i + 2) - 22);
//            }
//        }
//
//
//        for (int i = 0; i < moveSequence.length(); i++) {
//            if (moveSequence.charAt(i) >= 'A' && moveSequence.charAt(i) <= 'Z') {
//                moveSeqInt.add((int) moveSequence.charAt(i) - 65);
//            }
//            if (moveSequence.charAt(i) >= '0' && moveSequence.charAt(i) <= '9') {
//                moveSeqInt.add((int) moveSequence.charAt(i) - 22);
//            }
//        }
//
//
//        for (int i = 0; i < moveSeqInt.size() - 1; i++) {
//            if (moveSeqInt.get(i) % 6 == moveSeqInt.get(i + 1) % 6 || moveSeqInt.get(i) / 6 == moveSeqInt.get(i + 1) / 6) {
//                for (int j = 0; j < moveSeqInt.size()-1; j++) {
//                    for (int k = 0; k < setupInt.size()-1; k++)
//                        if (moveSeqInt.get(j) == setupInt.get(k)) {
//                            hasCard = true;
//                            moveSeqInt.remove(j);
//                            result = true;
//                        }
//                }
//
//            }
//        }
//        return result;
//
//    }
////
////        //whether there's card on location
////        for (int i = 0; i < setup.length(); i = i + 3) {
////            if (placement.charAt(i + 2) == locationChar) {
////                hasCard = true;
////                break;
////            }
////        }
////
////        if (((d % 6) == (l % 6)) || ((d / 6) == (l / 6))) { //Same row and column
////            result = true;
////        }
////        return result;}


    /**
     * Get the list of supporters for the chosen player, given the provided
     * setup and move sequence.
     * The list of supporters is a sequence of two-character card IDs, representing
     * the cards that the chosen player collected by moving Zhang Yi.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @param playerId     the player number for which to get the list of supporters, [0..(numPlayers-1)]
     * @return the list of supporters for the given player
     */
    public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
        // FIXME Task 7: get the list of supporters for a given player after a sequence of moves

        // for number of players


        int k = setup.length();
        int x = playerId +1;
        int n = moveSequence.length();
        int endterms = n%x;
        String newSequence = moveSequence.substring(0, moveSequence.length() - endterms);

        int m = newSequence.length();

        String playerCards = "";
        int i;
        int j= numPlayers;
        String locations = "";

        for(i=2; i<=k; i=i+3){

            char a = setup.charAt(i);
            locations = locations +a;
            System.out.println(locations);



        }

        if(numPlayers>=2 && numPlayers <5){

            for(i = playerId; i < m; i = i+j ){
                char y = newSequence.charAt(i);



                int position = locations.indexOf(y);
                position = (position*3) + 2;

                char z = setup.charAt(position-2);
                char z1 = setup.charAt(position-1);



                playerCards = playerCards + z +z1;


            }
            return playerCards;

        }
        return null;
    }

    /**
     * Given a setup and move sequence, determine which player controls the flag of each kingdom
     * after all the moves in the sequence have been played.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing a sequence of moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @return an array containing the player ID who controls each kingdom, where
     * - element 0 contains the player ID of the player who controls the flag of Qin
     * - element 1 contains the player ID of the player who controls the flag of Qi
     * - element 2 contains the player ID of the player who controls the flag of Chu
     * - element 3 contains the player ID of the player who controls the flag of Zhao
     * - element 4 contains the player ID of the player who controls the flag of Han
     * - element 5 contains the player ID of the player who controls the flag of Wei
     * - element 6 contains the player ID of the player who controls the flag of Yan
     * If no player controls a particular house, the element for that house will have the value -1.
     */
    public static int[] getFlags(String setup, String moveSequence, int numPlayers) {
        // FIXME Task 8: determine which player controls the flag of each kingdom after a given sequence of moves
        return null;
    }

    /**
     * Generate a legal move, given the provided placement string.
     * A move is valid if:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the destination location is different to Zhang Yi's current location;
     * - the destination is in the same row or column of the grid as Zhang Yi's current location; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * that are further away from Zhang Yi.
     * If there is no legal move available, return the null character '\0'.
     *
     * @param placement the current placement string
     * @return a location character representing Zhang Yi's destination for the move
     */
    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        return '\0';
    }
}
