import java.awt.*;
import java.awt.event.*;  //for ActionListener, ActionEvent
import javax.swing.*;  //for JFrame, BoxLayout, JLabel, JTextField, JButton


public class GUI extends JComponent implements ActionListener, Runnable
{
  private static final String BUTTON_PANEL = "button";
  private static final String TEXT_PANEL = "text";
  
  private static JFrame frame;
  private Image battleScreen;
  private JPanel buttonPanel;
  private JPanel textPanel;
  private JPanel cardPanel;
  private CardLayout cardLayout;
    
  public static Pokemon playerPok;
  private Pokemon oppPok;
  
  private Image oppPokImage;
  private static Image playerPokImage;
  private Image hpBar;
  private static ClientConnection client;
  public static Pokemon[] playerPokemons;
  public static Pokemon swappingTo;
  public static int swapIndex;
  private long startTime;
  private Image trainer;
  private Image pokeball;
  private double pokeballx;
  private double pokebally;
  private double pokeballvy;
  private boolean ballOpen;
  private boolean eraseBall;
  private int trainerLoc;
  
  private JButton move1;
  private JButton move2;
  private JButton move3;
  private JButton move4;
  private long elapsed;
  
  private boolean startingMessageDrawn = false;
  
  
//  public static void main(String[] args)
//  {
//    new GUI();
//  }
//  
  
 public GUI(ClientConnection klient)
 // public GUI()
  {
    client = klient;
    
    startTime = System.currentTimeMillis();
    
     battleScreen = new ImageIcon(getClass().getResource("background.png")).getImage();
     hpBar = new ImageIcon(getClass().getResource("hpbar.png")).getImage();
     
     trainer = new ImageIcon(getClass().getResource("throw1.png")).getImage();
     pokeball = new ImageIcon(getClass().getResource("ball5.png")).getImage();
     ballOpen = false;
     eraseBall = false;
     trainerLoc = 30;
     pokeballx = 165;
     pokebally = 240;
     pokeballvy = -9.66;
    
     setPokemon();
     
//     oppPok = Pokemon.pokemonList("Sandslash");
//     oppPokImage = oppPok.getFrontSprite();
//     
//     playerPokemons = new Pokemon[6];
//     playerPokemons[0] = Pokemon.pokemonList("Sandslash");
//     playerPokemons[1] = Pokemon.pokemonList("Magikarp");
//     playerPokemons[2] = Pokemon.pokemonList("Espeon");
//     playerPokemons[3] = Pokemon.pokemonList("Furret");
//     playerPokemons[4] = Pokemon.pokemonList("Sceptile");
//     playerPokemons[5] = Pokemon.pokemonList("Snorlax");
//     
//     playerPok = playerPokemons[0];
//     playerPokImage = playerPok.getBackSprite();
     
     
     
//make a window
    frame = new JFrame();
    frame.setTitle("Digimon");
    
//tell window to place each new component under the previous ones
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
    
    //add main image
    setPreferredSize(new Dimension(600, 338));
    frame.getContentPane().add(this);

    //button panel
    buttonPanel = new JPanel(new GridLayout(2, 3));
    
    move1 = new JButton(playerPok.getMove1().getName() + " " +playerPok.getMove1().getCurrentPP() + "/"+ playerPok.getMove1().getPowerPoints());
    move1.setActionCommand("Move1");
    buttonPanel.add(move1);
    move2 = new JButton(playerPok.getMove2().getName() + " " +playerPok.getMove2().getCurrentPP() + "/"+ playerPok.getMove2().getPowerPoints());
    move2.setActionCommand("Move2");
    buttonPanel.add(move2);
    JButton moveInfo = new JButton("Move Info");
    buttonPanel.add(moveInfo);   
    moveInfo.setActionCommand("MoveInfo");
    move3 = new JButton(playerPok.getMove3().getName()+ " " +playerPok.getMove3().getCurrentPP() + "/"+ playerPok.getMove3().getPowerPoints());
    buttonPanel.add(move3);  
    move3.setActionCommand("Move3");
    move4 = new JButton(playerPok.getMove4().getName() + " " +playerPok.getMove4().getCurrentPP() + "/"+ playerPok.getMove4().getPowerPoints());
    buttonPanel.add(move4);    
    move4.setActionCommand("Move4");
    JButton pokemon = new JButton("Pokemon");
    buttonPanel.add(pokemon);    
    pokemon.setActionCommand("Pokemon");
    
    move1.addActionListener(this);
    move2.addActionListener(this); 
    moveInfo.addActionListener(this);    
    move3.addActionListener(this);
    move4.addActionListener(this);  
    pokemon.addActionListener(this);        
    
    
    
    //text panel
    textPanel = new JPanel(new GridLayout(1, 1));
    textPanel.setBackground(Color.WHITE);
    
    //add card panel
    cardPanel = new JPanel(new CardLayout());
    cardPanel.add(buttonPanel, BUTTON_PANEL);
    cardPanel.add(textPanel, TEXT_PANEL);
    frame.getContentPane().add(cardPanel);
    
   cardLayout = (CardLayout)cardPanel.getLayout();
   cardLayout.show(cardPanel, TEXT_PANEL);
    
    frame.pack();  //determine best size for window
    frame.setVisible(true);  //show the window
    
    new Thread(this).start();
  }
  
