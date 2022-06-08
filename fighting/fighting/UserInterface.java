package fighting;

public class UserInterface {
    private String getWeaponText = "\nwhat kind of weapon would you like?\n" +
                                  "w) Sword and shield\n" +
                                  "x) Axe and shield\n" +
                                  "s) spear and shield\n" +
                                  "W) Greatsword\n" +
                                  "X) Greataxe\n" +
                                  "S) Longspear";
    private String wrongInput = "That's not an option!";
    private String getName = ", what is your name?";

    public void printGameChoice(){
        System.out.println("What type of game would you like to play?\n" +
                         "1) Single Player\n" +
                         "2) Two Player"); 
    }

    public void printWrongOutput(){
        System.out.println(wrongInput);
    }

    public void printWeaponText(){
        System.out.println(getWeaponText);
    }

    public void printCPWeaponText(){
        System.out.println("For CP\n" + getWeaponText);
    }

    public void printPlayerName(String PlayerNumber){
        System.out.println("\nPlayer" + PlayerNumber + getName);
    }
}

