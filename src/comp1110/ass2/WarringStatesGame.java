package comp1110.ass2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
     * @author: Ben
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
     * @author: Ben
     */
    public static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        boolean b = true;
        if (placement != null && placement.length() >= 3 && placement.length() <= 108 && placement.length() % 3 == 0) {
            for (int i = 0; i < placement.length() / 3 & b; i++) {
                String sub_i = placement.substring(3 * i, 3 * i + 3);
                String sub_i_1 = sub_i.substring(0, 2);
                String sub_i_2 = sub_i.substring(2);
                if (isCardPlacementWellFormed(sub_i)) {
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
     * @author: Ben
     */
    public static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal
        boolean result = false;
        char b;
        char c = '0'; //Zhangyi's 3rd char
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
                break;
            }
        }

        if (c == locationChar) {
            return false;
        }

        //find Zhangyi on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == c) {
                    zrow = i;
                    zcol = j;
                    break;
                }
            }
        }

        //whether there's card on location
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i + 2) == locationChar) {
                hasCard = true;
                break;
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
                        break;
                    }
                }
            } else {
                for (int i = lcol + 1; i < 6; i++) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        noFurther = false;
                        break;
                    }
                }
            }
        }
        if (zcol == lcol) {
            if (zrow > lrow) {
                for (int i = lrow - 1; i >= 0; i--) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        noFurther = false;
                        break;
                    }
                }
            } else {
                for (int i = lrow + 1; i < 6; i++) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        noFurther = false;
                        break;
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

    /**
     * put king code to the board ~~ means no king on that position
     *
     * @param placement the current placement string
     * @param board     board layout
     * @return board layout with king code on it.
     */
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
     * @author: Danny
     */
    static boolean isMoveSequenceValid(String setup, String moveSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        int zi = 0; //zhangyi's index
        int li = 0; //location's index
        char l; //destination's board location char
        boolean result = true;
        for (int i = 0; i < moveSequence.length(); i++) {

            if (setup.length() == 3) {
                return true;
            }

            //legal move
            if (isMoveLegal(setup, moveSequence.charAt(i))) {
                l = moveSequence.charAt(i);//where to go now, take the location char

                //find the index of zhangyi in current setup.
                for (int j = 0; j < setup.length(); j = j + 3) {
                    if (setup.charAt(j) == 'z') {
                        zi = j;//zhangyi's current index
                        break;
                    }
                }
                //find location char in setup string
                for (int j = 2; j < setup.length(); j = j + 3) {
                    if (setup.charAt(j) == l) {
                        // moveSequence.charAt(i) go to setup to find where is this
                        li = j;//location's current index
                        break;
                    }
                }

                ArrayList<String> sameStateKing;
                sameStateKing = findFurther(setup, l);

                setup = setup.replace(setup.substring(li - 2, li), "z9");
                //delete previous z9x
                StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3);
                setup = sb.toString();

                if (sameStateKing.size() > 0) {
                    for (int j = 0; j < sameStateKing.size(); j++) {
                        setup = setup.replaceAll(sameStateKing.get(j), "~~");
                    }
                    sameStateKing.clear();
                }
            } else {
                result = false;
                break;
            }
        }
        return result;
    }


    public static ArrayList<String> findFurther(String placement, char locationChar) {
        int zi = 0; //zhangyi's index
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
        int lrow = 0;//destination's row on board
        int lcol = 0;//destination's column on board
        char kingdom = 'z';//kingdom card of destination
        ArrayList<String> sameStateKing = new ArrayList<>();

        char[][] board = Board.board;

        String[][] occupation = placementToOccupation(placement, board);

        //find the index of zhangyi in current setup.
        for (int j = 0; j < placement.length(); j = j + 3) {
            if (placement.charAt(j) == 'z') {
                zi = j;//zhangyi's current index
                break;
            }
        }

        //find Zhangyi on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == placement.charAt(zi + 2)) {
                    zrow = i;
                    zcol = j;
                    break;
                }
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

        //find same state in the move
        if (zrow == lrow) {
            if (zcol > lcol) {
                for (int i = lcol + 1; i != zcol; i++) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        String a = "" + occupation[zrow][i].charAt(0) + occupation[zrow][i].charAt(1);
                        sameStateKing.add(a);
                    }
                }
            }
            if (zcol < lcol) {
                for (int i = lcol - 1; i != zcol; i--) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        String a = "" + occupation[zrow][i].charAt(0) + occupation[zrow][i].charAt(1);
                        sameStateKing.add(a);
                    }

                }
            }
        }
        if (zcol == lcol) {
            if (zrow > lrow) {
                for (int i = lrow + 1; i != zrow; i++) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        String a = "" + occupation[i][zcol].charAt(0) + occupation[i][zcol].charAt(1);
                        sameStateKing.add(a);
                    }
                }
            }
            if (zrow < lrow) {

                for (int i = lrow - 1; i != zrow; i--) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        String a = "" + occupation[i][zcol].charAt(0) + occupation[i][zcol].charAt(1);
                        sameStateKing.add(a);
                    }
                }

            }
        }
        return sameStateKing;
    }

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
     * @author: Vishnu 60 % and Danny 40% (Vishnu first wrote this task but the testGood fails. Danny rewrote 50% of the code to fix the bug.
     * Vishnu's method of finding if there is the same kingdom card when zhangyi move is not returning the correct result
     * also he forgot to update the setup string when zhangyi move.)
     */

    public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
        // FIXME Task 7: get the list of supporters for a given player after a sequence of moves
        int zi = 0; //zhangyi's index
        int li = 0; //location's index
        String inbetweenKingdoms = "";
        String output;  // the final return value

        String[] playerCharacterCards = new String[numPlayers]; // array to store the supporters
        for (int i = 0; i < numPlayers; i++) {
            playerCharacterCards[i] = "";
        }

        for (int i = 0; i < moveSequence.length(); i++) {
            if (i == 0) {
                // find the index of zhangyi in current setup
                for (int s = 0; s < setup.length(); s = s + 3) {
                    if (setup.charAt(s) == 'z') {
                        zi = s;
                        break;
                    }
                }

                //find location char in setup string
                for (int j = 2; j < setup.length(); j = j + 3) {
                    if (setup.charAt(j) == moveSequence.charAt(0)) {
                        li = j;//location's current index
                        break;
                    }
                }

                //find supporters from zhangyi's location to his destination location
                ArrayList<String> sameStateKing;
                sameStateKing = findFurther(setup, moveSequence.charAt(0));
                for (int j = 0; j < sameStateKing.size(); j++) {
                    inbetweenKingdoms = inbetweenKingdoms + sameStateKing.get(j);
                }
                playerCharacterCards[0] = playerCharacterCards[0] + inbetweenKingdoms + setup.charAt(li - 2) + setup.charAt(li - 1);
                inbetweenKingdoms = "";

                setup = setup.replace(setup.substring(li - 2, li), "z9");
                StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3);
                setup = sb.toString();

                if (sameStateKing.size() > 0) {
                    for (int w = 0; w < sameStateKing.size(); w++) {
                        setup = setup.replaceAll(sameStateKing.get(w), "~~");
                    }
                    sameStateKing.clear();
                }

            } else if (i > 0) {

                // find the index of zhangyi in current setup
                for (int s = 0; s < setup.length(); s = s + 3) {
                    if (setup.charAt(s) == 'z') {
                        zi = s;
                        break;
                    }
                }


                //find location char in setup string
                for (int j = 2; j < setup.length(); j = j + 3) {
                    if (setup.charAt(j) == moveSequence.charAt(i)) {
                        li = j;//location's current index
                        break;
                    }
                }

                ArrayList<String> sameStateKing;
                sameStateKing = findFurther(setup, moveSequence.charAt(i));
                for (int j = 0; j < sameStateKing.size(); j++) {
                    inbetweenKingdoms = inbetweenKingdoms + sameStateKing.get(j);
                }

                //categorizing the cards received for every sequence and placing in player strings
                if (i % numPlayers == 0) {
                    playerCharacterCards[0] = playerCharacterCards[0] + inbetweenKingdoms + setup.charAt(li - 2) + setup.charAt(li - 1);
                    inbetweenKingdoms = "";
                }


                if (i % numPlayers == 1) {
                    playerCharacterCards[1] = playerCharacterCards[1] + inbetweenKingdoms + setup.charAt(li - 2) + setup.charAt(li - 1);
                    inbetweenKingdoms = "";

                }
                if (i % numPlayers == 2) {
                    playerCharacterCards[2] = playerCharacterCards[2] + inbetweenKingdoms + setup.charAt(li - 2) + setup.charAt(li - 1);
                    inbetweenKingdoms = "";

                }
                if (i % numPlayers == 3) {
                    playerCharacterCards[3] = playerCharacterCards[3] + inbetweenKingdoms + setup.charAt(li - 2) + setup.charAt(li - 1);
                    inbetweenKingdoms = "";
                }

                setup = setup.replace(setup.substring(li - 2, li), "z9");
                StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3);
                setup = sb.toString();

                if (sameStateKing.size() > 0) {
                    for (int w = 0; w < sameStateKing.size(); w++) {
                        setup = setup.replaceAll(sameStateKing.get(w), "~~");
                    }
                    sameStateKing.clear();
                }
            }
        }


        output = removeDuplicates(playerCharacterCards[playerId]);
        return output;
    }


    public static String removeDuplicates(String x) {
        Set<String> set = new HashSet<>();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < x.length(); i = i + 2) {
            String c = "" + x.charAt(i) + x.charAt(i + 1);
            if (!set.contains(c)) {
                set.add(c);
                sf.append(c);
            }
        }
        return sf.toString();
    }

    /**
     * Given a setup and move sequence, determine which player controls the flag of each kingdom
     * after all the moves in the sequence have been played.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing a sequence of moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @return an array containing the player ID who controls each kingdom, where
     * @author Danny
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
        int[][] cards = {{0, 0, 0, 0, 0, 0, 0},//initialize the cards in every players hands as all-0
                {0, 0, 0, 0, 0, 0, 0},//rows represent players,columns represent kingdoms
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}};
        String oldSetup = setup;
        String newSetup = setup;

        int[] flags = {-1, -1, -1, -1, -1, -1, -1};

        for (int i = 0; i < moveSequence.length(); i++) {
            int currentPlayer = i % numPlayers;
            String[] playerSupporter = new String[numPlayers];

            playerSupporter[currentPlayer] = getSupporters(newSetup, moveSequence.charAt(i) + "", numPlayers, 0);

            if (i == 0) {
                newSetup = updateSetup(oldSetup, moveSequence.charAt(0));
            }
            if (i != 0) {
                oldSetup = newSetup;
                newSetup = updateSetup(oldSetup, moveSequence.charAt(i));
            }

            for (int j = 0; j < playerSupporter[currentPlayer].length(); j = j + 2) {
                if (playerSupporter[currentPlayer].charAt(j) == 'a') {
                    cards[currentPlayer][0] = cards[currentPlayer][0] + 1;
                    //flags[0] means the player who get the flag of Qin
                    if (flags[0] == -1 || flags[0] == currentPlayer)  // If this flag hasn't been owned by anyone
                        flags[0] = currentPlayer;
                    else if (cards[flags[0]][0] <= cards[currentPlayer][0])
                        flags[0] = currentPlayer;
                }
                // ....
                if (playerSupporter[currentPlayer].charAt(j) == 'b') {
                    cards[currentPlayer][1] = cards[currentPlayer][1] + 1;
                    if (flags[1] == -1 || flags[1] == currentPlayer)
                        flags[1] = currentPlayer;
                    else if (cards[flags[1]][1] <= cards[currentPlayer][1])
                        flags[1] = currentPlayer;
                }
                if (playerSupporter[currentPlayer].charAt(j) == 'c') {
                    cards[currentPlayer][2] = cards[currentPlayer][2] + 1;
                    if (flags[2] == -1 || flags[2] == currentPlayer)
                        flags[2] = currentPlayer;
                    else if (cards[flags[2]][2] <= cards[currentPlayer][2])
                        flags[2] = currentPlayer;
                }
                if (playerSupporter[currentPlayer].charAt(j) == 'd') {
                    cards[currentPlayer][3] = cards[currentPlayer][3] + 1;
                    if (flags[3] == -1 || flags[3] == currentPlayer)
                        flags[3] = currentPlayer;
                    else if (cards[flags[3]][3] <= cards[currentPlayer][3])
                        flags[3] = currentPlayer;
                }
                if (playerSupporter[currentPlayer].charAt(j) == 'e') {
                    cards[currentPlayer][4] = cards[currentPlayer][4] + 1;
                    if (flags[4] == -1 || flags[4] == currentPlayer)
                        flags[4] = currentPlayer;
                    else if (cards[flags[4]][4] <= cards[currentPlayer][4])
                        flags[4] = currentPlayer;
                }
                if (playerSupporter[currentPlayer].charAt(j) == 'f') {
                    cards[currentPlayer][5] = cards[currentPlayer][5] + 1;
                    if (flags[5] == -1 || flags[5] == currentPlayer)
                        flags[5] = currentPlayer;
                    else if (cards[flags[5]][5] <= cards[currentPlayer][5])
                        flags[5] = currentPlayer;
                }
                if (playerSupporter[currentPlayer].charAt(j) == 'g') {
                    cards[currentPlayer][6] = cards[currentPlayer][6] + 1;
                    if (flags[6] == -1 || flags[6] == currentPlayer)
                        flags[6] = currentPlayer;
                    else if (cards[flags[6]][6] <= cards[currentPlayer][6])
                        flags[6] = currentPlayer;
                }
            }
        }
        return flags;
    }

    public static String updateSetup(String oldsetup, char moveSeqChar) {
        int zi = 0; //zhangyi's index
        int li = 0; //location's index
        String newsetup = "";

        //find the index of zhangyi in old setup.
        for (int j = 0; j < oldsetup.length(); j = j + 3) {
            if (oldsetup.charAt(j) == 'z') {
                zi = j;//zhangyi's  index
                break;
            }
        }

        //find location char in setup string
        for (int j = 2; j < oldsetup.length(); j = j + 3) {
            if (oldsetup.charAt(j) == moveSeqChar) {
                // moveSequence.charAt(i) go to setup to find where is this
                li = j;//location's current index
                break;
            }
        }
        ArrayList<String> sameStateKing;
        sameStateKing = findFurther(oldsetup, moveSeqChar);
        newsetup = oldsetup.replace(oldsetup.substring(li - 2, li), "z9");
        //delete previous z9x
        StringBuilder sb = new StringBuilder(newsetup).delete(zi, zi + 3);
        newsetup = sb.toString();
        if (sameStateKing.size() > 0) {
            for (int j = 0; j < sameStateKing.size(); j++) {
                newsetup = newsetup.replaceAll(sameStateKing.get(j), "~~");
            }
            sameStateKing.clear();
        }
        return newsetup;
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
     * @author Ben
     */
    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        char location = '\0';
        char[][] board = Board.board;
        for (int i = 0; i <= 36; i++) {
            if (i >= 0 & i <= 9) {
                if (isMoveLegal(placement, (char) (i + 48))) {
                    location = (char) (i + 48);
                }
            } else {
                if (isMoveLegal(placement, (char) (i + 65))) {
                    location = (char) (i + 65);
                }
            }
        }
        return location;
    }

    /**
     * check if zhangyi has no place to go
     *
     * @param placement the placement string
     * @return true if zhangyi has no way to go
     * @author Danny
     */

    public static boolean checkEnd(String placement) {
        char c = '0'; //Zhangyi's 3rd char
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
        boolean up = false;//true if there is any card above zhangyi
        boolean down = false;
        boolean left = false;
        boolean right = false;
        char[][] board = Board.board;
        String[][] occupation = placementToOccupation(placement, board);

        //find Zhangyi
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i) == 'z') {
                c = placement.charAt(i + 2);
                break;
            }
        }
        //find Zhangyi on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == c) {
                    zrow = i;
                    zcol = j;
                    break;
                }
            }
        }

        //search up
        if (zrow != 0) {
            for (int i = zrow; i > 0; i--) {
                if (occupation[i - 1][zcol].substring(0, 2) != "~~") {
                    up = true;
                }
            }
        }

        //search down
        if (zrow != 5) {
            for (int i = zrow; i < 5; i++) {
                if (occupation[i + 1][zcol].substring(0, 2) != "~~") {
                    down = true;
                }
            }
        }

        //search left
        if (zcol != 0) {
            for (int i = zcol; i > 0; i--) {
                if (occupation[zrow][i - 1].substring(0, 2) != "~~") {
                    left = true;
                }
            }
        }

        //search right
        if (zcol != 5) {
            for (int i = zcol; i < 5; i++) {
                if (occupation[zrow][i + 1].substring(0, 2) != "~~") {
                    right = true;
                }
            }
        }

        if (up || down || left || right) {
            return false;
        } else {
            return true;
        }
    }

    // method ：who wins
    // stateNum[] is total number of flags of each player,cardNum[] is total number of cards of each player
    // value of win is the No of the player who wins, if win is -1, draw
    public static int win(int[] stateNum, int[] cardNum) {
        int win = 0;
        int flagmost = 0;
        for (int i = 0; i < stateNum.length; i++) {
            if (stateNum[i] > flagmost) {
                flagmost = stateNum[i];
                win = i;
            } else if (stateNum[i] == flagmost) {
                if (cardNum[i] > cardNum[win]) {
                    win = i;
                } else if (cardNum[i] == cardNum[win]) {
                    win = -1;
                    break;
                }
            }
        }
        return win;
    }
}