  public void setPokemon()
  {
    playerPokemons = client.getPokemon();
    
    playerPok = playerPokemons[0];
    
    playerPokImage = playerPok.getBackSprite();
    
    oppPok = ClientConnection.getActiveEnemy();
    
    oppPokImage = oppPok.getFrontSprite();
      
      
    
    
  }
  
  //paints battle part of screen
  public void paintComponent(Graphics g)
  {
    
    elapsed = System.currentTimeMillis() - startTime;  //in milliseconds
    
        g.drawImage(battleScreen, 0, 0, null);
        showInfoOpp(g, 163, 90);
        
        g.drawImage(trainer, trainerLoc, 178, null);
        
        if (elapsed > 4000 && elapsed < 4150)
        {
          g.drawImage(battleScreen, 0, 0, null);
          showInfoOpp(g, 163, 90);
          trainer = new ImageIcon(getClass().getResource("throw2.png")).getImage();
          g.drawImage(trainer, trainerLoc, 178, null);
          //trainerLoc = trainerLoc - 3;
          //pokeballx = pokeballx - 3;
        }
        
        
        
        if (elapsed > 4150 && elapsed < 4310)
        {
          
          g.drawImage(battleScreen, 0, 0, null);
          showInfoOpp(g, 163, 90);
          trainer = new ImageIcon(getClass().getResource("throw3.png")).getImage();
          g.drawImage(trainer, trainerLoc, 178, null);
          g.drawImage(pokeball, (int)pokeballx, (int)pokebally, null);
         // trainerLoc = trainerLoc - 5;
          
        }
        
        
        if (elapsed > 4310)
        {
          g.drawImage(battleScreen, 0, 0, null);
          showInfoOpp(g, 163, 90);
          trainer = new ImageIcon(getClass().getResource("throw4.png")).getImage();
          g.drawImage(trainer, trainerLoc, 178, null);
          if (eraseBall == false)
          g.drawImage(pokeball, (int)pokeballx, (int)pokebally, null);
          
       // trainerLoc = trainerLoc - 6;
        }
        
        showInfoPlayer(g, 470, 250);
        //x, y, width, height
        if (oppPok.getName().equals("Furret"))
        g.drawImage(oppPokImage, 360, 90, 133, 133, null);
        else if (oppPok.getName().equals("Charizard"))
          g.drawImage(oppPokImage, 377, 65, 133, 133, null);
        else if (oppPok.getName().equals("Sceptile"))
          g.drawImage(oppPokImage, 367, 80, 133, 133, null);
        else if (oppPok.getName().equals("Blastoise"))
          g.drawImage(oppPokImage, 367, 85, 133, 133, null);   
        else if (oppPok.getName().equals("Snorlax"))
          g.drawImage(oppPokImage, 362, 85, 133, 133, null);
        else if (oppPok.getName().equals("Rapidash"))
          g.drawImage(oppPokImage, 369, 80, 133, 133, null);
        else if (oppPok.getName().equals("Meganium"))
          g.drawImage(oppPokImage, 365, 77, 133, 133, null);
        else if (oppPok.getName().equals("Espeon"))
          g.drawImage(oppPokImage, 374, 88, 133, 133, null);
        else if (oppPok.getName().equals("Magikarp"))
          g.drawImage(oppPok.getBackSprite(), 370, 88, 133, 133, null);
        else if (oppPok.getName().equals("Gyarados"))
          g.drawImage(oppPokImage, 377, 65, 133, 133, null);
        else if (oppPok.getName().equals("Pikachu"))
          g.drawImage(oppPokImage, 379, 94, 133, 133, null);
        else if (oppPok.getName().equals("Dugtrio"))
          g.drawImage(oppPokImage, 371, 99, 133, 133, null);
        else if (oppPok.getName().equals("Mr.Mime"))
          g.drawImage(oppPokImage, 370, 86, 133, 133, null);
        else if (oppPok.getName().equals("Pichu"))
          g.drawImage(oppPokImage, 370, 94, 133, 133, null);
        else if (oppPok.getName().equals("Sandslash"))
          g.drawImage(oppPokImage, 367, 89, 133, 133, null);
         
        if (ballOpen == true && eraseBall == true)
        {
          if(!playerPok.isFainted())
          {
        if (playerPok.getName().equals("Furret"))
          g.drawImage(playerPokImage, 125, 180,230, 230, null);
        else if (playerPok.getName().equals("Charizard"))
          g.drawImage(playerPokImage, 113, 180, 230, 230, null);
        else if (playerPok.getName().equals("Sceptile"))
          g.drawImage(playerPokImage, 120, 180, 230, 230, null);
        else if (playerPok.getName().equals("Blastoise"))
          g.drawImage(playerPokImage, 120, 180, 230, 230, null);
        else if (playerPok.getName().equals("Snorlax"))
          g.drawImage(playerPokImage, 120, 147, 260, 260, null);
        else if (playerPok.getName().equals("Rapidash"))
          g.drawImage(playerPokImage, 115, 180, 230, 230, null);
        else if (playerPok.getName().equals("Meganium"))
          g.drawImage(playerPokImage, 135, 170, 220, 220, null);
        else if (playerPok.getName().equals("Espeon"))
          g.drawImage(playerPokImage, 115, 163, 230, 230, null);
        else if (playerPok.getName().equals("Magikarp"))
          g.drawImage(playerPok.getFrontSprite(), 115, 163, 230, 230, null);
        else if (playerPok.getName().equals("Gyarados"))
          g.drawImage(playerPokImage, 113, 180, 230, 230, null);
        else if (playerPok.getName().equals("Pikachu"))
          g.drawImage(playerPokImage, 110, 174, 230, 230, null);
        else if (playerPok.getName().equals("Dugtrio"))
          g.drawImage(playerPokImage, 116, 186, 230, 230, null);
        else if (playerPok.getName().equals("Mr.Mime"))
          g.drawImage(playerPokImage, 112, 172, 260, 260, null);
        else if (playerPok.getName().equals("Pichu"))
          g.drawImage(playerPokImage, 106, 170, 230, 230, null);
        else if (playerPok.getName().equals("Sandslash"))
          g.drawImage(playerPokImage, 126, 180, 230, 230, null);
        }
        }
  }
  
