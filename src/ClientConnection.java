import java.awt.List;
import java.io.*;
import java.net.*;

/*
 will be using a bridge metaphor throughout the code (in comments) to make understanding this easier on my puny mind
 
 */


public class ClientConnection extends Thread
{ 
  private Socket socket;//where land changes to bridge
  private BufferedReader in;//more accurate to call this incoming, incoming info
  private static PrintWriter out;//more accurate to call this outgoing, outgoing info
  private static Pokemon[] team;
  private static Pokemon activeEnemy;
  private static String[] list;
  private GUI gui;
  public static boolean bothConnected = false;
  private static String selectedMove;
  private static boolean swapping = false;
  private static int index;
  //one day will need to keep a reference to Ryan's GUI/display thing
  
  
  public static void main(String[] args) throws IOException
  {
    list = new String[] {"Charizard", "Charizard", "Charizard", "Charizard", "Charizard", "Charizard"};
    new ClientConnection("10.13.103.53", list);
  }
  
  
  public ClientConnection(String ipAddress, String[] pokeNames) throws IOException
  {
    socket = new Socket(ipAddress, 9629);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
    team = new Pokemon[6];
    
    
    for (int i = 0; i < 6; i++)
    {
      team[i] = Pokemon.pokemonList(pokeNames[i]);
    }
    
    for (int i = 0; i < pokeNames.length; i++)
    {
      send("chose " + pokeNames[i]);
    }
    
    
    //construct pokemon objects from pokeNames
    //store those pokemon objects in instance var (array of Pokemon)
    //ask james how he wants you to send that info to him
    //
    start();
  }
  
  
  public Pokemon[] getPokemon()
  {
    return team;
  }
  
  public static void setSelectedMove(String m)
  {
    selectedMove = m;
    send("move " + selectedMove);
  }
  
  public static void swapping(int i)
  {
    index = i;
    send("swapWith " + i);
    swapping = true;
  }
  
  public static void faintSwap(int i)
  {
    index = i;
    Pokemon temp = team[0];
    team[0] = team[index];
    team[index] = temp;
    send("newSacrifice " + i);
    GUI.swapPokemon();
  }
  
  private static void send(String message)
  {
    System.out.println("sending:  " + message);
    out.println(message);
  }
  
  public void setGUI(GUI gooey)
  {
    gui = gooey;
  }
  
  public static Pokemon getActiveEnemy()
  {
    return activeEnemy;
  }
  
  //override's Thread's run method
  public void run()
  {
    try
    {
      while (true)
      {
        String message = in.readLine();
        System.out.println("received:  " + message);
        String[] tokens = message.split(" ");
        if(tokens[0].equals("update"))
        {
          bothConnected = true;
          activeEnemy = Pokemon.pokemonList(tokens[1]);
          activeEnemy.setCurrentHealth(Integer.parseInt(tokens[2]));
          if (!tokens[3].equals(team[0].getName()))
          {
            Pokemon temp = team[0];
            team[0] = team[index];
            team[index] = temp;
            GUI.swapPokemon();
          }
          team[0].setCurrentHealth(Integer.parseInt(tokens[4])); //I changed this from team
        }
        else if(tokens[0].equals("faintSwap"))
        {
          GUI.faintSwap();
          activeEnemy = Pokemon.pokemonList(tokens[1]);
          activeEnemy.setCurrentHealth(Integer.parseInt(tokens[2]));
        }
        else if(tokens[0].equals("fail"))
        {
          
        }
        
//        if (tokens[0].equals("good"))
//        {
//          display.goodGuess(tokens[1]);
//        }
//        else if (tokens[0].equals("bad"))
//        {
//          display.badGuess(tokens[1]);
//        }
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
//  public Pokemon[] makePokemon()
//  {
//   Pokemon[] pokemons = new Pokemon[6];
//   for (int i = 0; i < 6; i++)
//   {
//    pokemons[i] = Pokemon.pokemonList(pokeNames[i]);
//   }
//   return pokemons;
//  }
  
  
  
}