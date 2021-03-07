package fighting;

public class TwoPBattle {

   // This has two players
   private Fighter player1;
   private Fighter player2;

   // constructor
   public TwoPBattle (Fighter p1, Fighter p2){
      player1 = p1;
      player2 = p2;
   }


   // Play the game. 
   /*
   * This should eventually be edited to have the player with the 
   * highest speed go first, but that will be impemented later.
   * 
   * This can also be used as a base file to eventually create a 
   * battle class for more than just two players.
   */
   public void play(){
      // while no one has died.
      while (player2.getHealth() > 0 && player1.getHealth() > 0){
         // print out the health.
         System.out.println(player1.getName() + "\'s health: " + player1.getHealth());

         // have player1 attack player2
         player1.attack(player2);

         // Check if anyone has died.
         if (player2.getHealth() > 0 && player1.getHealth() > 0)
         {  
            // print out the health, and have player2 attack player1
            System.out.println(player2.getName() + "\'s health: " + player2.getHealth());
            player2.attack(player1);
         }

         // have the players recover some stamina.
         player2.reduceStamina(-5);
         player1.reduceStamina(-5);
      }

      // check who won.
      if (player1.getHealth() > 0)
         System.out.println(player1.getName() +" won! " +
                            player2.getName() +" lost.");
      else
         System.out.println(player2.getName() +" won! " +
                            player1.getName() +" lost.");
      }
}
