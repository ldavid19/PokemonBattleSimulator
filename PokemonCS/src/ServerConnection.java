import java.io.*;
import java.net.*;

/*
 will be using a bridge metaphor throughout the code (in comments) to make understanding this easier on my puny mind
 
 */


public class ServerConnection extends Thread
{
  private Socket socket;//where land changes to bridge
  private BufferedReader in;//more accurate to call this incoming, incoming info
  private PrintWriter out;//more accurate to call this outgoing, outgoing info
  private Server server;//the server
  private Pokemon[] team;
  private int numTemp;
  private Move storedMove;
  private final int id = (int)(Math.random()*90000) + 10000;
  private int swapTarget;
  
  
  public ServerConnection(Socket socket, Server server) throws IOException
  {
    this.server = server;
    this.socket = socket;
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
    team = new Pokemon[6];
    numTemp = 0;
    storedMove = null;
    swapTarget = 0;//working on making swapping work
    
    start();
  }
  
//  public void sendGuess(String guess)
//  {
//    send("guess " + guess);
//  }
  
  public Pokemon[] getTeam()
  {
    return team;
  }
  
  public int getTeamLength()
  {
    return team.length;
  }
  
  public int getID()
  {
    return id;
  }
  
  public Pokemon getFirstPoke()
  {
    return team[0];
  }
  
  public void swap(int target)
  {
    Pokemon old = team[0];
    team[0] = team[target];
    team[target] = old;
  }
  
  public void send(String message)
  {
    System.out.println("sending:  " + message);
    out.println(message);
  }
  
  public Move getMove()
  {
    return storedMove;
  }
  
  public void clearMove()
  {
    storedMove = null;
  }
  
  public int getSwapTarget()
  {
    return swapTarget;
  }
  
  public void override()
  {
    storedMove = Move.moveList("—");
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
        //server.sendMessage(message, this);
        String[] tokens = message.split(" ");
        if(team.length <= 6 && tokens[0].equals("chose"))//for chosen initial pokemon
        {
          
          String pokeChosen = tokens[1];
          team[numTemp] = Pokemon.pokemonList(pokeChosen);
          numTemp++;
          
          if (numTemp == 6)
          {
            String temp = Server.update(id);
            String[] tokens2 = temp.split(" ");
            send(temp);
          }
        }
        else if(tokens[0].equals("move"))//for storing chosen move
        {
          String moveChosen = tokens[1];
          storedMove = Move.moveList(moveChosen);
          send("successfully chosen a move, please wait for the opponent to choose a move");
          Server.readyForBattle();
        }
        else if(tokens[0].equals("swapWith"))//for swapping pokemon
        {
          storedMove = new Move("swap", 1, 1, 1, 1, 1.0);//put an "if" in rFB to find the move name, if swap than swap
          swapTarget = Integer.parseInt(tokens[1]);
          Server.readyForBattle();
          /*
          int temp = 0;
          for(int i = 0; i < team.length; i++)
          {
            if(tokens[1].equals(team[i].getName()))
              temp = i;
          }
          Pokemon veryTemp = team[temp];
          if(!veryTemp.isFainted())
          {
            team[temp] = team[0];
            team[0] = veryTemp;
          }
          */
        }
        else if(tokens[0].equals("sendFaint"))
        {
          send("faintSwap " + tokens[1] + " " + tokens[2]);
        }
        else if(tokens[0].equals("newSacrifice"))
        {
          Pokemon temp = team[0];
          team[0] = team[Integer.parseInt(tokens[1])];
          team[Integer.parseInt(tokens[1])] = temp;
          Server.faintUpdate(id);/////////////////////////////////////////////////////////////////////////this line is new
        }
        else if(tokens[0].equals("battle") && storedMove != null)//for initiating the round
        {
          send("the battle message is actually used");
          Server.readyForBattle();
          //storedMove = null;////////////////////////////////////////////////////////////////////////this has can be deleted
          String temp = Server.update(id);
          send(temp);
        }
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}