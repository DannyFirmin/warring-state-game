package comp1110.ass2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        boolean result = true;
        for (int i = 0; i < moveSequence.length(); i++) {
            //legal move
            if (isMoveLegal(setup, moveSequence.charAt(i))) {
                l = moveSequence.charAt(i);//where to go now, take the location char

                //find the index of zhangyi in current setup.
                for (int j = 0; j < setup.length() ; j = j + 3) {
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
                //replace previous king to z9
                setup = setup.replace(setup.substring(li - 2, li), "z9");
                //delete previous z9x
                StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3);
                setup = sb.toString();
            } else {
                result = false;
                break;
            }
        }
        return result;
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


        char[][] board = Board.board;
        int zrow = 0; // zhangyi  row
        int zcol = 0; // zhangyi column
        int player0LocationAtStartrow = 0;  // player0 row before the game begins
        int player0LocationAtStartcol = 0;  // player 0 column before the game begins


        String output = "";  // the final return value
        int k = setup.length();  // length of the setup string
        int x = playerId + 1;
        int n = moveSequence.length();
        int endTerms = n % x; // probably not required
        String newSequence = moveSequence.substring(0, moveSequence.length() - endTerms); // making it fool proof

        int m = newSequence.length();

        String playerCards = "";
        int i;
        int v;

        int j = numPlayers;
        String locations = "";


        char locationOfPreviousPlayer = 0;
        char locationOfCurrentPlayer = 0;
        char locationOfZhangyi = 0;
        String locationsInBetween = "";


        for (i = 2; i <= k; i = i + 3) {    // finding the location characters from the setup string

            char a = setup.charAt(i);
            locations = locations + a;


        }


        // FIND ZHANGYI LOCATION FROM SETUP string  before the game begins

        for (i = 0; i < m; i = i + 3) {
            char zhangi = setup.charAt(i);
            if (zhangi == 'z') {
                locationOfZhangyi = setup.charAt(i + 2);
            }
        }


        for (i = 0; i < 6; i++) {
            for (v = 0; v < 6; v++) {
                if (board[i][j] == locationOfZhangyi) {
                    zrow = i;
                    zcol = v;
                }
            }
        }

        // location of player0 at the start of the game

        char player0LocationAtStart = moveSequence.charAt(0);


        for (i = 0; i < 6; i++) {
            for (v = 0; v < 6; v++) {
                if (board[i][v] == player0LocationAtStart) {
                    player0LocationAtStartrow = i;
                    player0LocationAtStartcol = v;
                }
            }
        }

        // finding the locations between zhangyi and player0  // a new method can be created to find the location between
        //two different sequences based on this.

        if (zcol == player0LocationAtStartcol || zrow == player0LocationAtStartrow) {
            if (zcol == player0LocationAtStartcol) {
                if (zrow > player0LocationAtStartrow) {
                    for (v = zrow; v <= player0LocationAtStartrow; v--) {
                        locationsInBetween = locationsInBetween + board[zcol][v];


                    }
                }
                if (zrow < player0LocationAtStartrow) {
                    for (v = zrow; v <= player0LocationAtStartrow; v++) {
                        locationsInBetween = locationsInBetween + board[zcol][v];

                    }
                }
            }

            if (zrow == player0LocationAtStartrow) {
                if (zcol > player0LocationAtStartcol) {
                    for (i = zcol; i <= player0LocationAtStartcol; i--) {
                        locationsInBetween = locationsInBetween + board[i][zrow];


                    }
                }
                if (zcol < player0LocationAtStartcol) {
                    for (i = zcol; i <= player0LocationAtStartcol; i++) {
                        locationsInBetween = locationsInBetween + board[i][zrow];
                    }
                }
            }

            System.out.println(locationsInBetween);
        }


        // finding kingdom cards of the locations


        //int numberOfLocations = locationsInBetween.length();

        String kingdomCards = "";
        // locationsInBetween = locationsInBetween.substring(1,locationsInBetween.length());
        //char[] locationArray = locationsInBetween.toCharArray();
        for (i = 0; i < locationsInBetween.length(); i++) {

            char y = locationsInBetween.charAt(i);


            int position = locations.indexOf(y);
            position = (position * 3) + 2; // position of current game player


            char z = setup.charAt(position - 2);
            char z1 = setup.charAt(position - 1);


            kingdomCards = kingdomCards + z + z1;


        }
        System.out.println(kingdomCards);


        // checking if the kingdom cards are same in the locations inbetwen

        String KingdomCardNumbers = "";

        if (kingdomCards.length() > 4) {
            for (i = 2; i < kingdomCards.length() - 1; i = i + 2) {
                if (kingdomCards.charAt(i) == kingdomCards.charAt(kingdomCards.length() - 2)) {

                    KingdomCardNumbers = KingdomCardNumbers + kingdomCards.charAt(i) + kingdomCards.charAt(i + 1);


                }
            }
            System.out.println(KingdomCardNumbers);
        }


