package fighting;


import java.util.ArrayList; // import the ArrayList class
import java.util.List; //import the List class

/*
possible directions include
r = right
l = left
u = up
d = down
c = center
a = all
*/


public class Weapon {
   private String weaponType;
   private float baseDamage;
   private float baseStaminaCost;
   private ArrayList<Move> moveList = new ArrayList<>();
   private ArrayList<AttackMove> attackMoveList = new ArrayList<>();

   private static String parry = "parry";
   private static String block = "block with ";
   private static String sword = "sword";
   private static String spear = "spear";
   private static String axe = "axe";
   private static String shield = "shield";
   private static String rSwing = "Right Swing";
   private static String lSwing = "left Swing";
   private static String stab = "stab";
   private static String oSwing = "overhead swing";
   private static String spMove = "spin move";
   private static char right = 'r';
   private static char left = 'l';
   private static char center = 'c';
   private static char up = 'u';

   //getters
   public String getWeaponType() {return weaponType;}
   public float getBaseDamage() {return baseDamage;}
   public float getBaseStaminaCost() {return baseStaminaCost;}
   public List<AttackMove> getAttackMoveList() {return attackMoveList;}
   public List<Move> getMoveList() {return moveList;}

   public Weapon(char wType){
      switch (wType) {
         case 'w':
            // create a sword weapon
            setWeaponStats(sword, 15, 10);
            giveSwordMoveSet();
            break;
         case 'x':
            // create an axe weapon
            setWeaponStats(axe, 20, 15);
            giveAxeMoveSet();
            break;
         case 's':
            // create a spear weapon
            setWeaponStats(spear, 10, 5);
            giveSpearMoveSet();
            break;
         case 'W':
            // create a greatsword weapon
            setWeaponStats(sword, 25, 15);
            giveSwordMoveSet();
            break;
         case 'X':
            // create a greataxe weapon
            setWeaponStats(axe, 30, 20);
            giveAxeMoveSet();
            break;
         case 'S':
            // create a longspear weapon
            setWeaponStats(spear, 20, 10);
            giveSpearMoveSet();
            break;
         case 'h':
            // create a shield weapon
            setWeaponStats(shield, 10, 5);
            giveShieldMoveSet();
            break;
         default:
            // this shouldn't happen.     
            System.out.println("Sorry, we don't have that weapon.");   
      }
       
   }

   private void giveSwordMoveSet(){
      moveList.add(new Move(parry, 'a', baseStaminaCost + 5));
      moveList.add(new Move(block + "sword", 'a', 5.0f, 10.0f));
      attackMoveList.add(new AttackMove(rSwing, right, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(lSwing, left, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(stab, center, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(oSwing, up, baseStaminaCost*1.5f, baseDamage*1.5f));
      attackMoveList.add(new AttackMove(spMove, left, baseStaminaCost*2, baseDamage*2));
   }

   private void giveAxeMoveSet(){
      moveList.add(new Move(block + "axe", 'a', 5.0f, 10.0f));
      attackMoveList.add(new AttackMove(rSwing, right, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(lSwing, left, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(oSwing, up, baseStaminaCost*1.5f, baseDamage*1.5f));
      attackMoveList.add(new AttackMove(spMove, left, baseStaminaCost*2, baseDamage*2));
   }

   private void giveSpearMoveSet(){
      moveList.add(new Move(block + "spear", 'a', 5.0f, 10.0f));
      attackMoveList.add(new AttackMove(rSwing, right, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(lSwing, left, baseStaminaCost, baseDamage));
      attackMoveList.add(new AttackMove(stab, center, baseStaminaCost, baseDamage*1.5f));
      attackMoveList.add(new AttackMove(oSwing, up, baseStaminaCost*1.5f, baseDamage*1.5f));
      attackMoveList.add(new AttackMove(spMove, left, baseStaminaCost*2, baseDamage*4));
   }

   private void giveShieldMoveSet(){
      moveList.add(new Move(block + "shield", 'a', baseStaminaCost, 15.0f));
      attackMoveList.add(new AttackMove("shield bash", 'c', baseStaminaCost, baseDamage));
   }

   private void setWeaponStats(String wType, float bDamage, float bStaminaCost){
      weaponType = wType;
      baseDamage = bDamage;
      baseStaminaCost = bStaminaCost; 
   }
}

