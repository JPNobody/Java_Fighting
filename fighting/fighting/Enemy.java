package fighting;

import java.util.Random;


// This is a fighter that uses random inputs to make decisions.
public class Enemy extends Fighter{

   private Random rand = new Random();

   Enemy(String weaponType, String name, boolean alive){
      super(weaponType, name, alive);
   }

   public int getChoice(int startLimit, int endLimit)
   {
      return startLimit + rand.nextInt(endLimit - startLimit + 1) - 1;
   }
}
