package comp1110.ass2;


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
    private static int reEncode(char origin) {
        int newcode = 0;
        if (origin >= 'A' && origin <= 'Z') {
            newcode = origin - 65;
        } else if (origin >= '0' && origin <= '9') {
            newcode = origin - 12;
        }
        return newcode;
    }
    private static char decode(int encode) {
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
        char a;//zhangyi's 1st character  //Danny: It will always be z I don't think it's necessary
        char b;//zhangyi's 2nd character
        char c = '0';//zhangyi's 3rd character , location, board code
        char x;//destination's 1st character
        char y;//destination's 2nd character
        char z = '0';//destination's 3rd character
        int d; //recoded variables for c //Danny: I deleted d=0 because it is redundant
        int l;//Danny: recoded variables for locationChar. I deleted l=0 because it is redundant
        int z2; //recoded variables for z
        int m = 0;//index of zhangyi's location  XX
        int n = 0;//index of destination's location
        boolean hasCard = false;//whether destination has card
        boolean noFurther = false;//whether further card of same kingdom

        //find Zhangyi
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i) == 'z') {
                a = 'z';
                b = placement.charAt(i + 1);
                c = placement.charAt(i + 2);
                m = i + 2;
                break;
            }
        }

        //find destination
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i + 2) == locationChar) {
                x = placement.charAt(i);
                y = placement.charAt(i + 1);
                z = locationChar;
                n = i + 2;
                break;
            }
        }

        //whether there's card on location
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i + 2) == locationChar) {
                hasCard = true;
                break;
            }
        }
/*
        //recode the location
        int[] location = new int[placement.length() / 3];
        for (int i = 0; i < placement.length() / 3; i++) {
            if (placement.charAt(3 * i + 2) >= 'A' && placement.charAt(3 * i + 2) <= 'Z') {
                location[i] = (int) placement.charAt(3 * i + 2) - 65;
            }
            if (placement.charAt(3 * i + 2) >= '0' && placement.charAt(3 * i + 2) <= '9') {
                location[i] = (int) placement.charAt(3 * i + 2) - 22;
            }
        }

*/
        //whether furthest same kingdom card
        d = reEncode(c);
        l = reEncode(z);
        if ((d % 6) == (l % 6)) {
            if (d > l) {
                for (int i = d - 6; (i >= 0 || i<=6); i = i - 6) {
                    for (int j = i-6; j >= 0; j = j - 6) {
                        char p1 = decode(i);
                        char p2 = decode(j);
                        int pos1 = placement.indexOf(p1);
                        int pos2 = placement.indexOf(p2);

                        if (placement.charAt(pos1 - 2) == placement.charAt(pos2 - 2)) {
                            noFurther = false;

                        }
                    }

                }

            }

            if (d < l) {
                for (int i = l + 6; i <= 35; i = i + 6) {
                    for (int j = l; j <= 35; j = j + 6) {
                        char p1 = decode(i);
                        char p2 = decode(j);
                        int pos1 = placement.indexOf(p1);
                        int pos2 = placement.indexOf(p2);

                        if (placement.charAt(pos1 - 2) == placement.charAt(pos2 - 2)) {
                            noFurther = false;

                        }
                    }
                }
            }
        }

        if ((d / 6) == (l / 6)) {
            if (d > l) {

                for (int i = l + 1; (i / 6) != (l / 6); i = i + 1) {
                    for (int j = i; (j / 6) != (l / 6); j = j + 1) {
                        char p1 = decode(i);
                        char p2 = decode(j);
                        int pos1 = placement.indexOf(p1);
                        int pos2 = placement.indexOf(p2);

                        if (placement.charAt(pos1 - 2) == placement.charAt(pos2 - 2)) {
                            noFurther = false;

                        }
                    }
                }
            }
            if (d < l) {

                for (int i = l - 1; (i / 6) != (l / 6); i = i - 1) {
                    for (int j = i; (j / 6) != (l / 6); j = j - 1) {
                        char p1 = decode(i);
                        char p2 = decode(j);
                        int pos1 = placement.indexOf(p1);
                        int pos2 = placement.indexOf(p2);

                        if (placement.charAt(pos1 - 2) == placement.charAt(pos2 - 2)) {
                            noFurther = false;

                        }
                    }
                }

            }
        }


        //check
        //   if (isPlacementWellFormed(placement)) {
        if (locationChar != '\0') {
            if (d >= 0 && d <= 35 && l >= 0 && l <= 35) {
                if (hasCard) {
                    if (((d % 6) == (l % 6)) || ((d / 6) == (l / 6))) { //Same row and column
                        if (noFurther) {
                            result = true;
                        }
                    }
                }
            }
        }
        //      }
        return result;
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
        return false;
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
     */
    public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
        // FIXME Task 7: get the list of supporters for a given player after a sequence of moves
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