//


        // this block is kept on hold

        /*if (numPlayers >= 2 && numPlayers < 5) {
            for (i = playerId - 1; i < m; i = i + j) {
                char y1 = newSequence.charAt(i);
                int position1 = locations.indexOf(y1);
                position1 = (position1 * 3) + 2; // position of previous game player
                locationOfPreviousPlayer = setup.charAt(position1);



            }
        }*/


        if (numPlayers >= 2 && numPlayers < 5) {

            for (i = playerId; i < m; i = i + j) {
                char y = newSequence.charAt(i);


                int position = locations.indexOf(y);
                position = (position * 3) + 2; // position of current game player
                locationOfCurrentPlayer = setup.charAt(position);


                char z = setup.charAt(position - 2);
                char z1 = setup.charAt(position - 1);


                playerCards = playerCards + z + z1;


            }
            output = playerCards;

        }

        return output;
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
        int[] getFlags = {0, 0, 0, 0, 0, 0, 0};
        char c = '0'; //Zhangyi's 3rd card
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
//        int lrow = 0;//destination's row on board
//        int lcol = 0;//destination's column on board
        char[][] board = Board.board;//board in 2 dimensional array
        String[][] occupation = placementToOccupation(setup, board);//transform string of setup to 2 dimensional array on board
        int[][] cards = {{0, 0, 0, 0, 0, 0, 0},//initialize the cards in every players hands as all-0
                {0, 0, 0, 0, 0, 0, 0},//rows represent players,columns represent kingdoms
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0}};


        //find Zhangyi in setup
        for (int i = 0; i < setup.length(); i = i + 3) {
            if (setup.charAt(i) == 'z') {
                c = setup.charAt(i + 2);
                break;
            }
        }
        //find Zhangyi on board at first place
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == c) {
                    zrow = i;
                    zcol = j;
                    break;
                }
            }
        }

        /*move each step,
        1.cards in hand change;
        2.occupation change;
        3.Zhangyi's location change;
        4.flags may change.*/
        for (int i = 0; i < moveSequence.length(); i++) {//for each step in the moveSequence
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {//for each location on board
                    if (moveSequence.charAt(i) == board[j][k]) {//for one character in moveSequence, find the location on board
                        int m = cards[0][occupation[j][k].charAt(0) - 97];//number of cards of kingdom on location that plaer 0 have
                        char kingdom = occupation[j][k].charAt(0);
                        if (zrow == j) {//Zhangyi and location in the same row
                            if (zcol > k) {//Zhangyi is on the right of location
                                for (int l = k; l <= zcol; l++) {
                                    if ((int) occupation[j][k].charAt(0) == (int) occupation[j][l].charAt(0) && !occupation[j][l].equals("~~")) {
                                        cards[i % numPlayers][occupation[j][k].charAt(0) - 97] += 1;//add 1 card to certain player
                                        occupation[j][l] = "~~";//delete data of related location on occupation
                                    }
                                }
                                int ID = 0;//initialize the playerID as 0
                                for (int n = 0; n < numPlayers; n++) {//check every player
                                    if (cards[n][kingdom - 97] >= m) {
                                        m = cards[n][kingdom - 97];//player n has the most cards till now
                                        ID = n;//player n should get the flag
                                    }
                                }
                                zcol = k;//change Zhangyi on board
                                getFlags[kingdom - 97] = ID;//change flag
                            }

                            if (zcol < k) {//Zhangyi is on the left of location
                                for (int l = zcol; l <= k; l++) {
                                    if ((int) occupation[j][k].charAt(0) == (int) occupation[j][l].charAt(0) && !occupation[j][l].equals("~~")) {
                                        cards[i % numPlayers][occupation[j][k].charAt(0) - 97] += 1;
                                        occupation[j][l] = "~~";
                                    }
                                }
                                int ID = 0;//initialize the playerID as 0
                                for (int n = 0; n < numPlayers; n++) {//check every player
                                    if (cards[n][kingdom - 97] >= m) {
                                        m = cards[n][kingdom - 97];//player n has the most cards till now
                                        ID = n;//player n should get the flag
                                    }
                                }
                                zcol = k;//change Zhangyi on board
                                getFlags[kingdom - 97] = ID;//change flag
                            }
                        }

                        if (zcol == k) {//Zhangyi and location in the same column
                            if (zrow > j) {//Zhangyi is on the lowerside of location
                                for (int l = j; l <= zrow; l++) {
                                    if (occupation[j][k].charAt(0) - 97 >= 0 && occupation[j][k].charAt(0) - 97 < numPlayers) {
                                        if ((int) occupation[j][k].charAt(0) == (int) occupation[l][k].charAt(0) && !occupation[l][k].equals("~~")) {
                                            cards[i % numPlayers][occupation[j][k].charAt(0) - 97] += 1;
                                            occupation[l][k] = "~~";
                                        }
                                    }
                                }
                                int ID = 0;//initialize the playerID as 0
                                for (int n = 0; n < numPlayers; n++) {//check every player
                                    if (cards[n][kingdom - 97] >= m) {
                                        m = cards[n][kingdom - 97];//player n has the most cards till now
                                        ID = n;//player n should get the flag
                                    }
                                }
                                zrow = j;//change Zhangyi on board
                                getFlags[kingdom - 97] = ID;//change flag
                            }

                            if (zrow < j) {//Zhangyi is on the upperside of location
                                for (int l = zrow; l <= j; l++) {
                                    if ((int) occupation[j][k].charAt(0) == (int) occupation[l][k].charAt(0) && !occupation[l][k].equals("~~")) {
                                        cards[i % numPlayers][occupation[j][k].charAt(0) - 97] += 1;
                                        occupation[l][k] = "~~";
                                    }
                                }
                                int ID = 0;//initialize the playerID as 0
                                for (int n = 0; n < numPlayers; n++) {//check every player
                                    if (cards[n][kingdom - 97] >= m) {
                                        m = cards[n][kingdom - 97];//player n has the most cards till now
                                        ID = n;//player n should get the flag
                                    }
                                }
                                zrow = j;//change Zhangyi on board
                                getFlags[kingdom - 97] = ID;//change flag
                            }
                        }
                    }
                }
            }
        }
        return getFlags;
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
