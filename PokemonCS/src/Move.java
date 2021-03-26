public class Move
{
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
  
  private static final int PHYSICAL = 0;
  private static final int SPECIAL = 1;
  private static final int SELFSTATUS = 2;
  private static final int OPPSTATUS = 3;
  
  private String name;
  private int pp;
  private double acc;
  private int power;
  private int type;
  private int cat;
  private int currentPP;
  
  //Physical == 0
  //Special == 1
  //Status == 2
  
  public Move(String moveName, int pPoints, int moveCat, int moveType, int movePower, double accuracy)
  {
    name = moveName;
    pp = pPoints;
    type = moveType;
    acc = accuracy;
    power = movePower;
    cat = moveCat;
    currentPP = pPoints;
  }
  
  public int getCurrentPP()
  {
    return currentPP;
  }
  
  public void minusPP()
  {
    currentPP--;
  }
  
  public int getType()
  {
    return type;
  }
  
  public void setType(int moveType)
  {
    type = moveType;
  }
  
  public void setName(String str)
  {
    name = str;
  }
  
  public String getName()
  {
    return name;
  }
  
  public int getPower()
  {
    return power;
  }
  
  
  
  public void setPower(int movePower)
  {
    power = movePower;
  }
  
  public void setPowerPoints(int x)
  {
    if (x <= 56)
      pp = x;
    else
      System.out.println("Power points is maxed out");
  }
  
  public int getPowerPoints()
  {
    return pp;
  }
  
  public void setCat(int x)
  {
    cat = x;
  }
  
  public int getCat()
  {
    return cat;
  }
  
  public double getAcc()
  {
    return acc;
  }
  
  public void setAcc(double x)
  {
    acc = x;
  }
  
  
  
  /*
  Normal = 0
  Grass = 1
  Fire = 2
  Water = 3
  */
  
  // Move name, pp, move category, move type, move power, accuaracy 
  public static Move moveList(String moveName)
  {
    if(moveName.equals("Flamethrower"))
    {
      return new Move("Flamethrower", 15, SPECIAL, FIRE, 90, 1.0);
    }
    else if(moveName.equals("Scald"))
    {
      return new Move("Scald", 15, SPECIAL, WATER, 80, 1.0);
    }
    else if(moveName.equals("LeafBlade"))
    {
      return new Move("LeafBlade", 15, PHYSICAL, GRASS, 90, 1.0);
    }
    else if(moveName.equals("Strength"))
    {
      return new Move("Strength", 15, PHYSICAL, NORMAL, 80, 1.0);
    }
    else if(moveName.equals("HydroPump"))
    {
      return new Move("HydroPump", 5, SPECIAL, WATER, 110, 0.8);
    }
    else if(moveName.equals("—"))
    {
      return new Move("—", 0, 0, 0, 0 , 0);
    }
    else if(moveName.equals("Tackle"))
    {
      return new Move("Tackle", 35, PHYSICAL, NORMAL, 40, 1.0);
    }
    else if(moveName.equals("Splash"))
    {
      return new Move ("Splash", 40, SELFSTATUS, NORMAL, 0, 0);
    }
    else if(moveName.equals("Earthquake"))
    {
      return new Move ("Earthquake", 10, PHYSICAL, GROUND, 100, 1.0);
    }
    else if(moveName.equals("Psychic"))
    {
       return new Move ("Psychic", 10, SPECIAL, PSYCHIC, 90, 1.0);
    }
    else if(moveName.equals("Hyperbeam"))
    {
      return new Move ("Hyperbeam", 2, SPECIAL, NORMAL, 150, 0.9);
    }
    else if(moveName.equals("ShadowBall"))
    {
      return new Move ("ShadowBall", 15, SPECIAL, GHOST, 80, 1.0);
    }
    else if(moveName.equals("DreamEater"))
    {
      return new Move ("DreamEater", 5, SPECIAL, PSYCHIC, 100, 1.0);
    }
    else if(moveName.equals("Waterfall"))
    {
      return new Move ("Waterfall", 15, PHYSICAL, WATER, 80, 1.0);
    }
    else if(moveName.equals("Icebeam"))
    {
      return new Move ("Icebeam", 10, SPECIAL, ICE,  90, 1.0);
    }
    else if(moveName.equals("GigaImpact"))
    {
      return new Move ("GigaImpact", 2, PHYSICAL, NORMAL, 150, 0.9);
    }
    else if(moveName.equals("Surf"))
    {
      return new Move ("Surf", 15, SPECIAL, WATER, 90, 1.0);
    }
    else if(moveName.equals("Aquatail"))
    {
      return new Move ("Aquatail", 10, PHYSICAL, WATER, 90, 0.9);
    }
    else if(moveName.equals("Airslash"))
    {
      return new Move ("Airslash", 20, SPECIAL, FLYING, 75, 0.95);
    }
    else if(moveName.equals("Fireblast"))
    {
      return new Move("Fireblast", 5, SPECIAL, FIRE, 110, 0.85);
    }
    else if(moveName.equals("PetalDance"))
    {
      return new Move("PetalDance", 5, SPECIAL, GRASS, 110, 0.85);
    }
    else if(moveName.equals("ZenHeadbutt"))
    {
      return new Move("ZenHeadbutt", 15, PHYSICAL, PSYCHIC, 80, 0.9);
    }
    else if(moveName.equals("VoltTackle"))
    {
      return new Move("VoltTackle", 5, PHYSICAL, ELECTRIC, 110, 0.9);
    }
    else if(moveName.equals("Thunderbolt"))
    {
      return new Move("Thunderbolt", 15, SPECIAL, ELECTRIC, 90, 1.0);
    }
        else if(moveName.equals("Headbutt"))
    {
      return new Move("Headbutt", 15, PHYSICAL, NORMAL, 70, 1.0);
    }
    else
    {
      throw new RuntimeException("Invalid move: "+ moveName + "!");
    }

  }
  
  
}