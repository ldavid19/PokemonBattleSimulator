public class Battle
{
  /*
  private Pokemon p1;
  private Pokemon p2;
  
  public Battle(Pokemon pl1, Pokemon pl2)
  {
    p1 = pl1;
    p2 = pl2;
  }
  */
  
  private static final int NORMAL = 0;
  private static final int GRASS = 1;
  private static final int FIRE = 2;
  private static final int WATER = 3;
  private static final int PSYCHIC = 4;
  private static final int GROUND = 5;
  private static final int ELECTRIC = 6;
  private static final int GHOST = 7;
  private static final int ICE = 8;
  private static final int FLYING = 9;
  
  
  public static int ePokePreBattle;
  public static int ePokePostBattle;
  
  public static void semiRound(Pokemon poke, Pokemon ePoke, Move move)//will have 2 semiRounds per round (for each move)
  {
    int pokeMoveCat = move.getCat();//edit the move class so that it seperates type and category, did this
    double accCheck = Math.random();
    
    int crit = (int)(Math.random() * 16);
    if(crit == 0)
    {
      crit = 3;
    }
    else
    {
      crit = 1;
    }
    
    double stab = 1.0;
    if(poke.getType1() == move.getType() || poke.getType2() == move.getType())
    {
      stab = 1.5;
    }
    
    if(accCheck < move.getAcc())
    {
      if(pokeMoveCat == 0)
      {
        double pokeAttack = poke.getAttack();
        double ePokeDefense = ePoke.getDefense();
        ePokePreBattle = ePoke.getCurrentHealth();
        ePoke.setCurrentHealth((int)(ePoke.getCurrentHealth() - ((((22)*(move.getPower())*(pokeAttack/ePokeDefense)/50)+2)
                                                        *(crit*typeBoost(move,ePoke)*stab))));
        ePokePostBattle = ePoke.getCurrentHealth();
      }
      else if(pokeMoveCat == 1)
      {
        double pokeSpAtk = poke.getSpAtk();
        double ePokeSpDef = ePoke.getSpDef();
        ePokePreBattle = ePoke.getCurrentHealth();
        ePoke.setCurrentHealth((int)(ePoke.getCurrentHealth() - ((((22)*(move.getPower())*(pokeSpAtk/ePokeSpDef)/50)+2)
                                                        *(crit*typeBoost(move,ePoke)*stab))));
        ePokePostBattle = ePoke.getCurrentHealth();
      }
      else if(pokeMoveCat == 2)
      {
        
      }
      else
      {
        
      }
    }
  }
  
  
  
  /*
  Normal = 0
  Grass = 1
  Fire = 2
  Water = 3
  */
  public static double typeBoost(Move moveX, Pokemon ePoke)//move damage boost against pokemon
  {
    int move = moveX.getType();
    int ePokeType1 = ePoke.getType1();
    int ePokeType2 = ePoke.getType2();
    if(move == GRASS && ePokeType1 == WATER || move == GRASS && ePokeType2 == WATER)//grass move boost
    {
      return 2.0;
    }
    else if(move == GRASS && ePokeType1 == FIRE || move == GRASS && ePokeType2 == FIRE)//grass move weakness
    {
      return 0.5;
    }
    else if(move == FIRE && ePokeType1 == GRASS || move == FIRE && ePokeType2 == GRASS)//fire move boost
    {
      return 2.0;
    }
    else if(move == FIRE && ePokeType1 == WATER || move == FIRE && ePokeType2 == WATER)//fire move weakness
    {
      return 0.5;
    }
    else if(move == FIRE && ePokeType1 == WATER || move == FIRE && ePokeType2 == WATER)//water move boost
    {
      return 2.0;
    }
    else if(move == WATER && ePokeType1 == GRASS || move == WATER && ePokeType2 == GRASS)//water move weakness
    {
      return 0.5;
    }
    else if(move == ELECTRIC && ePokeType1 == GROUND || move == ELECTRIC && ePokeType2 == GROUND)
    {
      return 0;
    }
    else if(move == ELECTRIC && ePokeType1 == WATER || move == ELECTRIC && ePokeType2 == WATER)
    {
      return 2.0;
    }
    else if(move == ELECTRIC && ePokeType1 == GRASS || move == ELECTRIC && ePokeType2 == GRASS)
    {
      return 0.5;
    }
    else if(move == GROUND && ePokeType1 == ELECTRIC || move == GROUND && ePokeType1 == ELECTRIC)
    {
      return 2.0;
    }
    else if(move == GROUND && ePokeType1 == GRASS || move == GROUND && ePokeType2 == GRASS)
    {
      return 0.5;
    }
    else if(move == GROUND && ePokeType1 == FIRE || move == GROUND && ePokeType2 == FIRE)
    {
      return 2.0;
    }
    else if(move == WATER && ePokeType1 == GROUND || move == WATER && ePokeType2 == GROUND)
    {
      return 2.0;
    }
    else if(move == PSYCHIC && ePokeType1 == PSYCHIC || move == PSYCHIC && ePokeType2 == PSYCHIC)
    {
      return 0.5;
    }
    else if(move == FIRE && ePokeType1 == FIRE || move == FIRE && ePokeType2 == FIRE)
    {
      return 0.5;
    }
    else if(move == WATER && ePokeType1 == WATER || move == WATER && ePokeType2 == WATER)
    {
      return 0.5;
    }
    else if(move == GHOST && ePokeType1 == PSYCHIC || move == GHOST && ePokeType2 == GHOST)
    {
      return 2.0;
    }
    else if(move == GHOST && ePokeType1 == NORMAL || move == GHOST && ePokeType2 == NORMAL)
    {
      return 0;
    }
    else if(move == ICE && ePokeType1 == GRASS || move == ICE && ePokeType2 == GRASS)
    {
      return 2.0;
    }
    else if(move == ICE && ePokeType1 == GROUND || move == ICE && ePokeType2 == GROUND)
    {
      return 2.0;
    }
    else if(move == ICE && ePokeType1 == FIRE || move == ICE && ePokeType2 == FIRE)
    {
      return 0.5;
    }
    else if(move == ICE && ePokeType1 == WATER || move == ICE && ePokeType2 == WATER)
    {
      return 0.5;
    }
    else if(move == FLYING && ePokeType1 == GRASS || move == FLYING && ePokeType2 == FLYING)
    {
      return 2.0;
    }
    else if(move == FLYING && ePokeType1 == ELECTRIC || move == FLYING && ePokeType2 == ELECTRIC)
    {
      return 0.5;
    }
    else
    {
      return 1;
    }
  }
  
  
  
}