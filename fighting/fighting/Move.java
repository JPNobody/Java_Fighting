package fighting;

public class Move {
   protected char direction;
   protected float staminaCost;
   private String moveName;
   private float damageReduct;


   public Move(String name, char direc, float stamCost){
      direction = direc;
      staminaCost = stamCost;
      moveName = name;
   }

   public Move(String name, char direc, float stamCost, float damageRed ){
      direction = direc;
      staminaCost = stamCost;
      moveName = name;
      damageReduct = damageRed;
   }

   // getters
   public char getDirection() { return direction; }
   public float getStamCost() { return staminaCost; }
   public String getMoveName() { return moveName; } 
   public float getDamageReduct() {return damageReduct;}

}