  public void showInfoOpp(Graphics g, int x, int y)
  {
    g.setColor(new Color (255, 253, 208));
    g.fillRect(x, y, 120, 27);  //x (left), y (top), width, height
    g.drawImage(hpBar, 168, 57, 110, 110, null);
    
    g.setColor(opponentHealthColor());
    g.fillRect(x+31, y+19, opponentHealth(), 5);

    g.setColor(Color.BLACK);
    g.drawString(oppPok.getName(), x + 5, y+15);  //text, x (left), y (bottom)
  }
  
 
  
  public void showInfoPlayer(Graphics g, int x, int y)
  {
    //Info box
    g.setColor(new Color (255, 253, 208));
    g.fillRect(x, y, 120, 45);  //x (left), y (top), width, height
    g.drawImage(hpBar,x+5,y-33,110,110,null);
    
    //Health Bar
    //playerPok.setCurrentHealth(30);
    g.setColor(playerHealthColor());    
    g.fillRect(x+31, y+19,playerHealth(), 5);
    
    //Name and Numerical HP Value
    g.setColor(Color.BLACK);
    g.drawString(playerPok.getName(), x + 7, y+15);  //text, x (left), y (bottom)
    g.drawString(playerPok.getCurrentHealth()+"/"+playerPok.getHP(),x+62, y+40);
  }
  
  public int playerHealth()
  {
    if (playerPok.getCurrentHealth()/playerPok.getHP() == 1)
    {
      return 77;
    }
    else
    {
      int targetHealth;
      double healthWidth;
      healthWidth = (double)((double)(playerPok.getCurrentHealth())/playerPok.getHP());
      return (int)(healthWidth * 77);
    }
  }
  
  public Color playerHealthColor()
  {
    int healthValue = playerHealth();
    if (healthValue >= 38)
      return Color.GREEN;
    else if (healthValue < 38 && healthValue > 15)
      return Color.YELLOW;
    else
      return Color.RED;
  }
  
