import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class SwapPokemon extends JDialog implements ActionListener
{
  private SwapButton pokemon2;
  private SwapButton pokemon3;
  private SwapButton pokemon4;
  private SwapButton pokemon5;
  private SwapButton pokemon6;
  
  public SwapPokemon(JFrame frame)
  {
    super(frame, true);
    setTitle("Party");
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    this.setResizable(false);
    
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));

    SwapButton currentPok = new SwapButton(GUI.playerPokemons[0]);
     currentPok.setActionCommand("Current");
     currentPok.addActionListener(this);
    getContentPane().add(currentPok);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(5, 1));
    getContentPane().add(buttonPanel);
    
    pokemon2 = new SwapButton(GUI.playerPokemons[1]);
    pokemon2.setPreferredSize(new Dimension(170, 108));
    buttonPanel.add(pokemon2);
    pokemon2.setActionCommand("2");
    pokemon2.addActionListener(this);
    
    pokemon3 = new SwapButton(GUI.playerPokemons[2]);
    buttonPanel.add(pokemon3);
    pokemon3.setActionCommand("3");
    pokemon3.addActionListener(this);    
    
    pokemon4 = new SwapButton(GUI.playerPokemons[3]);
    buttonPanel.add(pokemon4);
    pokemon4.setActionCommand("4");
    pokemon4.addActionListener(this);    
    
    pokemon5 = new SwapButton(GUI.playerPokemons[4]);
    buttonPanel.add(pokemon5);
    pokemon5.setActionCommand("5");
    pokemon5.addActionListener(this);
    
    pokemon6 = new SwapButton(GUI.playerPokemons[5]);
    buttonPanel.add(pokemon6);
    pokemon6.setActionCommand("6");
    pokemon6.addActionListener(this);    
    
    pack();
    setVisible(true);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    Pokemon storage;
    if (e.getActionCommand().equals("Current"))
    {
      JOptionPane.showMessageDialog(this, "This Pokémon is already out!");
    }
    else if (e.getActionCommand().equals("2"))
    {
      if (pokemon2.isFainted())
      {
        JOptionPane.showMessageDialog(this, "This Pokémon can no longer fight!");
      }
      else
      {
      int option;
      option = JOptionPane.showConfirmDialog(this,"Are you sure you want to switch to "+ GUI.playerPokemons[1].getName() + "?", "Test", JOptionPane.YES_NO_OPTION);
      if (option == JOptionPane.YES_OPTION)
      {
      //storage = GUI.playerPokemons[0];
      //GUI.playerPokemons[0] = GUI.playerPokemons[1];
      //GUI.playerPokemons[1] = storage;
      GUI.swappingTo = GUI.playerPokemons[1];
      GUI.swapIndex = 1;
      setVisible(false);
      }
      else
      {
      }
      }
    }
    else if(e.getActionCommand().equals("3"))
    {
      if (pokemon3.isFainted())
      {
        JOptionPane.showMessageDialog(this, "This Pokémon can no longer fight!");
      }
      else
      {
      int option;
      option = JOptionPane.showConfirmDialog(this,"Are you sure you want to switch to "+ GUI.playerPokemons[2].getName() + "?", "Test", JOptionPane.YES_NO_OPTION);
      if (option == JOptionPane.YES_OPTION)
      {
      //storage = GUI.playerPokemons[0];
      //GUI.playerPokemons[0] = GUI.playerPokemons[2];
      //GUI.playerPokemons[2] = storage;
       GUI.swappingTo = GUI.playerPokemons[2];
       GUI.swapIndex = 2;
      setVisible(false);
      }
      else
      {
      }
      }
    }
    else if(e.getActionCommand().equals("4"))
    {
      if (pokemon4.isFainted())
      {
        JOptionPane.showMessageDialog(this, "This Pokémon can no longer fight!");
      }
      else
      {
      int option;
      option = JOptionPane.showConfirmDialog(this,"Are you sure you want to switch to "+ GUI.playerPokemons[3].getName() + "?", "Test", JOptionPane.YES_NO_OPTION);
      if (option == JOptionPane.YES_OPTION)
      {
      //storage = GUI.playerPokemons[0];
      //GUI.playerPokemons[0] = GUI.playerPokemons[3];
      //GUI.playerPokemons[3] = storage;
        GUI.swappingTo = GUI.playerPokemons[3];
        GUI.swapIndex = 3;
      setVisible(false);
      }
      else
      {
      }     
      }
    }
    else if(e.getActionCommand().equals("5"))
    {
      if (pokemon5.isFainted())
      {
        JOptionPane.showMessageDialog(this, "This Pokémon can no longer fight!");
      }
      else
      {
      int option;
      option = JOptionPane.showConfirmDialog(this,"Are you sure you want to switch to "+ GUI.playerPokemons[4].getName() + "?", "Test", JOptionPane.YES_NO_OPTION);
      if (option == JOptionPane.YES_OPTION)
      {
      //storage = GUI.playerPokemons[0];
      //GUI.playerPokemons[0] = GUI.playerPokemons[4];
      //GUI.playerPokemons[4] = storage;
        GUI.swappingTo = GUI.playerPokemons[4];
        GUI.swapIndex = 4;
      setVisible(false);
      }
      else
      {
      }    
      }
    }
    else if(e.getActionCommand().equals("6"))
    {
      if (pokemon6.isFainted())
      {
        JOptionPane.showMessageDialog(this, "This Pokémon can no longer fight!");
      }
      else
      {
      int option;
      option = JOptionPane.showConfirmDialog(this,"Are you sure you want to switch to "+ GUI.playerPokemons[5].getName() + "?", "Test", JOptionPane.YES_NO_OPTION);
      if (option == JOptionPane.YES_OPTION)
      {
       //storage = GUI.playerPokemons[0];
      //GUI.playerPokemons[0] = GUI.playerPokemons[5];
      //GUI.playerPokemons[5] = storage;
        GUI.swappingTo = GUI.playerPokemons[5];
        GUI.swapIndex = 5;
      setVisible(false);
      }
      else
      {
      }     
      }
    }
    else
    {
    setVisible(false);
    }
  }
}