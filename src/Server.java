import java.io.*;
import java.net.*;

public class Server
{
  
  public static void main(String[] args) throws IOException
  {
    Server s = new Server();
  }
  
  private static ServerConnection connection1;
  private static ServerConnection connection2;
  
  public Server() throws IOException
  {
    
    ServerSocket serverSocket = new ServerSocket(9629);
    Socket socket1 = serverSocket.accept();
    Socket socket2 = serverSocket.accept();
    
    connection1 = new ServerConnection(socket1, this);
    connection2 = new ServerConnection(socket2, this);
    
  }
  
  public static void send(String s, ServerConnection sender)
  {
    if(sender == connection1)
      connection2.send(s);
    else
      connection1.send(s);
  }
  
  public static boolean bothConnected()
  {
    Pokemon[] team1 = connection1.getTeam();
    Pokemon[] team2 = connection2.getTeam();
    if(team1[5] != null && team2[5] != null)
      return true;
    else
      return false;
  }
  
  public static void faintUpdate(int id)
  {
    if(connection1.getID() == id)
    {
      connection2.send(update(connection2.getID()));
    }
    else if(connection2.getID() == id)
    {
      connection1.send(update(connection1.getID()));
    }
  }
  
  public static String update(int id)
  {
    
    try{Thread.sleep(100);}catch(Exception e){}
    if(bothConnected())
    {
      if(connection1.getID() == id)
      {
        if(connection1.getFirstPoke().getCurrentHealth() <= 0)
        {
          connection1.override();
          return "faintSwap " + connection2.getFirstPoke().getName() + " " + connection2.getFirstPoke().getCurrentHealth() 
            + " " + connection1.getID();
        }
        else
        {
          return "update "+ connection2.getFirstPoke().getName() + " " + connection2.getFirstPoke().getCurrentHealth()
            + " " + connection1.getFirstPoke().getName()+ " " + connection1.getFirstPoke().getCurrentHealth()
            + " " + connection1.getID();
        }
      }
      else if(connection2.getID() == id)
      {
        if(connection2.getFirstPoke().getCurrentHealth() <= 0)
        {
          connection2.override();
          return "faintSwap " + connection1.getFirstPoke().getName() + " " + connection1.getFirstPoke().getCurrentHealth() 
            + " " + connection2.getID();
        }
        else
        {
          return "update "+ connection1.getFirstPoke().getName() + " " + connection1.getFirstPoke().getCurrentHealth()
            + " " + connection2.getFirstPoke().getName()+ " " + connection2.getFirstPoke().getCurrentHealth()
            + " " + connection2.getID();
        }
      }
      else
      {
        return "id error: could not find id";
      }
    }
    else
    {
      return "shouldn't reach here, this is in update";
    }
    
  }
  
  public static boolean bothStoredMoves()
  {
    return connection1.getMove() != null && connection2.getMove() != null;
  }
  
  public static void readyForBattle()//worry about accuracy and semirounds here
  {
    System.out.println("readyForBattle:  " + connection1.getMove() + ", " + connection2.getMove());
    if (connection1.getMove() != null && connection2.getMove() != null)
    {
      System.out.println("move: " + connection1.getMove().getName() + ", ID: " + connection1.getID());
      System.out.println("move: " + connection2.getMove().getName() + ", ID: " + connection2.getID());
      
      if(connection1.getMove().getName().equals("swap") || connection2.getMove().getName().equals("swap"))
      {
        if(connection1.getMove().getName().equals("swap") && connection2.getMove().getName().equals("swap"))
        {
          System.out.println("made it into the both swap instance");
          connection1.swap(connection1.getSwapTarget());
          connection2.swap(connection2.getSwapTarget());
        }
        else if(connection1.getMove().getName().equals("swap"))
        {
          System.out.println("made it into the connection1 swap instance");
          connection1.swap(connection1.getSwapTarget());
          Battle.semiRound(connection2.getFirstPoke(), connection1.getFirstPoke(), connection2.getMove());
        }
        else if(connection2.getMove().getName().equals("swap"))
        {
          System.out.println("made it into the connection2 swap instance");
          connection2.swap(connection2.getSwapTarget());
          Battle.semiRound(connection1.getFirstPoke(), connection2.getFirstPoke(), connection1.getMove());
        }
        connection1.clearMove();
        connection2.clearMove();
        send(update(connection1.getID()), connection2);//sending info 
        send(update(connection2.getID()), connection1);
        connection1.send("round just finished");
        connection2.send("round just finished");
      }
      else
      {
        if(connection1.getFirstPoke().getSpeed() > connection2.getFirstPoke().getSpeed())//connection1 has faster speed
        {
          System.out.println("first attack");
          Battle.semiRound(connection1.getFirstPoke(), connection2.getFirstPoke(), connection1.getMove());
          
          if(connection2.getFirstPoke().hasFainted())
          {
            connection2.send("sendFaint " + connection1.getFirstPoke() + " " + connection1.getFirstPoke().getCurrentHealth());
          }
          else
          {
            System.out.println("second attack");
            Battle.semiRound(connection2.getFirstPoke(), connection1.getFirstPoke(), connection2.getMove());
          }
        }
        
        else if(connection1.getFirstPoke().getSpeed() < connection2.getFirstPoke().getSpeed())//connection2 has faster speed
        {
          System.out.println("first attack");
          Battle.semiRound(connection2.getFirstPoke(), connection1.getFirstPoke(), connection2.getMove());
          
          if(connection1.getFirstPoke().hasFainted())
          {
            connection1.send("sendFaint " + connection2.getFirstPoke() + " " + connection2.getFirstPoke().getCurrentHealth());
          }
          else
          {
            System.out.println("second attack");
            Battle.semiRound(connection1.getFirstPoke(), connection2.getFirstPoke(), connection1.getMove());
          }
        }
        
        else if(connection1.getFirstPoke().getSpeed() == connection2.getFirstPoke().getSpeed())//if equal speeds
        {
          
          if((int)(Math.random()*2)+1  == 1)//connection1 lucks out speed
          {
            System.out.println("first attack");
            Battle.semiRound(connection1.getFirstPoke(), connection2.getFirstPoke(), connection1.getMove());
            
            if(connection2.getFirstPoke().hasFainted())
            {
              connection2.send("sendFaint " + connection1.getFirstPoke() + " " + connection1.getFirstPoke().getCurrentHealth());
            }
            else
            {
              System.out.println("second attack");
              Battle.semiRound(connection2.getFirstPoke(), connection1.getFirstPoke(), connection2.getMove());
            }
          }
          else//connection2 lucks out on speed
          {
            System.out.println("first attack");
            Battle.semiRound(connection2.getFirstPoke(), connection1.getFirstPoke(), connection2.getMove());
            
            if(connection1.getFirstPoke().hasFainted())
            {
              connection1.send("sendFaint " + connection2.getFirstPoke() + " " + connection2.getFirstPoke().getCurrentHealth());
            }
            else
            {
              System.out.println("second attack");
              Battle.semiRound(connection1.getFirstPoke(), connection2.getFirstPoke(), connection1.getMove());
            }
          }
          
        }
        connection1.clearMove();
        connection2.clearMove();
        send(update(connection1.getID()), connection2);//sending info 
        send(update(connection2.getID()), connection1);
        connection1.send("round just finished");
        connection2.send("round just finished");
      }
    }
    else
    {
      System.out.println("first move has been selected, nothing is supposed to happen");
    }
  }
  
}


