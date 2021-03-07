package fighting;

import java.util.HashMap; // import the HashMap class
import java.util.Map; // import the Map class
import java.util.ArrayList; // import the ArrayList class
import java.util.List; //import the List class






abstract class Fighter {
   protected static Move nothing = new Move("nothing", 'n', 0f);
   protected static AttackMove attackNothing = new AttackMove("nothing", 'n', 0f, 0f);
   private static final String parry = "parry";

   private int multipleUsePenalty = 1;
   private Move lastMove;
   protected boolean isAlive;
   protected String  name;
   private Weapon leftHand;
   private Weapon rightHand;
   private float health = 100f;
   private float stamina = 100f;
   private ArrayList<Move> moveSet = new ArrayList<>();
   private ArrayList<AttackMove> attackMoveSet = new ArrayList<>();

    //These dictionaries allow for a switch case for creating the weapon
   private static Map<String, Character> oneHandedWeaponTypes = new HashMap<String, Character>()
   {
      private static final long serialVersionUID = 1L;
      {
         put("sword", 'w');
         put("axe", 'x');
         put("spear", 's');
         put("shield", 'h');
      }
   };
   private static Map<String, Character> twoHandedWeaponTypes = new HashMap<String, Character>()
   {

      private static final long serialVersionUID = 1L;
      {
         put("greatsword", 'W');
         put("greataxe", 'X');
         put("longspear", 'S');
      }
   };
   
   // getters
   public String getName() {return name;}
   public float getHealth() {return health;}
   public List<AttackMove> getAttackMoveSet() {return attackMoveSet;}
   public List<Move> getMoveSet() {return moveSet;}
   public float getStamina() { return stamina;}

   // This is a function that adjusts the health when the fighter is hit.
   public void getHit(float damage){
      health -= damage;
   }

   public void reduceStamina(float loss){
      stamina -= loss;
   }

   // constructor
   protected Fighter(String wType, String n, boolean alive) {
      isAlive = alive;
      name = n;
      moveSet.add(new Move("dodge left", 'l', 10f));
      moveSet.add(new Move("dodge right", 'r', 10f));
      moveSet.add(new Move("duck", 'd', 10f)); // d = down
      moveSet.add(new Move("rest", 'n', -20f)); // n = none
      char shield = 'h';
      if (oneHandedWeaponTypes.containsKey(wType)) 
      {
         char wTypeChar = oneHandedWeaponTypes.get(wType);
         // create a one-handed weapon
         rightHand = new Weapon(wTypeChar);
         leftHand = new Weapon(shield);
         combineMoveSets(rightHand);
         combineMoveSets(leftHand);
      }
      else if (twoHandedWeaponTypes.containsKey(wType)){
         char wTypeChar = twoHandedWeaponTypes.get(wType);
         // create a two-handed weapon
         rightHand = new Weapon(wTypeChar);
         leftHand = rightHand;
         combineMoveSets(rightHand);
      }
      else{
         System.out.println("We don't have that weapon.");
      } 
   }


   // This combines the move sets from the weapon with that of the fighter.
   private void combineMoveSets(Weapon hand){
      attackMoveSet.addAll(hand.getAttackMoveList());
      moveSet.addAll(hand.getMoveList());
   }

   // This method attacks an enemy and calls the enemy's defend method
   public void attack(Fighter enemy){
      // If the fighter is too tired to attack
      if (getStamina() <= 0){
         System.out.println(name + " is too tired to attack right now.\n");
         reduceStamina(-10f); // have them rest
         // have the enemy attack
         AttackMove attack = enemy.midAttack(); 
         System.out.println(enemy.getName() + " uses " + attack.getMoveName());
         getHit(attack.getDamage());
         System.out.println(name + " took " + attack.getDamage() + " damage\n");
         // end the function.
         return;
      }
      // make sure that the fighter has a moveset.
      if (getAttackMoveSet() != null) {
         //ask the user for a move
         System.out.println(name + "\'s stamina: " + getStamina());
         if (isAlive)
            System.out.println("\nWhat would you like to do?");
         //print the attack moves
         if (isAlive){
            printAttackMoves(true);
         }
         

         // get player input with error handling
         int moveIndex = getChoice(1, getAttackMoveSet().size() + 1);

         // check if the player is attacking, otherwise rest.
         AttackMove attack;
         if (moveIndex < getAttackMoveSet().size())
         {
            attack = getAttackMoveSet().get(moveIndex);
            reduceStamina(attack.getStamCost()); // reduce the stamina for the attack.
            System.out.println('\n' + name + " uses " + attack.getMoveName());
            //have the enemy defend
            Move defend = enemy.defend(this, attack);

            //make it more costly to continue to defend with the same move.
            if (lastMove == defend)
            {
               enemy.reduceStamina(defend.getStamCost() + 5 * multipleUsePenalty);
               multipleUsePenalty++;
            }
            else 
            {
               enemy.reduceStamina(defend.getStamCost());
               multipleUsePenalty++;
            }
            lastMove = defend;
         }
         // rest
         else 
         {
            Move rest = attackNothing;
            for (int i = 0; i < getMoveSet().size(); i++){
               if (getMoveSet().get(i).getMoveName() == "rest")
                  rest = getMoveSet().get(i);
            }
            System.out.println(name + " uses " + rest.getMoveName());
            reduceStamina(rest.getStamCost());
            Move defend = enemy.defend(this, attackNothing);
            enemy.reduceStamina(defend.getStamCost());
         }    
      }        
   }
   


