import static comp1110.ass2.WarringStatesGame.isMoveLegal;

public class Task6 {
    public static void main(String[] args) {
        System.out.println(isMoveSequenceValid("e0Ba7Dc04f25c27z99a4Vg0Hb2Kd4Tf0Jb6Xa2Nc5P",
        "45BDPJHK"));
    }

    static boolean isMoveSequenceValid(String setup, String moveSequence) {
        int zi = 0; //zhangyi's index
        int li = 0; //location's index
        char l; //destination's board location char
        boolean result = true;
        for (int i = 0; i < moveSequence.length(); i++) {
            //legal move
            if (isMoveLegal(setup, moveSequence.charAt(i))) {
                l = moveSequence.charAt(i);//where to go now, take the location char

                //find the index of zhangyi in current setup.
                for (int j = 0; j < setup.length(); j = j + 3) {
                    if (setup.charAt(j) == 'z') {
                        zi = j;//zhangyi's current index;
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
                if (setup.charAt(zi + 2) != l) {
                    setup = setup.replace(setup.substring(li - 2, li), "z9");
                    StringBuilder sb = new StringBuilder(setup).delete(zi, zi + 3);
                    setup = sb.toString();
                } else {
                    result = false;
                    System.out.println("40"+result);
                    break;
                }
            } else {
                result = false;
                System.out.println("45"+result);
                break;
            }
        }
        return result;
    }
}