  public int opponentHealth()
  {
    if (oppPok.getCurrentHealth()/oppPok.getHP() == 1)
    {
      return 77;
    }
    else
    {
      int targetHealth;
      double healthWidth;
      healthWidth = (double)((double)(oppPok.getCurrentHealth())/oppPok.getHP());
      return (int)(healthWidth * 77);
    }    
  }
  
  public Color opponentHealthColor()
  {
    int healthValue = opponentHealth();
    if (healthValue >= 38)
      return Color.GREEN;
    else if (healthValue < 38 && healthValue > 15)
      return Color.YELLOW;
    else
      return Color.RED;    
  }
  
  public static void swapPokemon()
  {
    playerPokemons = client.getPokemon();
    
    playerPok = playerPokemons[0];
    
    playerPokImage = playerPok.getBackSprite();
  }
  
  public static void faintSwap()
  {
    playerPok.fainted();
    playerPok.setCurrentHealth(0);
    JOptionPane.showMessageDialog(frame, "Oh no! " + playerPok.getName() + " fainted!");
    new SwapPokemon(frame);
    ClientConnection.faintSwap(swapIndex);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Move1"))
    {
      //CardLayout cardLayout = (CardLayout)cardPanel.getLayout();
     // cardLayout.show(cardPanel, TEXT_PANEL);
      if(playerPok.getMove1().getCurrentPP() > 0)
      {
      playerPok.getMove1().minusPP();
      ClientConnection.setSelectedMove(playerPok.getMove1().getName());
      }
      else
      {
       JOptionPane.showMessageDialog(frame, playerPok.getName() + " doesn't have enough Power Points to use " + playerPok.getMove1().getName() + "!");
      }
    }
    else if (e.getActionCommand().equals("Move2"))
    {
      //CardLayout cardLayout = (CardLayout)cardPanel.getLayout();
     // cardLayout.show(cardPanel, TEXT_PANEL);
      if(playerPok.getMove2().getCurrentPP() > 0)
      {
      playerPok.getMove2().minusPP();
      ClientConnection.setSelectedMove(playerPok.getMove2().getName());
      }
      else
      {
        JOptionPane.showMessageDialog(frame, playerPok.getName() + " doesn't have enough Power Points to use " + playerPok.getMove2().getName() + "!");
      }
    }
    else if (e.getActionCommand().equals("Move3"))
    {
      //CardLayout cardLayout = (CardLayout)cardPanel.getLayout();
     // cardLayout.show(cardPanel, TEXT_PANEL);
      if(playerPok.getMove3().getCurrentPP() > 0)
      {
      playerPok.getMove3().minusPP();
      ClientConnection.setSelectedMove(playerPok.getMove3().getName());
      }
      else
      {
        JOptionPane.showMessageDialog(frame, playerPok.getName() + " doesn't have enough Power Points to use " + playerPok.getMove3().getName() + "!");
      }
    }
    else if (e.getActionCommand().equals("Move4"))
    {
      //CardLayout cardLayout = (CardLayout)cardPanel.getLayout();
     // cardLayout.show(cardPanel, TEXT_PANEL);
      if(playerPok.getMove4().getCurrentPP() > 0)
      {
      playerPok.getMove4().minusPP();
      ClientConnection.setSelectedMove(playerPok.getMove4().getName());
      }
      else
      {
        JOptionPane.showMessageDialog(frame, playerPok.getName() + " doesn't have enough Power Points to use " + playerPok.getMove4().getName() + "!");
      }
    }
    else if (e.getActionCommand().equals("Pokemon"))
    {
      new SwapPokemon(frame);
      ClientConnection.swapping(swapIndex);
     // playerPokImage = playerPokemons[0].getBackSprite();
     // playerPok = playerPokemons[0];
    }
    
  }
  