   //defend function
   public Move defend(Fighter enemy, AttackMove attack) {
      // make sure the fighter has a moveset.
      if (getMoveSet() != null){
         // check if the fighter has stamina
         if (getStamina() <= 0){
            //if the fighter doesn't have stamina get attacked
            System.out.println(name + " too tired to defend right now.");
            reduceStamina(getStamina());
            reduceStamina(-10f);
            getHit(attack.getDamage());
            System.out.println(name + " took "+ attack.getDamage() + " damage\n");
            return nothing;
         }
         
         // if a player print the possible moves
         if (isAlive)
            printMoves();
         
         // get the move to defend with
         int moveIndex = getChoice(0, getMoveSet().size());

         // if the fighter wants to attack instead of defend.
         if (moveIndex == -1){
            // call the midAttack function
            AttackMove counterAttack = midAttack();
            // keep track of how much stamina each attack costs.
            float atStamCost = attack.getStamCost();
            float counStamCost = counterAttack.getStamCost() + 5f;

            // compare stamina costs, and let the move with the lower stamina cost go, 
            // unless the stamina cost differece is greater than 10 
            if (atStamCost < counStamCost && atStamCost > 0 && counStamCost > 0 && (counStamCost - atStamCost) <= 10)
            {
               System.out.println(name + " reacts with " + counterAttack.getMoveName());  
               System.out.println('\n' + name +"\'s attack was interrupted.\n"
                                + name + " took "+ attack.getDamage() + " damage\n");
               getHit(attack.getDamage());
            }
            else if (atStamCost > counStamCost  && atStamCost > 0 && counStamCost > 0 && (atStamCost - counStamCost) <= 10)
            {
               System.out.println(name + " reacts with " + counterAttack.getMoveName());  
               System.out.println('\n' + enemy.getName() +"\'s attack was interrupted.\n"
                                + enemy.getName() + " took "+ counterAttack.getDamage() + " damage\n");
               enemy.getHit(counterAttack.getDamage());
            }
            else 
            {
               System.out.println(name + " reacts with " + counterAttack.getMoveName());  
               System.out.println('\n' + enemy.getName() +" took "+ counterAttack.getDamage() + " damage\n"
                                + name +           " took "+ attack.getDamage() + " damage\n");
               getHit(attack.getDamage());
               enemy.getHit(attack.getDamage());
            }
            return nothing;
         }
         else
         {
            //just defend normally
            Move defend = getMoveSet().get(moveIndex);
            float damage = calculateDamage(attack, defend, this);
            getHit(damage);
            System.out.println(name + " reacts with " + defend.getMoveName());  
            System.out.println('\n' + name + " took "+ damage + " damage\n");
            return defend;
         }
            
      }
      else {
         return nothing;
      }
      
   }

   //attack function for when a fighter wants toattack instead of defending.
   // This returns an AttackMove
   private AttackMove midAttack(){
         if (isAlive)
            printAttackMoves(false);

         // get player input with error handling
         int moveIndex = getChoice(1, getAttackMoveSet().size());

         AttackMove attack;
         if (moveIndex < getAttackMoveSet().size()) // error handling
         {
            attack = getAttackMoveSet().get(moveIndex);
            reduceStamina(attack.getStamCost()); // reduce the stamina for the attack.
            return attack;
         }
         else
            return attackNothing;
           
   }
   
   //print all the attack moves in the moveset
   private void printAttackMoves(boolean printRest)
   {
      System.out.print('\n');
      for (int i = 0; i <= getAttackMoveSet().size(); i++)
         {
            if (i < getAttackMoveSet().size())
               System.out.println((i+1) + ") " + getAttackMoveSet().get(i).getMoveName());
            else
            {
               if (printRest)
               {
                  for (int j = 0; j < getMoveSet().size(); j++) 
                  {
                     if (getMoveSet().get(j).getMoveName() == "rest")
                     {
                        System.out.println((i+1) + ") rest");
                     }
                  }
               
               }
            }      
         }
   }

   //print all the moves in the moveset
   private void printMoves()
   {
      System.out.println('\n' + name + "\'s stamina: " + getStamina()+
                         "\nHow would you like to react?\n");
         for (int i = 0; i < getMoveSet().size(); i++){
            System.out.println((i+1) + ") " + getMoveSet().get(i).getMoveName());
         }
         System.out.println("0) Go to attack moves and ignore the attack.");
   }

