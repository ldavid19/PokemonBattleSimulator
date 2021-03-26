import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwapButton extends JButton
{
  
  private Pokemon pokemon;
  private boolean fainted;
  private Image hpBar;

  public SwapButton(Pokemon pok)
  {    
    hpBar = new ImageIcon(getClass().getResource("hpbar.png")).getImage();
    pokemon = pok;
    setText(pokemon.getName());
    ImageIcon buttonIcon = new ImageIcon(getClass().getResource(pokemon.getName() + "front.gif"));
    setIcon(buttonIcon);
    if (pok.isFainted())
      fainted = true;
    else
      fainted = false;
    setPreferredSize(new Dimension(170, 100));
    
      
  }
  
  
  public int healthWidth()
  {
    if (pokemon.getCurrentHealth()/pokemon.getHP() == 1)
    {
      return 77;
    }
    else
    {
      int targetHealth;
      double healthWidth;
      healthWidth = (double)((double)(pokemon.getCurrentHealth())/pokemon.getHP());
      return (int)(healthWidth * 77);
    }
  }
  
  public Color healthColor()
  {
    int healthValue = healthWidth();
    if (healthValue >= 38)
      return Color.GREEN;
    else if (healthValue < 38 && healthValue > 15)
      return Color.YELLOW;
    else
      return Color.RED;
  }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(hpBar, 7, 42, 110, 110, null);
    g.setColor(healthColor());
    g.fillRect(33,94, healthWidth(), 5);
    
    g.setColor(Color.BLACK);
    g.drawString(pokemon.getCurrentHealth()+"/"+pokemon.getHP(),117, 100);
    
    if (fainted == true)
    {
      g.setColor(new Color(255, 0, 0, 127));
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }
  
 public boolean isFainted()
 {
   return fainted;
 }

}