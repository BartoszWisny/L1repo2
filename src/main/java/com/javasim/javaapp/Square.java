package com.javasim.javaapp;

import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Klasa Square zmienia kolor danego kwadratu i umozliwia zatrzymanie watku. 
 */

public class Square extends Thread implements MouseListener {
  public static Panel panel;
  public Label label;
  public Random random;
  public Square[][] squares;
  public int tmpi;
  public int tmpj;
  public int rows;
  public int columns;
  public int time;
  public double probability;
  public boolean active;

  /**
   * Konstruktor klasy Square generuje dany kwadrat z losowym kolorem.
   */
    
  public Square(Panel panel, Random random, Square[][] squares, int tmpi, int tmpj, int rows, 
      int columns, int time, double probability) {
    super();

    this.random = random;
    this.squares = squares;
    this.tmpi = tmpi;
    this.tmpj = tmpj;
    this.rows = rows;
    this.columns = columns;
    this.time = time;
    this.probability = probability;

    label = new Label();
    label.setBackground(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
    label.setSize(MyFrame.blockSize, MyFrame.blockSize);
    label.addMouseListener(this);
    panel.add(label);
    active = true;

    setDaemon(true);
  }

  /**
   * Konstruktor Kolor odpowiada za zmiane koloru kwadratu wewnatrz 
   * na srednia kolorow z kwadratow dookola do niego przylegajacych. 
   */  
  
  public Color averageColor(int tmp) {
    int red = 0;
    int green = 0;
    int blue = 0;

    int tmpx1 = ((tmpi - 1) % rows + rows) % rows;
    
    if (squares[tmpx1][tmpj].active) {
      red = red + squares[tmpx1][tmpj].label.getBackground().getRed();
      green = green + squares[tmpx1][tmpj].label.getBackground().getGreen();
      blue = blue + squares[tmpx1][tmpj].label.getBackground().getBlue();
    }
    
    int tmpx2 = ((tmpj + 1) % columns + columns) % columns;

    if (squares[tmpi][tmpx2].active) {
      red = red + squares[tmpi][tmpx2].label.getBackground().getRed();
      green = green + squares[tmpi][tmpx2].label.getBackground().getGreen();
      blue = blue + squares[tmpi][tmpx2].label.getBackground().getBlue();
    }
    
    int tmpx3 = ((tmpi + 1) % rows + rows) % rows;

    if (squares[tmpx3][tmpj].active) {
      red = red + squares[tmpx3][tmpj].label.getBackground().getRed();
      green = green + squares[tmpx3][tmpj].label.getBackground().getGreen();
      blue = blue + squares[tmpx3][tmpj].label.getBackground().getBlue();
    }
    
    int tmpx4 = ((tmpj - 1) % columns + columns) % columns;

    if (squares[tmpi][tmpx4].active) {
      red = red + squares[tmpi][tmpx4].label.getBackground().getRed();
      green = green + squares[tmpi][tmpx4].label.getBackground().getGreen();
      blue = blue + squares[tmpi][tmpx4].label.getBackground().getBlue();
    }

    red = red / tmp;
    green = green / tmp;
    blue = blue / tmp;

    return new Color(red, green, blue);
  }

  /**
   * Funkcja run odpowida za dzialanie watkow. 
   */
  
  public void run() {
    while (active) {
      try {
        int runtime;
        double test;
        int newcolorR;
        int newcolorG;
        int newcolorB;

        synchronized (random) {
          runtime = time / 2 + random.nextInt(time);
          test = random.nextDouble();
          newcolorR = random.nextInt(255);
          newcolorG = random.nextInt(255);
          newcolorB = random.nextInt(255);
        }

        synchronized (this) {
          wait(runtime);

          if (test < probability) {
            label.setBackground(new Color(newcolorR, newcolorG, newcolorB));
          } else {
            int tmp = 0;

            int tmpx1 = ((tmpi - 1) % rows + rows) % rows;
            
            if (squares[tmpx1][tmpj].active) {
              tmp++;
            }
            
            int tmpx2 = ((tmpj + 1) % columns + columns) % columns;
            
            if (squares[tmpi][tmpx2].active) {
              tmp++;
            }
            
            int tmpx3 = ((tmpi + 1) % rows + rows) % rows;

            if (squares[tmpx3][tmpj].active) {
              tmp++;
            } 
            
            int tmpx4 = ((tmpj - 1) % columns + columns) % columns;

            if (squares[tmpi][tmpx4].active) {
              tmp++;
            }
            
            if (tmp != 0) {
              label.setBackground(averageColor(tmp));
            }
          }
        }
      } catch (InterruptedException e) { 
        return;
      }
    }
  }
  
  public void mousePressed(MouseEvent event) {
    active = false;
    squares[tmpi][tmpj].interrupt();
  }

  public void mouseClicked(MouseEvent event) {}
  
  public void mouseReleased(MouseEvent event) {}
  
  public void mouseEntered(MouseEvent event) {}
  
  public void mouseExited(MouseEvent event) {}
}