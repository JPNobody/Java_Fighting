package fighting;

public class AttackMove extends Move {
   private float damage;

   //Attack Move constructor
   public AttackMove(String name, char direc, float stamCost, float dmg){
      super(name, direc, stamCost);
      damage = dmg;
   }

   //damage getter
   public float getDamage() { return damage; }
   
}
