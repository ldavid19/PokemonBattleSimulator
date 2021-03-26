import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PokemonButton extends JButton
{
  private boolean selected;
  private ImageIcon buttonIcon;
  
  public boolean hasBeenSelected()
  {
    return selected;
  }
  
  
  public PokemonButton(String name)
  {
    setText(name);
    buttonIcon = new ImageIcon(getClass().getResource(name + "front.gif"));
    setIcon(buttonIcon);
    selected = false;
  }
  
  public ImageIcon getIcon()
  {
    return buttonIcon;
  }
  
  public void select(boolean s)
  {
    selected = s;
  }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (selected)
    {
      g.setColor(new Color(127, 127, 127, 127));
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }
}