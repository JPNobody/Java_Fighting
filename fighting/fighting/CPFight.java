package fighting;

public class CPFight {
   private Enemy enemy2;
   private Enemy enemy1;
   public CPFight (Enemy e1, Enemy e2){
      enemy1 = e1;
      enemy2 = e2;
   }
   public void play(){
      while (enemy1.getHealth() > 0 && enemy2.getHealth() > 0){
         System.out.println(enemy2.getName() + " health: " + enemy2.getHealth());
         enemy2.attack(enemy1);
         if (enemy1.getHealth() > 0 && enemy2.getHealth() > 0)
         {
            System.out.println(enemy1.getName() + " health: " + enemy1.getHealth());
            enemy1.attack(enemy2);
         }
         enemy1.reduceStamina(-5);
         enemy2.reduceStamina(-5);
      }
      if (enemy2.getHealth() > 0)
         System.out.println(enemy2.getName() +" won!");
      else
         System.out.println(enemy1.getName() +" won!");
      }
}

