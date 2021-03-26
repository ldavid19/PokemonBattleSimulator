import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class SelectionScreen implements ActionListener
{
  private PokemonButton[][] pokemons;
  private ArrayList<JLabel> selectedPokemon;
  private JPanel currentlySelected;
  public int numSelected = 0;
  private JFrame frame;
  private ArrayList<String> pokNames;
  private String ip = "10.13.103.105";
  
  public SelectionScreen()
  {
    frame = new JFrame();
    frame.setTitle("Selection Screen");
    
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
    
    currentlySelected = new JPanel(new GridLayout(1,6));
    selectedPokemon = new ArrayList<JLabel>();
    for (int i = 0; i < 6; i++)
    {
      JLabel label = new JLabel();
      label.setIcon(new ImageIcon(getClass().getResource("questionmark.gif")));
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setPreferredSize(new Dimension(96, 96));
      selectedPokemon.add(label);
      currentlySelected.add(selectedPokemon.get(i));
    } 
    frame.getContentPane().add(currentlySelected);
    
    
    JPanel buttonPanel = new JPanel(new GridLayout(3, 5));
    buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    frame.getContentPane().add(buttonPanel);
    
    String[][] pokemonNames = {
      {"Furret", "Charizard", "Sceptile", "Blastoise", "Snorlax"},
      {"Rapidash", "Meganium", "Espeon", "Magikarp", "Gyarados"},
      {"Pikachu", "Dugtrio", "Mr.Mime", "Pichu", "Sandslash"}};
    
    pokemons = new PokemonButton[3][5];
    for (int x = 0; x < 3; x++)
     {
       for (int y = 0; y < 5; y++)
       {
         
         PokemonButton button = new PokemonButton(pokemonNames[x][y]);
         pokemons[x][y] = button;
         button.addActionListener(this);
         buttonPanel.add(button);
       }
     }
     
     JPanel bottomPanel = new JPanel(new BorderLayout());
     frame.getContentPane().add(bottomPanel);
     
     JLabel label = new JLabel(" Choose your Pokemon!");
     label.setFont(new Font(null, Font.PLAIN, 19));
     //label.setAlignmentX(Component.CENTER_ALIGNMENT);
     bottomPanel.add(label, BorderLayout.LINE_START);
     
     JButton finishedButton = new JButton("Done Selecting");
     finishedButton.setActionCommand("Done");
     finishedButton.addActionListener(this);
     bottomPanel.add(finishedButton, BorderLayout.LINE_END);
     
     pokNames = new ArrayList<String>();
     
    frame.pack();
    frame.setVisible(true);cd 
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Done"))
    {
      if (numSelected < 6)
      {
        JOptionPane.showMessageDialog(frame, "Please select "+(6-numSelected)+" more Pokémon!");
      }
      else
      {
        String[] names = new String[6];
        for(int i = 0; i < 6; i++)
        {
          names[i] = pokNames.get(i);
        }
        
        frame.setVisible(false);
        
        try
        {
          ClientConnection client = new ClientConnection(ip, names);
          
          while(ClientConnection.bothConnected == false)
          {
            
            try
            {
            Thread.sleep(100);
            }
            catch (Exception oof)
            {
            }
          }
          GUI gooey = new GUI(client);
          
          client.setGUI(gooey);
        }
        catch(IOException ex)
        {
          ex.printStackTrace();
          throw new RuntimeException("should never get here");
        }
        
        
        //AT THIS POINT MUST CLOSE THIS WINDOW, CREATE GUI WINDOW, AND CREATE CLIENTCONNECTION WITH THIS ARRAY OF STRINGS
        
      }
    }
    else
    {
    PokemonButton button = (PokemonButton)e.getSource();
    //System.out.println(button.getText());
    
    //update whether button is selected
    if (!button.hasBeenSelected())
      button.select(true);
    else
      button.select(false);
    
    if(button.hasBeenSelected())
    {
      numSelected++;
      if (numSelected <= 6)
      {
        if (numSelected == 1)
        {
          selectedPokemon.get(0).setIcon(button.getIcon());
          pokNames.add(button.getText());
        }
        if (numSelected == 2)
        {
          selectedPokemon.get(1).setIcon(button.getIcon());
          pokNames.add(button.getText());
        }
        if (numSelected == 3)
        {
          selectedPokemon.get(2).setIcon(button.getIcon());
          pokNames.add(button.getText());
        }
        if (numSelected == 4)
        {
          selectedPokemon.get(3).setIcon(button.getIcon());
          pokNames.add(button.getText());
        }
        if (numSelected == 5)
        {
          selectedPokemon.get(4).setIcon(button.getIcon());
          pokNames.add(button.getText());
        }
        if (numSelected == 6)
        {
          selectedPokemon.get(5).setIcon(button.getIcon());
          pokNames.add(button.getText());
        }
      }
      else
      {
        button.select(false);
        numSelected--;
        JOptionPane.showMessageDialog(frame, "You may have no more than 6 Pokémon at a time in your party!");
      }
    }
    else
    {
     numSelected--;
     
     for (int i = 0; i < 6; i++)
       selectedPokemon.get(i).setIcon(new ImageIcon(getClass().getResource("questionmark.gif")));
     int numReAdded = 0;
     pokNames = new ArrayList<String>();
     for (int x = 0; x < 3; x++)
     {
       for (int y = 0; y < 5; y++)
       {
        if (pokemons[x][y].hasBeenSelected())
        {
          selectedPokemon.get(numReAdded).setIcon(pokemons[x][y].getIcon());
          pokNames.add(pokemons[x][y].getText());
          numReAdded++;
        }
       }
     }

    }
    }
  }
  
  public String[] getNames()
  {
      String[] names = new String[6];
      for(int i = 0; i < 6; i++)
      {
        names[i] = pokNames.get(i);
      }
      return names;
  }
  
  public static void main(String[] args)
  {
    new SelectionScreen();
  }
  
}