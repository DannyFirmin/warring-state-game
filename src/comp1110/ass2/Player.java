package comp1110.ass2;

import java.util.Scanner;

public class Player {
    String playerName;
    int playerID;

    public Player(int playerID, String playerName) {
        this.playerName = playerName;
        this.playerID = playerID;
    }

    public String getName() {
        return playerName;
    }

    @Override
    public String toString(){
        return playerID + "" + playerName;
    }

}