   /* IMPORTANT FOR UNDERSTANDING caluculateDamage!
   possible directions include
   r = right
   l = left
   u = up
   d = down
   c = center
   a = all
   */


   /*
   * This function calculates the damage a player receives based on the attack move
   *  and defend move of each player.
   *
   * basic summation of the logic. 
   * If a player is dodging in the direction of 
   *  an attack, they take 1.5 times the damage. 
   * If a player is dodging in the opposite direction 
   *  of the attack, they take .5 timse the damage
   * left or right attacks hit everything but a downward 
   *  defending move such as duck, while up or center 
   *  attacks hit everything but a dodge to the
   *  left or right. 
   * Parring is reduces the stamina of the person 
   *  being parried, but does not work against a
   *  shield bash.
   * Blocking reduces the damage taken by the 
   *  damage reduction of the specific blocking
   *  move.
   */
   public float calculateDamage(AttackMove attack, Move defend, Fighter defender){
      switch(attack.getDirection()){
         case 'r':
            if (defend.getDirection() == 'r')
               return (attack.getDamage() * 1.5f);
            else if (defend.getDirection() == 'l')
               return (attack.getDamage() * .5f);
            else if (defend.getDirection() == 'd')
               return 0;
            else if (defend.getMoveName() == parry) {
               if (attack.getMoveName() != "shield bash")
               {
                  defender.reduceStamina(5f);
                  return(0);
               }
               else
               {
                  System.out.println("Parry did not work.");
                  return(attack.getDamage());
               }
            }  
            else if (defend.getDirection() == 'a')
               return(attack.getDamage() - defend.getDamageReduct());
            else
               return attack.getDamage();
         case 'l':
            if (defend.getDirection() == 'r')
               return (attack.getDamage() * .5f);
            else if (defend.getDirection() == 'l')
               return (attack.getDamage() * 1.5f);
            else if (defend.getDirection() == 'd')
               return 0;
            else if (defend.getMoveName() == parry) {
               if (attack.getMoveName() != "shield bash")
               {
                  defender.reduceStamina(5f);
                  return(0);
               }
               else
               {
                  System.out.println("Parry did not work.");
                  return(attack.getDamage());
               }
            }  
            else if (defend.getDirection() == 'a')
               return(attack.getDamage() - defend.getDamageReduct());
            else
               return attack.getDamage();
         case 'u':
            if (defend.getDirection() == 'l')
               return 0;
            else if (defend.getDirection() == 'r')
               return 0;
            else if (defend.getDirection() == 'd')
               return (attack.getDamage() * 1.5f);
            else if (defend.getMoveName() == parry) {
               if (attack.getMoveName() != "shield bash")
               {
                  defender.reduceStamina(5f);
                  return(0);
               }
               else
               {
                  System.out.println("Parry did not work.");
                  return(attack.getDamage());
               }
            }
            else if (defend.getDirection() == 'a')
               return(attack.getDamage() - defend.getDamageReduct());
            else
               return attack.getDamage();
         case 'd':
            if (defend.getDirection() == 'l')
               return 0;
            else if (defend.getDirection() == 'r')
               return 0;
            else if (defend.getDirection() == 'd')
               return (attack.getDamage());
            else if (defend.getMoveName() == parry) {
               if (attack.getMoveName() != "shield bash")
               {
                  defender.reduceStamina(5f);
                  return(0);
               }
               else
               {
                  System.out.println("Parry did not work.");
                  return(attack.getDamage());
               }
            }
            else if (defend.getDirection() == 'a')
               return(attack.getDamage() - defend.getDamageReduct());
            else 
               return attack.getDamage();
         case 'c':
            if (defend.getDirection() == 'l')
               return 0;
            else if (defend.getDirection() == 'r')
               return 0;
            else if (defend.getDirection() == 'd')
               return 0;
            else if (defend.getMoveName() == parry) {
               if (attack.getMoveName() != "shield bash")
               {
                  defender.reduceStamina(5f);
                  return(0);
               }
               else
               {
                  System.out.println("parry did not work.");
                  return(attack.getDamage());
               }
            }
            else if (defend.getDirection() == 'a')
               return((attack.getDamage()* 1.5f) - defend.getDamageReduct());
            else
               return attack.getDamage() * 1.5f;
         case 'a':
            if (defend.getDirection() == 'a' && defend.getMoveName() != parry)
               return (attack.getDamage() - defend.getDamageReduct());
            return attack.getDamage();
         case 'n':
            return 0;
         default:
            return 0;
      }
   }

   // This forces any classes that inherit Fighter to have a getChoice
   // function.
   public abstract int getChoice(int startLimit, int endLimit);
}

