package fighting;

import java.util.Scanner;

public class Game {
   private String player1Name = "Player1";
   private String player2Name = "Player2";
   UserInterface ui = new UserInterface();
   Player player1;

   
   //This runs the game
   public void run(){
      Scanner in = new Scanner(System.in);
      System.out.println("What type of game would you like to play?\n" +
                         "1) Single Player\n" +
                         "2) Two Player");         
      int choice = getChoiceNumb(1, 2);
      //This allows for multiple game modes.
      switch(choice){
         case 1:
            // one player game mode
            ui.printPlayerName("1");
            player1Name = in.nextLine();
            ui.printWeaponText();
            String PWeapon = getWeaponType();
            ui.printCPWeaponText();
            String CPWeapon = getWeaponType();
            player1 = new Player(PWeapon, player1Name, true);
            Enemy CP = new Enemy(CPWeapon, "CP", false);
            TwoPBattle OnePGame = new TwoPBattle(player1, CP);
            OnePGame.play();
            break;
         case 2:
            // two player game mode
            ui.printPlayerName("1");
            player1Name = in.nextLine();
            ui.printWeaponText();
            String weapon1 = getWeaponType();

            ui.printPlayerName("2");   
            player2Name = in.nextLine();
            ui.printWeaponText();
            String weapon2 = getWeaponType();
            player1 = new Player(weapon1, player1Name, true);
            Player player2 = new Player(weapon2, player2Name, true);
            TwoPBattle game = new TwoPBattle(player1, player2);
            game.play();
            break;
         default:
            ui.printWrongOutput();;
      }
      in.close();
   }

   // This function gets a number
   private int getChoiceNumb(int startLimit, int endLimit){
      Scanner myObj = new Scanner(System.in);
      int choice = 0;
      boolean isInvalid;
      do 
      {
         while (!myObj.hasNextInt()) 
         {
            myObj.next();
            ui.printWrongOutput();
         }
         choice = myObj.nextInt();
         isInvalid = false;
         if (choice < startLimit || choice > endLimit)
         {
            ui.printWrongOutput();
            isInvalid = true;
         }
      } while (isInvalid);
      return choice;
   }

   //This function is used for getting the weapon type.
   private String getWeaponType()
   {
      Scanner myObj = new Scanner(System.in);
      String choice;
      String finalChoice = "nothing";
      boolean isInvalid;
      do 
      {
         while(!myObj.hasNextLine())
         {
            myObj.next();
            ui.printWrongOutput();
         }
         choice = myObj.nextLine();
         isInvalid = false;
         // This gets the line and takes the first character from the line
         // for this switch case
         switch(choice.toCharArray()[0])
         {
            case 'w':
               // create a sword weapon
               finalChoice = "sword";
               break; 
            case 'x':
               // create an axe weapon
               finalChoice = "axe";
               break; 
            case 's':
               // create a spear weapon
               finalChoice = "spear";
               break;
            case 'W':
               // create a greatsword weapon
               finalChoice = "greatsword";
               break; 
            case 'X':
               // create a greataxe weapon
               finalChoice = "greataxe";
               break; 
            case 'S':
               // create a longspear weapon
               finalChoice = "spear";
               break; 
            default:
               // This should keep the loop going if 
               //they don't pick one of the weapons
               isInvalid = true;   
               System.out.println("Sorry, we don't have that weapon.");   
         }
      } while (isInvalid);
      return finalChoice;
   }
}