  public void run()
  {
    long landed = 0;
    while (true)
    {
      revalidate();
      repaint();
      try{Thread.sleep(110);}catch(Exception e){}
            
      if (!ballOpen)
      {
      if (elapsed > 4210 && elapsed < 4305)
         pokeball = new ImageIcon(getClass().getResource("ball3.png")).getImage();
      else if (elapsed > 4305 && elapsed < 4400)
        pokeball = new ImageIcon(getClass().getResource("ball6.png")).getImage();
      else if (elapsed > 4400 && elapsed < 4495)
        pokeball = new ImageIcon(getClass().getResource("ball5.png")).getImage();
      else if (elapsed > 4495 && elapsed < 4590)
        pokeball = new ImageIcon(getClass().getResource("ball7.png")).getImage();
      else if (elapsed > 4590 && elapsed < 4685)
        pokeball = new ImageIcon(getClass().getResource("ball8.png")).getImage();
      else if (elapsed > 4685 && elapsed < 4780)
        pokeball = new ImageIcon(getClass().getResource("ball2.png")).getImage();
      else if (elapsed > 4780 && elapsed < 4875)
        pokeball = new ImageIcon(getClass().getResource("ball10.png")).getImage();
      else if (elapsed > 4875 && elapsed < 4970)
        pokeball = new ImageIcon(getClass().getResource("ball6.png")).getImage();
      else if (elapsed > 4970 && elapsed < 5065)
        pokeball = new ImageIcon(getClass().getResource("ball4.png")).getImage();
      else if (elapsed > 5065 && elapsed < 5160)
        pokeball = new ImageIcon(getClass().getResource("ball1.png")).getImage();
      else if (elapsed > 5160 && elapsed < 5255)
        pokeball = new ImageIcon(getClass().getResource("ball12.png")).getImage();
      else if (elapsed > 5255 && elapsed < 5350)
        pokeball = new ImageIcon(getClass().getResource("ball7.png")).getImage();
      else if (elapsed > 5350 && elapsed < 5445)
        pokeball = new ImageIcon(getClass().getResource("ball5.png")).getImage();
      else if (elapsed > 5445 && elapsed < 5540)
        pokeball = new ImageIcon(getClass().getResource("ball11.png")).getImage();
      else if (elapsed > 5540)
        pokeball = new ImageIcon(getClass().getResource("ball8.png")).getImage();
      }
      
      
      if(elapsed > 4120) //Pokeball position
      {
          if (pokeballx <= 255 && pokebally <= 300)
          {
          pokeballx = pokeballx + 5.256;
          pokebally = pokebally + pokeballvy;
          pokeballvy = pokeballvy + 2.5;
          }
          else
          {
            if((System.currentTimeMillis() - landed) > 750 && landed != 0)
            {
              pokeball = new ImageIcon(getClass().getResource("ball9.png")).getImage();  //open ball
              ballOpen = true;
              cardLayout.show(cardPanel, BUTTON_PANEL);
              if(System.currentTimeMillis() - landed > 980)
                eraseBall = true;
            }
            else if((System.currentTimeMillis() - landed) < 750 && landed > 0)
            {
              pokeball = new ImageIcon(getClass().getResource("ball8.png")).getImage();
            }
            else
            {
              landed = System.currentTimeMillis();
              pokeball = new ImageIcon(getClass().getResource("ball8.png")).getImage();  //closed ball
            }
          }
      }           
       
        if (elapsed > 4000 && elapsed < 4150)
        {
          if(startingMessageDrawn == false)
          {
         JLabel startingMessage = new JLabel(" Go " + playerPok.getName() + "!", SwingConstants.LEADING);
         startingMessage.setVerticalAlignment(SwingConstants.TOP);
         startingMessage.setFont(new Font(null, Font.PLAIN, 15));
         textPanel.add(startingMessage);
         startingMessageDrawn = true;
          }
          trainerLoc = trainerLoc - 4;
          pokeballx = pokeballx - 4;
        }
        
        if (elapsed > 4150 && elapsed < 4290)
        {
          
          trainerLoc = trainerLoc - 8;
          
        }        
        
        if (elapsed > 4290)
        {
        
          
        trainerLoc = trainerLoc - 10;
        }        
        
        if(playerPok.getCurrentHealth() <= 0)
        {
          playerPok.fainted();
          playerPok.setCurrentHealth(0);
          //new SwapPokemon(frame);
        }
        
        if(oppPok.getCurrentHealth() <= 0)
        {
          oppPok.fainted();
          oppPok.setCurrentHealth(0);
        }
        
        oppPok = ClientConnection.getActiveEnemy();
        oppPokImage = oppPok.getFrontSprite();
        
        move1.setText(playerPok.getMove1().getName() + " " +playerPok.getMove1().getCurrentPP() + "/"+ playerPok.getMove1().getPowerPoints());
        move2.setText(playerPok.getMove2().getName()+ " " +playerPok.getMove2().getCurrentPP() + "/"+ playerPok.getMove2().getPowerPoints());
        move3.setText(playerPok.getMove3().getName()+ " " +playerPok.getMove3().getCurrentPP() + "/"+ playerPok.getMove3().getPowerPoints());
        move4.setText(playerPok.getMove4().getName()+ " " +playerPok.getMove4().getCurrentPP() + "/"+ playerPok.getMove4().getPowerPoints());
    }
    
  }
}