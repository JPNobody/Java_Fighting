package fighting;

// Change so that attack and defend control damage.


import java.util.Scanner;

// This is a Fighter that gets user input to make decisions
public class Player extends Fighter {
   

   private String wrongInput = "That's not an option!";
   Scanner myObj = new Scanner(System.in);

   public Player(String weaponType, String name, boolean alive) {
      super(weaponType, name, alive);
   }

   public int getChoice(int startLimit, int endLimit){
      int moveIndex = 0;
      boolean isInvalid;
      
      do 
         {
            while (!myObj.hasNextInt()) 
            {
               myObj.next();
               System.out.println(wrongInput);
            }
            moveIndex = myObj.nextInt();
            isInvalid = false;
            if (moveIndex < startLimit || moveIndex > endLimit)
            {
               System.out.println(wrongInput);
               isInvalid = true;
            }
         } while (isInvalid);
      return moveIndex -1;
   }
   
}
