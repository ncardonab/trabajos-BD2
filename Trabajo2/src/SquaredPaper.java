
/*Este ejemplo fue tomado de internet y fue modificado para acceder y
 pintar figuras desde la BD*/
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.math.*;

public class SquaredPaper extends JFrame {
  String ciudad;

  public SquaredPaper(String ciudad) {
    this.ciudad = ciudad;
  }

  public void paint(Graphics g) {
    Dimension d = getSize();
    int x = d.width;
    int y = d.height;

    g.setColor(Color.white);
    g.fillRect(0, 0, x, y);

    g.setColor(Color.blue);

    blic

  static void main(String args[]) {
    SquaredPaper DrawWindow = new SquaredPaper();

    DrawWindow.setSize(500,500);
    DrawWindow.setResizable(false);
    Dra wWindow.setLocation(200, 50);
      rawWindow.setitle("Pintando figuras almacenadas en la BD");
    DrawWin dw.setDefaulCloseOperation(JFrame.EXIT_ON_CLOSE);
    DrawWindow.setVisible(true);
  }
  
  